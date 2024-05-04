package org.dpu.collageautomationsystemsbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MessagesController {

    @GetMapping("/messages")
    public ResponseEntity<List<String>> message() {
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }
}
