defmodule DayOne do
  def part_one() do
    input()
    |> Enum.map(fn x -> Enum.map(x, &String.to_integer/1) end)
    |> Enum.map(&Enum.sum(&1))

    |> Enum.max()
    |> IO.puts()
  end

  def part_two() do
    input()
    |> Enum.map(fn x -> Enum.map(x, &String.to_integer/1) end)
    |> Enum.map(&Enum.sum(&1))

    |> Enum.sort()
    |> Enum.reverse()
    |> Enum.take(3)
    |> Enum.sum()
    |> IO.puts()
  end

  defp input() do
    File.read!("input.txt")
    |> String.split("\n\n")
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.split(&1, "\n"))
  end
end

DayOne.part_one()
DayOne.part_two()
