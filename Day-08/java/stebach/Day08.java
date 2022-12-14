import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);
       List<List<Integer>> grid = new ArrayList<>();
       while (scanner.hasNextLine()) {
           grid.add(scanner.nextLine().chars().mapToObj(r->r-'0').collect(Collectors.toList()));
       }

       int visible = 0;
       int bestScore = 0;

       for (int y=0; y<grid.size(); y++) {
           for (int x=0; x<grid.get(y).size(); x++) {
               if (
                       noBiggerTrees(x,y,0,-1,grid)
                               || noBiggerTrees(x,y,1,0,grid)
                               || noBiggerTrees(x,y,0,1,grid)
                               || noBiggerTrees(x,y,-1,0,grid)
               ) {
                   visible += 1;
               }

               int scenicScore = checkVisibleTrees(x,y,0,-1,grid)
                       * checkVisibleTrees(x,y,1,0,grid)
                       * checkVisibleTrees(x,y,0,1,grid)
                       * checkVisibleTrees(x,y,-1,0,grid);
               if (scenicScore > bestScore) {
                   bestScore = scenicScore;
               }
           }
       }

       System.out.println("Solution 1: " + visible);
       System.out.println("Solution 2: " + bestScore);
   }

    private static boolean noBiggerTrees(int startX, int startY, int xMod, int yMod, List<List<Integer>> grid) {
        Integer treeSize = grid.get(startY).get(startX);
        for (int y = startY + yMod, x = startX + xMod; y >= 0 && y < grid.size() && x >= 0 && x < grid.get(y).size(); y += yMod, x += xMod) {
            if (grid.get(y).get(x) >= treeSize) {
                return false;
            }
        }
        return true;
    }

    private static int checkVisibleTrees(int startX, int startY, int xMod, int yMod, List<List<Integer>> grid) {
        int mySize = grid.get(startY).get(startX);
        int visible = 0;
        for (int y = startY + yMod, x = startX + xMod; y >= 0 && y < grid.size() && x >= 0 && x < grid.get(y).size(); y += yMod, x += xMod) {
            visible ++;
            if (grid.get(y).get(x) >= mySize) {
                break;
            }
        }
        return visible;
    }
}
