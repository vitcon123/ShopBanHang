package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.OrderDetailOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DeliveryStatus;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.constants.PaymentMethod;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.application.repositories.IItemDetailRepository;
import com.hoa.shopbanhang.application.repositories.IOrderRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IOrderService;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.ItemDetail;
import com.hoa.shopbanhang.domain.entities.Order;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
  private final IOrderRepository orderRepository;
  private final IItemDetailRepository itemDetailRepository;
  private final IUserRepository userRepository;
  private final IProductService productService;
  private final ModelMapper modelMapper;

  public OrderServiceImpl(IOrderRepository orderRepository, IItemDetailRepository itemDetailRepository, IUserRepository userRepository, IProductService productService, ModelMapper modelMapper) {
    this.orderRepository = orderRepository;
    this.itemDetailRepository = itemDetailRepository;
    this.userRepository = userRepository;
    this.productService = productService;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public OrderDetailOutput getOrderById(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);

    OrderDetailOutput output = new OrderDetailOutput(order.get(), order.get().getItemDetails());
    return output;
  }

  @Override
  public List<Order> getOrderByUser(Long idUser) {
    Optional<User> user = userRepository.findById(idUser);
    UserServiceImpl.checkUserExists(user);
    return user.get().getOrders();
  }


  @Override
  public OrderDetailOutput createOrder(CreateOrderInput createOrderInput) {
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
      }
    }

    OrderDetailOutput output = new OrderDetailOutput(newOrder, itemDetailRepository.getAllByOrder(newOrder));

    return output;
  }

  @Override
  public RequestResponse setOrderOrderPlaced(Long idOrder) {
    Optional<Order> order = orderRepository.findById(idOrder);
    checkOrderExists(order);
    order.get().setDeliveryStatus(DeliveryStatus.ORDER_PLACED);

//    Reduce the number of products when ordering
    for (ItemDetail itemDetail : order.get().getItemDetails()) {
      productService.updateStockProduct(itemDetail.getProduct().getId(), itemDetail.getAmount(), true);
    }
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

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Order> oldOrder = orderRepository.findById(id);
    checkOrderExists(oldOrder);

    oldOrder.get().setDeleteFlag(true);
    orderRepository.save(oldOrder.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkOrderExists(Optional<Order> order) {
    if (order.isEmpty()) {
      throw new VsException(MessageConstant.ORDER_NOT_EXISTS);
    }
  }
}
