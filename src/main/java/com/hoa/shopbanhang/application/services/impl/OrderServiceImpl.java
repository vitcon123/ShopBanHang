package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.OrderDetailOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ProductOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.*;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.application.inputs.order.FilterOrderInput;
import com.hoa.shopbanhang.application.outputs.common.PagingMeta;
import com.hoa.shopbanhang.application.outputs.order.GetListOrderOutput;
import com.hoa.shopbanhang.application.outputs.product.GetListProductOutput;
import com.hoa.shopbanhang.application.repositories.IItemDetailRepository;
import com.hoa.shopbanhang.application.repositories.IOrderRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IOrderService;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.application.utils.SendMailUtil;
import com.hoa.shopbanhang.application.utils.UrlUtil;
import com.hoa.shopbanhang.configs.exceptions.NotFoundException;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.ItemDetail;
import com.hoa.shopbanhang.domain.entities.Order;
import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
  private final IOrderRepository orderRepository;
  private final IItemDetailRepository itemDetailRepository;
  private final IUserRepository userRepository;
  private final IProductService productService;
  private final ModelMapper modelMapper;
  private final HttpServletRequest request;


  public OrderServiceImpl(IOrderRepository orderRepository, IItemDetailRepository itemDetailRepository, IUserRepository userRepository, IProductService productService, ModelMapper modelMapper, HttpServletRequest request) {
    this.orderRepository = orderRepository;
    this.itemDetailRepository = itemDetailRepository;
    this.userRepository = userRepository;
    this.productService = productService;
    this.modelMapper = modelMapper;
    this.request = request;
  }

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public GetListOrderOutput filterOrders(FilterOrderInput filterOrderInput, Integer page, Integer size) {
    List<Order> orders = orderRepository.findOrders(filterOrderInput, null);
    Integer total = orders.size();
    PagingMeta pagingMeta;

    if (page != null && size > 0) {
      orders = orderRepository.findOrders(filterOrderInput, PageRequest.of(page.intValue(), size));
      pagingMeta = new PagingMeta(total, page, size);
    } else {
      pagingMeta = new PagingMeta(total, null, null);
    }

    return new GetListOrderOutput(orders, pagingMeta);
  }

  @Override
  public Order getOrderById(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);

    return order.get();
  }

  @Override
  public List<Order> getOrderByUser(Long idUser) {
    Optional<User> user = userRepository.findById(idUser);
    UserServiceImpl.checkUserExists(user);

    return user.get().getOrders();
  }


  @Override
  public RequestResponse createOrder(CreateOrderInput createOrderInput) {
    Optional<User> user = userRepository.findById(createOrderInput.getIdUser());
    UserServiceImpl.checkUserExists(user);
    Order order = modelMapper.map(createOrderInput, Order.class);
    order.setUser(user.get());
    order.setDeliveryStatus(DeliveryStatus.ORDER_PLACED);
    for (PaymentMethod paymentMethod : PaymentMethod.values()) {
      if(createOrderInput.getPaymentMethod().equals(paymentMethod)) {
        order.setPaymentMethod(paymentMethod);
      }
    }
    order.setDeliveredDate(createOrderInput.getDeliveredDate());
    Order newOrder = orderRepository.save(order);

    if(createOrderInput.getIdItemDetails() != null) {
      for (Long idItemDetail : createOrderInput.getIdItemDetails()) {
        Optional<ItemDetail> itemDetail = itemDetailRepository.findById(idItemDetail);
        ItemDetailServiceImpl.checkItemDetailExists(itemDetail);
        itemDetail.get().setOrder(newOrder);
        itemDetail.get().setCart(null);
        itemDetailRepository.save(itemDetail.get());
        productService.updateStockProduct(itemDetail.get().getProduct().getId(), itemDetail.get().getAmount(), true);
      }
    }

    String url =
        UrlUtil.applicationUrl(request)
            + "/order/" + newOrder.getId();

    String contentOrder =
        "Bạn đã đặt hàng thành công"
            + ".\n\nXem chi tiết đơn hàng: " + url
            + ".\n\nCảm ơn vì đã xử dụng dịch vụ của chúng tôi.";

    try {
      SendMailUtil.sendMailSimple(user.get().getEmail(), contentOrder, EmailConstant.SUBJECT_ORDERED);
    } catch (Exception e) {
      throw new NotFoundException(EmailConstant.SEND_FAILED);
    }

    return new RequestResponse(CommonConstant.TRUE, "Order Successfully");
  }

  @Override
  public RequestResponse cancelOrder(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);


    if(order.get().getDeliveryStatus().compareTo(DeliveryStatus.ORDER_PLACED) != 0) {
      throw new VsException(MessageConstant.CANNOT_CANCEL_ORDER);
    }
    for (ItemDetail itemDetail : order.get().getItemDetails()) {
      productService.updateStockProduct(itemDetail.getProduct().getId(), itemDetail.getAmount(), false);
    }
    orderRepository.delete(order.get());
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse setOrderOrderPlaced(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);
    order.get().setDeliveryStatus(DeliveryStatus.ORDER_PLACED);

    orderRepository.save(order.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse setOrderPreparingToShip(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);
    order.get().setDeliveryStatus(DeliveryStatus.PREPARING_TO_SHIP);
    orderRepository.save(order.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse setOrderInTransit(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);
    order.get().setDeliveryStatus(DeliveryStatus.IN_TRANSIT);
    orderRepository.save(order.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse setOrderDelivered(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);
    order.get().setDeliveryStatus(DeliveryStatus.DELIVERED);
    orderRepository.save(order.get());

    String url =
        UrlUtil.applicationUrl(request)
            + "/order/" + order.get().getId();

    String contentOrder =
        "Đã giao hàng thành công đơn hàng: " + order.get().getId()
            + ".\n\nXem chi tiết đơn hàng: " + url
            + ".\n\nCảm ơn vì đã xử dụng dịch vụ của chúng tôi.";

    try {
      SendMailUtil.sendMailSimple(order.get().getUser().getEmail(), contentOrder, EmailConstant.SUBJECT_DELIVERED);
    } catch (Exception e) {
      throw new NotFoundException(EmailConstant.SEND_FAILED);
    }

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkOrderExists(Optional<Order> order) {
    if (order.isEmpty()) {
      throw new VsException(MessageConstant.ORDER_NOT_EXISTS);
    }
  }
}
