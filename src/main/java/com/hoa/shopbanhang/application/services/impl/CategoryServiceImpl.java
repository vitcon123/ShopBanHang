package com.hoa.shopbanhang.application.services.impl;

import com.github.slugify.Slugify;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
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
    checkCategoryExists(oldCategory, id);

    return oldCategory.get();
  }

  @Override
  public Category createCategory(CreateCategoryInput createCategoryInput) {
    Category newCategory = modelMapper.map(createCategoryInput, Category.class);
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(createCategoryInput.getName());
    newCategory.setSlug(slug);

    return categoryRepository.save(newCategory);
  }

  @Override
  public Category updateCategory(UpdateCategoryInput updateCategoryInput) {
    Optional<Category> oldCategory = categoryRepository.findById(updateCategoryInput.getId());
    checkCategoryExists(oldCategory, updateCategoryInput.getId());

    modelMapper.map(updateCategoryInput, oldCategory.get());
    Slugify slugify = new Slugify();
    String slug = slugify.slugify(updateCategoryInput.getName());
    oldCategory.get().setSlug(slug);

    return categoryRepository.save(oldCategory.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Category> oldCategory = categoryRepository.findById(id);
    checkCategoryExists(oldCategory, id);

    oldCategory.get().setDeleteFlag(true);
    categoryRepository.save(oldCategory.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkCategoryExists(Optional<Category> Category, Long id) {
    if(Category.isEmpty()) {
      throw new VsException(UserMessageConstant.Category.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Category.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }
}
