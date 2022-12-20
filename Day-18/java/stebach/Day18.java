import java.io.*;
import java.util.*;

public class Day18 {
    private static List<int[]> directionsToExpand;

    public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);
       List<Coord> coords = new ArrayList<>();
       while (scanner.hasNextLine()) {
           coords.add(new Coord(Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray()));
       }

       int connectedSides = 0;
       Coord coord1;
       Coord coord2;
       for (int i = 0; i < coords.size()-1; i ++) {
           coord1 = coords.get(i);
           for (int j = i + 1; j < coords.size(); j ++) {
               coord2 = coords.get(j);
               if (coord1.isTouching(coord2)) {
                   connectedSides ++;
               }
           }
       }

       Coord edgeCoord = coords.stream().min(Comparator.comparingInt(o -> o.x)).get();

       directionsToExpand = new ArrayList<>();
       directionsToExpand.add(new int[] { -1,0,0 });
       directionsToExpand.add(new int[] { 1,0,0 });
       directionsToExpand.add(new int[] { 0,-1,0 });
       directionsToExpand.add(new int[] { 0,1,0 });
       directionsToExpand.add(new int[] { 0,0,-1 });
       directionsToExpand.add(new int[] { 0,0,1 });

       Set<Coord> wrapper = new HashSet<>();
       wrapper.add(edgeCoord.shifted(directionsToExpand.get(0)));

       Set<Coord> wrapperPartsToCheck = new HashSet<>();
       wrapperPartsToCheck.add(edgeCoord.shifted(directionsToExpand.get(0)));
       Set<Coord> newWrapperPieces = new HashSet<>();


       while (!wrapperPartsToCheck.isEmpty()) {
           newWrapperPieces.clear();
           for (Coord partToCheck : wrapperPartsToCheck) {
               newWrapperPieces.addAll(partToCheck.expand(wrapper, coords));
           }
           wrapper.addAll(newWrapperPieces);
           wrapperPartsToCheck.clear();
           wrapperPartsToCheck.addAll(newWrapperPieces);
       }

       int outerSides = 0;
       for (Coord w : wrapper) {
           for (Coord coord : coords) {
               if (w.isTouching(coord)) {
                   outerSides ++;
               }
           }
       }

       System.out.println("Solution 1: " + (6 * coords.size() - 2 * connectedSides));
       System.out.println("Solution 2: " + outerSides);
   }

    private static class Coord {
        private final int x;
        private final int y;
        private final int z;

        public Coord(int[] coords) {
            x = coords[0];
            y = coords[1];
            z = coords[2];
        }

        private boolean isTouching(Coord other) {
            return (x == other.x && y == other.y && Math.abs(z - other.z) == 1)
                    || (x == other.x && Math.abs(y - other.y) == 1 && z == other.z)
                    || (Math.abs(x - other.x) == 1 && y == other.y && z == other.z);
        }

        public Coord shifted(int[] shift) {
            return new Coord(new int[] { x+shift[0], y+shift[1], z+shift[2] });
        }

        public Set<Coord> expand(Set<Coord> wrapper, List<Coord> coords) {
            Set<Coord> retVal = new HashSet<>();

            for (int[] directionToExpand : directionsToExpand) {
                Coord posToCheck = new Coord(new int[] { x + directionToExpand[0], y + directionToExpand[1], z + directionToExpand[2] });
                if (wrapper.contains(posToCheck) || coords.contains(posToCheck)) {
                    continue;
                }
                for (Coord coord : coords) {
                    if (posToCheck.isWithinRange(coord)) {
                        retVal.add(posToCheck);
                        break;
                    }
                }
            }
            return retVal;
        }

        private boolean isWithinRange(Coord coord) {
            return x >= coord.x - 1
                    && x <= coord.x + 1
                    && y >= coord.y - 1
                    && y <= coord.y + 1
                    && z >= coord.z - 1
                    && z <= coord.z + 1
                    && !(x == coord.x && y == coord.y && z == coord.z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return x == coord.x && y == coord.y && z == coord.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
