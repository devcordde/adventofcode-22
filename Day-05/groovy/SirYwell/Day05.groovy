import java.nio.file.Files
import java.nio.file.Path

class Day05 {
    static void main(String[] args) {
        def lines = Files.readAllLines(Path.of("input.txt"))
        println simulate(lines, false)
        println simulate(lines, true)
    }

    private static String simulate(List<String> lines, boolean reverse) {
        List<ArrayDeque<Character>> stacks = (0..<9).collect { new ArrayDeque<Character>() }
        for (line in lines.takeWhile { it.contains("[") }) {
            def index = line.indexOf("[")
            while (index >= 0) {
                stacks[index / 4].addFirst(line.charAt(index + 1))
                index = line.indexOf("[", index + 1)
            }
        }
        for (instr in lines.dropWhile { !it.startsWith("move")}) {
            def arr = instr.split(" ")
            def c = arr[1].toInteger()
            def s = arr[3].toInteger() - 1
            def t = arr[5].toInteger() - 1
            def tmp = (0..<c).collect { stacks[s].removeLast() }
            stacks[t].addAll(reverse ? tmp.reverse() : tmp)
        }
        return stacks.collect { it.last }.join("")
    }
}
