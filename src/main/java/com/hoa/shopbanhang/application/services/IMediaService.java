package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.media.CreateMediaInput;
import com.hoa.shopbanhang.application.inputs.media.UpdateMediaInput;
import com.hoa.shopbanhang.domain.entities.Media;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMediaService {
  List<Media> getAll();

  Media getMediaById(Long id);

  Media createMedia(CreateMediaInput createMediaInput);

  Media updateMedia(UpdateMediaInput updateMediaInput);

  RequestResponse deleteById(Long id);
}
