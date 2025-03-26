package com.project.dreamshops.service;

import com.project.dreamshops.dto.ImageDto;
import com.project.dreamshops.model.Image;
import com.project.dreamshops.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId) throws IOException, SQLException;
    void updateImage(MultipartFile file,Long id);
}
