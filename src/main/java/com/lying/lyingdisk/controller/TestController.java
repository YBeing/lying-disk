package com.lying.lyingdisk.controller;

import com.lying.lyingdisk.aop.annotation.Authorized;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Authorized

public class TestController {
    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
