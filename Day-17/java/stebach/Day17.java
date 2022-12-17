import java.io.*;
import java.util.*;

public class Day17 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);


       String line = scanner.nextLine();

       int [] jetPattern = new int[line.length()];
       for (int i = 0; i < line.length(); i++) {
           jetPattern[i] = line.charAt(i) - 61;
       }

       List<RockShape> rockShapes = new ArrayList<>();
       rockShapes.add(new RockShape(new int[][] { {2,3}, {3,3}, {4,3}, {5,3} }));
       rockShapes.add(new RockShape(new int[][] { {3,5}, {2,4}, {3,4}, {4,4}, {3,3} }));
       rockShapes.add(new RockShape(new int[][] { {2,3}, {3,3}, {4,3}, {4,4}, {4,5} }));
       rockShapes.add(new RockShape(new int[][] { {2,3}, {2,4}, {2,5}, {2,6} }));
       rockShapes.add(new RockShape(new int[][] { {2,3}, {3,3}, {2,4}, {3,4} }));

       System.out.println("Solution 1: " + getTopAfterNumberOfRocks(2022, rockShapes, jetPattern));
       System.out.println("Solution 2: " + getTopAfterNumberOfRocks(1_000_000_000_000L, rockShapes, jetPattern));
   }

    private static long getTopAfterNumberOfRocks(long numberOfRocks, List<RockShape> rockShapes, int[] jetPattern) {
        List<int[]> grid = new ArrayList<>();
        int jetPatternIndex = 0;

        int rockShapeIndex = 0;
        RockShape rock = rockShapes.get(rockShapeIndex).newInstance(grid.size());
        rockShapeIndex += 1;

        long landings = 0;

        int checkLen = 100;
        long additionalHeight = 0;
        Map<String, Long[]> checkPositions = new HashMap<>();

        while (!rock.hasLanded()) {
            rock.tryJetPatternMove(jetPattern[jetPatternIndex], grid);
            jetPatternIndex ++;
            if (jetPatternIndex == jetPattern.length) {
                jetPatternIndex = 0;
            }
            rock.tryToFall(grid);

            if (rock.landed) {
                landings += 1;
                if (grid.size() > checkLen) {
                    // make hash & check!
                    String lastLines = getLastLines(grid, checkLen);

                    if (checkPositions.containsKey(lastLines) && additionalHeight == 0) {
                        Long[] match = checkPositions.get(lastLines);

                        long loopRockCount = landings - match[0];
                        long loopHeightDiff = grid.size() - match[1];

                        long additionalLandings = (numberOfRocks - landings) / loopRockCount;
                        additionalHeight = loopHeightDiff * additionalLandings;
                        landings += additionalLandings * loopRockCount;
                    }

                    checkPositions.put(lastLines, new Long[]{ landings, (long)grid.size(), (long)rockShapeIndex });
                }
                if (landings == numberOfRocks) {
                    break;
                }

                rock = rockShapes.get(rockShapeIndex).newInstance(grid.size());
                rockShapeIndex += 1;
                if (rockShapeIndex == rockShapes.size()) {
                    rockShapeIndex = 0;
                }
            }
        }

        return grid.size() + additionalHeight;
    }

    private static String getLastLines(List<int[]> grid, int len) {
        StringBuilder sb = new StringBuilder();
        List<int[]> lines = grid.subList(grid.size() - len, grid.size());
        for (int[] line : lines) {
            for (int i : line) {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    private static class RockShape {
        private final int[][] blocks;
        private boolean landed;

        public RockShape(int[][] blocks) {
            this.blocks = blocks;
        }

        public RockShape newInstance(int currentGridHeight) {
            int[][] myShape = new int[blocks.length][2];
            for (int i = 0; i < this.blocks.length; i++) {
                myShape[i] = this.blocks[i].clone();
                myShape[i][1] += currentGridHeight;
            }
            return new RockShape(myShape);
        }

        public boolean hasLanded() {
            return landed;
        }

        public void tryJetPatternMove(int direction, List<int[]> grid) {
            for (int[] ints : blocks) {
                if (ints[0] + direction < 0 || ints[0] + direction > 6 || (grid.size() > ints[1] && grid.get(ints[1])[ints[0] + direction] == 1)) {
                    return;
                }
            }
            for (int[] block : blocks) {
                block[0] += direction;
            }
        }

        public void tryToFall(List<int[]> grid) {
            for (int[] ints : blocks) {
                if (ints[1] - 1 < 0 || (grid.size() > ints[1] - 1 && grid.get(ints[1] - 1)[ints[0]] == 1)) {
                    landed = true;
                    for (int[] block : blocks) {
                        while (grid.size() <= block[1]) {
                            grid.add(new int[7]);
                        }
                        grid.get(block[1])[block[0]] = 1;
                    }
                    break;
                }
            }
            for (int[] block : blocks) {
                block[1] -= 1;
            }
        }
    }
}
