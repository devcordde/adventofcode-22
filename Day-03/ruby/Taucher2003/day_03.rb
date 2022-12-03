class String
  def halves
    chars.each_slice(size / 2).map(&:join)
  end

  def is_uppercase?
    /[[:upper:]]/.match(self)
  end

  def priority
    is_uppercase? ? ord - 38 : ord - 96
  end
end

input = ARGF.read
return if input.nil?

lines = input.split("\n")

def part1(lines)
  lines.map(&:halves).map { |line| line[0].chars & line[1].chars }.flatten.map(&:priority).sum
end

def part2(lines)
  lines.each_slice(3).map { |slice| slice[0].chars & slice[1].chars & slice[2].chars }.flatten.map(&:priority).sum
end

puts "Part 1: #{part1 lines}"
puts "Part 2: #{part2 lines}"
