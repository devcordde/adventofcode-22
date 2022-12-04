defmodule DayFour do
  def part1() do
    input = File.read!("input.txt")
    String.split(input, "\n")
    |> Enum.map(&parse_line/1)
    |> Enum.reduce(0, fn [elf1, elf2], acc -> overlaps_whole(elf1, elf2) + acc end)
    |> IO.puts()
  end

  def part2() do
    input = File.read!("input.txt")
    String.split(input, "\n")
    |> Enum.map(&parse_line/1)
    |> Enum.reduce(0, fn [elf1, elf2], acc -> overlaps_part(elf1, elf2) + acc end)
    |> IO.puts()
  end

  # some helper functions
  defp parse_line(line) do
    String.split(line, ",")
    |> Enum.map(&parse_range/1)
  end

  defp parse_range(str_range) do
    [from, to] = String.split(str_range, "-")
    MapSet.new(String.to_integer(from)..String.to_integer(to))
  end

  defp overlaps_whole(s1, s2) do if MapSet.subset?(s1, s2) || MapSet.subset?(s2, s1) do 1 else 0 end end
  defp overlaps_part(s1, s2) do if MapSet.disjoint?(s1, s2) do 0 else 1 end end
end

DayFour.part1()
DayFour.part2()
