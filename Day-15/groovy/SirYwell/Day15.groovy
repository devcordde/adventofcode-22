import java.nio.file.Files
import java.nio.file.Path

class Day15 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))

        def points = lines.collect { it.split(" ") }
                .collect { [it[2], it[3], it[8], it[9]].collect { it.split("=")[1].replaceAll("[^-\\d]", "").toInteger() } }
        def distances = points.collect { Math.abs(it[0] - it[2]) + Math.abs(it[1] - it[3]) }

        def row = 2_000_000
        def rowPoints = new HashSet<Integer>()
        for (i in 0..<points.size()) {
            def x = points[i][0]
            def y = points[i][1]
            def rowRest = distances[i] - Math.abs(y - row)
            if (rowRest >= 0) {
                rowPoints.addAll((x - rowRest)..x)
                rowPoints.addAll(x..(x + rowRest))
            }
        }
        for (p in points) {
            if (p[3] == row) {
                rowPoints.remove(p[2])
            }
        }
        println rowPoints.size()
        int upperLimit = 4_000_000
        MultiRange[] multiRanges = new MultiRange[upperLimit + 1]
        Arrays.parallelSetAll(multiRanges) { new MultiRange() }
        for (i in 0..<points.size()) {
            def x = points[i][0]
            def y = points[i][1]
            def dist = distances[i]
            (Math.max(0, y - dist)..Math.min(upperLimit, y + dist)).parallelStream()
                    .forEach { yy ->
                        def rest = dist - Math.abs(y - yy)
                        def min = Math.min(upperLimit, Math.max(0, x - rest))
                        def max = Math.min(upperLimit, Math.max(0, x + rest))
                        multiRanges[yy].insert([min, max])
                    }
        }
        multiRanges.each { it.compact() }
        def all = multiRanges
                .findIndexValues { it.ranges.size() > 1 }
        println((multiRanges[all[0] as int].ranges.firstEntry().value + 1L) * upperLimit + all[0])
    }

    static class MultiRange {
        TreeMap<Integer, Integer> ranges = new TreeMap<>()

        void insert(List<Integer> range) {
            def result = range
            def lo = ranges.floorEntry(range[0])
            def hi = ranges.ceilingEntry(range[0])
            int maxHi = range[0]
            // lo:     ++++
            // result:    ++++
            //         ^     ^
            if (lo != null && lo.value + 1 >= result[0]) {
                // merge with lo
                result = [lo.key, Math.max(lo.value, result[1])]
            }
            // hi:        ++++
            // result: ++++
            //         ^     ^
            if (hi != null && hi.key <= result[1] + 1) {
                // merge with hi
                result = [result[0], Math.max(result[1], hi.value)]
                maxHi = hi.key
            }
            Iterator<Map.Entry<Integer, Integer>> iterator = ranges.tailMap(result[0]).iterator()
            // subSet(...).clear() is too slow
            while (iterator.hasNext() && iterator.next().key <= maxHi) {
                iterator.remove()
            }
            ranges.put(result[0], result[1])
        }

        void compact() {
            def old = ranges
            ranges = new TreeMap<>()
            old.each { insert([it.key, it.value]) }
        }
    }
}
