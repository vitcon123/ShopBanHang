package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.product.ReportProductInput;
import com.hoa.shopbanhang.application.inputs.statistic.AdminStatisticInput;
import com.hoa.shopbanhang.application.inputs.statistic.CreateStatisticInput;
import com.hoa.shopbanhang.application.services.IStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class StatisticController {

  private final IStatisticService statisticService;

  public StatisticController(IStatisticService statisticService) {
    this.statisticService = statisticService;
  }


  @Operation(summary = "Get All Statistic - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(UrlConstant.Statistic.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(statisticService.getAll());
  }

  @Operation(summary = "Get Statistic - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Statistic.VIEW)
  public ResponseEntity<?> getStatistic(@RequestBody(required = false) AdminStatisticInput input) {
    return VsResponseUtil.ok(statisticService.getStatistic(input));
  }

  @Operation(summary = "Report Revenue - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Statistic.REPORT_REVENUE)
  public ResponseEntity<?> reportRevenue(@RequestBody(required = false) ReportProductInput input) {
    return VsResponseUtil.ok(statisticService.reportRevenue(input));
  }

  @Operation(summary = "Get Statistic By Id - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(UrlConstant.Statistic.GET)
  public ResponseEntity<?> getStatisticById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(statisticService.getStatisticById(id));
  }

  @Operation(summary = "Create Statistic - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Statistic.CREATE)
  public ResponseEntity<?> createStatistic(@ModelAttribute CreateStatisticInput createStatisticInput) {
    return VsResponseUtil.ok(statisticService.createStatistic(createStatisticInput));
  }

  @Operation(summary = "Delete Statistic - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(UrlConstant.Statistic.DELETE)
  public ResponseEntity<?> deleteStatistic(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(statisticService.deleteById(id));
  }
}
