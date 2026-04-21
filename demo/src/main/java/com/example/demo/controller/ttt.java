package com.example.demo.controller;
import com.example.demo.Logics.tttlogic;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/ttt")
@CrossOrigin(origins = "*")
public class ttt {
    private final tttlogic service;

    public ttt(tttlogic service){
        this.service = service;
    }

    @GetMapping("/Start")
    public void start(){
        service.start();
    }

    @PostMapping("/move")
    public void move(@RequestBody String input){
        service.move(Integer.parseInt(input));
    }

    @GetMapping("/enemymove")
    public int enemymove(){
        return service.enemymove();
    }

    @GetMapping("/player")
    public char player(){
        return service.player();
    }

    @GetMapping("/Checkwin")
    public int checkwin(){
        return service.checkwin();
    }

    @GetMapping("/stats")
    public Map<String, Integer> stats(){
        return service.stats();
    }
}
