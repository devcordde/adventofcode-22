import java.io.*;
import java.util.*;

public class Day10 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);
       int x = 1;
       int instructionDuration = 1;
       int signalStrength = 0;

       List<Integer> cyclesToCheck = Arrays.asList(20, 60, 100, 140, 180, 220);
       String instruction="";
       StringBuilder display = new StringBuilder();

       for (int cycle = 1; cycle <= 240; cycle++) {
           //start of cycle
           instructionDuration--;
           if (instructionDuration == 0) { // get next instruction
               instruction = scanner.next();
               instructionDuration = "addx".equals(instruction) ? 2 : 1;
           }

           //mid of cycle
           if (cyclesToCheck.contains(cycle)) {
               signalStrength += cycle * x;
           }

           if ((cycle -1) % 40 <= x+1 && (cycle -1) % 40 >= x-1) {
               display.append("█");
           } else {
               display.append(" ");
           }

           if (cycle % 40 == 0) {
               display.append("\n");
           }

           //end of cycle
           if ("addx".equals(instruction) && instructionDuration == 1) {
               x += scanner.nextInt();
           }
       }

       System.out.println("Solution 1: " + signalStrength);
       System.out.println("Solution 2:\n" + display);
   }
}
