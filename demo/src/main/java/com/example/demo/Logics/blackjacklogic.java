package com.example.demo.Logics;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import com.example.demo.database.databases;
import com.example.demo.base.base;

@Service
public class blackjacklogic {

    private final Random rand = new Random();
    private int total = 0;
    private int enemy_total = 0;

    public void start() {
        total = 0;
        enemy_total = 0;
    }

    public int getnum(){
        int value = rand.nextInt(10) + 1;
        total += value;
        return value;
    }

    public int getEnemy(){
        int value = rand.nextInt(10) + 1;
        enemy_total += value;
        return value;
    }

    public int getTotal(){
        return total;
    }

    public int getEnemyTotal(){
        return enemy_total;
    }

    public void Score(String input){
        if(Objects.equals(input, "win")) {
            databases.win(base.username,"Blackjack");
        } else if (Objects.equals(input,"lose")) {
            databases.lose(base.username,"Blackjack");
        }
    }

    public Map<String, Integer> stats(){
        return databases.stats(base.username,"Blackjack");
    }
}