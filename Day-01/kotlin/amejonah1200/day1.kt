import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
  val calories = Path("input.txt").readText().split("\n\n").map{ it.lines().map{ it.toInt() }.sum() }
  println("Part 1: ${calories.max()}")
  println("Part 2: ${calories.sorted().takeLast(3).sum()}")
}
