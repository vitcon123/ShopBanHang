package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.rate.CreateRateInput;
import com.hoa.shopbanhang.application.inputs.rate.UpdateRateInput;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.repositories.IRateRepository;
import com.hoa.shopbanhang.application.services.IProductService;
import com.hoa.shopbanhang.application.services.IRateService;
import com.hoa.shopbanhang.application.services.IUserService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.Rate;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements IRateService {
  private final IRateRepository rateRepository;
  private final IProductService productService;
  private final IProductRepository productRepository;
  private final IUserService userService;
  private final ModelMapper modelMapper;

  public RateServiceImpl(IRateRepository rateRepository, IProductService productService, IProductRepository productRepository, IUserService userService, ModelMapper modelMapper) {
    this.rateRepository = rateRepository;
    this.productService = productService;
    this.productRepository = productRepository;
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Rate> getAll() {
    return rateRepository.findAll();
  }

  @Override
  public Rate getRateById(Long id) {
    Optional<Rate> oldRate = rateRepository.findById(id);
    checkRateExists(oldRate, id);

    return oldRate.get();
  }

  @Override
  public Rate createRate(CreateRateInput createRateInput) {
    User oldUser = userService.getUserById(createRateInput.getIdUser());
    Optional<Product> oldProduct = productRepository.findById(createRateInput.getIdProduct());

    Rate newRate = modelMapper.map(createRateInput, Rate.class);
    newRate.setUser(oldUser);
    newRate.setProduct(oldProduct.get());

    return rateRepository.save(newRate);
  }

  @Override
  public Rate updateRate(UpdateRateInput updateRateInput) {
    Optional<Rate> oldRate = rateRepository.findById(updateRateInput.getId());
    checkRateExists(oldRate, updateRateInput.getId());

    modelMapper.map(updateRateInput, oldRate.get());

    return rateRepository.save(oldRate.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Rate> oldRate = rateRepository.findById(id);
    checkRateExists(oldRate, id);

    oldRate.get().setDeleteFlag(true);
    rateRepository.save(oldRate.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkRateExists(Optional<Rate> Rate, Long id) {
    if(Rate.isEmpty()) {
      throw new VsException("Not found Rate");
    }
  }
}
