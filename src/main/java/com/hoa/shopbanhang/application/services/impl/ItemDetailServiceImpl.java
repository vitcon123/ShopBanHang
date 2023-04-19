package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ReportProductOutput;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.product.ReportProductInput;
import com.hoa.shopbanhang.application.repositories.ICartRepository;
import com.hoa.shopbanhang.application.repositories.IItemDetailRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IItemDetailService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.ItemDetail;
import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemDetailServiceImpl implements IItemDetailService {
  private final IItemDetailRepository itemDetailRepository;
  private final ICartRepository cartRepository;
  private final IUserRepository userRepository;
  private final IProductRepository productRepository;

  public ItemDetailServiceImpl(IItemDetailRepository itemDetailRepository, ICartRepository cartRepository,
                               IUserRepository userRepository, IProductRepository productRepository) {
    this.itemDetailRepository = itemDetailRepository;
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  public static void checkItemDetailExists(Optional<ItemDetail> cartDetail) {
    if (cartDetail.isEmpty()) {
      throw new VsException(MessageConstant.ITEM_DETAIL_NOT_EXISTS);
    }
  }

  @Override
  public List<ItemDetail> getAllItemDetailInCart(Long idCart) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart);
    List<ItemDetail> itemDetails = itemDetailRepository.getAllByCart(cart.get());
    return itemDetails;
  }

  @Override
  public void addProductToCartById(Long idUser, Long idProduct, Integer amount) {
    Optional<User> user = userRepository.findById(idUser);
    UserServiceImpl.checkUserExists(user);

    Cart cart = user.get().getCart();

    Optional<Product> product = productRepository.findById(idProduct);
    ProductServiceImpl.checkProductExists(product);

    List<ItemDetail> productAdded = cart.getItemDetails();

    boolean isExist = false;
    for (ItemDetail itemDetail : productAdded) {
      if (itemDetail.getProduct().equals(product.get())) {
        isExist = true;
        itemDetail.setAmount(itemDetail.getAmount() + amount);
        itemDetailRepository.save(itemDetail);
        break;
      }
    }
    if (!isExist) {
      ItemDetail itemDetail = new ItemDetail();
      itemDetail.setCart(cart);
      itemDetail.setProduct(product.get());
      itemDetail.setAmount(amount);
      itemDetailRepository.save(itemDetail);
    }
  }

  @Override
  public void editItemDetailById(Long iditemDetail, Integer amount) {
    Optional<ItemDetail> cartDetail = itemDetailRepository.findById(iditemDetail);
    checkItemDetailExists(cartDetail);

    cartDetail.get().setAmount(amount);
    itemDetailRepository.save(cartDetail.get());
  }

  @Override
  public void deleteItemDetailById(Long idItemDetail) {
    Optional<ItemDetail> cartDetail = itemDetailRepository.findById(idItemDetail);
    checkItemDetailExists(cartDetail);
    itemDetailRepository.delete(cartDetail.get());
  }

  @Override
  public void deleteAllItemDetailInCart(Long idCart) {
    Optional<Cart> cart = cartRepository.findById(idCart);
    CartServiceImpl.checkCartExists(cart);
    itemDetailRepository.deleteAllByCart(cart.get());
  }

  @Override
  public List<ReportProductOutput> reportProduct(ReportProductInput reportProductInput) {
    List<ReportProductOutput> outputs = itemDetailRepository.reportProduct(reportProductInput);
    return outputs;
  }

}
