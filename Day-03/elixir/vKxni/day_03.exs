defmodule DayThree do
  defp read_input do
    File.read!("input.txt")
    |> String.split("\n")
  end

  def part1 do
    read_input()
    |> split_rs
    |> Enum.map(fn {bin1, bin2} -> find_common_item(bin1, bin2) end)
    |> Enum.map(&score_item/1)
    |> Enum.sum()
    |> IO.inspect()
  end

  def part2 do
    read_input()
    |> Enum.chunk_every(3)
    |> Enum.map(fn [ruck1, ruck2, ruck3] -> find_common_item(ruck1, ruck2, ruck3) end)
    |> Enum.map(&score_item/1)
    |> Enum.sum()
    |> IO.inspect()
  end

  defp split_rs(rucksack) do
    rucksack
    |> Enum.map(fn line ->
      line
      |> String.split_at(div(String.length(line), 2))
    end)
  end

  defp find_common_item(bin1, bin2) do
    str1 = String.graphemes(bin1)
    str2 = String.graphemes(bin2)
    Enum.find(str1, fn a -> a in str2 end)
  end

  defp find_common_item(bin1, bin2, bin3) do
    str1 = String.graphemes(bin1)
    str2 = String.graphemes(bin2)
    str3 = String.graphemes(bin3)
    Enum.find(str1, fn a -> a in str2 && a in str3 end)
  end

  defp score_item(char) do
    "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    |> String.graphemes()
    |> Enum.find_index(fn x -> x == char end)
  end
end

DayThree.part1()
DayThree.part2()
