package com.hoa.shopbanhang.application.services.impl;

import com.github.slugify.Slugify;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.MediaType;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.inputs.media.CreateMediaInput;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.repositories.ICategoryRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.services.IMediaService;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Category;
import com.hoa.shopbanhang.domain.entities.Media;
import com.hoa.shopbanhang.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
  private final IProductRepository productRepository;
  private final ICategoryRepository categoryRepository;
  private final IMediaService mediaService;
  private final ModelMapper modelMapper;

  public ProductServiceImpl(IProductRepository productRepository, ICategoryRepository categoryRepository, IMediaService mediaService, ModelMapper modelMapper) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.mediaService = mediaService;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public Product getProductById(Long id) {
    Optional<Product> oldProduct = productRepository.findById(id);
    checkProductExists(oldProduct, id);

    return oldProduct.get();
  }

  @Transactional
  @Override
  public Product createProduct(CreateProductInput createProductInput) {
    Optional<Category> oldCategory = categoryRepository.findById(createProductInput.getIdCategory());
    checkCategoryExists(oldCategory, createProductInput.getIdCategory());

    Product newProduct = modelMapper.map(createProductInput, Product.class);
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(createProductInput.getName());
    newProduct.setSlug(slug);
    newProduct.setCategory(oldCategory.get());
    Long idProduct = productRepository.save(newProduct).getId();

    setMediasProduct(createProductInput.getMultipartFiles(), idProduct);
    return newProduct;
  }

  private void setMediasProduct(List<MultipartFile> multipartFiles, Long idProduct) {
    multipartFiles.forEach(multipartFile -> {
      CreateMediaInput createMediaInput = new CreateMediaInput();
      createMediaInput.setFile(multipartFile);
      createMediaInput.setIdProduct(idProduct);
      try {
        mediaService.createMedia(createMediaInput);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public Product updateProduct(UpdateProductInput updateProductInput) {
    Optional<Product> oldProduct = productRepository.findById(updateProductInput.getId());
    checkProductExists(oldProduct, updateProductInput.getId());

    modelMapper.map(updateProductInput, oldProduct.get());
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(updateProductInput.getName());
    oldProduct.get().setSlug(slug);

    return productRepository.save(oldProduct.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Product> oldProduct = productRepository.findById(id);
    checkProductExists(oldProduct, id);

    oldProduct.get().setDeleteFlag(true);
    productRepository.save(oldProduct.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkProductExists(Optional<Product> product, Long id) {
    if(product.isEmpty()) {
      throw new VsException(UserMessageConstant.Category.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Category.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }

  private void checkCategoryExists(Optional<Category> category, Long id) {
    if(category.isEmpty()) {
      throw new VsException(UserMessageConstant.Category.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Category.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }
}
