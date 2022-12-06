input = ARGF.read

def find_marker_index(input, length)
  possible_markers = (0..input.length - length).map { |index| input[index, length] }
  first_marker = possible_markers.find { |string| string.chars.uniq.size == string.chars.size }
  possible_markers.index(first_marker) + length
end

puts "Part 1: #{find_marker_index input, 4}"
puts "Part 2: #{find_marker_index input, 14}"
