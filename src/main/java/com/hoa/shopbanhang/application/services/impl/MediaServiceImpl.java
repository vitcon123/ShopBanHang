package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.media.CreateMediaInput;
import com.hoa.shopbanhang.application.repositories.IMediaRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.services.IMediaService;
import com.hoa.shopbanhang.application.utils.FileUtil;
import com.hoa.shopbanhang.application.utils.UrlUtil;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Media;
import com.hoa.shopbanhang.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaServiceImpl implements IMediaService {

  private final IProductRepository productRepository;
  private final IMediaRepository mediaRepository;
  private final ModelMapper modelMapper;
  private final HttpServletRequest request;

  public MediaServiceImpl(IProductRepository productRepository, IMediaRepository mediaRepository,
                          ModelMapper modelMapper,
                          HttpServletRequest request) {
    this.productRepository = productRepository;
    this.mediaRepository = mediaRepository;
    this.modelMapper = modelMapper;
    this.request = request;
  }

  @Override
  public List<Media> getAll() {
    return mediaRepository.findAll();
  }

  @Override
  public Media getMediaById(Long id) {
    Optional<Media> media = mediaRepository.findById(id);
    checkMediaExists(media);

    return media.get();
  }

  @Transactional
  @Override
  public Media createMedia(CreateMediaInput createMediaInput) throws IOException {
    UUID name = UUID.randomUUID();
    Optional<Product> product = productRepository.findById(createMediaInput.getIdProduct());
    ProductServiceImpl.checkProductExists(product);
    String fileName = FileUtil.saveFile(name.toString(), CommonConstant.MEDIAS, createMediaInput.getFile());
    String path = UrlUtil.applicationUrl(request) + CommonConstant.URL_MEDIA + fileName;

    Media media = modelMapper.map(createMediaInput, Media.class);
    media.setPath(path);
    media.setProduct(product.get());
    return mediaRepository.save(media);
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Media> media = mediaRepository.findById(id);
    checkMediaExists(media);

    media.get().setDeleteFlag(true);
    mediaRepository.save(media.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkMediaExists(Optional<Media> media) {
    if (media.isEmpty()) {
      throw new VsException(MessageConstant.MEDIA_NOT_EXISTS);
    }
  }

}
