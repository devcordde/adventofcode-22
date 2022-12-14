import java.io.*;
import java.util.*;

public class Day5 {
    private static List<List<Character>> stacks = new ArrayList<>();
    private static List<List<Character>> stacks2 = new ArrayList<>();

    public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       int line = 0;
       while (scanner.hasNextLine()) {
           if (!parseInitialPositionLine(scanner.nextLine(), line)) {
               break;
           }
           line += 1;
       }
       scanner.nextLine();

       while (scanner.hasNextLine()) {
           String[] instructionParts = scanner.nextLine().split(" ");
           moveCrates(stacks, Integer.parseInt(instructionParts[3]), Integer.parseInt(instructionParts[5]), Integer.parseInt(instructionParts[1]), false);
           moveCrates(stacks2, Integer.parseInt(instructionParts[3]), Integer.parseInt(instructionParts[5]), Integer.parseInt(instructionParts[1]), true);
       }

       System.out.println(getTopCrates(stacks));
       System.out.println(getTopCrates(stacks2));
   }

    private static boolean parseInitialPositionLine(String data, int line) {
        boolean startingStackFound = false;
        for (int charPos=0; charPos<data.length(); charPos+=4) {
            if (data.charAt(charPos) == '[') {
                startingStackFound = true;
                while (stacks.size() < charPos/4+1) {
                    stacks.add(new ArrayList<>());
                }
                while (stacks2.size() < charPos/4+1) {
                    stacks2.add(new ArrayList<>());
                }
                stacks.get(charPos/4).add(0, data.charAt(charPos+1));
                stacks2.get(charPos/4).add(0, data.charAt(charPos+1));
            }
        }
        return startingStackFound;
   }

    private static String getTopCrates(List<List<Character>> stacks) {
        StringBuilder topCrates = new StringBuilder();
        for (List<Character> stack : stacks) {
            if (!stack.isEmpty()) {
                topCrates.append(stack.get(stack.size()-1));
            }
        }
        return topCrates.toString();
    }

    private static void moveCrates(List<List<Character>> stacks, int from, int to, int amount, boolean multipleAtOnce) {
        List<Character> fromStack = stacks.get(from - 1);
        List<Character> toMove = fromStack.subList(fromStack.size() - amount, fromStack.size());
        stacks.set(from-1, fromStack.subList(0, fromStack.size() - amount));
        if (!multipleAtOnce) {
            Collections.reverse(toMove);
        }
        stacks.get(to-1).addAll(toMove);
    }
}
