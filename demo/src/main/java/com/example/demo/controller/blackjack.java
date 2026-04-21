package com.example.demo.controller;
import com.example.demo.Logics.blackjacklogic;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/blackjack")
@CrossOrigin(origins = "*")
public class blackjack {

    private final blackjacklogic service;

    public blackjack(blackjacklogic service) {
        this.service = service;
    }

    @GetMapping("/start")
    public void start() {
        service.start();
    }
    @GetMapping("/number")
    public int getNumber() {
        return service.getnum();
    }
    @GetMapping("/number2")
    public int getNumber2() {
        return service.getEnemy();
    }
    @GetMapping("/getTotal")
    public int getTotal(){
        return service.getTotal();
    }
    @GetMapping("/stats")
    public Map<String, Integer> stats(){
        return service.stats();
    }
    @GetMapping("/getEnemy")
    public int getEnemy(){
        return service.getEnemyTotal();
    }
    @PostMapping("/receive")
    public void Score(@RequestBody String input){
        service.Score(input);
    }
}

