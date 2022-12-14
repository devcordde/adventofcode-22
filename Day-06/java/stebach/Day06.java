import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day6 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);
       char[] data = scanner.nextLine().toCharArray();

       System.out.println("Solution 1: " + getStartOfFirstAllUniqueString(data, 4));
       System.out.println("Solution 2: " + getStartOfFirstAllUniqueString(data, 14));
   }

    private static int getStartOfFirstAllUniqueString(char[] line, int patternLength) {
        RepetitionFinder repetitionFinder = new RepetitionFinder();
        int i = 0;
        AtomicInteger advance = new AtomicInteger();
        while (i < line.length - patternLength)
        {
            if (repetitionFinder.HasRepetition(line, i, patternLength, advance))
            {
                return i + patternLength;
            }

            i += advance.intValue();
        }

        return -1;
    }

    private boolean allCharactersUnique(char[] characters) {
        Set<Character> check = new HashSet<>();
        for (char character : characters) {
            if (!check.add(character)) {
                return false;
            }
        }
        return true;
    }

    private static class RepetitionFinder {
        private static final int NotFound = -1;
        private final int[] _foundCharacterCache = new int[] { NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound, NotFound };

        public boolean HasRepetition(char[] line, int startIndex, int patternLength, AtomicInteger advance) {
            for (int i = 0; i < patternLength; i++)
            {
                int c = line[startIndex + i] - 'a';
                if (_foundCharacterCache[c] != NotFound)
                {
                    advance.set(_foundCharacterCache[c] + 1);
                    CleanupCache(line, startIndex, i);
                    return false;
                }

                _foundCharacterCache[c] = i;
            }

            advance.set(0);
            return true;
        }

        private void CleanupCache(char[] line, int startIndex, int length) {
            for (int i = 0; i < length; i++)
            {
                int c = line[startIndex + i] - 'a';
                _foundCharacterCache[c] = NotFound;
            }
        }
   }
}
