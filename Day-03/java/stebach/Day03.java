import java.io.*;
import java.util.*;

public class Day3 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       int solution1 = 0;
       int solution2 = 0;

       List<String> data = new ArrayList<>();
       while (scanner.hasNextLine()) {
           data.add(scanner.nextLine());
       }

       for (int i=0; i<data.size(); i++) {
           String rucksackContents = data.get(i);
           String compartment1 = rucksackContents.substring(0, rucksackContents.length() / 2);
           String compartment2 = rucksackContents.substring(rucksackContents.length() / 2);

           char item = searchCommonItem(compartment1, new String[] { compartment2 });
           solution1 += priorityForItem(item);

           if (i % 3 == 0) {
               char badgeItm = searchCommonItem(rucksackContents, new String[] {
                       data.get(i + 1),
                       data.get(i + 2)
               });
               solution2 += priorityForItem(badgeItm);
           }
       }

       System.out.println("Solution 1: " + solution1);
       System.out.println("Solution 2: " + solution2);
   }

    private static char searchCommonItem(String first, String[] others) {
        for (char item : first.toCharArray()) {
            boolean wrongItem = false;
            for (String other : others) {
                if (!other.contains(item+"")) {
                    wrongItem = true;
                    break;
                }
            }
            if (!wrongItem) {
                return item;
            }
        }
        throw new RuntimeException("no matching item found");
    }

    private static int priorityForItem(char item) {
        if (item < 'a') {
            return item - 'A' + 27;
        } else {
            return item - 'a' + 1;
        }
    }
}
