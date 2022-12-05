import scala.io.Source

object Day2 extends App {
  sealed trait HandShape {
    val value: Int
  }

  case object Rock extends HandShape {
    override val value: Int = 1
  }

  case object Paper extends HandShape {
    override val value: Int = 2
  }

  case object Scissors extends HandShape {
    override val value: Int = 3
  }

  def readLines(consume: Iterator[String] => Unit): Unit = {
    val source = Source
      .fromFile("input.txt")
    consume(source.getLines())
    source.close()
  }

  val isWin: Map[HandShape, HandShape] =
    Map(Rock -> Paper, Paper -> Scissors, Scissors -> Rock)
  val isLoose = isWin.map { case (key, value) => (value, key) }

  // Part #1
  readLines { lines =>
    val score = lines
      .map(line => (readHandShape(line.head), readHandShape(line.last)))
      .map { case (opponent, self) => playRound(opponent, self) + self.value }
      .sum

    println(s"Your score is: $score")
  }

  def readHandShape(handShape: Char): HandShape = handShape match {
    case 'A' | 'X' => Rock
    case 'B' | 'Y' => Paper
    case 'C' | 'Z' => Scissors
    case other =>
      throw new IllegalArgumentException(s"unknown hand shape: $other")
  }

  def playRound(opponent: HandShape, self: HandShape): Int = {
    if (opponent == self) {
      return 3
    }

    isWin
      .get(opponent)
      .filter(_ == self)
      .map(_ => 6)
      .getOrElse(0)
  }

  // Part #2
  readLines { lines =>
    val score2 = lines
      .map(line => readHandShapes(line.head, line.last))
      .map { case (opponent, self) => playRound(opponent, self) + self.value }
      .sum

    println(s"Your second score is: $score2")
  }

  def readHandShapes(opponent: Char, self: Char): (HandShape, HandShape) = {
    val opponentHandShape = readHandShape(opponent)

    val selfHandShape = self match {
      case 'X' => isLoose(opponentHandShape)
      case 'Y' => opponentHandShape
      case 'Z' => isWin(opponentHandShape)
    }

    (opponentHandShape, selfHandShape)
  }

}
