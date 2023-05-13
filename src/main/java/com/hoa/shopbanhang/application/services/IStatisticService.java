package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AdminStatisticOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ReportRevenueOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.product.ReportProductInput;
import com.hoa.shopbanhang.application.inputs.statistic.AdminStatisticInput;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.domain.entities.Statistic;

import java.util.List;

public interface IStatisticService {

  List<AdminStatisticOutput> getStatistic(AdminStatisticInput input);

  Double reportRevenue(ReportProductInput input);

  List<Statistic> getAll();

  Statistic getStatisticById(Long id);

  RequestResponse createStatistic(CreateStatisticInput createStatisticInput);

  RequestResponse deleteById(Long id);

}
