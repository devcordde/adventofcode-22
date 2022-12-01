import scala.io.Source

object Main extends App {
  val source = Source.fromFile("input.txt")

  val elves = source
    .getLines()
    .mkString(",")
    .split(",,")
    .map(l => l.split(",").map(_.toInt).sum)
    .zipWithIndex
    .sortBy(_._1)
    .map(_.swap)
    .reverse

  // Part #1
  val (index, calories) = elves.head
  println(s"Elf number ${index + 1} has $calories calories")

  // Part #2
  val topThreeCalories = elves.take(3).map(_._2).sum
  println(s"Top three elves have $topThreeCalories calories")

  source.close()
}
