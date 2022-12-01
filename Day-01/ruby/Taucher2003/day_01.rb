input = ARGF.read
return if input == nil

elf_data = input.split("\n\n")
elf_calories = elf_data.map { |data| data.split("\n").map(&:to_i).sum }

puts "Part 1: #{elf_calories.max}"

puts "Part 2: #{elf_calories.sort.reverse.take(3).sum}"
