import java.io.*;
import java.util.*;

public class Day20 {
    record SequenceNumber ( long number, long number2, int id) {}

    public static void main(String args[]) {
        final InputStream inputStream = System.in;
        Scanner scanner = new Scanner(inputStream);

        List<SequenceNumber> origNumbers = new ArrayList<>();
        int i = 0;
        SequenceNumber zeroNumber = null;
        while (scanner.hasNextInt()) {
            int nextInt = scanner.nextInt();
            SequenceNumber nextNumber = new SequenceNumber(nextInt, nextInt * 811589153L, i);
            if (nextNumber.number == 0) {
                zeroNumber = nextNumber;
            }
            origNumbers.add(nextNumber);
            i++;
        }
        List<SequenceNumber> numbers = new ArrayList<>(origNumbers);


        mix(origNumbers, numbers, false);
        int index = numbers.indexOf(zeroNumber);
        long solution1 = numbers.get((index+1000) % numbers.size()).number
                + numbers.get((index+2000) % numbers.size()).number
                + numbers.get((index+3000) % numbers.size()).number;

        numbers.clear();
        numbers.addAll(origNumbers);
        for (int j = 0; j < 10; j++) {
            mix(origNumbers, numbers, true);
        }

        index = numbers.indexOf(zeroNumber);
        long solution2 = numbers.get((index+1000) % numbers.size()).number2
                + numbers.get((index+2000) % numbers.size()).number2
                + numbers.get((index+3000) % numbers.size()).number2;


        System.out.println("Solution 1: " + solution1);
        System.out.println("Solution 2: " + solution2);
    }

    private static void mix(List<SequenceNumber> origNumbers, List<SequenceNumber> numbers, boolean part2) {
        for (SequenceNumber origNumber : origNumbers) {
            int idx = numbers.indexOf(origNumber);
            numbers.remove(idx);

            int pos = (int)(((idx + (part2 ? origNumber.number2 : origNumber.number)) % numbers.size()) + numbers.size()) % numbers.size();
            numbers.add(pos, origNumber);
        }
    }
}
