import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
  val input = Path("input.txt").readLines()
  println("Part 1: " + solve(input, listOf("BX", "CY", "AZ", "AX", "BY", "CZ", "CX", "AY", "BZ")))
  println("Part 2: " + solve(input, listOf("BX", "CX", "AX", "AY", "BY", "CY", "CZ", "AZ", "BZ")))
}

private fun solve(lines: List<String>, possibilities: List<String>) = lines.sumOf { possibilities.indexOf("${it[0]}${it[2]}") + 1 }
