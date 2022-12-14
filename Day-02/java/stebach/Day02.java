import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private static final int SCORE_DRAW = 3;
    private static final int SCORE_WIN = 6;

    public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);
       Pattern pattern = Pattern.compile("^([A-C]) ([X-Z])");
       Matcher matcher;
       List<GameStrategy> data = new ArrayList<>();

       while (scanner.hasNextLine()) {
           matcher = pattern.matcher(scanner.nextLine());
           if (matcher.find()){
               data.add(new GameStrategy(matcher));
           } else {
               throw new RuntimeException("no match found");
           }

       }

       int score1 = 0;
       int score2 = 0;
       for (GameStrategy game : data) {
           if (game.isDraw()) {
               score1 += SCORE_DRAW + game.getAttribute2();
           } else if (game.isDefeat()) {
               score1 += game.getAttribute2();
           } else {
               score1 += SCORE_WIN + game.getAttribute2();
           }
           if (game.getAttribute2() == GameStrategy.RESULT_I_SHOULD_WIN) {
               score2 += SCORE_WIN + game.getShapeToWin();
           } else if (game.getAttribute2() == GameStrategy.RESULT_I_SHOULD_LOSE) {
               score2 += game.getShapeToLose();
           } else { //game.getExpectedResult() == GameStrategy.RESULT_I_SHOULD_DRAW
               score2 += SCORE_DRAW + game.getShapeToDraw();
           }
       }
       System.out.println("Solution 1: " + score1);
       System.out.println("Solution 2: " + score2);
   }

    private static class GameStrategy {
        public static final int SHAPE_ROCK = 1;
        public static final int SHAPE_PAPER = 2;
        public static final int SHAPE_SCISSORS = 3;
        public static final int RESULT_I_SHOULD_LOSE = 1;
        public static final int RESULT_I_SHOULD_DRAW = 2;
        public static final int RESULT_I_SHOULD_WIN = 3;
        private int shapeOtherPlayer;
        private int attribute2;

        public GameStrategy(Matcher matcher) {
            shapeOtherPlayer = matcher.group(1).charAt(0) - 64;
            attribute2 = matcher.group(2).charAt(0) - 87;

        }

        public int getAttribute2() {
            return attribute2;
        }

        public int getShapeToWin() {
            if (shapeOtherPlayer < SHAPE_SCISSORS) {
                return shapeOtherPlayer + 1;
            }
            return shapeOtherPlayer - 2;
        }

        public int getShapeToLose() {
            if (shapeOtherPlayer > SHAPE_ROCK) {
                return shapeOtherPlayer - 1;
            }
            return shapeOtherPlayer + 2;
        }

        public int getShapeToDraw() {
            return shapeOtherPlayer;
        }

        public boolean isDraw() {
            return shapeOtherPlayer == attribute2;
        }

        public boolean isDefeat() {
            return shapeOtherPlayer -1 == attribute2 || shapeOtherPlayer + 2 == attribute2;
        }
   }
}
