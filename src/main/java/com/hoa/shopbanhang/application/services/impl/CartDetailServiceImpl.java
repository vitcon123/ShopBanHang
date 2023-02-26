package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.repositories.ICartDetailRepository;
import com.hoa.shopbanhang.application.repositories.ICartRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.services.ICartDetailService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.CartDetail;
import com.hoa.shopbanhang.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements ICartDetailService {
  private final ICartDetailRepository cartDetailRepository;
  private final ICartRepository cartRepository;
  private final IProductRepository productRepository;
  private final ModelMapper modelMapper;

  public CartDetailServiceImpl(ICartDetailRepository cartDetailRepository, ICartRepository cartRepository, IProductRepository productRepository, ModelMapper modelMapper) {
    this.cartDetailRepository = cartDetailRepository;
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<CartDetail> getAllCartDetailInCart(Long idCart) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart, idCart);
    List<CartDetail> cartDetails = cartDetailRepository.getAllByCart(cart.get());
    return cartDetails;
  }

  @Override
  public void addProductToCartById(Long idCart, Long idProduct, Long amount) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart, idCart);

    Optional<Product> product = productRepository.findById(idProduct);
    ProductServiceImpl.checkProductExists(product, idProduct);

    List<CartDetail> productAdded = cart.get().getCartDetails();

    boolean isExist = false;
    for(CartDetail cartDetail : productAdded) {
      if(cartDetail.getProduct().equals(product.get())) {
        isExist = true;
        cartDetail.setAmount(cartDetail.getAmount() + amount);
        cartDetailRepository.save(cartDetail);
        break;
      }
    }
    if(!isExist) {
      CartDetail cartDetail = new CartDetail();
      cartDetail.setCart(cart.get());
      cartDetail.setProduct(product.get());
      cartDetail.setAmount(amount);
      cartDetailRepository.save(cartDetail);
    }
  }

  @Override
  public void editCartDetailById(Long idCartDetail, Long amount) {
    Optional<CartDetail> cartDetail = cartDetailRepository.findById(idCartDetail);
    checkCartDetailExists(cartDetail, idCartDetail);

    cartDetail.get().setAmount(amount);
    cartDetailRepository.save(cartDetail.get());
  }

  @Override
  public void deleteCartDetailById(Long idCartDetail) {
    Optional<CartDetail> cartDetail = cartDetailRepository.findById(idCartDetail);
    checkCartDetailExists(cartDetail, idCartDetail);
    cartDetailRepository.delete(cartDetail.get());
  }

  @Override
  public void deleteAllCartDetailInCart(Long idCart) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart, idCart);
    cartDetailRepository.deleteAllByCart(cart.get());
  }

  private void checkCartDetailExists(Optional<CartDetail> cartDetail, Long id) {
    if(cartDetail.isEmpty()) {
      throw new VsException(UserMessageConstant.Cart.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Cart.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }

}
