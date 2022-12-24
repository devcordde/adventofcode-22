import java.io.*;
import java.util.*;

public class Day23 {
    private static List<List<int[]>> directions;
    private static int[][] allDirections;

    public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);


        Map<Coord, Position> grid = new HashMap<>();

        int row = 0;
        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            for (int col = 0; col < line.length; col ++) {
                if (line[col] == '#') {
                    grid.put(new Coord(col, row), new Position(true));
                }
            }
            row += 1;
        }

        directions = new ArrayList<>();
        directions.add(Arrays.asList(new int[] { 0, 1 }, new int[] { -1, 1 }, new int[] { 1, 1 }));
        directions.add(Arrays.asList(new int[] { 0, -1 }, new int[] { -1, -1 }, new int[] { 1, -1 }));
        directions.add(Arrays.asList(new int[] { -1, 0 }, new int[] { -1, 1 }, new int[] { -1, -1 }));
        directions.add(Arrays.asList(new int[] { 1, 0 }, new int[] { 1, 1 }, new int[] { 1, -1 }));

        allDirections = new int[][] {
                { -1, -1 },
                { 0, -1 },
                { 1, -1 },
                { -1, 0 },
                { 1, 0 },
                { -1, 1 },
                { 0, 1 },
                { 1, 1 },
        };

        for (int i = 0; i < 10; i++) {
            doMove(grid);
        }
        int x1 = grid.keySet().stream().mapToInt(r->r.col).min().getAsInt();
        int x2 = grid.keySet().stream().mapToInt(r->r.col).max().getAsInt();
        int y1 = grid.keySet().stream().mapToInt(r->r.row).min().getAsInt();
        int y2 = grid.keySet().stream().mapToInt(r->r.row).max().getAsInt();

        System.out.println("Solution 1: " + ((x2 - x1 + 1) * (y2 - y1 + 1) - grid.size()));

        int moves = 10;

        while (doMove(grid)) {
            moves ++;
        }

       System.out.println("Solution 2: " + (moves + 1));
   }

    private static boolean doMove(Map<Coord, Position> grid) {
        Map<Coord, Position> newGrid = new HashMap<>();
        boolean empty;
        Coord target;

        for (Map.Entry<Coord, Position> entry : grid.entrySet()) {
            boolean allEmpty = true;
            for (int[] allDirection : allDirections) {
                target = new Coord(entry.getKey().col + allDirection[0], entry.getKey().row - allDirection[1]);
                if (grid.containsKey(target)) {
                    allEmpty = false;
                    break;
                }
            }
            if (allEmpty) {
                continue;
            }

            for (List<int[]> direction : directions) {
                empty = true;
                for (int[] check : direction) {
                    target = new Coord(entry.getKey().col + check[0], entry.getKey().row - check[1]);
                    if (grid.containsKey(target)) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    target = new Coord(entry.getKey().col + direction.get(0)[0], entry.getKey().row - direction.get(0)[1]);
                    newGrid.putIfAbsent(target, new Position(false));
                    newGrid.get(target).addProposition(entry.getKey());
                    break;
                }
            }
        }

        boolean didMove = false;
        for (Map.Entry<Coord, Position> entry : newGrid.entrySet()) {
            if (entry.getValue().propositions.size() == 1) {
                didMove = true;
                grid.put(entry.getKey(), grid.get(entry.getValue().propositions.get(0)));
                grid.remove(entry.getValue().propositions.get(0));
            }
        }

        directions.add(directions.remove(0));

        return didMove;
    }

    private static class Position {
        private final boolean hasElf;
        List<Coord> propositions = new ArrayList<>();

        public Position(boolean hasElf) {
            this.hasElf = hasElf;
        }

        @Override
        public String toString() {
            return "[" + hasElf + "]";
        }

        public void addProposition(Coord coord) {
            propositions.add(coord);
        }
    }

    private record Coord(int col, int row) {};
}
