package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.application.inputs.statistic.UpdateStatisticInput;
import com.hoa.shopbanhang.domain.entities.Statistic;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IStatisticService {
  List<Statistic> getAll();

  Statistic getStatisticById(Long id);

  Statistic createStatistic(CreateStatisticInput createStatisticInput);

  Statistic updateStatistic(UpdateStatisticInput updateStatisticInput);

  RequestResponse deleteById(Long id);
}
