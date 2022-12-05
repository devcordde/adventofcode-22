import scala.io.Source

object Day3 extends App {

  def readLines(consume: Iterator[String] => Unit): Unit = {
    val source = Source
      .fromFile("input.txt")
    consume(source.getLines())
    source.close()
  }

  val priorities = ('a' to 'z') ++ ('A' to 'Z')

  type Collector = Iterator[String] => Iterator[Seq[Set[Char]]]

  def calculateSum(collectItems: Collector): Unit = {
    readLines { lines =>
      val sum = collectItems(lines)
        .map(_.reduce((f, s) => f.intersect(s)))
        .map(_.head)
        .map(priorities.indexOf(_) + 1)
        .sum

      println(s"Sum of priorities is: $sum")
    }
  }

  // Part #1
  calculateSum(lines =>
    lines.map(line =>
      Seq(
        Set.from(line.substring(0, line.length / 2)),
        Set.from(line.substring(line.length / 2))
      )
    )
  )

  // Part #2
  calculateSum(lines => lines.map(line => Set.from(line)).grouped(3))

}
