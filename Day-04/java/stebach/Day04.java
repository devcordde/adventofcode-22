import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       Pattern pattern = Pattern.compile("^([0-9]+)-([0-9]+),([0-9]+)-([0-9]+)$");
       Matcher matcher;

       int solution1 = 0;
       int solution2 = 0;

       while (scanner.hasNextLine()) {
           matcher = pattern.matcher(scanner.nextLine());
           if (matcher.find()) {
               int from1 = Integer.parseInt(matcher.group(1)), to1 = Integer.parseInt(matcher.group(2)), from2 = Integer.parseInt(matcher.group(3)), to2 = Integer.parseInt(matcher.group(4));
               if ((from1 <= from2 && to1 >= to2) || (from2 <= from1 && to2 >= to1)) {
                   solution1 += 1;
               }
               if (!(to1 < from2 || from1 > to2)) {
                   solution2 += 1;
               }
           } else {
               throw new RuntimeException("no match found!");
           }
       }

       System.out.println("Solution 1: " + solution1);
       System.out.println("Solution 2: " + solution2);
   }
}
