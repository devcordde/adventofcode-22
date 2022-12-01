package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CalorieCounter {

    private static final List<Elf> elvesList = new ArrayList<>();

    public static void main(String[] args) {
        String fileContent;
        try (InputStream in = new FileInputStream("input.txt")){
            fileContent = new String(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] calories = fileContent.split("\\r?\\n");
        ArrayList<Integer> caloriesTotal = new ArrayList<>();
        for (String calorie : calories) {
            if(calorie.isBlank()){
                elvesList.add(new Elf(caloriesTotal));
                caloriesTotal.clear();
            }else {
                caloriesTotal.add(Integer.parseInt(calorie));
            }
        }
        elvesList.sort(Comparator.comparingInt(Elf::getCalories));
        System.out.println("The most calories is: " + elvesList.get(elvesList.size() - 1).getCalories());
        // part 2
        int topThreeTotal = elvesList.get(elvesList.size() - 1).getCalories() + elvesList.get(elvesList.size() -2).getCalories() + elvesList.get(elvesList.size() - 3).getCalories();
        System.out.println("The top three elves are carrying " + topThreeTotal + " calories");
    }
}
record Elf(List<Integer> calories){
    Elf(List<Integer> calories) {
        this.calories = new ArrayList<>(calories);
    }

    public int getCalories() {
        AtomicInteger temp = new AtomicInteger();
        calories.forEach(temp::addAndGet);
        return temp.get();
    }
}