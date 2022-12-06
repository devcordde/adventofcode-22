input = File.read!("input.txt") |> String.trim() |> String.to_charlist()

has_uniq_chars = &(length(Enum.uniq(&1)) == length(&1))

get_marker_index =
  &(Enum.reduce_while(input, [], fn x, acc ->
      # check if havent looped for at least 4 chars and check if the last 4 chars are not unique
      if length(acc) < 4 or not (Enum.take(acc, &1) |> has_uniq_chars.()) do
        {:cont, acc ++ [x]}
      else
        {:halt, acc}
      end
    end)
    |> length())

# part 1
get_marker_index.(-4)
|> IO.puts()

# part 2
get_marker_index.(-14)
|> IO.puts()
