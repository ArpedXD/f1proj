package com.example.demo.controller;

import com.example.demo.database.highscore;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/controller")
@CrossOrigin(origins = "*")
public class HighscoreController {

    @GetMapping("/highscores")
    public List<Map<String, Object>> getHighscores(
            @RequestParam(required = false) String game) {

        if (game != null && !game.isBlank()) {
            return highscore.getHighscoresByGame(game);
        }
        return highscore.getAllHighscores();
    }
}