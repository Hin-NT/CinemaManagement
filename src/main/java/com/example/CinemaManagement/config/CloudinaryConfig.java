package com.example.CinemaManagement.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "du4kvj6e3",
                "api_key", "435791621522184",
                "api_secret", "fedNaZJOGyfjxZpqzAeUQ5hV8O4",
                "secure", true
        ));
    }

}
