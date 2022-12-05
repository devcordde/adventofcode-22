import java.nio.file.Files
import java.nio.file.Path

def lines = Files.readAllLines(Path.of("input.txt"))
def toRange = { String[] s -> (s[0] as int ..(s[1] as int)) }
def ranges = lines.collect { it.split(",").collect { toRange it.split("-") } }
println ranges.findAll { it[0].containsAll(it[1]) || it[1].containsAll(it[0]) }.size()
println ranges.findAll { !it[0].disjoint(it[1]) }.size()
