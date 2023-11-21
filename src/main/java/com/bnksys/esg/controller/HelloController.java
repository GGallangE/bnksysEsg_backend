package com.bnksys.esg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {

    @GetMapping("/apia/hello")
    public String test() {
        return "Hello, ";
    }
}