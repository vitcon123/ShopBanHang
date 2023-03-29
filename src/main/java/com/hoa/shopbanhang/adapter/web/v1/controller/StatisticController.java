package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.statistic.AdminStatisticInput;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.application.services.IStatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class StatisticController {

  private final IStatisticService statisticService;

  public StatisticController(IStatisticService statisticService) {
    this.statisticService = statisticService;
  }

  @GetMapping(UrlConstant.Statistic.LIST)
  public ResponseEntity<?> getAll(@RequestBody(required = false) AdminStatisticInput input) {
    return VsResponseUtil.ok(statisticService.getAll(input));
  }

  @GetMapping(UrlConstant.Statistic.GET)
  public ResponseEntity<?> getStatisticById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(statisticService.getStatisticById(id));
  }

  @PostMapping(UrlConstant.Statistic.CREATE)
  public ResponseEntity<?> createStatistic(@RequestBody CreateStatisticInput createStatisticInput) {
    return VsResponseUtil.ok(statisticService.createStatistic(createStatisticInput));
  }

  @DeleteMapping(UrlConstant.Statistic.DELETE)
  public ResponseEntity<?> deleteStatistic(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(statisticService.deleteById(id));
  }
}
