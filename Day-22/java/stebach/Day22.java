import java.io.*;
import java.util.*;

public class Day22 {
    private static List<char[]> grid;
    private static List<Integer> instructions;
    private static int sideSize;

   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);

       grid = new ArrayList<>();
       instructions = new ArrayList<>();
       int maxLen = 0;
       while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           if (line.length() == 0) {
               line = scanner.nextLine();
               StringBuilder currentNumber = new StringBuilder();
               for (char c : line.toCharArray()) {
                   if (c == 'R' || c == 'L') {
                       instructions.add(Integer.parseInt(currentNumber.toString()));
                       currentNumber = new StringBuilder();
                       instructions.add(c == 'R' ? -90 : 90);
                   } else {
                       currentNumber.append(c);
                   }
               }
               instructions.add(Integer.parseInt(currentNumber.toString()));
           } else {
               if (line.length() > maxLen) {
                   maxLen = line.length();
               }
               grid.add(line.toCharArray());
           }
       }

       for (int i = 0; i < grid.size(); i++) {
           if (grid.get(i).length < maxLen) {
               char[] newRow = new char[maxLen];
               System.arraycopy(grid.get(i), 0, newRow, 0, grid.get(i).length);
               for (int j = grid.get(i).length; j < maxLen; j++) {
                   newRow[j] = ' ';
               }
               grid.set(i, newRow);
           }
       }

       if (grid.size() / 3 == grid.get(0).length / 4) {
           sideSize = grid.size() / 3;
       } else if (grid.size() / 4 == grid.get(0).length / 3) {
           sideSize = grid.size() / 4;
       } else {
           throw new RuntimeException("size ratio not recognized");
       }

       System.out.println("Solution 1: " + solve(false));
       System.out.println("Solution 2: " + solve(true));
   }

    private static int solve(boolean part2) {
        int[] startPos = findStartPos();
        int x = startPos[0];
        int y = startPos[1];


        boolean isTurn = false;
        int[] direction = { 1, 0 };
        for (Integer instruction : instructions) {
            if (isTurn) {
                int cos = (int) Math.cos(Math.toRadians(instruction));
                int sin = (int) Math.sin(Math.toRadians(instruction));
                int newX = direction[0] * cos - direction[1] * sin;
                int newY = direction[0] * sin + direction[1] * cos;
                direction[0] = newX;
                direction[1] = newY;
            } else {
                movement:
                for (int i = 0; i < instruction; i++) {
                    int[] newDirection = direction.clone();
                    int newY = y - newDirection[1];
                    int newX = x + newDirection[0];

                    int[] fixedData = fixData(newX, newY, direction, part2);
                    newX = fixedData[0];
                    newY = fixedData[1];
                    newDirection[0] = fixedData[2];
                    newDirection[1] = fixedData[3];


                    switch (grid.get(newY)[newX]) {
                        case '.':
                            x = newX;
                            y = newY;
                            direction[0] = newDirection[0];
                            direction[1] = newDirection[1];
                            break;
                        case '#':
                            break movement;
                        default:
                            throw new RuntimeException("unknown map tile \"" + grid.get(newY)[newX] + "\" " + newX + " " + newY + " | " + direction[0] + " / " + direction[1]);
                    }
                }
            }
            isTurn = !isTurn;
        }

        int facing = 0;
        if (direction[1] == -1) {
            facing = 1;
        } else if (direction[0] == -1) {
            facing = 2;
        } else if (direction[1] == 1) {
            facing = 3;
        }


        return 1000 * (y + 1) + 4 * (x + 1) + facing;
    }

    private static int[] fixData(int x, int y, int[] direction, boolean part2) {
        int[] newDirection = direction.clone();
        if (!part2) {
            if (grid.size() <= y) {
                y = 0;
            } else if (y < 0) {
                y = grid.size() - 1;
            }
            if (grid.get(y).length <= x) {
                x = 0;
            } else if (x < 0) {
                x = grid.get(y).length - 1;
            }
            while (grid.get(y)[x] == ' ') {
                int[] fixedData = fixData(x + direction[0], y - direction[1], direction, part2);
                x = fixedData[0];
                y = fixedData[1];
            }
        } else {
            if (grid.size() <= y) {
                y = 0;
            } else if (y < 0) {
                y = grid.size() - 1;
            }
            if (grid.get(y).length <= x) {
                x = 0;
            } else if (x < 0) {
                x = grid.get(y).length - 1;
            }
            if (grid.get(y)[x] == ' ') {
                // look for other side


                if (newDirection[0] == -1) {
                    if (y < 50) {
                        x = 0;
                        y = 149 - y;
                        newDirection[0] = 1;
                    } else if (y <= 99) {
                        x = y - 50;
                        y = 100;
                        newDirection[0] = 0;
                        newDirection[1] = -1;
                    } else if (y <= 149) {
                        x = 50;
                        y = 149 - y;
                        newDirection[0] = 1;
                    } else if (y <= 199) {
                        x = y - 100;
                        y = 0;
                        newDirection[0] = 0;
                        newDirection[1] = -1;
                    }
                } else if (newDirection[0] == 1) {
                    if (y <= 49) {
                        x = 99;
                        y = 149 - y;
                        newDirection[0] = -1;
                    } else if (y <= 99) {
                        x = y + sideSize;
                        y = 49;
                        newDirection[0] = 0;
                        newDirection[1] = 1;
                    } else if (y <= 149) {
                        y = 3 * sideSize - 1 - y;
                        x = 149;
                        newDirection[0] = -1;
                    } else if (y <= 199) {
                        x = y - 100;
                        y = 149;
                        newDirection[0] = 0;
                        newDirection[1] = 1;
                    }
                } else if (newDirection[1] == -1) {
                    if (x <= 49) {
                        x += 2 * sideSize;
                    } else if (x <= 99) {
                        y = 2 * sideSize + x;
                        x = 49;
                        newDirection[0] = -1;
                        newDirection[1] = 0;
                    } else if (x <= 150) {
                        y = x - sideSize;
                        x = 99;
                        newDirection[0] = -1;
                        newDirection[1] = 0;
                    }
                } else if (newDirection[1] == 1) {
                    if (x <= 49) {
                        y = sideSize + x;
                        x = 50;
                        newDirection[0] = 1;
                        newDirection[1] = 0;
                    } else if (x <= 99) {
                        y = 2 * sideSize + x;
                        x = 0;
                        newDirection[0] = 1;
                        newDirection[1] = 0;
                    } else if (x <= 149) {
                        x -= 2 * sideSize;
                    }
                }
            }
        }
        return new int[] { x, y, newDirection[0], newDirection[1] };
    }


    private static int[] findStartPos() {
        int x = 0;
        int y = 0;
        outer:
        for (int startY = 0; startY < grid.size(); startY ++) {
            for (int startX = 0; startX < grid.get(startY).length; startX ++) {
                if (grid.get(startY)[startX] == '.') {
                    x = startX;
                    y = startY;
                    break outer;
                }
            }
        }
        return new int[] {x,y};
    }
}
