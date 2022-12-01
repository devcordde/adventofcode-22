elves =
  for elf <- String.split(File.read!("./input.txt"), "\n\n") do
    String.split(elf, "\n", trim: true)
    |> Enum.map(&String.to_integer/1)
    |> Enum.sum()
  end

# part 1
IO.puts(Enum.max(elves))

# part 2
elves
|> Enum.sort(:desc)
|> Enum.take(3)
|> Enum.sum
|> IO.puts()
