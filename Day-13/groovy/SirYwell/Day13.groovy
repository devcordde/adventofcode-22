import java.nio.file.Files
import java.nio.file.Path

class Day13 {
    static void main(String[] args) {
        def pairs = Files.readString(Path.of("input.txt")).split("\r?\n\r?\n")
        def loader = new GroovyClassLoader()
        def instances = pairs.collect { it.readLines() }.withIndex()
                .collect { buildClass(it.v2, it.v1[0], it.v1[1]) }
                .collect { loader.parseClass(it).newInstance(new Object[0]) as Base }

        def values = instances.findIndexValues { (orderOfLists(it.left(), it.right()) < 0) }
        println values.collect { it + 1 }.sum()

        def flat = instances.collectMany { [it.left(), it.right()] } + [[[2]], [[6]]]
        flat.sort { List o1, List o2 -> return orderOfLists(o1, o2) }
        println flat.findIndexValues { it == [[2]] || it == [[6]] }.inject { a, b -> (a + 1) * (b + 1) }
    }

    static int orderOfLists(List left, List right) {
        def minSize = Math.min(left.size(), right.size())
        for (i in 0..<minSize) {
            def order = orderOf(left[i], right[i])
            if (order != 0) return order
        }
        return left.size() <=> right.size()
    }

    static int orderOf(def l, def r) {
        if (l instanceof List && r instanceof List) orderOfLists(l, r)
        else if (l instanceof Integer && r instanceof Integer) l <=> r
        else orderOfLists(l instanceof List ? l : [l], r instanceof List ? r : [r])
    }

    static String buildClass(int i, String left, String right) {
        """\
        class X$i implements ${Base.class.name} {
            @Override List left() { $left }
            @Override List right() { $right }
        }
        """.stripMargin()
    }

    interface Base {
        List left()

        List right()
    }
}
