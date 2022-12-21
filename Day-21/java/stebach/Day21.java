import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       Pattern numberPattern = Pattern.compile("^([a-z]{4}): ([0-9]+)$");
       Pattern operatorPattern = Pattern.compile("^([a-z]{4}): ([a-z]{4}) ([\\-/+*]) ([a-z]{4})$");
       Matcher matcher;

       Map<String, Monkey> monkeys = new HashMap<>();
       while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           matcher = numberPattern.matcher(line);
           if (matcher.find()) {
               monkeys.put(matcher.group(1), new NumberMonkey(Long.parseLong(matcher.group(2))));
           } else {
               matcher = operatorPattern.matcher(line);
               if (matcher.find()) {
                   monkeys.put(matcher.group(1), new OperatorMonkey(matcher.group(2), matcher.group(3).charAt(0), matcher.group(4)));
               } else {
                   throw new RuntimeException("NO MATCH!");
               }
           }
       }

       System.out.println("Solution 1: " + (long)monkeys.get("root").getResult(monkeys));

       Monkey monkey1 = monkeys.get(((OperatorMonkey)monkeys.get("root")).monkey1);
       Monkey monkey2 = monkeys.get(((OperatorMonkey)monkeys.get("root")).monkey2);

       NumberMonkey me = (NumberMonkey) monkeys.get("humn");

       double check1 = monkey1.getResult(monkeys);
       double check2 = monkey2.getResult(monkeys);

       me.number += 10;

       double goal;
       Monkey monkeyToCheck;
       int direction = 1;

       if (check1 != monkey1.getResult(monkeys)) {
           goal = check2;
           monkeyToCheck = monkey1;
           if (Math.abs(check2 - check1) < Math.abs(check2 - monkey1.getResult(monkeys))) {
               direction = -1;
           }
       } else {
           goal = check1;
           monkeyToCheck = monkey2;
           if (Math.abs(check1 - check2) < Math.abs(check1 - monkey2.getResult(monkeys))) {
               direction = -1;
           }
       }

       while (monkeyToCheck.getResult(monkeys) != goal) {
           double diff = Math.abs(monkeyToCheck.getResult(monkeys) - goal);
           int multiplicator = 1;
           if (diff > 10_000_000_000L) {
               multiplicator = 100_000_000;
           } else if (diff > 100_000_000L) {
               multiplicator = 1_000_000;
           } else if (diff > 1_000_000L) {
               multiplicator = 10_000;
           } else if (diff > 10_000L) {
               multiplicator = 100;
           }
           me.number += direction * multiplicator;
       }

       System.out.println("Solution 2: " + me.number);
   }

    private interface Monkey {
        double getResult(Map<String, Monkey> monkeys);
    }

    private static class NumberMonkey implements Monkey {
        private long number;

        public NumberMonkey(long number) {
            this.number = number;
        }

        @Override
        public double getResult(Map<String, Monkey> monkeys) {
            return number;
        }
    }

    private static class OperatorMonkey implements Monkey {
        private final String monkey1;
        private final String monkey2;
        private final char operation;

        public OperatorMonkey(String monkey1, char operation, String monkey2) {
            this.monkey1 = monkey1;
            this.monkey2 = monkey2;
            this.operation = operation;
        }

        @Override
        public double getResult(Map<String, Monkey> monkeys) {
            return switch (operation) {
                case '*' -> monkeys.get(monkey1).getResult(monkeys) * monkeys.get(monkey2).getResult(monkeys);
                case '/' -> monkeys.get(monkey1).getResult(monkeys) / monkeys.get(monkey2).getResult(monkeys);
                case '+' -> monkeys.get(monkey1).getResult(monkeys) + monkeys.get(monkey2).getResult(monkeys);
                case '-' -> monkeys.get(monkey1).getResult(monkeys) - monkeys.get(monkey2).getResult(monkeys);
                default -> throw new RuntimeException("unknown operation: " + operation);
            };
        }
    }
}
