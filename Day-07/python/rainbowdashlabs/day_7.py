from typing import Union


class File:
    def __init__(self, name: str, size: int):
        self.name = name
        self.size = size

    @staticmethod
    def parse(input: str) -> "File":
        size, name = input.strip().split(" ")
        return File(name, int(size))

    def __repr__(self):
        return f"{self.name} {self.size}"


class Dir:
    def __init__(self, name: str, parent: Union["Dir", None]):
        self.name = name
        self.parent = parent
        self.children: dict[str, "Dir"] = {}
        self.files: dict[str, File] = {}

    @staticmethod
    def parse(input: str, parent: Union["Dir", None]) -> "Dir":
        return Dir(input.strip().split(" ")[1], parent)

    def size(self):
        return sum([e.size for e in self.files.values()]) + sum([e.size() for e in self.children.values()])

    def __repr__(self):
        return f"{self.name}"

    def directories_flat(self, include_root: bool = False) -> list["Dir"]:
        dirs = [e.directories_flat() for e in self.children.values()]
        return [v for k, v in self.children.items()] + [e for sub in dirs for e in sub] + (
            [self] if self.parent is None and include_root else [])


root = Dir("/", None)
curr_dir: Dir = root

cmds = [e.strip() for e in open("input.txt")]
for line in cmds[1:]:
    if line.startswith("$ ls"):
        continue
    if line.startswith("$ cd"):
        dir = line.split(" ")[2]
        if dir == "..":
            curr_dir = curr_dir.parent
            continue
        curr_dir = curr_dir.children[dir]
        continue
    if line.startswith("dir"):
        d = Dir.parse(line, curr_dir)
        curr_dir.children[d.name] = d
        continue
    f = File.parse(line)
    curr_dir.files[f.name] = f

dirs = root.directories_flat()
total = sum([e.size() for e in root.directories_flat() if e.size() <= 100_000])
print(f"Part 1: {total}")

total = root.size()
free = 70_000_000 - total
free_up = 30_000_000 - free

dirs.sort(key=lambda x: x.size())

for e in dirs:
    if e.size() > free_up:
        print(f"Part 2: {e.size()}")
        break
