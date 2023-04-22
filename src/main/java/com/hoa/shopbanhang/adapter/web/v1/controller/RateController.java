package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.rate.CreateRateInput;
import com.hoa.shopbanhang.application.inputs.rate.UpdateRateInput;
import com.hoa.shopbanhang.application.services.IRateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class RateController {

  private final IRateService rateService;

  public RateController(IRateService rateService) {
    this.rateService = rateService;
  }

  @Operation(summary = "Get All Rate")
  @GetMapping(UrlConstant.Rate.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(rateService.getAll());
  }

  @Operation(summary = "Get Rate By Id")
  @GetMapping(UrlConstant.Rate.GET)
  public ResponseEntity<?> getRateById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(rateService.getRateById(id));
  }

  @Operation(summary = "Create Rate - ADMIN, USER")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @PostMapping(UrlConstant.Rate.CREATE)
  public ResponseEntity<?> createRate(@RequestBody CreateRateInput createRateInput) {
    return VsResponseUtil.ok(rateService.createRate(createRateInput));
  }

  @Operation(summary = "Update Rate - ADMIN, USER")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @PatchMapping(UrlConstant.Rate.UPDATE)
  public ResponseEntity<?> updateRate(@RequestBody UpdateRateInput updateRateInput) {
    return VsResponseUtil.ok(rateService.updateRate(updateRateInput));
  }

  @Operation(summary = "Delete Rate - ADMIN, USER")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @DeleteMapping(UrlConstant.Rate.DELETE)
  public ResponseEntity<?> deleteRate(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(rateService.deleteById(id));
  }
}
