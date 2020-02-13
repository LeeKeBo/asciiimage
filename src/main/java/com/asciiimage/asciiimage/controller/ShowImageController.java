package com.asciiimage.asciiimage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowImageController {

    @RequestMapping("/getImage")
    public String getImage(){
        return "/test";
    }
}
