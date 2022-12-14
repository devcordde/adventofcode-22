import java.nio.file.Files
import java.nio.file.Path

class Day14 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
        def rockLines = lines.collect {
            it.split(" -> ").collect { it.split(",").collect { it.toInteger() } }
        }

        def rocks = new LinkedHashSet<List<Integer>>()
        for (line in rockLines) {
            for (i in 1..<line.size()) {
                def start = line[i - 1]
                def end = line[i]
                rocks.addAll((start[0]..end[0]).collectMany { x -> (start[1]..end[1]).collect { y -> [x, y] } })
            }
        }

        def lowestRock = rocks.collect { it[1] }.max()
        def rockAndSand = new HashSet<>(rocks)
        def printFirst = true
        def start = [500, 0]
        def lookup = new ArrayDeque<List<Integer>>()
        while (!rockAndSand.contains([500, 0])) {
            def (x, y) = lookup.pollLast() ?: start
            while (true) {
                if (y < lowestRock + 1 && !rockAndSand.contains([x, y + 1])) {
                    lookup.add([x, y + 1])
                    y++
                } else if (y < lowestRock + 1 && !rockAndSand.contains([x - 1, y + 1])) {
                    lookup.add([x - 1, y + 1])
                    y++; x--
                } else if (y < lowestRock + 1 && !rockAndSand.contains([x + 1, y + 1])) {
                    lookup.add([x + 1, y + 1])
                    y++; x++
                } else {
                    rockAndSand.add([x, y])
                    break
                }
                if (printFirst && y >= lowestRock) {
                    println(rockAndSand.size() - rocks.size())
                    printFirst = false
                }
            }
        }
        println(rockAndSand.size() - rocks.size())
    }
}
