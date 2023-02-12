package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.domain.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
  List<Product> getAll();

  Product getProductById(Long id);

  Product createProduct(CreateProductInput createProductInput);

  Product updateProduct(UpdateProductInput updateProductInput);

  RequestResponse deleteById(Long id);
}
