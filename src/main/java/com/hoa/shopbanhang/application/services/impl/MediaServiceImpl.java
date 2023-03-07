package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
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

  public MediaServiceImpl(IProductRepository productRepository, IMediaRepository mediaRepository, ModelMapper modelMapper,
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
    Optional<Media> oldMedia = mediaRepository.findById(id);
    checkMediaExists(oldMedia, id);

    return oldMedia.get();
  }

  @Transactional
  @Override
  public Media createMedia(CreateMediaInput createMediaInput) throws IOException {
    UUID name = UUID.randomUUID();
    Optional<Product> oldProduct = productRepository.findById(createMediaInput.getIdProduct());
    checkProductExists(oldProduct, createMediaInput.getIdProduct());
    String fileName = FileUtil.saveFile(name.toString(), CommonConstant.MEDIAS, createMediaInput.getFile());
    String path = UrlUtil.applicationUrl(request) + CommonConstant.URL_MEDIA + fileName;

    Media newMedia = modelMapper.map(createMediaInput, Media.class);
    newMedia.setPath(path);
    newMedia.setProduct(oldProduct.get());
    return mediaRepository.save(newMedia);
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Media> oldMedia = mediaRepository.findById(id);
    checkMediaExists(oldMedia, id);

    oldMedia.get().setDeleteFlag(true);
    mediaRepository.save(oldMedia.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkMediaExists(Optional<Media> media, Long id) {
    if(media.isEmpty()) {
      throw new VsException(UserMessageConstant.Media.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Media.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }

  private void checkProductExists(Optional<Product> product, Long id) {
    if(product.isEmpty()) {
      throw new VsException(UserMessageConstant.Category.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Category.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }
}
