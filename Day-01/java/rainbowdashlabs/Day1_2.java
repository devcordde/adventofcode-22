import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static java.util.Arrays.stream;

public class Day1_2 {
    public static void main(String[] args) throws IOException {
        var calories = stream(Files.readString(Path.of("input.txt")).split("\n\n"))
                .mapToInt(group -> -group.lines().mapToInt(Integer::parseInt).sum())
                .sorted()
                .limit(3)
                .toArray();

        System.out.printf("Part 1: %d%n", -calories[0]);

        System.out.printf("Part 2: %d%n", stream(calories).sum());
    }
}
