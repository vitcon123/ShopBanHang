package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ProductOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.product.AddImagesProductInput;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.RemoveImagesProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.outputs.product.GetListProductOutput;
import com.hoa.shopbanhang.domain.entities.Product;

public interface IProductService {
  GetListProductOutput getAll(Integer page, Integer size);

  ProductOutput getProductById(Long id);

//  GetListProductOutput findProducts(FindProductInput findProductInput);
  GetListProductOutput findProducts(String category, String name, Integer page, Integer size);

  Product createProduct(CreateProductInput createProductInput);

  Product updateProduct(UpdateProductInput updateProductInput);
  Product addImagesProduct(AddImagesProductInput addImagesProductInput);
  Product removeImagesProduct(RemoveImagesProductInput removeImagesProductInput);

  Boolean updateStockProduct(Long productId, Integer amount, Boolean isBuy);

  RequestResponse deleteById(Long id);
}
