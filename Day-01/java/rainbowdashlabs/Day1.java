import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class Day1 {
    public static void main(String[] args) throws IOException {
        var calories = Arrays.stream(Files.readString(Path.of("input.txt")).split("\n\n"))
                             .map(group -> group.lines()
                                                .mapToInt(Integer::parseInt)
                                                .sum())
                             .toList();

        System.out.printf("Part 1: %d%n", calories.stream()
                                                  .mapToInt(i -> i)
                                                  .max()
                                                  .getAsInt());

        System.out.printf("Part 2: %d%n", calories.stream()
                                                  .sorted(Comparator.reverseOrder())
                                                  .limit(3)
                                                  .mapToInt(i -> i)
                                                  .sum());
    }
}
