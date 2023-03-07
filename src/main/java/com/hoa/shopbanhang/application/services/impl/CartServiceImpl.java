package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.repositories.ICartRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.ICartDetailService;
import com.hoa.shopbanhang.application.services.ICartService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.CartDetail;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
  private final ICartRepository cartRepository;
  private final ICartDetailService cartDetailService;
  private final IUserRepository userRepository;
  private final ModelMapper modelMapper;

  public CartServiceImpl(ICartRepository cartRepository, ICartDetailService cartDetailService, IUserRepository userRepository, ModelMapper modelMapper) {
    this.cartRepository = cartRepository;
    this.cartDetailService = cartDetailService;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Cart> getAll() {
    return cartRepository.findAll();
  }

  @Override
  public Cart getCartById(Long id) {
    Optional<Cart> oldCart = cartRepository.findById(id);
    checkCartExists(oldCart, id);

    return oldCart.get();
  }

  @Override
  public List<CartDetail> getCartByIdUser(Long idUser) {
    Cart cart = cartRepository.findCartByUser(idUser);
    if (cart == null) {
      throw new VsException(UserMessageConstant.Cart.ERR_NOT_FOUND_BY_ID);
    }
    return cartDetailService.getAllCartDetailInCart(cart.getId());
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
    Optional<Cart> oldCart = cartRepository.findById(id);
    checkCartExists(oldCart, id);

    oldCart.get().setDeleteFlag(true);
    cartRepository.save(oldCart.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkCartExists(Optional<Cart> cart, Long id) {
    if(cart.isEmpty()) {
      throw new VsException(UserMessageConstant.Cart.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Cart.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }
}
