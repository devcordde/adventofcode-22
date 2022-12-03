defmodule Day2 do
  @wins [{"C", "A"}, {"B", "C"}, {"A", "B"}]
  @loses %{"A" => "C", "C" => "B", "B" => "A"}
  @wins_map %{"C" => "A", "B" => "C", "A" => "B"}
  @scores %{"A" => 1, "B" => 2, "C" => 3}

  def calculate_score({_, me} = round) do
    calculate_score_for_pick(me) + calculate_score_for_ending(round)
  end

  def calculate_score_for_pick(pick), do: @scores[pick]

  def calculate_score_for_ending(round)
  def calculate_score_for_ending({opponent, me}) when opponent == me, do: 3
  def calculate_score_for_ending(round) when round in @wins, do: 6
  def calculate_score_for_ending(_), do: 0

  def win_with(opponent, goal)
  def win_with(opponent, "A"), do: calculate_score({opponent, Map.get(@loses, opponent)})
  def win_with(opponent, "B"), do: calculate_score({opponent, opponent})
  def win_with(opponent, "C"), do: calculate_score({opponent, Map.get(@wins_map, opponent)})
end

rounds =
  for round <- String.split(File.read!("./input.txt"), "\n") do
    String.split(round, " ", trim: true)
    |> List.to_tuple()
  end
  # map X, Y, Z to A, B, C
  # this took way longer than it should've lol
  |> Enum.map(&put_elem(&1, 1, <<:binary.decode_unsigned(elem(&1, 1)) - 23>>))

# part 1
Enum.map(rounds, &Day2.calculate_score/1)
|> Enum.sum()
|> IO.puts()

# part 2
Enum.map(rounds, &Day2.win_with(elem(&1, 0), elem(&1, 1)))
|> Enum.sum()
|> IO.puts()
