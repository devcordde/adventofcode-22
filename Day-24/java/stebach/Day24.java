import java.io.*;
import java.util.*;

public class Day24 {
    private static final int BLIZZARD_NORTH = 0b0001;
    private static final int BLIZZARD_EAST = 0b0010;
    private static final int BLIZZARD_SOUTH = 0b0100;
    private static final int BLIZZARD_WEST = 0b1000;
    private static int height;
    private static int width;
    private static Map<Coordinate, Integer>  blizzards;
    private static Coordinate start;
    private static Coordinate goal;
    private static Set<Coordinate> queue;
    private static List<Coordinate> directions;

    private record Coordinate(int x, int y) {}
    public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

        int row = 0;
        blizzards = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            boolean isTopOrBottomWall = line.charAt(2) == '#';
            width = line.length();
            if (!isTopOrBottomWall) {
                for (int i = 1; i < line.length() - 1; i++) {
                    switch (line.charAt(i)) {
                        case '^' -> blizzards.put(new Coordinate(i, row), BLIZZARD_NORTH);
                        case '>' -> blizzards.put(new Coordinate(i, row), BLIZZARD_EAST);
                        case 'v' -> blizzards.put(new Coordinate(i, row), BLIZZARD_SOUTH);
                        case '<' -> blizzards.put(new Coordinate(i, row), BLIZZARD_WEST);
                    }
                }
            }
            row += 1;
        }
        height = row;

        start = new Coordinate(1,0);
        goal = new Coordinate(width - 2, height-1);

        queue = new HashSet<>();
        queue.add(start);

        directions = Arrays.asList(
                new Coordinate(0,-1),
                new Coordinate(1,0),
                new Coordinate(0,1),
                new Coordinate(-1,0),
                new Coordinate(0,0)
        );


        int runs = 0;
        while (!queue.contains(goal)) {
            runs ++;
            move();
        }

        System.out.println("Solution 1: " + runs);

        queue.clear();
        queue.add(goal);
        while (!queue.contains(start)) {
            runs ++;
            move();
        }
        queue.clear();
        queue.add(start);
        while (!queue.contains(goal)) {
            runs ++;
            move();
        }

        System.out.println("Solution 2: " + runs);
    }

    private static void move() {
        Set<Coordinate> newQueue = new HashSet<>();
        Map<Coordinate, Integer> newBlizzards = new HashMap<>();

        for (Map.Entry<Coordinate, Integer> entry : blizzards.entrySet()) {
            if ((entry.getValue() & BLIZZARD_NORTH) > 0) {
                addBlizzard(newBlizzards, new Coordinate(entry.getKey().x, entry.getKey().y - 1), BLIZZARD_NORTH);
            }
            if ((entry.getValue() & BLIZZARD_EAST) > 0) {
                addBlizzard(newBlizzards, new Coordinate(entry.getKey().x + 1, entry.getKey().y), BLIZZARD_EAST);
            }
            if ((entry.getValue() & BLIZZARD_SOUTH) > 0) {
                addBlizzard(newBlizzards, new Coordinate(entry.getKey().x, entry.getKey().y + 1), BLIZZARD_SOUTH);
            }
            if ((entry.getValue() & BLIZZARD_WEST) > 0) {
                addBlizzard(newBlizzards, new Coordinate(entry.getKey().x - 1, entry.getKey().y), BLIZZARD_WEST);
            }
        }

        blizzards.clear();
        blizzards.putAll(newBlizzards);
        newBlizzards.clear();

        for (Coordinate coordinate : queue) {
            for (Coordinate direction : directions) {
                Coordinate check = new Coordinate(coordinate.x + direction.x, coordinate.y + direction.y);

                if (
                        (
                                (check.x > 0 && check.x < width - 1 && check.y > 0 && check.y < height - 1)
                                        || check.equals(start) || check.equals(goal)
                        )
                                && !blizzards.containsKey(check)
                ) {
                    newQueue.add(check);
                }
            }
        }

        queue.clear();
        queue.addAll(newQueue);
        newQueue.clear();
    }

    private static void addBlizzard(Map<Coordinate, Integer> blizzards, Coordinate coordinate, int direction) {
        if (coordinate.x == 0) {
            coordinate = new Coordinate(width - 2, coordinate.y);
        } else if (coordinate.x == width - 1) {
            coordinate = new Coordinate(1, coordinate.y);
        }
        if (coordinate.y == 0) {
            coordinate = new Coordinate(coordinate.x, height - 2);
        } else if (coordinate.y == height - 1) {
            coordinate = new Coordinate(coordinate.x, 1);
        }

        if (blizzards.containsKey(coordinate)) {
            direction = direction | blizzards.get(coordinate);
        }

        blizzards.put(coordinate, direction);
    }
}
