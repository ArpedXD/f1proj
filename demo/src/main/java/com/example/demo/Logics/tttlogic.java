package com.example.demo.Logics;

import com.example.demo.base.base;
import com.example.demo.database.databases;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

class ai{
    protected char[] board = new char[9];
    protected char playerChar = 'O';
    protected char AiChar = 'X';

    int[][] winPatterns = {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {1,4,7}, {2,5,8}, // columns
            {0,4,8}, {2,4,6}            // diagonals
    };

    public boolean isBoardFull() {
        for (char c : board) {
            if (c != 'X' && c != 'O') {
                return false;
            }
        }
        return true;
    }

    public void changeChar(){
        char tempA = playerChar;

        playerChar = AiChar;
        AiChar = tempA;
    }
}

@Service
public class tttlogic extends ai{

    public void start(){
        board = new char[9];
    }

    public char player(){
        return playerChar;
    }

    public void move(int input){
        board[input] = playerChar;
    }

    public int checkwin(){
        for (int[] pattern : winPatterns) {
            char firstCell = board[pattern[0]];

            if (firstCell != ' ' &&
                    firstCell == board[pattern[1]] &&
                    firstCell == board[pattern[2]]) {

                if (firstCell == playerChar) {
                    changeChar();
                    System.out.println("You win");
                    databases.win(base.username,"TicTacToe");
                    start();
                    return 1;
                } else if (firstCell == AiChar) {
                    changeChar();
                    System.out.println("AI wins");
                    databases.lose(base.username,"TicTacToe");
                    start();
                    return 2;
                }
            }
        }

        if (isBoardFull()) {
            changeChar();
            System.out.println("Draw");
            start();
            return 3;
        }
        return 0;
    }

    public int enemymove(){
        int move = getRandomMove();
        board[move] = AiChar;
        return move;
    }

    public int getRandomMove() {
        Random rand = new Random();
        int move;

        do {
            move = rand.nextInt(9); // 0-8
        } while (board[move] == 'X' || board[move] == 'O');

        return move;
    }

    public Map<String, Integer> stats(){
        return databases.stats(base.username,"TicTacToe");
    }
}
