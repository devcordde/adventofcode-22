import java.io.*;
import java.util.*;

public class Day9 {
    public static void main(String args[]) {
        final InputStream inputStream = System.in;
        Scanner scanner = new Scanner(inputStream);

        List<int[]> positions = Arrays.asList(
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 },
                new int[] { 0,0 }
        );
        Set<String> visited1 = new HashSet<>();
        Set<String> visited9 = new HashSet<>();

        Map<String, int[]> directions = new HashMap<>();
        directions.put("R", new int[] { 1,0 });
        directions.put("L", new int[] { -1,0 });
        directions.put("D", new int[] { 0,1 });
        directions.put("U", new int[] { 0,-1 });

        Set<String> visited;

        while (scanner.hasNext()) {
            String direction = scanner.next();
            int steps = scanner.nextInt();

            for (int step=0; step<steps; step++) {
                positions.get(0)[0] += directions.get(direction)[0];
                positions.get(0)[1] += directions.get(direction)[1];

                for (int i=1;i<positions.size(); i++) {
                    visited = null;
                    if (i==1) {
                        visited = visited1;
                    } else if (i==9) {
                        visited = visited9;
                    }
                    positions.set(i, updateRelativePosition(positions.get(i), positions.get(i-1), visited));
                }
            }
        }

        System.out.println("Solution 1: " + visited1.size());
        System.out.println("Solution 2: " + visited9.size());
    }

    private static int[] updateRelativePosition(int[] me, int[] other, Set<String> visited) {
        if (Math.abs(other[0] - me[0]) > 1) {
            me[0] += (other[0] - me[0]) / 2;
            if (other[1] != me[1]) {
                me[1] += (other[1] - me[1]) / Math.abs(other[1] - me[1]);
            }
        }
        if (Math.abs(other[1] - me[1]) > 1) {
            me[1] += (other[1] - me[1]) / 2;
            if (other[0] != me[0]) {
                me[0] += (other[0] - me[0]) / Math.abs(other[0] - me[0]);
            }
        }
        if (visited != null) {
            visited.add(me[0] +"x"+me[1]);
        }
        return me;
    }
}
