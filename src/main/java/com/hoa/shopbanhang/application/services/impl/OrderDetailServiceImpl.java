package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderDetailInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderDetailInput;
import com.hoa.shopbanhang.application.repositories.IOrderDetailRepository;
import com.hoa.shopbanhang.application.services.IOrderDetailService;
import com.hoa.shopbanhang.domain.entities.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
  private final IOrderDetailRepository categoryRepository;
  private final ModelMapper modelMapper;

  public OrderDetailServiceImpl(IOrderDetailRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<OrderDetail> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public OrderDetail getOrderDetailById(Long id) {
    Optional<OrderDetail> oldOrderDetail = categoryRepository.findById(id);
    checkOrderDetailExists(oldOrderDetail, id);

    return oldOrderDetail.get();
  }

  @Override
  public OrderDetail createOrderDetail(CreateOrderDetailInput createOrderDetailInput) {
    OrderDetail newOrderDetail = modelMapper.map(createOrderDetailInput, OrderDetail.class);

    return categoryRepository.save(newOrderDetail);
  }

  @Override
  public OrderDetail updateOrderDetail(UpdateOrderDetailInput updateOrderDetailInput) {
    Optional<OrderDetail> oldOrderDetail = categoryRepository.findById(updateOrderDetailInput.getId());
    checkOrderDetailExists(oldOrderDetail, updateOrderDetailInput.getId());

    modelMapper.map(updateOrderDetailInput, oldOrderDetail.get());

    return categoryRepository.save(oldOrderDetail.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<OrderDetail> oldOrderDetail = categoryRepository.findById(id);
    checkOrderDetailExists(oldOrderDetail, id);

    oldOrderDetail.get().setDeleteFlag(true);
    categoryRepository.save(oldOrderDetail.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkOrderDetailExists(Optional<OrderDetail> OrderDetail, Long id) {

  }
}
