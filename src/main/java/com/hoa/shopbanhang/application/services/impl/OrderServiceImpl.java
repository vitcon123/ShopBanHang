package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderInput;
import com.hoa.shopbanhang.application.repositories.IOrderRepository;
import com.hoa.shopbanhang.application.services.IOrderService;
import com.hoa.shopbanhang.domain.entities.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
  private final IOrderRepository categoryRepository;
  private final ModelMapper modelMapper;

  public OrderServiceImpl(IOrderRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Order> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Order getOrderById(Long id) {
    Optional<Order> oldOrder = categoryRepository.findById(id);
    checkOrderExists(oldOrder, id);

    return oldOrder.get();
  }

  @Override
  public Order createOrder(CreateOrderInput createOrderInput) {
    Order newOrder = modelMapper.map(createOrderInput, Order.class);

    return categoryRepository.save(newOrder);
  }

  @Override
  public Order updateOrder(UpdateOrderInput updateOrderInput) {
    Optional<Order> oldOrder = categoryRepository.findById(updateOrderInput.getId());
    checkOrderExists(oldOrder, updateOrderInput.getId());

    modelMapper.map(updateOrderInput, oldOrder.get());

    return categoryRepository.save(oldOrder.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Order> oldOrder = categoryRepository.findById(id);
    checkOrderExists(oldOrder, id);

    oldOrder.get().setDeleteFlag(true);
    categoryRepository.save(oldOrder.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkOrderExists(Optional<Order> Order, Long id) {

  }
}
