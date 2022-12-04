import java.io.*;
import java.util.*;

public class Part1 {
    public static void main(String args[]) {
      Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        int fullyPairs = 0;
        while(s.hasNext()){
            String line = s.next();
            List<Integer> firstGroup = getNumbersInRange(Integer.parseInt(line.split(",")[0].split("-")[0]), Integer.parseInt(line.split(",")[0].split("-")[1]));
            List<Integer> secondGroup = getNumbersInRange(Integer.parseInt(line.split(",")[1].split("-")[0]), Integer.parseInt(line.split(",")[1].split("-")[1]));
            if(firstGroup.containsAll(secondGroup) || secondGroup.containsAll(firstGroup))
                fullyPairs++;
            
        }

        System.out.println("Result: " + fullyPairs);
    }
    
    public static List<Integer> getNumbersInRange(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(i);
        }
        return result;
    }
}

