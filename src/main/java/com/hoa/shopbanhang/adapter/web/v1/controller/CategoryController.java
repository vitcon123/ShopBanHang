package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.category.CreateCategoryInput;
import com.hoa.shopbanhang.application.inputs.category.UpdateCategoryInput;
import com.hoa.shopbanhang.application.services.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class CategoryController {

  private final ICategoryService categoryService;

  public CategoryController(ICategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(UrlConstant.Category.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(categoryService.getAll());
  }

//  @GetMapping(UrlConstant.Category.GET)
//  public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
//    return VsResponseUtil.ok(categoryService.getCategoryById(id));
//  }

  @GetMapping(UrlConstant.Category.GET_BY_NAME)
  public ResponseEntity<?> getCategoryByName(@PathVariable("name") String name) {
    return VsResponseUtil.ok(categoryService.getCategoryByName(name));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Category.CREATE)
  public ResponseEntity<?> createCategory(@RequestBody CreateCategoryInput createCategoryInput) {
    return VsResponseUtil.ok(categoryService.createCategory(createCategoryInput));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping(UrlConstant.Category.UPDATE)
  public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryInput updateCategoryInput) {
    return VsResponseUtil.ok(categoryService.updateCategory(updateCategoryInput));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(UrlConstant.Category.DELETE)
  public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(categoryService.deleteById(id));
  }
}
