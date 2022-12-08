input = ARGF.read
lines = input.split "\n"

class Entry
  attr_accessor :parent, :children, :name, :size

  def directory?
    size.nil?
  end

  def actual_size
    return size if children.nil?

    children.map(&:actual_size).compact.sum
  end
end

current_entry = nil
until lines.empty?
  line = lines.shift
  next if line == '$ ls'

  if line == '$ cd ..'
    current_entry = current_entry&.parent
  elsif line.start_with? '$ cd '
    entry = Entry.new.tap do |e|
      e.name = line.delete_prefix '$ cd '
      e.parent = current_entry
      e.children = []
    end
    current_entry.children << entry unless current_entry.nil?
    current_entry = entry
  elsif !line.start_with?('dir') && !current_entry.nil?
    data = line.split ' '
    current_entry.children << Entry.new.tap do |e|
      e.name = data[1]
      e.size = data[0].to_i
      e.parent = current_entry
    end
  end
end

return if current_entry.nil?

current_entry = current_entry.parent until current_entry.parent.nil?
root_entry = current_entry

def deep_select(entry, &block)
  result = []
  result << entry if block.call entry
  entry.children&.map { |c| deep_select(c, &block) }&.each { |c| result << c }
  result
end

directories = deep_select(root_entry, &:directory?).flatten.compact

puts "Part 1: #{directories.map(&:actual_size).select { |size| size < 100_000 }.sum}"

AVAILABLE_SPACE = 70_000_000
REQUIRED_SPACE = 30_000_000

puts "Part 2: #{directories
                  .map(&:actual_size)
                  .select { |size| AVAILABLE_SPACE - root_entry.actual_size + size > REQUIRED_SPACE }
                  .min}"
