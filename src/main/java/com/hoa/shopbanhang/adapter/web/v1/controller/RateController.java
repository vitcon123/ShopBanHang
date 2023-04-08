package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.rate.CreateRateInput;
import com.hoa.shopbanhang.application.inputs.rate.UpdateRateInput;
import com.hoa.shopbanhang.application.services.IRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class RateController {

  private final IRateService rateService;

  public RateController(IRateService rateService) {
    this.rateService = rateService;
  }

  @GetMapping(UrlConstant.Rate.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(rateService.getAll());
  }

  @GetMapping(UrlConstant.Rate.GET)
  public ResponseEntity<?> getRateById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(rateService.getRateById(id));
  }

  @PostMapping(UrlConstant.Rate.CREATE)
  public ResponseEntity<?> createRate(@RequestBody CreateRateInput createRateInput) {
    return VsResponseUtil.ok(rateService.createRate(createRateInput));
  }

  @PatchMapping(UrlConstant.Rate.UPDATE)
  public ResponseEntity<?> updateRate(@RequestBody UpdateRateInput updateRateInput) {
    return VsResponseUtil.ok(rateService.updateRate(updateRateInput));
  }

  @DeleteMapping(UrlConstant.Rate.DELETE)
  public ResponseEntity<?> deleteRate(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(rateService.deleteById(id));
  }
}
