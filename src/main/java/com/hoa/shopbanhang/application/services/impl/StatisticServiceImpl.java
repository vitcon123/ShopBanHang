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
  private final IStatisticRepository statisticRepository;
  private final ModelMapper modelMapper;

  public StatisticServiceImpl(IStatisticRepository statisticRepository, ModelMapper modelMapper) {
    this.statisticRepository = statisticRepository;
    this.modelMapper = modelMapper;
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
  public Statistic createStatistic(CreateStatisticInput createStatisticInput) {
    Statistic statistic = modelMapper.map(createStatisticInput, Statistic.class);

    return statisticRepository.save(statistic);
  }

  @Override
  public Statistic updateStatistic(UpdateStatisticInput updateStatisticInput) {
    Optional<Statistic> statistic = statisticRepository.findById(updateStatisticInput.getId());
    checkStatisticExists(statistic);

    modelMapper.map(updateStatisticInput, statistic.get());

    return statisticRepository.save(statistic.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Statistic> statistic = statisticRepository.findById(id);
    checkStatisticExists(statistic);

    statistic.get().setDeleteFlag(true);
    statisticRepository.save(statistic.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkStatisticExists(Optional<Statistic> Statistic) {

  }
}
