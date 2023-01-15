package com.example.federation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
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