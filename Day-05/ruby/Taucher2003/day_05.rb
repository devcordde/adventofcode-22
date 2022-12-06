input = ARGF.read
return if input.nil?

lines = input.split "\n"

stacks = 9.times.map { [] }

lines.take_while { |line| line.include? '[' }.each do |line|
  index = line.index '['
  until index.nil?
    stacks[index / 4].unshift line[index + 1]
    index = line.index '[', index + 1
  end
end

instructions = lines.drop_while { |line| !line.start_with? 'move' }

def simulate_crane(instructions, stacks, move_multiple: false)
  local_stacks = stacks.map(&:clone)
  instructions.each do |line|
    instruction = line.split ' '
    amount = instruction[1].to_i
    from = instruction[3].to_i - 1
    to = instruction[5].to_i - 1

    tmp = []
    until amount.zero?
      tmp << local_stacks[from].pop
      amount -= 1
    end

    proc = ->(item) { local_stacks[to] << item }

    if move_multiple
      tmp.reverse_each(&proc)
    else
      tmp.each(&proc)
    end
  end

  local_stacks.map(&:last).join
end

puts "Part 1: #{simulate_crane instructions, stacks}"
puts "Part 2: #{simulate_crane instructions, stacks, move_multiple: true}"
