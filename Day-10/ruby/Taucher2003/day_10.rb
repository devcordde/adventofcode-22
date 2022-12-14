input = ARGF.read
lines = input.split("\n")

collector = [1]

def build_cycle(collector, instruction)
  if instruction == 'noop'
    collector << collector.last
    return
  end

  split = instruction.split(' ')
  current_x = collector.last
  new_x = current_x + split[1].to_i

  collector << current_x
  collector << new_x
end

lines.each { |line| build_cycle collector, line }

puts "Part 1: #{[20, 60, 100, 140, 180, 220].map { |cycle| collector[cycle - 1] * cycle }.sum}"

puts "Part 2:"

print_crt = ->(value, index) { (index % 40) >= value - 1 && (index % 40) <= value + 1 ? '#' : '.' }
collector.map.with_index(&print_crt).each_slice(40).map(&:join).each { |line| puts line }
