input = ARGF.read
lines = input.split("\n").map(&:chars)
width = lines[0].size
height = lines.size
lines = lines.flatten.map(&:to_i)

def find_check_trees(lines, width, height, x, y)
  x_trees = lines.select.with_index { |_, index| index % width == x }
  y_trees = lines.select.with_index { |_, index| index / height == y }

  [x_trees[..y - 1].reverse, x_trees[y + 1..], y_trees[..x - 1].reverse, y_trees[x + 1..]]
end

tree_checks = (1..width - 2).map do |x|
  (1..height - 2).map do |y|
    { checks: find_check_trees(lines, width, height, x, y), height: lines[x + (y * width)] }
  end
end.flatten


visible = tree_checks.select do |tree_check|
  tree_check[:checks].any? { |check| [check.max, 0].compact.max < tree_check[:height] }
end

puts "Part 1: #{visible.size + (height * 2) + ((width - 2) * 2)}"

class Array
  def take_until
    return [] if size.zero?

    result = []
    index = 0

    loop do
      current_item = self[index]
      should_break = yield current_item
      result << current_item
      index += 1
      break if should_break || index == size
    end

    result
  end
end

tree_sights = tree_checks.map do |tree_check|
  tree_check[:checks].map { |check| check.take_until { |tree| tree >= tree_check[:height] }.size }
                     .reject(&:zero?)
                     .inject(:*)
end

puts "Part 2: #{tree_sights.max}"
