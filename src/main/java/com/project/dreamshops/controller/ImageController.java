package com.project.dreamshops.controller;

import com.project.dreamshops.dto.ImageDto;
import com.project.dreamshops.model.Image;
import com.project.dreamshops.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    @Autowired
    private IImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<?> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {

        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return new ResponseEntity<>(imageDtos, HttpStatus.OK);
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Upload Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);

            ByteArrayResource resource = new ByteArrayResource(
                    image.getImage().getBytes(1L, (int) image.getImage().length())
            );

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFiletype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                    .body(resource);
        } catch (SQLException e) {
            e.printStackTrace(); // You can use a logger instead
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    @PutMapping("image/{imageId}/update")
    public ResponseEntity<String> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {

        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return new ResponseEntity<>("Update Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No image to uplaod", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId){
        try
        {
            Image image=imageService.getImageById(imageId);
            if(image != null){
                imageService.deleteImageById(imageId);
                return new ResponseEntity<>("Image deleted succesfully ",HttpStatus.OK);
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Failed to delete",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
