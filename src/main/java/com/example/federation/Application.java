package com.example.federation;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
public class Application {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("2056KB"));
        factory.setMaxRequestSize(DataSize.parse("2056KB"));
        return factory.createMultipartConfig();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}