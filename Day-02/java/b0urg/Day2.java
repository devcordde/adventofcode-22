package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class RockPaperScissors {

    private static Integer score = 0;
    private static Integer secondScore = 0;

    public static void main(String[] args) {
        Path path = Paths.get("input.txt");
        final String[] prev = {""};
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEachOrdered(line -> {
                System.out.println(line);
                String first = line.split(" ")[0];
                String second = line.split("")[2];
                switch (first){
                    case "A" -> {
                        switch (second){
                            case "Y" -> {score += 8; secondScore +=  4; }
                            case "X" -> { score += 4; secondScore += 3; }
                            case "Z" -> { score += 3; secondScore += 8; }
                        }
                    }
                    case "B" -> {
                        switch (second){
                            case "Y" -> { score += 5; secondScore += 5; }
                            case "X" -> { score += 1; secondScore += 1; }
                            case "Z" -> { score += 9; secondScore += 9; }
                        }
                    }
                    default -> {
                        switch (second){
                            case "Y" -> { score += 2; secondScore += 6; }
                            case "X" -> { score += 7; secondScore += 2; }
                            case "Z" -> { score += 6; secondScore += 7; }
                        }
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Frist score : " + score);
        System.out.println("Second score : " + secondScore);
    }
}
