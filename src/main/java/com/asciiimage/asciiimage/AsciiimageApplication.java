package com.asciiimage.asciiimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@SpringBootApplication
//@PropertySource({"file:src/main/resources/application.properties"})
public class AsciiimageApplication {

    public static void main(String[] args) throws FileNotFoundException {

//        String path = AsciiimageApplication.class.getResource("").toString();
//        System.out.println();
//        System.out.println(ResourceUtils.getFile("classpath:"));
//        System.out.println(ResourceUtils.getFile("file:src/main/resources/test.jpg"));
        SpringApplication.run(AsciiimageApplication.class, args);
    }

}
