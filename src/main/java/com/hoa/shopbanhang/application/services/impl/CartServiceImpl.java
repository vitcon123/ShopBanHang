package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.cart.CreateCartInput;
import com.hoa.shopbanhang.application.inputs.cart.UpdateCartInput;
import com.hoa.shopbanhang.application.repositories.ICartRepository;
import com.hoa.shopbanhang.application.services.ICartService;
import com.hoa.shopbanhang.domain.entities.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
  private final ICartRepository categoryRepository;
  private final ModelMapper modelMapper;

  public CartServiceImpl(ICartRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Cart> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Cart getCartById(Long id) {
    Optional<Cart> oldCart = categoryRepository.findById(id);
    checkCartExists(oldCart, id);

    return oldCart.get();
  }

  @Override
  public Cart createCart(CreateCartInput createCartInput) {
    Cart newCart = modelMapper.map(createCartInput, Cart.class);

    return categoryRepository.save(newCart);
  }

  @Override
  public Cart updateCart(UpdateCartInput updateCartInput) {
    Optional<Cart> oldCart = categoryRepository.findById(updateCartInput.getId());
    checkCartExists(oldCart, updateCartInput.getId());

    return categoryRepository.save(oldCart.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Cart> oldCart = categoryRepository.findById(id);
    checkCartExists(oldCart, id);

    oldCart.get().setDeleteFlag(true);
    categoryRepository.save(oldCart.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkCartExists(Optional<Cart> Cart, Long id) {

  }
}
