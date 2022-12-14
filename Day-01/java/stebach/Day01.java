import java.io.*;
import java.util.*;

public class Day1 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       List<Integer> numbers = new ArrayList<>();
       int currentNumber = 0;
       while (scanner.hasNextLine()) {
           String row = scanner.nextLine();
           if (row.length() > 0) {
               currentNumber += Integer.parseInt(row);
           } else {
               numbers.add(currentNumber);
               currentNumber = 0;
           }
       }
       numbers.add(currentNumber);

       Collections.sort(numbers);
       Collections.reverse(numbers);

       System.out.println("Solution 1: " + numbers.get(0));
       System.out.println("Solution 2: " + (numbers.get(0) + numbers.get(1) + numbers.get(2)));
   }
}
