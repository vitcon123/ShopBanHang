package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.rate.CreateRateInput;
import com.hoa.shopbanhang.application.inputs.rate.UpdateRateInput;
import com.hoa.shopbanhang.domain.entities.Rate;

import java.util.List;

public interface IRateService {
  List<Rate> getAll();

  Rate getRateById(Long id);

  Rate createRate(CreateRateInput createRateInput);

  Rate updateRate(UpdateRateInput updateRateInput);

  RequestResponse deleteById(Long id);
}
