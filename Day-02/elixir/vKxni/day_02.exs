defmodule DayTwo do
  def partone do
    scores = %{
      "A Y" => 2 + 6,
      "A X" => 1 + 3,
      "A Z" => 3 + 0,
      "B X" => 1 + 0,
      "B Y" => 2 + 3,
      "B Z" => 3 + 6,
      "C X" => 1 + 6,
      "C Y" => 2 + 0,
      "C Z" => 3 + 3
    }

    File.read!("input.txt")
    |> String.split("\n")
    |> Enum.reduce(0, &(scores[&1] + &2))
    |> IO.inspect()
  end

  def parttwo do
    decide = %{
      "A X" => 3 + 0,
      "A Y" => 1 + 3,
      "A Z" => 2 + 6,
      "B X" => 1 + 0,
      "B Y" => 2 + 3,
      "B Z" => 3 + 6,
      "C X" => 2 + 0,
      "C Y" => 3 + 3,
      "C Z" => 1 + 6
    }

    File.read!("input.txt")
    |> String.split("\n")
    |> Enum.reduce(0, &(decide[&1] + &2))
    |> IO.inspect()
  end
end

DayTwo.partone()
DayTwo.parttwo()
