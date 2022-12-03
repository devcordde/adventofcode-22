defmodule Day03 do
  def intersect(a, b) do
    Enum.filter(a, fn i -> Enum.any?(b, &(&1 == i)) end)
    |> Enum.uniq()
  end
end

input =
  for rucksack <- String.split(File.read!("input.txt"), "\n", trim: true) do
    to_charlist(rucksack)
    # map chars to priorities
    |> Enum.map(&(&1 - if &1 >= 97 do 96 else 38 end))
  end

import Enum, only: [at: 2]

# part 1
Enum.map(input, fn rucksack ->
  {compartmentOne, compartmentTwo} = Enum.split(rucksack, div(length(rucksack), 2))
  Day03.intersect(compartmentOne, compartmentTwo)
  |> at(0)
end)
|> Enum.sum()
|> IO.puts()

# part 2
Enum.chunk_every(input, 3)
|> then(fn chunks ->
  Enum.map(chunks, &(Day03.intersect(at(&1, 0), at(&1, 1)) |> Day03.intersect(at(&1, 2))))
end)
|> List.flatten()
|> Enum.sum()
|> IO.puts()
