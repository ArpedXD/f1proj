package com.example.demo.controller;
import com.example.demo.Logics.pingponglogic;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pingpong")
@CrossOrigin(origins = "*")
public class pingpong {

    private final pingponglogic service;

    public pingpong(pingponglogic service) {
        this.service = service;
    }

    @PostMapping("/end")
    public void Score(@RequestBody String inp){
        service.Score(inp);
    }
}

