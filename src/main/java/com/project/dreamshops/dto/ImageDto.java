package com.project.dreamshops.dto;

import com.project.dreamshops.model.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

@Data
@NoArgsConstructor
public class ImageDto {
    private  String type;
    private Long id;
    private String fileName;

    private String downloadUrl;

}
