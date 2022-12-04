input =
  for pair <- String.split(File.read!("input.txt"), "\n") do
    Enum.map(String.split(pair, ","), fn elf ->
      [start, stop] = Enum.map(String.split(elf, "-"), &String.to_integer(&1))

      MapSet.new(start..stop)
    end)
  end

# part 1
Enum.filter(input, fn [elfOne, elfTwo] ->
  MapSet.subset?(elfOne, elfTwo) or MapSet.subset?(elfTwo, elfOne)
end)
|> Enum.count()
|> IO.puts()

# part 2
Enum.reject(input, fn [elfOne, elfTwo] ->
  MapSet.disjoint?(elfOne, elfTwo)
end)
|> Enum.count()
|> IO.puts()
