input = ARGF.read
return if input == nil

STRATEGY = { A: :rock, B: :paper, C: :scissors }
WIN_RULES = { rock: :scissors, paper: :rock, scissors: :paper }
SELECTION_POINTS = { rock: 1, paper: 2, scissors: 3 }

lines = input.split("\n").map { |line| line.split(" ").map(&:to_sym) }

def line_to_strategy(line, strategy)
    [strategy[line[0]], strategy[line[1]]]
end

def win_points(opponent_selection, your_selection)
  return 6 if WIN_RULES[your_selection] == opponent_selection
  return 3 if your_selection == opponent_selection
  0
end

def calculate_points(lines)
    lines.map { |line| win_points(line[0], line[1]) + SELECTION_POINTS[line[1]] }.sum
end

STRATEGY_PART1 = STRATEGY.merge({ X: :rock, Y: :paper, Z: :scissors })

puts "Part 1: #{calculate_points lines.map { |line| line_to_strategy(line, STRATEGY_PART1) }}"

def strategy_part2(line)
    return STRATEGY.merge({ Z: WIN_RULES.key(STRATEGY[line[0]])}) if line[1] == :Z
    return STRATEGY.merge({ Y: STRATEGY[line[0]] }) if line[1] == :Y
    STRATEGY.merge({ X: WIN_RULES[STRATEGY[line[0]]] })
end

puts "Part 2: #{calculate_points lines.map { |line| line_to_strategy(line, strategy_part2(line)) }}"
