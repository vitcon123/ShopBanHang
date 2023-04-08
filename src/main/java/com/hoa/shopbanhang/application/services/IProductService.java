package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ListProductOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ProductOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.SearchProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.domain.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
  ListProductOutput getAll();

  ProductOutput getProductById(Long id);

  List<Product> findProducts(SearchProductInput searchProductInput);

  Product createProduct(CreateProductInput createProductInput);

  Product updateProduct(UpdateProductInput updateProductInput);

  void updateStockProduct(Long productId, Integer amount, Boolean isBuy);

  RequestResponse deleteById(Long id);
}
