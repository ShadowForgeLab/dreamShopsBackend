package com.project.dreamshops.service;

import com.project.dreamshops.dto.ImageDto;
import com.project.dreamshops.exception.ResourceNotFoundException;
import com.project.dreamshops.model.Image;
import com.project.dreamshops.model.Product;
import com.project.dreamshops.repo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Image not Found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,()->{throw new ResourceNotFoundException("image not found");});
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> SavedImageDtos=new ArrayList<>();
        for (MultipartFile file :files){
            try{
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFiletype(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = "/api/v1/images/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepo.save(image);

                savedImage.setDownloadUrl("/api/v1/images/download/" + savedImage.getId());
                imageRepo.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setName(savedImage.getFilename());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                SavedImageDtos.add(imageDto);
            }
            catch (IOException | SQLException e){

            }
;        }
        return SavedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long id) {
        try {
            Image image=getImageById(id);
            image.setFilename(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (IOException |SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
