import java.nio.file.Files
import java.nio.file.Path
import java.util.function.IntPredicate

class Day08 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
                .collect { it.toCharArray().collect { it - ((char) '0') } }
        int height = lines.size()
        int width = lines[0].size()
        int visibleCount = 0
        int maxScenicScore = 0
        for (h in 0..<height) {
            for (w in 0..<width) {
                int v = lines[h][w]
                if ((0..<h).every { lines[it][w] < v } ||
                        (h<..<height).every { lines[it][w] < v } ||
                        (0..<w).every { lines[h][it] < v } ||
                        (w<..<width).every { lines[h][it] < v }
                ) visibleCount++
                def up = calcDistance((0..<h).reverse()) { lines[it][w] >= v }
                def down = calcDistance((h<..<height)) { lines[it][w] >= v }
                def left = calcDistance((0..<w).reverse()) { lines[h][it] >= v }
                def right = calcDistance((w<..<width)) { lines[h][it] >= v }
                maxScenicScore = Math.max(up * down * left * right, maxScenicScore)
            }
        }
        println visibleCount
        println maxScenicScore
    }

    static int calcDistance(Iterable<Integer> it, IntPredicate a) {
        int pos = 0
        def iter = it.iterator()
        while (iter.hasNext()) {
            pos++
            if (a(iter.next())) {
                return pos
            }
        }
        return pos
    }
}
