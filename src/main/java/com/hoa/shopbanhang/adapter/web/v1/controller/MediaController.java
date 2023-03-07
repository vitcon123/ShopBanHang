package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.media.CreateMediaInput;
import com.hoa.shopbanhang.application.services.IMediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestApiV1
public class MediaController {

  private final IMediaService mediaService;

  public MediaController(IMediaService mediaService) {
    this.mediaService = mediaService;
  }

  @GetMapping(UrlConstant.Media.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(mediaService.getAll());
  }

  @GetMapping(UrlConstant.Media.GET)
  public ResponseEntity<?> getMediaById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(mediaService.getMediaById(id));
  }

  @PostMapping(UrlConstant.Media.CREATE)
  public ResponseEntity<?> createMedia(@ModelAttribute CreateMediaInput createMediaInput) {
    try {
      return VsResponseUtil.ok(mediaService.createMedia(createMediaInput));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @DeleteMapping(UrlConstant.Media.DELETE)
  public ResponseEntity<?> deleteMedia(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(mediaService.deleteById(id));
  }
}
