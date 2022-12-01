import java.io.*;
import java.util.*;

public class Day1 {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n\n");
        ArrayList<Integer> elfs = new ArrayList<>();
        while(s.hasNext()){
            int calories = 0;
            String line = s.next();
            for(String item : line.split("\n")) {
                int itemCalories = Integer.parseInt(item);
                calories += itemCalories;
            }
            elfs.add(calories);
        }
        System.out.println("Part 1: "+Collections.max(elfs));

        // Part 2

        Collections.sort(elfs);
        List<Integer> top3 = new ArrayList<Integer>(elfs.subList(elfs.size() -3, elfs.size()));
        int total = 0;
        for (int current : top3)
            total += current;
        System.out.println("Part 2: "+total);
    }
}
