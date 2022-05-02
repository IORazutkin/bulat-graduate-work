package com.example.bulatgraduatework.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentDto {
  String title;
  MultipartFile file;
}
