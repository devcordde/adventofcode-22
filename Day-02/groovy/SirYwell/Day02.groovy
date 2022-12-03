import java.nio.file.Files
import java.nio.file.Path
def string = Files.readString(Path.of("input.txt"))
def first = string.readLines().collect { [it.charAt(0) - (char) 'A', it.charAt(2) - (char) 'X'] }
def second = first.collect { [it[0], (it.sum() + 2) % 3] }
static def eval(List<List<Integer>> l) {
    l.collect { (4 - it[0] + it[1]) % 3 * 3 + it[1] + 1 }.sum()
}
println(eval first)
println(eval second)
