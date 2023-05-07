package com.hoa.shopbanhang.application.services.impl;

import com.github.slugify.Slugify;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ProductOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.product.AddImagesProductInput;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.RemoveImagesProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.application.outputs.common.PagingMeta;
import com.hoa.shopbanhang.application.outputs.product.GetListProductOutput;
import com.hoa.shopbanhang.application.repositories.ICategoryRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.repositories.IRateRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.application.services.IStatisticService;
import com.hoa.shopbanhang.application.utils.CloudinaryUtil;
import com.hoa.shopbanhang.application.utils.SecurityUtil;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Category;
import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductServiceImpl implements IProductService {
  private final IProductRepository productRepository;
  private final ICategoryRepository categoryRepository;
  private final IUserRepository userRepository;
  private final IStatisticService statisticService;
  private final IRateRepository rateRepository;
  private final ModelMapper modelMapper;

  public ProductServiceImpl(IProductRepository productRepository, ICategoryRepository categoryRepository,
                            IUserRepository userRepository, IStatisticService statisticService,
                            IRateRepository rateRepository, ModelMapper modelMapper) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
    this.statisticService = statisticService;
    this.rateRepository = rateRepository;
    this.modelMapper = modelMapper;
  }

  public static void checkProductExists(Optional<Product> product) {
    if (product.isEmpty()) {
      throw new VsException(MessageConstant.PRODUCT_NOT_EXISTS);
    }
  }

  @Override
  public GetListProductOutput getAll(Integer page, Integer size) {
    List<Product> products = productRepository.findAll();
    Integer total = products.size();
    PagingMeta pagingMeta;

    if (page != null && size > 0) {
      products = productRepository.findAll(PageRequest.of(page.intValue(), size));
      pagingMeta = new PagingMeta(total, page, size);
    } else {
      pagingMeta = new PagingMeta(total, null, null);
    }

    List<ProductOutput> productOutputs = new ArrayList<>();
    for (Product product : products) {
      productOutputs.add(convertProductToProductOutput(product));
    }

    return new GetListProductOutput(productOutputs, pagingMeta);
  }

  @Override
  public ProductOutput getProductById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    checkProductExists(product);

    // Tạo bản ghi thống kê lượt xem sản phẩm
    String emailUserCurrent = SecurityUtil.getCurrentUserLogin();
    if (emailUserCurrent.compareTo("anonymousUser") != 0) {
      Optional<User> userCurrent = userRepository.findByEmail(emailUserCurrent);
      UserServiceImpl.checkUserExists(userCurrent);
      CreateStatisticInput createStatisticInput;
      Integer ageOfUser = null;
      if (userCurrent.get().getBirthday() != null) {
        ageOfUser =
            Period.between(LocalDate.parse(userCurrent.get().getBirthday()), LocalDate.now()).getYears();
      }
      createStatisticInput = new CreateStatisticInput(ageOfUser, userCurrent.get(), product.get());
      statisticService.createStatistic(createStatisticInput);
    }

    return convertProductToProductOutput(product.get());
  }

  @Override
  public GetListProductOutput findProducts(String category, String name, Integer page, Integer size) {
    List<Product> products = productRepository.findByName(category, name, null);
    Integer total = products.size();
    PagingMeta pagingMeta;

    if (page != null && size > 0) {
      products = productRepository.findByName(category, name, PageRequest.of(page.intValue(), size));
      pagingMeta = new PagingMeta(total, page, size);
    } else {
      pagingMeta = new PagingMeta(total, null, null);
    }

    List<ProductOutput> productOutputs = new ArrayList<>();
    for (Product product : products) {
      productOutputs.add(convertProductToProductOutput(product));
    }

    return new GetListProductOutput(productOutputs, pagingMeta);
  }

//  @Override
//  public GetListProductOutput findProducts(FindProductInput findProductInput) {
//    Long total = productRepository.countProduct(findProductInput);
//
//    PagingMeta meta = new PagingMeta(total, findProductInput.getPageNum(), findProductInput.getPageSize(),
//        findProductInput.getSortBy(), findProductInput.getSortType());
//
//    List<Product> products = productRepository.searchProduct(findProductInput,
//        PageRequest.of(findProductInput.getPageNum(),
//            findProductInput.getPageSize()), Sort.by(Sort.Direction.valueOf(findProductInput.getSortType()),
//            findProductInput.getSortBy()));
//
//    List<ProductOutput> productOutputs = new ArrayList<>();
//    for (Product product : products) {
//      productOutputs.add(convertProductToProductOutput(product));
//    }
//
//    return new GetListProductOutput(productOutputs, meta);
//  }


  @Transactional
  @Override
  public Product createProduct(CreateProductInput createProductInput) {
    Optional<Category> category = categoryRepository.findById(createProductInput.getIdCategory());
    CategoryServiceImpl.checkCategoryExists(category);

    Product newProduct = modelMapper.map(createProductInput, Product.class);
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(createProductInput.getName());
    newProduct.setSlug(slug);
    newProduct.setCategory(category.get());
    List<String> images = new ArrayList<>();
    for (MultipartFile image : createProductInput.getImages()) {
      images.add(CloudinaryUtil.getUrlFromFile(image));
    }
    newProduct.setImages(images);
    return productRepository.save(newProduct);
  }

  @Override
  public Product updateProduct(UpdateProductInput updateProductInput) {
    Optional<Product> product = productRepository.findById(updateProductInput.getId());
    checkProductExists(product);

    modelMapper.map(updateProductInput, product.get());
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(updateProductInput.getName());
    product.get().setSlug(slug);

    if(updateProductInput.getImages() != null) {
      List<String> images = new ArrayList<>();
      for (MultipartFile image : updateProductInput.getImages()) {
        images.add(CloudinaryUtil.getUrlFromFile(image));
      }
      product.get().setImages(images);
    }

    return productRepository.save(product.get());
  }

  @Override
  public Product addImagesProduct(AddImagesProductInput addImagesProductInput) {
    Optional<Product> product = productRepository.findById(addImagesProductInput.getId());
    checkProductExists(product);

    List<String> images = product.get().getImages();
    for (MultipartFile image : addImagesProductInput.getImages()) {
      images.add(CloudinaryUtil.getUrlFromFile(image));
    }
    product.get().setImages(images);
    return productRepository.save(product.get());
  }

  @Override
  public Product removeImagesProduct(RemoveImagesProductInput removeImagesProductInput) {
    Optional<Product> product = productRepository.findById(removeImagesProductInput.getId());
    checkProductExists(product);

    List<String> images = product.get().getImages();
    for (String image : removeImagesProductInput.getImages()) {
      if(images.contains(image)) {
        images.remove(image);
      }
    }
    product.get().setImages(images);
    return productRepository.save(product.get());
  }

  @Override
  public Boolean updateStockProduct(Long productId, Integer amount, Boolean isBuy) {
    Optional<Product> product = productRepository.findById(productId);
    checkProductExists(product);
    if (isBuy) {
      if (product.get().getStock() > amount) {
        product.get().setStock(product.get().getStock() - amount);
      } else {
        return Boolean.FALSE;
      }

    } else {
      product.get().setStock(product.get().getStock() + amount);
    }
    productRepository.save(product.get());
    return Boolean.TRUE;
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Product> oldProduct = productRepository.findById(id);
    checkProductExists(oldProduct);

    productRepository.delete(oldProduct.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  ProductOutput convertProductToProductOutput(Product product) {
    ProductOutput productOutput = modelMapper.map(product, ProductOutput.class);

    productOutput.setCategory(product.getCategory().getName());
    Double rating = rateRepository.getAvgOfProduct(product);
    if(rating == null) {
      Random random = new Random();
      rating = Math.round((random.nextDouble() + 4) * 10.0) / 10.0;
    }
    productOutput.setRating(rating);
    productOutput.setThumbnail(product.getImages().get(0));

    return productOutput;
  }

}
