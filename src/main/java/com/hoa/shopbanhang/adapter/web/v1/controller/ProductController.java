package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.product.*;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.application.services.impl.ItemDetailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class ProductController {

  private final IProductService productService;
  private final ItemDetailServiceImpl itemDetailService;

  public ProductController(IProductService productService, ItemDetailServiceImpl itemDetailService) {
    this.productService = productService;
    this.itemDetailService = itemDetailService;
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
  public ResponseEntity<?> findProducts(@RequestParam(name = "category", required = false) String category,
                                        @RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "page", required = false) Integer page,
                                        @RequestParam(name = "size", required = false) Integer size) {
    return VsResponseUtil.ok(productService.findProducts(category, name, page, size));
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

  @Operation(summary = "Add Image Of Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping(UrlConstant.Product.ADD_IMAGES)
  public ResponseEntity<?> addImagesProduct(@ModelAttribute AddImagesProductInput input) {
    return VsResponseUtil.ok(productService.addImagesProduct(input));
  }

  @Operation(summary = "Remove Image Of Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping(UrlConstant.Product.REMOVE_IMAGES)
  public ResponseEntity<?> removeImagesProduct(@RequestBody RemoveImagesProductInput input) {
    return VsResponseUtil.ok(productService.removeImagesProduct(input));
  }

  @Operation(summary = "Delete Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(UrlConstant.Product.DELETE)
  public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(productService.deleteById(id));
  }

  @Operation(summary = "Report Product - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Product.REPORT)
  public ResponseEntity<?> reportProduct(@RequestBody(required = false) ReportProductInput input) {
    return VsResponseUtil.ok(itemDetailService.reportProduct(input));
  }

}
