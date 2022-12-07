input = open('input.txt').read().splitlines()

class Dir:
    def __init__(self, name, parent):
        self.name = name
        self.parent = parent
        self.subdirs = {}
        self.files = []

root = Dir('/', None)
current_dir = root

for line in input[1:]:
    line_parts = line.split(' ')
    if line.startswith('dir'):
        current_dir.subdirs[line_parts[1]] = Dir(line_parts[1], current_dir)
    if line.startswith('$ cd'):
        current_dir = current_dir.parent if line_parts[2] == '..' else current_dir.subdirs[line_parts[2]]
    if line_parts[0].isnumeric():
        current_dir.files.append(int(line_parts[0]))

dir_sizes = []
def calc_dir_sizes(dir):
    file_size = sum(dir.files)
    for subdir in dir.subdirs.values():
        file_size += calc_dir_sizes(subdir)
    dir_sizes.append(file_size)
    return file_size

# Task 1
calc_dir_sizes(root)
print(sum([size for size in dir_sizes if size < 100_000]))

# Task 2
needed_space = 30_000_000 - (70_000_000 - dir_sizes[-1])
print(min([size for size in dir_sizes if size >= needed_space]))
