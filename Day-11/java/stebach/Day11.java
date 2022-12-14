import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       List<Monkey> monkeys = new ArrayList<>();
       List<Monkey> monkeysNoRelief = new ArrayList<>();
       String line = "";
       Pattern operationPattern = Pattern.compile("^  Operation: new = old ([+\\-*]) ([0-9]+|old)$");
       Pattern testPattern = Pattern.compile("^  Test: divisible by ([0-9]+)$");
       Pattern targetPattern = Pattern.compile("^    If (true|false): throw to monkey ([0-9]+)$");

       Matcher matcher;

       int commonMultiple = 1;

       while (scanner.hasNext()) {
           Monkey monkey = new Monkey();
           line = scanner.nextLine(); // monkey no
           if (line.length() == 0) {
               continue;
           }

           monkey.setStartingItems(Arrays.stream(scanner.nextLine().split(":")[1].trim().split(",")). mapToLong(r -> Long.parseLong(r.trim())).toArray());

           matcher = operationPattern.matcher(scanner.nextLine());
           if (matcher.find()) {
               if ("old".equals(matcher.group(2))) {
                   monkey.setOperation(matcher.group(1).charAt(0));
               } else {
                   monkey.setOperation(matcher.group(1).charAt(0), Integer.parseInt(matcher.group(2)));
               }
           } else {
               throw new RuntimeException("operation not found");
           }

           matcher = testPattern.matcher(scanner.nextLine());
           if (matcher.find()) {
               monkey.setTest(Integer.parseInt(matcher.group(1)));
               commonMultiple *= Integer.parseInt(matcher.group(1));
           } else {
               throw new RuntimeException("test not found");
           }

           matcher = targetPattern.matcher(scanner.nextLine());
           if (matcher.find()) {
               monkey.setTrueTarget(Integer.parseInt(matcher.group(2)));
           } else {
               throw new RuntimeException("target (true) not found");
           }

           matcher = targetPattern.matcher(scanner.nextLine());
           if (matcher.find()) {
               monkey.setFalseTarget(Integer.parseInt(matcher.group(2)));
           } else {
               throw new RuntimeException("target (false) not found");
           }

           monkeys.add(monkey);
           monkeysNoRelief.add(monkey.withoutRelief());
       }

       for (Monkey monkey : monkeys) {
           monkey.setCommonMultiple(commonMultiple);
       }
       for (Monkey monkey : monkeysNoRelief) {
           monkey.setCommonMultiple(commonMultiple);
       }

       for (int round = 0; round < 20; round++) {
           for (Monkey monkey : monkeys) {
               monkey.throwItems(monkeys);
           }
       }

       Collections.sort(monkeys, (m1, m2) -> {
           if (m1.getInspections() > m2.getInspections()) {
               return -1;
           }
           if (m1.getInspections() < m2.getInspections()) {
               return 1;
           }
           return 0;
       });

       System.out.println("Solution 1: " + monkeys.get(0).getInspections() * monkeys.get(1).getInspections());

       for (int round = 0; round < 10000; round++) {
           for (Monkey monkey : monkeysNoRelief) {
               monkey.throwItems(monkeysNoRelief);
           }
       }

       Collections.sort(monkeysNoRelief, (m1, m2) -> {
           if (m1.getInspections() > m2.getInspections()) {
               return -1;
           }
           if (m1.getInspections() < m2.getInspections()) {
               return 1;
           }
           return 0;
       });

       System.out.println("Solution 2: " + (long)monkeysNoRelief.get(0).getInspections() * (long)monkeysNoRelief.get(1).getInspections());
   }

    private static class Monkey {
        private List<Long> items = new ArrayList<>();
        private char operation;
        private boolean operationAgainstOld;
        private long operationNumber;
        private int testDivisableBy;
        private int targetTrue;
        private int targetFalse;
        private int inspectionCount;
        private boolean noRelief;
        private long commonMultiple;

        public void setStartingItems(long[] startingItems) {
            for (long startingItem : startingItems) {
                items.add(startingItem);
            }
        }

        public void setOperation(char operation) {
            this.operation = operation;
            this.operationAgainstOld = true;
        }

        public void setOperation(char operation, int number) {
            this.operation = operation;
            this.operationNumber = number;
        }

        public void setTest(int number) {
            testDivisableBy = number;
        }

        public void setTrueTarget(int number) {
            targetTrue = number;
        }
        public void setFalseTarget(int number) {
            targetFalse = number;
        }

        public void throwItems(List<Monkey> monkeys) {
            while (!items.isEmpty()) {
                Long item = items.remove(0);
                long number = operationNumber;
                if (operationAgainstOld) {
                    number = item;
                }

                inspectionCount ++;

                switch (operation) {
                    case '*' -> item *= number;
                    case '+' -> item += number;
                    default -> throw new RuntimeException("unknwon operation: " + operation);
                }

                if (!noRelief) {
                    item = Math.floorDiv(item, 3);
                } else {
                    if (item % commonMultiple == 0) {
                        item = commonMultiple;
                    } else {
                        item = item % commonMultiple;
                    }
                }

                if (item % testDivisableBy == 0) {
                    monkeys.get(targetTrue).getNewItem(item);
                } else {
                    monkeys.get(targetFalse).getNewItem(item);
                }


            }
        }

        private void getNewItem(long item) {
            items.add(item);
        }

        public int getInspections() {
            return inspectionCount;
        }

        public Monkey withoutRelief() {
            Monkey retVal = new Monkey();
            retVal.items = new ArrayList<>(items);
            retVal.operation = operation;
            retVal.operationAgainstOld = operationAgainstOld;
            retVal.operationNumber = operationNumber;
            retVal.testDivisableBy = testDivisableBy;
            retVal.targetTrue = targetTrue;
            retVal.targetFalse = targetFalse;
            retVal.inspectionCount = inspectionCount;
            retVal.commonMultiple = commonMultiple;
            retVal.noRelief = true;

            return retVal;
        }

        public void setCommonMultiple(long commonMultiple) {
            this.commonMultiple = commonMultiple;
        }
   }
}
