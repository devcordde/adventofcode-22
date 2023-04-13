import java.io.*;
import java.util.*;

public class Day25 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       long sum = 0;
       while (scanner.hasNextLine()) {
           sum += snafu2dec(scanner.nextLine());
       }

       System.out.println("Solution 1: " + dec2snafu(sum));
   }


    public static long snafu2dec(String snafu) {
        long retVal = 0;
        for (char c : snafu.toCharArray()) {
            retVal *= 5;
            if (c == '-') {
                retVal -= 1;
            } else if (c == '=') {
                retVal -= 2;
            } else {
                retVal += c - 48;
            }
        }
        return retVal;
    }

    public static String dec2snafu(long dec) {
        char[] data = Long.toString(dec, 5).toCharArray();
        StringBuilder sb = new StringBuilder();

        boolean incrementNext = false;
        for (int i = data.length - 1; i >= 0; i --) {
            char c = (char)(data[i] + (incrementNext ? 1 : 0));
            incrementNext = false;
            switch (c) {
                case '5' -> {
                    sb.append("0");
                    incrementNext = true;
                }
                case '3' -> {
                    sb.append("=");
                    incrementNext = true;
                }
                case '4' -> {
                    sb.append("-");
                    incrementNext = true;
                }
                default -> sb.append(c);
            }
        }
        if (incrementNext) {
            sb.append('1');
        }

        sb.reverse();
        return sb.toString();
    }

}
