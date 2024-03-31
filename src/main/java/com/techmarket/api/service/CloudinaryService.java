package com.techmarket.api.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
  @Value("${cloudinary.cloud_name}")
  private String cloudName;

  @Value("${cloudinary.api_key}")
  private String apiKey;

  @Value("${cloudinary.api_secret}")
  private String apiSecret;
  Cloudinary cloudinary;

  @PostConstruct
  public void init() {
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("cloud_name", cloudName);
    valuesMap.put("api_key", apiKey);
    valuesMap.put("api_secret", apiSecret);
    cloudinary = new Cloudinary(valuesMap);
  }
  public String upload(MultipartFile multipartFile) throws IOException {
    File file = convert(multipartFile);
    Map<String, Object> params = new HashMap<>();
    params.put("folder", "api_techmarket");
    Map result = cloudinary.uploader().upload(file,params);
    if (!Files.deleteIfExists(file.toPath())) {
      throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
    }
    return result.get("url").toString();
  }

  public Map delete(String id) throws IOException {
    return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
  }

  private File convert(MultipartFile multipartFile) throws IOException {
    File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    FileOutputStream fo = new FileOutputStream(file);
    fo.write(multipartFile.getBytes());
    fo.close();
    return file;
  }
}
