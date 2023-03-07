package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.repositories.ICartRepository;
import com.hoa.shopbanhang.application.repositories.IItemDetailRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.services.IItemDetailService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.ItemDetail;
import com.hoa.shopbanhang.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemDetailServiceImpl implements IItemDetailService {
  private final IItemDetailRepository itemDetailRepository;
  private final ICartRepository cartRepository;
  private final IProductRepository productRepository;
  private final ModelMapper modelMapper;

  public ItemDetailServiceImpl(IItemDetailRepository itemDetailRepository, ICartRepository cartRepository, IProductRepository productRepository, ModelMapper modelMapper) {
    this.itemDetailRepository = itemDetailRepository;
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<ItemDetail> getAllItemDetailInCart(Long idCart) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart, idCart);
    List<ItemDetail> itemDetails = itemDetailRepository.getAllByCart(cart.get());
    return itemDetails;
  }

  @Override
  public void addProductToCartById(Long idCart, Long idProduct, Long amount) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart, idCart);

    Optional<Product> product = productRepository.findById(idProduct);
    ProductServiceImpl.checkProductExists(product, idProduct);

    List<ItemDetail> productAdded = cart.get().getItemDetails();

    boolean isExist = false;
    for(ItemDetail itemDetail : productAdded) {
      if(itemDetail.getProduct().equals(product.get())) {
        isExist = true;
        itemDetail.setAmount(itemDetail.getAmount() + amount);
        itemDetailRepository.save(itemDetail);
        break;
      }
    }
    if(!isExist) {
      ItemDetail itemDetail = new ItemDetail();
      itemDetail.setCart(cart.get());
      itemDetail.setProduct(product.get());
      itemDetail.setAmount(amount);
      itemDetailRepository.save(itemDetail);
    }
  }

  @Override
  public void editItemDetailById(Long iditemDetail, Long amount) {
    Optional<ItemDetail> cartDetail = itemDetailRepository.findById(iditemDetail);
    checkItemDetailExists(cartDetail);

    cartDetail.get().setAmount(amount);
    itemDetailRepository.save(cartDetail.get());
  }

  @Override
  public void deleteItemDetailById(Long iditemDetail) {
    Optional<ItemDetail> cartDetail = itemDetailRepository.findById(iditemDetail);
    checkItemDetailExists(cartDetail);
    itemDetailRepository.delete(cartDetail.get());
  }

  @Override
  public void deleteAllItemDetailInCart(Long idCart) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart, idCart);
    itemDetailRepository.deleteAllByCart(cart.get());
  }

  public static void checkItemDetailExists(Optional<ItemDetail> cartDetail) {
    if(cartDetail.isEmpty()) {
      throw new VsException(MessageConstant.ITEM_DETAIL_NOT_EXISTS);
    }
  }

}
