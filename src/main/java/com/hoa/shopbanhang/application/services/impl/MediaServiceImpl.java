package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.inputs.media.CreateMediaInput;
import com.hoa.shopbanhang.application.inputs.media.UpdateMediaInput;
import com.hoa.shopbanhang.application.repositories.IMediaRepository;
import com.hoa.shopbanhang.application.services.IMediaService;
import com.hoa.shopbanhang.domain.entities.Media;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaServiceImpl implements IMediaService {
  private final IMediaRepository mediaRepository;
  private final ModelMapper modelMapper;

  public MediaServiceImpl(IMediaRepository mediaRepository, ModelMapper modelMapper) {
    this.mediaRepository = mediaRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Media> getAll() {
    return mediaRepository.findAll();
  }

  @Override
  public Media getMediaById(Long id) {
    Optional<Media> oldMedia = mediaRepository.findById(id);
    checkMediaExists(oldMedia, id);

    return oldMedia.get();
  }

  @Override
  public Media createMedia(CreateMediaInput createMediaInput) {
    Media newMedia = modelMapper.map(createMediaInput, Media.class);

    return mediaRepository.save(newMedia);
  }

  @Override
  public Media updateMedia(UpdateMediaInput updateMediaInput) {
    Optional<Media> oldMedia = mediaRepository.findById(updateMediaInput.getId());
    checkMediaExists(oldMedia, updateMediaInput.getId());

    return mediaRepository.save(oldMedia.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Media> oldMedia = mediaRepository.findById(id);
    checkMediaExists(oldMedia, id);

    oldMedia.get().setDeleteFlag(true);
    mediaRepository.save(oldMedia.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkMediaExists(Optional<Media> Media, Long id) {

  }
}
