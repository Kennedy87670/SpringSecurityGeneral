package com.generaSrpinglSecurity.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class DemoController {


    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("hello from secret url");

    }


    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Hello Admin");
    }
}
