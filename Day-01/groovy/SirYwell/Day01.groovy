import java.nio.file.Files
import java.nio.file.Path
def lines = Files.readAllLines(Path.of("input.txt"))
List<List<Integer>> elves = []
elves << []
for (final line in lines) {
    if (line.isEmpty()) {
        elves << []
    } else {
        elves.last() << (line as int)
    }
}
def sorted = elves*.sum().flatten().toSorted().asReversed()
println sorted.first()
println sorted.subList(0, 3).sum()
