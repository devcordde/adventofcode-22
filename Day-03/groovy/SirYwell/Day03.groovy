import java.nio.file.Files
import java.nio.file.Path

class Day03 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
        println(findCommon 2, bitset(
                lines.collectMany { [it.substring(0, it.length() >> 1), it.substring(it.length() >> 1)] }
        ))
        println(findCommon 3, bitset(lines))
    }

    static List<Long> bitset(List<String> l) {
        l.collect {
            it.chars.collect { it.isUpperCase() ? it - ((char) 'A') + 27 : it - ((char) 'a') + 1 }
                    .inject(0L) { m, v -> m | (1L << v) }
        }
    }

    static int findCommon(int range, List<Long> input) {
        input.collate(range)
                .collect { it.inject(-1L) { m, v -> m & v } }
                .collect { 63 - Long.numberOfLeadingZeros(it) }
                .sum() as int
    }
}
