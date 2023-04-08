package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.FindProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class ProductController {

  private final IProductService productService;

  public ProductController(IProductService productService) {
    this.productService = productService;
  }

  @GetMapping(UrlConstant.Product.LIST)
  public ResponseEntity<?> getAll(@RequestParam(name = "page", required = false) Long page) {
    return VsResponseUtil.ok(productService.getAll(page, CommonConstant.PAGE_SIZE_DEFAULT));
  }

  @GetMapping(UrlConstant.Product.GET)
  public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.getProductById(id));
  }

  @PostMapping(UrlConstant.Product.SEARCH)
  public ResponseEntity<?> findProducts(@RequestBody(required = false) FindProductInput findProductInput) {
    return VsResponseUtil.ok(productService.findProducts(findProductInput));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Product.CREATE)
  public ResponseEntity<?> createProduct(@ModelAttribute CreateProductInput createProductInput) {
    return VsResponseUtil.ok(productService.createProduct(createProductInput));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping(UrlConstant.Product.UPDATE)
  public ResponseEntity<?> updateProduct(@ModelAttribute UpdateProductInput updateProductInput) {
    return VsResponseUtil.ok(productService.updateProduct(updateProductInput));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(UrlConstant.Product.DELETE)
  public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.deleteById(id));
  }
}
