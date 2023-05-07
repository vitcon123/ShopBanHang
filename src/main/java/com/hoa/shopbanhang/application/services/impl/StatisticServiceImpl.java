package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AdminStatisticOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.CheckSpamStatisticOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.inputs.statistic.AdminStatisticInput;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.repositories.IStatisticRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IStatisticService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.Statistic;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticServiceImpl implements IStatisticService {
  private final IStatisticRepository statisticRepository;
  private final IUserRepository userRepository;
  private final IProductRepository productRepository;

  public StatisticServiceImpl(IStatisticRepository statisticRepository, IUserRepository userRepository,
                              IProductRepository productRepository) {
    this.statisticRepository = statisticRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Override
  public List<AdminStatisticOutput> getStatistic(AdminStatisticInput input) {
    List<AdminStatisticOutput> adminStatisticOutputs = statisticRepository.adminStatistic(input);
    return adminStatisticOutputs;
  }

  @Override
  public List<Statistic> getAll() {
    return statisticRepository.findAll();
  }

  @Override
  public Statistic getStatisticById(Long id) {
    Optional<Statistic> statistic = statisticRepository.findById(id);
    checkStatisticExists(statistic);

    return statistic.get();
  }

  @Override
  public RequestResponse createStatistic(CreateStatisticInput input) {
    CheckSpamStatisticOutput output = statisticRepository.checkSpamStatisticOutput(input.getUser().getId(), input.getProduct().getId());
    if(output != null && output.getTimes() >= 3) {
      return new RequestResponse(CommonConstant.FALSE, CommonConstant.EMPTY_STRING);
    }
    Statistic statistic = new Statistic();
    statistic.setAgeOfUser(input.getAgeOfUser());
    statistic.setUser(input.getUser());
    statistic.setProduct(input.getProduct());

    statisticRepository.save(statistic);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Statistic> statistic = statisticRepository.findById(id);
    checkStatisticExists(statistic);

    statisticRepository.delete(statistic.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkStatisticExists(Optional<Statistic> statistic) {
    if (statistic.isEmpty()) {
      throw new VsException(MessageConstant.STATISTIC_NOT_EXISTS);
    }
  }
}
