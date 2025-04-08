package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
public class TestController {

        @GetMapping
        public ResponseEntity getTests() {
            return ResponseEntity.ok("all");
        }

        @GetMapping("/1")
        public ResponseEntity getTest() {
            return ResponseEntity.ok("ok");
        }
}
