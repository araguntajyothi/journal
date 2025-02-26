package com.springboot.rest.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/health-check")
    public String healthCheck() {
        log.info("Health is ok !");
        return "Your Journal App is Successfully deployed, Please use client to test your apis";
    }
}
