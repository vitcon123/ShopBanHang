package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.category.CreateCategoryInput;
import com.vitweb.vitwebapi.application.inputs.category.UpdateCategoryInput;
import com.vitweb.vitwebapi.domain.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {
  List<Category> getAll();

  Category getCategoryById(Long id);

  Category createCategory(CreateCategoryInput createCategoryInput);

  Category updateCategory(UpdateCategoryInput updateCategoryInput);

  RequestResponse deleteById(Long id);
}
