package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.product.CreateProductInput;
import com.hoa.shopbanhang.application.inputs.product.UpdateProductInput;
import com.hoa.shopbanhang.application.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class ProductController {

  private final IProductService productService;

  public ProductController(IProductService productService) {
    this.productService = productService;
  }

  @Operation(summary = "Get All Product")
  @GetMapping(UrlConstant.Product.LIST)
  public ResponseEntity<?> getAll(@RequestParam(name = "page", required = false) Integer page,
                                  @RequestParam(name = "size", required = false) Integer size) {
    return VsResponseUtil.ok(productService.getAll(page, size));
  }

  @Operation(summary = "Get Product By Id")
  @GetMapping(UrlConstant.Product.GET)
  public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.getProductById(id));
  }

  @Operation(summary = "Search Product")
  @PostMapping(UrlConstant.Product.SEARCH)
  public ResponseEntity<?> findProducts(@RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "page", required = false) Integer page,
                                        @RequestParam(name = "size", required = false) Integer size) {
    return VsResponseUtil.ok(productService.findProducts(name, page, size));
  }

  @Operation(summary = "Create Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Product.CREATE)
  public ResponseEntity<?> createProduct(@ModelAttribute CreateProductInput createProductInput) {
    return VsResponseUtil.ok(productService.createProduct(createProductInput));
  }

  @Operation(summary = "Update Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping(UrlConstant.Product.UPDATE)
  public ResponseEntity<?> updateProduct(@ModelAttribute UpdateProductInput updateProductInput) {
    return VsResponseUtil.ok(productService.updateProduct(updateProductInput));
  }

  @Operation(summary = "Update Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(UrlConstant.Product.DELETE)
  public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.deleteById(id));
  }
}
