package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.CartDetailOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.repositories.ICartRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.ICartService;
import com.hoa.shopbanhang.application.services.IItemDetailService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
  private final ICartRepository cartRepository;
  private final IItemDetailService itemDetailService;
  private final IUserRepository userRepository;
  private final ModelMapper modelMapper;

  public CartServiceImpl(ICartRepository cartRepository, IItemDetailService itemDetailService,
                         IUserRepository userRepository, ModelMapper modelMapper) {
    this.cartRepository = cartRepository;
    this.itemDetailService = itemDetailService;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Cart> getAll() {
    return cartRepository.findAll();
  }

  @Override
  public CartDetailOutput getCartById(Long id) {
    Optional<Cart> cart = cartRepository.findById(id);
    checkCartExists(cart);

    CartDetailOutput output = new CartDetailOutput(cart.get(), cart.get().getItemDetails());
    return output;
  }

  @Override
  public CartDetailOutput getCartByIdUser(Long idUser) {
    Cart cart = cartRepository.findCartByUser(idUser);
    if (cart == null) {
      throw new VsException(MessageConstant.CART_NOT_EXISTS);
    }
    CartDetailOutput output = new CartDetailOutput(cart, cart.getItemDetails());
    return output;
  }

  @Override
  public Cart createCart(Long idUser) {
    Optional<User> user = userRepository.findById(idUser);
    UserServiceImpl.checkUserExists(user, idUser);
    Cart cart = new Cart();
    cart.setUser(user.get());
    return cartRepository.save(cart);
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Cart> cart = cartRepository.findById(id);
    checkCartExists(cart);

    cart.get().setDeleteFlag(true);
    cartRepository.save(cart.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkCartExists(Optional<Cart> cart) {
    if (cart.isEmpty()) {
      throw new VsException(MessageConstant.CART_NOT_EXISTS);
    }
  }
}
