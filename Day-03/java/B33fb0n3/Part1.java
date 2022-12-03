import java.io.*;
import java.util.*;

public class Part1 {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        int prioritySum = 0;
        ArrayList<String> duplicates = new ArrayList<>();
        while(s.hasNext()){
            String line = s.next();
            String[] firstCompartments = line.substring(0,(line.length()/2)).split("");
            String[] secondCompartments = line.substring((line.length()/2),line.length()).split("");
            HashSet<String> map = new HashSet<String>();
            for (String i : firstCompartments)
                map.add(i);
            for (String i : secondCompartments) {
                if (map.contains(i)) {
                    duplicates.add(i);
                    break;
                }
            }
        }
        char[] ch  = duplicates.toString().toCharArray();
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