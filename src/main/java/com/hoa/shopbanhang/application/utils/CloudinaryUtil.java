package com.hoa.shopbanhang.application.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hoa.shopbanhang.configs.exceptions.UploadFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public class CloudinaryUtil {

  private static final Cloudinary cloudinary;

  static {
    cloudinary = BeanUtil.getBean(Cloudinary.class);
  }

  public static String getUrlFromFile(MultipartFile multipartFile) {
    try {
      Map<?, ?> map = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
      return map.get("secure_url").toString();
    } catch (IOException e) {
      throw new UploadFileException("Upload file failed");
    }
  }

  public static void removeFileFromUrl(String url) {
    try {
      cloudinary.uploader().destroy(url, ObjectUtils.emptyMap());
    } catch (IOException e) {
      throw new UploadFileException("Destroy file failed");
    }
  }

}
