import java.nio.file.Files
import java.nio.file.Path
class Day10 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))

        def X = 1
        def cycle = 0
        def s = 0
        List<List<Character>> sprite = []
        def during = { int c, int x ->
            if ((c - 20) % 40 == 0) {
                s += c * x
            }
            if ((c - 1) % 40 == 0) {
                sprite << []
            }
            sprite.last().add (Math.abs(x + 1 - (c % 40)) > 1 ? "." as char : "#" as char)
        }
        for (line in lines) {
            cycle++ // during
            def V = 0
            if (line != "noop") {
                during(cycle, X)
                cycle++
                V = line.split(" ")[1].toInteger()
            }
            during(cycle, X)
            X += V
        }
        println s
        println sprite.collect { it.join("") }.join("\n")

    }
}