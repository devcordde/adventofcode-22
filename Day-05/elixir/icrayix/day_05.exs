defmodule Day05 do
  def move_crates(stacks, [count, from, to], opts \\ []) do
    List.update_at(
      stacks,
      to - 1,
      fn stack ->
        stack ++
          (Enum.at(stacks, from - 1)
           |> Enum.take(-count)
           |> then(
             &if Keyword.get(opts, :reverse) do
               Enum.reverse(&1)
             else
               &1
             end
           ))
      end
    )
    |> List.update_at(from - 1, &Enum.drop(&1, -count))
  end
end

[stacks, instructions] =
  File.read!("input.txt")
  |> String.split("\n\n")

# the next 20 lines are pure pain of parsing the stacks dynamically without regex lol
layers =
  stacks
  |> String.split("\n")
  |> then(fn layers -> Enum.reject(layers, &(List.last(layers) == &1)) end)
  |> Enum.map(&String.to_charlist(&1))
  |> Enum.reverse()
  |> Enum.map(&Enum.drop_every(&1, 2))
  |> Enum.map(&Enum.drop_every(' ' ++ &1, 2))

stacks_parsed =
  List.duplicate([], length(Enum.at(layers, 0)))
  |> Enum.with_index()
  |> Enum.map(fn {stack, index} ->
    for layer <- layers do
      stack ++ [Enum.at(layer, index)]
    end
    |> Enum.reject(&(&1 == ' '))
  end)
  |> dbg()

# and now follows parsing the instructions \o/
instructions =
  String.split(instructions, "\n", trim: true)
  |> Enum.map(
    &(String.to_charlist(&1)
      |> Kernel.--('move from to')
      |> String.Chars.to_string()
      |> String.split()
      |> Enum.map(fn char -> String.to_integer(char) end))
  )

# part 1
Enum.reduce(instructions, stacks_parsed, fn instruction, stacks ->
  Day05.move_crates(stacks, instruction, reverse: true)
end)
|> Enum.map(&List.last(&1))
|> Enum.join("")
|> IO.inspect()

# part 2
Enum.reduce(instructions, stacks_parsed, fn instruction, stacks ->
  Day05.move_crates(stacks, instruction)
end)
|> Enum.map(&List.last(&1))
|> Enum.join("")
|> IO.inspect()
