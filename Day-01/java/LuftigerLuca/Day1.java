package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CalorieCounting {

    private static List<Elf> elfList = new ArrayList<>();

    public static void main(String[] args) {
        getElfFromPuzzle();

        elfList.sort(Comparator.comparingInt(Elf::getTotalCalories));
        System.out.println("The most calories is: " + elfList.get(elfList.size() - 1).getTotalCalories());

        int totalThree = elfList.get(elfList.size() - 1).getTotalCalories() + elfList.get(elfList.size() - 2).getTotalCalories() + elfList.get(elfList.size() - 3).getTotalCalories();
        System.out.println("The top three Elfves carrying int total: " + totalThree);

    }

    public static void getElfFromPuzzle() {
        File file = new File("src/main/resources/puzzle.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            List<Integer> currentCalories = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if(line.equals("")){
                    elfList.add(new Elf(currentCalories));
                    currentCalories.clear();
                }else {
                    currentCalories.add(Integer.parseInt(line));
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
record Elf(List<Integer> calories) {
    Elf(List<Integer> calories) {
        this.calories = new ArrayList<>(calories);
    }

    public int getTotalCalories() {
        return calories.stream().reduce(0, Integer::sum);
    }
}