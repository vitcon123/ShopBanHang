package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.cart_detail.CreateCartDetailInput;
import com.hoa.shopbanhang.application.inputs.cart_detail.UpdateCartDetailInput;
import com.hoa.shopbanhang.application.repositories.ICartDetailRepository;
import com.hoa.shopbanhang.application.services.ICartDetailService;
import com.hoa.shopbanhang.domain.entities.CartDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements ICartDetailService {
  private final ICartDetailRepository cartDetailRepository;
  private final ModelMapper modelMapper;

  public CartDetailServiceImpl(ICartDetailRepository cartDetailRepository, ModelMapper modelMapper) {
    this.cartDetailRepository = cartDetailRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<CartDetail> getAll() {
    return cartDetailRepository.findAll();
  }

  @Override
  public CartDetail getCartDetailById(Long id) {
    Optional<CartDetail> oldCartDetail = cartDetailRepository.findById(id);
    checkCartDetailExists(oldCartDetail, id);

    return oldCartDetail.get();
  }

  @Override
  public CartDetail createCartDetail(CreateCartDetailInput createCartDetailInput) {
    CartDetail newCartDetail = modelMapper.map(createCartDetailInput, CartDetail.class);

    return cartDetailRepository.save(newCartDetail);
  }

  @Override
  public CartDetail updateCartDetail(UpdateCartDetailInput updateCartDetailInput) {
    Optional<CartDetail> oldCartDetail = cartDetailRepository.findById(updateCartDetailInput.getId());
    checkCartDetailExists(oldCartDetail, updateCartDetailInput.getId());

    modelMapper.map(updateCartDetailInput, oldCartDetail.get());

    return cartDetailRepository.save(oldCartDetail.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<CartDetail> oldCartDetail = cartDetailRepository.findById(id);
    checkCartDetailExists(oldCartDetail, id);

    oldCartDetail.get().setDeleteFlag(true);
    cartDetailRepository.save(oldCartDetail.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkCartDetailExists(Optional<CartDetail> CartDetail, Long id) {

  }
}
