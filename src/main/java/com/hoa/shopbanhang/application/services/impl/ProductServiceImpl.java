package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
  private final IProductRepository categoryRepository;
  private final ModelMapper modelMapper;

  public ProductServiceImpl(IProductRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Product> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Product getProductById(Long id) {
    Optional<Product> oldProduct = categoryRepository.findById(id);
    checkProductExists(oldProduct, id);

    return oldProduct.get();
  }

  @Override
  public Product createProduct(CreateProductInput createProductInput) {
    Product newProduct = modelMapper.map(createProductInput, Product.class);

    return categoryRepository.save(newProduct);
  }

  @Override
  public Product updateProduct(UpdateProductInput updateProductInput) {
    Optional<Product> oldProduct = categoryRepository.findById(updateProductInput.getId());
    checkProductExists(oldProduct, updateProductInput.getId());

    modelMapper.map(updateProductInput, oldProduct.get());

    return categoryRepository.save(oldProduct.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Product> oldProduct = categoryRepository.findById(id);
    checkProductExists(oldProduct, id);

    oldProduct.get().setDeleteFlag(true);
    categoryRepository.save(oldProduct.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkProductExists(Optional<Product> product, Long id) {
    if(product.isEmpty()) {
      throw new VsException(UserMessageConstant.Product.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Product.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }
}
