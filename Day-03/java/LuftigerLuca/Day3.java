package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RucksackReorganization {

    public static void main(String[] args) throws IOException {
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int priorityScore = 0;
        int priorityScore2 = 0;

        List<String> group = new ArrayList<>();

        for (String line : Files.readAllLines(Path.of("src/main/resources/puzzle-day3.txt"))) {
            //Part1
            String[] parts = {line.substring(0, line.length()/2), line.substring(line.length()/2)};

            for (char c : alphabet.toCharArray()) {
                if(parts[0].contains(String.valueOf(c)) && parts[1].contains(String.valueOf(c))){
                    priorityScore += alphabet.indexOf(c) + 1;
                }
            }

            //Part2
            group.add(line);
            if(group.size() == 3){
                for (char c : alphabet.toCharArray()) {
                    if(group.get(0).contains(String.valueOf(c)) && group.get(1).contains(String.valueOf(c)) && group.get(2).contains(String.valueOf(c))){
                        priorityScore2 += alphabet.indexOf(c) + 1;
                    }
                }
                group.clear();
            }
        }

        System.out.println("The priority score is: " + priorityScore);
        System.out.println("The priority score2 is: " + priorityScore2);
    }
}