package com.project.dreamshops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Data
@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String filetype;
    @JsonIgnore
    @Lob
    private Blob image;
    private String downloadUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
