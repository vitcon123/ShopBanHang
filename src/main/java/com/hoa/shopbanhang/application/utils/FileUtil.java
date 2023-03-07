package com.hoa.shopbanhang.application.utils;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUtil {
  private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

  public static final Path RESOURCES_PATH = CURRENT_FOLDER.resolve(Paths.get("src/main/resources"));

  //Save file upload to Resources
  //Example: saveFile("xxx", "upload/xxx/xxx", file)
  public static String saveFile(String newFileName, String uploadPath, MultipartFile multipartFile) throws IOException {
    Path path = RESOURCES_PATH.resolve(Paths.get(uploadPath));
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    String fileType = fileName.substring(fileName.lastIndexOf("."));
    String newFile = newFileName + fileType;
    Path filePath;
    try (InputStream inputStream = multipartFile.getInputStream()) {
      filePath = path.resolve(newFile);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {
      throw new IOException("Could not save file: " + fileName);
    }
    return newFile;
  }

  @SneakyThrows
  public static String uploadFile(MultipartFile multipartFile) {
    Path staticPath = Paths.get("static");
    Path fileMailPath = Paths.get("file_mail");
    if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(fileMailPath))) {
      Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(fileMailPath));
      Files.createDirectories(CURRENT_FOLDER);
    }
    Path file = CURRENT_FOLDER.resolve(staticPath)
        .resolve(fileMailPath).resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    try (OutputStream os = Files.newOutputStream(file)) {
      os.write(multipartFile.getBytes());
    }
    return fileMailPath.resolve(multipartFile.getOriginalFilename()).toString();
  }


  public static File convertMultipartToFile(MultipartFile file) throws IOException {
    File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
}
