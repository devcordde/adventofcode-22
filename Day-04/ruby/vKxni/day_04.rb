input = File.read('input.txt').split("\n")

range_sets = input.map do |line|
  line.split(',').map do |range|
    a, b = range.split('-')
    (a.to_i..b.to_i)
  end
end

subset_flags = range_sets.map do |range_set|
  range_set[0].cover?(range_set[1]) or range_set[1].cover?(range_set[0])
end

# Part 1
puts subset_flags.count(true)

# Part 2
intersections = range_sets.map do |range_set|
  range_set[0].to_a & range_set[1].to_a
end.select { |i| i.any? }

puts intersections.length