package com.example.demo.controller;
import com.example.demo.Logics.Birdlogic;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bird")
@CrossOrigin(origins = "*")
public class bird {

    private final Birdlogic service;

    public bird(Birdlogic service) {
        this.service = service;
    }

    @PostMapping("/end")
    public void Score(@RequestBody String inp){
        service.Score(inp);
    }
}

