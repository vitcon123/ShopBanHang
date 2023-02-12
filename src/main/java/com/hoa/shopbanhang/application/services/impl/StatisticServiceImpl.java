package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.application.inputs.statistic.UpdateStatisticInput;
import com.hoa.shopbanhang.application.repositories.IStatisticRepository;
import com.hoa.shopbanhang.application.services.IStatisticService;
import com.hoa.shopbanhang.domain.entities.Statistic;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticServiceImpl implements IStatisticService {
  private final IStatisticRepository categoryRepository;
  private final ModelMapper modelMapper;

  public StatisticServiceImpl(IStatisticRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Statistic> getAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Statistic getStatisticById(Long id) {
    Optional<Statistic> oldStatistic = categoryRepository.findById(id);
    checkStatisticExists(oldStatistic, id);

    return oldStatistic.get();
  }

  @Override
  public Statistic createStatistic(CreateStatisticInput createStatisticInput) {
    Statistic newStatistic = modelMapper.map(createStatisticInput, Statistic.class);

    return categoryRepository.save(newStatistic);
  }

  @Override
  public Statistic updateStatistic(UpdateStatisticInput updateStatisticInput) {
    Optional<Statistic> oldStatistic = categoryRepository.findById(updateStatisticInput.getId());
    checkStatisticExists(oldStatistic, updateStatisticInput.getId());

    modelMapper.map(updateStatisticInput, oldStatistic.get());

    return categoryRepository.save(oldStatistic.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Statistic> oldStatistic = categoryRepository.findById(id);
    checkStatisticExists(oldStatistic, id);

    oldStatistic.get().setDeleteFlag(true);
    categoryRepository.save(oldStatistic.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkStatisticExists(Optional<Statistic> Statistic, Long id) {

  }
}
