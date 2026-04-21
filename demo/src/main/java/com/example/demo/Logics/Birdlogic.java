package com.example.demo.Logics;

import com.example.demo.database.databases;
import com.example.demo.base.base;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Birdlogic {
    public void Score(String input){
        if(Objects.equals(input, "win")) {
            databases.win(base.username,"Bird");
        } else if (Objects.equals(input,"lose")) {
            databases.lose(base.username,"Bird");
        }
    }
}
