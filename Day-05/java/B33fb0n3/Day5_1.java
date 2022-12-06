import java.io.*;
import java.util.*;

public class Day5_1 {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("");
        int dockNumber = 1;
        Map<Integer, LinkedList<String>> map = new HashMap<>();
        while(s.hasNextLine()) {
            String[] lines = s.nextLine().replace("[", "").replace("]", "").replace("    ", " .").split(" ");
            String b = "";
            for (String a : lines)
                b = b + a;

            int tempDock = 1;
            for(String letter : b.split("")) {
                if(!letter.equals(".")) {
                    LinkedList<String> currentDock = map.getOrDefault(tempDock, new LinkedList<String>());
                    currentDock.add(letter);
                    map.put(tempDock, currentDock);
                }
                tempDock++;
            }
        }

        for(int i = 0; i < ((args.length) / 5); i++) {
            if(((i*6)+1) > 3024)
                break;
            int howMany = Integer.parseInt(args[(i*6)+1]);
            int fromPosition = Integer.parseInt(args[(i*6)+3]);
            int toPosition = Integer.parseInt(args[(i*6)+5]);
            LinkedList<String> from = map.get(fromPosition);
            LinkedList<String> to = map.get(toPosition);
            LinkedList<String> tempMover = new LinkedList<String>();
            for(int c = 0; c < howMany; c++) {
                tempMover.add(from.get(0));
                from.remove(0);
            }
            for(String move : tempMover)
                to.addFirst(move);
            map.put(fromPosition, from);
            map.put(toPosition, to);
        }

        for(LinkedList<String> list : map.values())
            System.out.println(list.get(0));
    }
}
