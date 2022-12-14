import java.io.*;
import java.util.*;

public class Day12 {
    private static int endX;
    private static int endY;
    private static List<List<Integer>> grid;

   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       grid = new ArrayList<>();
       int startX = 0;
       int startY = 0;
       endX = 0;
       endY = 0;

       List<int[]> lowestPoints = new ArrayList<>();

       int y = 0;
       while (scanner.hasNextLine()) {
           List<Integer> newRow = new ArrayList<>();
           char[] chars = scanner.nextLine().toCharArray();
           for (int x=0; x<chars.length; x++) {
               char c = chars[x];
               if (c == 'S') {
                   startX = x;
                   startY = y;
                   c = 'a';
               } else if (c == 'E') {
                   endX = x;
                   endY = y;
                   c = 'z';
               }
               newRow.add(c - 'a');
               if (c == 'a') {
                   lowestPoints.add(new int[] { x, y });
               }
           }
           grid.add(newRow);
           y += 1;
       }

       Position.setHeight(grid.size());
       Position.setWidth(grid.get(0).size());

       System.out.println("Solution 1: " + getDistanceToEnd(startX, startY));

       List<Integer> distances = new ArrayList<>();
       for (int[] lowestPoint : lowestPoints) {
           distances.add(getDistanceToEnd(lowestPoint[0], lowestPoint[1]));
       }
       Collections.sort(distances);

       System.out.println("Solution 2: " + distances.get(0));
   }

    private static int getDistanceToEnd(int startX, int startY) {
        TreeSet<Position> positionsToCheck = new TreeSet<>((o1, o2) -> {
            if (o1.hashCode() == o2.hashCode()) {
                return 0;
            }
            if (o1.getCost() < o2.getCost()) {
                return -1;
            }
            if (o1.getCost() > o2.getCost()) {
                return 1;
            }
            return 1;
        });
        positionsToCheck.add(new Position(startX, startY, grid, 0));
        List<Integer> checkedPositions = new ArrayList<>();

        int minDistance = Integer.MAX_VALUE;

        while (!positionsToCheck.isEmpty()) {
            Position position = positionsToCheck.pollFirst();

            checkedPositions.add(position.hashCode());

            List<Position> newPositions = position.getReachablePositions();
            for (Position newPosition : newPositions) {
                if (!checkedPositions.contains(newPosition.hashCode())) {
                    if (newPosition.getX() == endX && newPosition.getY() == endY) {
                        minDistance = newPosition.getCost();
                        break;
                    } else if (!positionsToCheck.add(newPosition) && positionsToCheck.stream().filter(r -> r.equals(newPosition)).findFirst().get().getCost() > newPosition.getCost()) {
                        positionsToCheck.remove(newPosition);
                        positionsToCheck.add(newPosition);
                    }
                }
            }
            if (minDistance != Integer.MAX_VALUE) {
                break;
            }
        }
        return minDistance;
    }

    private static class Position {
        private static int height;
        private static int width;
        private final int x;
        private final int y;
        private final List<List<Integer>> grid;
        private final int cost;
        private final int elevation;

        public Position(int x, int y, List<List<Integer>> grid, int cost) {
            this.x = x;
            this.y = y;
            this.grid = grid;
            this.cost = cost;
            this.elevation = grid.get(y).get(x);
        }

        public static void setHeight(int height) {
            Position.height = height;
        }

        public static void setWidth(int width) {
            Position.width = width;
        }

        public List<Position> getReachablePositions() {
            List<Position> retVal = new ArrayList<>();
            if (x > 0 && grid.get(y).get(x-1) <= elevation + 1) {
                retVal.add(new Position(x-1,y, grid, cost+1));
            }
            if (x < width - 1 && grid.get(y).get(x+1) <= elevation + 1) {
                retVal.add(new Position(x+1,y, grid, cost+1));
            }
            if (y > 0 && grid.get(y-1).get(x) <= elevation + 1) {
                retVal.add(new Position(x,y-1, grid, cost+1));
            }
            if (y < height - 1 && grid.get(y+1).get(x) <= elevation + 1) {
                retVal.add(new Position(x,y+1, grid, cost+1));
            }
            return retVal;
        }

        public int getCost() {
            return cost;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return y * (height * 10) + x;
        }

        @Override
        public String toString() {
            return "Position @ " + x + "/" + y;
        }
   }
}
