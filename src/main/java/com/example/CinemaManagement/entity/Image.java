package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tbl_image")
@NoArgsConstructor
public class Image {
    @Id
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    public Image(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
