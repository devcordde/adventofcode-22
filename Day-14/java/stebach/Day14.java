import java.io.*;
import java.util.*;

public class Day14 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       List<List<Integer>> grid = new ArrayList<>();

       while (scanner.hasNextLine()) {
           List<int[]> points = new ArrayList<>(Arrays.stream(scanner.nextLine().split(" -> "))
                   .map(r -> Arrays.stream(r.split(",")).mapToInt(Integer::parseInt).toArray()).toList());
           int[] lastPoint = points.remove(0);
           while (!points.isEmpty()) {
               int[] point = points.remove(0);
               int xDirecton = point[0] == lastPoint[0] ? 0 : (point[0] - lastPoint[0]) / Math.abs(point[0] - lastPoint[0]);
               int yDirecton = point[1] == lastPoint[1] ? 0 : (point[1] - lastPoint[1]) / Math.abs(point[1] - lastPoint[1]);
               boolean stop = false;
               for (int x = lastPoint[0], y = lastPoint[1]; !stop; x += xDirecton, y += yDirecton) {
                   while(grid.size() <= y) {
                       grid.add(new ArrayList<>());
                   }
                   while (grid.get(y).size() <= x) {
                       grid.get(y).add(0);
                   }
                   grid.get(y).set(x,1);
                   stop = (x == point[0] && y == point[1]);
               }
               lastPoint = point;
           }
       }

       int unitsOfSand = 0;
       int unitsOfSand2 = 0;

       while (dropSand(grid, 500, 0, false)) {
           unitsOfSand += 1;
           unitsOfSand2 += 1;
       }

       grid.add(new ArrayList<>());
       while (dropSand(grid, 500, 0, true)) {
           unitsOfSand2 += 1;
       }

       System.out.println("Solution 1: " + unitsOfSand);
       System.out.println("Solution 2: " + unitsOfSand2);
   }

    private static boolean dropSand(List<List<Integer>> grid, int x, int y, boolean withFloor) {
        int xPos = x;
        int yPos = y;

        while (yPos + 1 < grid.size()) {
            while (xPos + 1 >= grid.get(yPos).size()) {
                grid.get(yPos).add(0);
            }
            while (xPos + 1 >= grid.get(yPos + 1).size()) {
                grid.get(yPos + 1).add(0);
            }

            if (grid.get(yPos + 1).get(xPos) == 0) {
                yPos += 1;
            } else if (xPos > 0 && grid.get(yPos + 1).get(xPos - 1) == 0) {
                yPos += 1;
                xPos -= 1;
            } else if (grid.get(yPos + 1).get(xPos + 1) == 0) {
                yPos += 1;
                xPos += 1;
            } else if (grid.get(yPos).get(xPos) == 0) {
                grid.get(yPos).set(xPos, 2);
                return true;
            } else {
                break;
            }
        }
        if (withFloor) {
            grid.get(yPos).set(xPos, 2);
            return  yPos != y || xPos != x;
        }
        return false;
   }
}
