input = File.read("input.txt").split("\n")

def simulate(input, reverse)
  stacks = Array.new(9) { [] }
  input.take_while { |line| line.include?("[" )}.each do |line|
    index = line.index("[")
    while index != nil
      stacks[index / 4].unshift(line[index + 1])
      index = line.index("[", index + 1)
    end
  end
  
  input.drop_while { |line| !line.start_with?("move") }.each do |instr|
    arr = instr.split(" ")
    c = arr[1].to_i
    s = arr[3].to_i - 1
    t = arr[5].to_i - 1
    tmp = (0...c).map { |i| stacks[s].pop }
    stacks[t].concat(reverse ? tmp.reverse : tmp)
  end
  stacks.map { |stack| stack.last }.join
end

puts simulate(input, false)
puts simulate(input, true)