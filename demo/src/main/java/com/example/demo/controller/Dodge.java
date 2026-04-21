package com.example.demo.controller;
import com.example.demo.Logics.Dodgelogic;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Dodge")
@CrossOrigin(origins = "*")
public class Dodge {

    private final Dodgelogic service;

    public Dodge(Dodgelogic service) {
        this.service = service;
    }

    @PostMapping("/end")
    public void Score(@RequestBody String inp){
        service.Score(inp);
    }
}

