package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RockPaperScissors {

    public static void main(String[] args) {
        int score = 0;
        int score2 = 0;
        try {
            for (String line : Files.readAllLines(Path.of("src/main/resources/puzzle-day2.txt"))) {
                String first = line.split(" ")[0];
                String second = line.split(" ")[1];

                switch (first){
                    //Rock
                    case "A" -> {
                        //Part 1
                        if (second.equals("Y")) score+=2+6;
                        else if (second.equals("X")) score+=1+3;
                        else if (second.equals("Z")) score+=3;

                        //Part 2
                        if(second.equals("Y")) score2+=1+3;
                        else if(second.equals("X")) score2+=3;
                        else if(second.equals("Z")) score2+=2+6;
                    }
                    //Paper
                    case "B" -> {
                        //Part 1
                        if (second.equals("Y")) score+=2+3;
                        else if (second.equals("X")) score+=1;
                        else if (second.equals("Z")) score+=3+6;

                        //Part 2
                        if(second.equals("Y")) score2+=2+3;
                        else if(second.equals("X")) score2+=1;
                        else if(second.equals("Z")) score2+=3+6;
                    }
                    //Scissors
                    case "C" -> {
                        //Part 1
                        if (second.equals("Y")) score+=2;
                        else if (second.equals("X")) score+=1+6;
                        else if (second.equals("Z")) score+=3+3;

                        //Part 2
                        if(second.equals("Y")) score2+=3+3;
                        else if(second.equals("X")) score2+=2;
                        else if(second.equals("Z")) score2+=1+6;
                    }
                }
            }

            System.out.println("The score is: " + score);
            System.out.println("The score2 is: " + score2);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}