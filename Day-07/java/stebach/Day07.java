import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Day7 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       Map<String, Long> folderSizes = new HashMap<>();
       List<String> currentFolder = new ArrayList<>();
       folderSizes.put("/", 0L);

       while (scanner.hasNextLine()) {
           String[] line = scanner.nextLine().split(" ");
           if ("cd".equals(line[1])) {
               if ("/".equals(line[2])) {
                   currentFolder.clear();
               } else if ("..".equals(line[2])) {
                   currentFolder.remove(currentFolder.size()-1);
               } else {
                   currentFolder.add(line[2]);
               }
           } else if ("ls".equals(line[1]) || "dir".equals(line[0])) {
               // ignore
           } else {
               for (int j=currentFolder.size(); j>=0; j--) {
                   String folder = "/" + String.join("/", currentFolder.subList(0,j));
                   folderSizes.putIfAbsent(folder, 0L);
                   folderSizes.compute(folder, (k,v)->v +=  Integer.parseInt(line[0]));
               }
           }
       }

       long solution1 = 0;
       long solution2 = Long.MAX_VALUE;
       long minSizeForSolution2 = 30000000 - (70000000 - folderSizes.get("/"));
       for (Long value : folderSizes.values()) {
           if (value <= 100000) {
               solution1 += value;
           }
           if (value < solution2 && value >= minSizeForSolution2) {
               solution2 = value;
           }
       }

       System.out.println("Solution 1: " + solution1);
       System.out.println("Solution 2: " + solution2);
   }
}
