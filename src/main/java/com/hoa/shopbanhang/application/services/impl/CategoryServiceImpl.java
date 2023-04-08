package com.hoa.shopbanhang.application.services.impl;

import com.github.slugify.Slugify;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.category.CreateCategoryInput;
import com.hoa.shopbanhang.application.inputs.category.UpdateCategoryInput;
import com.hoa.shopbanhang.application.repositories.ICategoryRepository;
import com.hoa.shopbanhang.application.services.ICategoryService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
  private final ICategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  public CategoryServiceImpl(ICategoryRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Category getCategoryById(Long id) {
    Optional<Category> oldCategory = categoryRepository.findById(id);
    checkCategoryExists(oldCategory);

    return oldCategory.get();
  }

  @Override
  public Category createCategory(CreateCategoryInput createCategoryInput) {
    Category category = modelMapper.map(createCategoryInput, Category.class);
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(createCategoryInput.getName());
    category.setSlug(slug);

    return categoryRepository.save(category);
  }

  @Override
  public Category updateCategory(UpdateCategoryInput updateCategoryInput) {
    Optional<Category> category = categoryRepository.findById(updateCategoryInput.getId());
    checkCategoryExists(category);

    modelMapper.map(updateCategoryInput, category.get());
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(updateCategoryInput.getName());
    category.get().setSlug(slug);

    return categoryRepository.save(category.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Category> category = categoryRepository.findById(id);
    checkCategoryExists(category);

    categoryRepository.delete(category.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkCategoryExists(Optional<Category> Category) {
    if (Category.isEmpty()) {
      throw new VsException(MessageConstant.CATEGORY_NOT_EXISTS);
    }
  }
}
