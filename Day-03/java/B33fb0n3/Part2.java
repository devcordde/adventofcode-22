import java.io.*;
import java.util.*;

public class Part2 {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        int prioritySum = 0;
        ArrayList<String> duplicates = new ArrayList<>();
        ArrayList<String> badges = new ArrayList<>();
        while(s.hasNext()){
            final String[] line1 = s.next().split("");
            final String[] line2 = s.next().split("");
            final String[] line3 = s.next().split("");
            HashSet<String> map = new HashSet<String>();
            for (String i : line1)
                map.add(i);
            for (String i : line2) {
                if (map.contains(i))
                    duplicates.add(i);
            }
            for (String i : line3) {
                if (duplicates.contains(i)) {
                    badges.add(i);
                    duplicates.clear();
                    break;
                }
            }
        }

        char[] ch  = badges.toString().toCharArray();
        for(char c : ch){
            int temp = (int)c;
            int lowerCase_temp_integer = 96; //for lower case
            int upperCase_temp_integer = 64; //for upper case
            if(temp<=122 & temp>=97) {
                prioritySum += temp-lowerCase_temp_integer;
            } else if(temp<=90 & temp>=65)
                prioritySum += (temp-upperCase_temp_integer + 26);
        }
        System.out.println("Part 1: " + prioritySum);
    }
}