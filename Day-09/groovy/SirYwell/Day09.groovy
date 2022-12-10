import java.nio.file.Files
import java.nio.file.Path
class Day09 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
                .collect { [it[0], it.substring(2).toInteger()]}
        simulate lines, 2
        simulate lines, 10
    }

    private static void simulate(List<List<Serializable>> lines, int knots) {
        def xs = new int[knots]
        def ys = new int[knots]
        def visited = [[xs[knots - 1], ys[knots - 1]]].toSet()
        for (instr in lines) {
            int n = instr[1]
            for (i in 0..<n) {
                switch (instr[0]) {
                    case "R" -> xs[0]++
                    case "U" -> ys[0]++
                    case "L" -> xs[0]--
                    case "D" -> ys[0]--
                }
                for (k in 1..<knots) {
                    if ((Math.abs(xs[k] - xs[k - 1]) | Math.abs(ys[k] - ys[k - 1])) <= 1)  continue
                    xs[k] += Integer.compare xs[k - 1], xs[k]
                    ys[k] += Integer.compare ys[k - 1], ys[k]
                }
                visited.add([xs[knots - 1], ys[knots - 1]])
            }
        }
    }
}
