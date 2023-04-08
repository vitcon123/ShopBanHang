package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ProductOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.FindProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.outputs.product.GetListProductOutput;
import com.hoa.shopbanhang.domain.entities.Product;

import java.util.List;

public interface IProductService {
  List<ProductOutput> getAll(Long page, Integer size);

  ProductOutput getProductById(Long id);

  GetListProductOutput findProducts(FindProductInput findProductInput);

  Product createProduct(CreateProductInput createProductInput);

  Product updateProduct(UpdateProductInput updateProductInput);

  void updateStockProduct(Long productId, Integer amount, Boolean isBuy);

  RequestResponse deleteById(Long id);
}
