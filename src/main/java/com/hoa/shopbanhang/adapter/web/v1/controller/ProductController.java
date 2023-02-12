package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class ProductController {

  private final IProductService productService;

  public ProductController(IProductService productService) {
    this.productService = productService;
  }

  @GetMapping(UrlConstant.Product.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(productService.getAll());
  }

  @GetMapping(UrlConstant.Product.GET)
  public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.getProductById(id));
  }

  @PostMapping(UrlConstant.Product.CREATE)
  public ResponseEntity<?> createProduct(@ModelAttribute CreateProductInput createProductInput) {
    return VsResponseUtil.ok(productService.createProduct(createProductInput));
  }

  @PatchMapping(UrlConstant.Product.UPDATE)
  public ResponseEntity<?> updateProduct(@Valid @ModelAttribute UpdateProductInput updateProductInput) {
    return VsResponseUtil.ok(productService.updateProduct(updateProductInput));
  }

  @DeleteMapping(UrlConstant.Product.DELETE)
  public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.deleteById(id));
  }
}
