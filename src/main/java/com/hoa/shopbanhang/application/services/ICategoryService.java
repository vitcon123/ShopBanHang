package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.category.CreateCategoryInput;
import com.hoa.shopbanhang.application.inputs.category.UpdateCategoryInput;
import com.hoa.shopbanhang.domain.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICategoryService {
  List<Category> getAll();

  Category getCategoryById(Long id);

  Category createCategory(CreateCategoryInput createCategoryInput);

  Category updateCategory(UpdateCategoryInput updateCategoryInput);

  RequestResponse deleteById(Long id);
}
