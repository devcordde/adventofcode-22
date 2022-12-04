import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
  val sacks = Path("input.txt").readLines()
  println("Part 1: " + sacks.sumOf { priorityOfIntersect(it.substring(0, it.length / 2).toSet(), it.substring(it.length / 2).toSet()) })
  println("Part 2: " + sacks.chunked(3).sumOf { priorityOfIntersect(*it.map(String::toSet).toTypedArray()) })
}

private fun priorityOfIntersect(vararg iterables: Iterable<Char>) =
  iterables.reduce(Iterable<Char>::intersect).first().let { if (it in 'a'..'z') it - 'a' else it - 'A' + 26 } + 1
