input = ARGF.read
return if input.nil?

lines = input.split("\n").map { |line| line.split(",").map { |elf_range| elf_range.split("-").map(&:to_i) } }
ranges = lines.map { |line| line.map { |elf_range| elf_range[0]..elf_range[1] } }

puts "Part 1: #{ranges.count { |range| range[0].cover?(range[1]) || range[1].cover?(range[0]) }}"

puts "Part 2: #{ranges.map { |range| range[0].to_a & range[1].to_a }.count(&:any?)}"
