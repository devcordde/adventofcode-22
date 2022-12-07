from typing import Union


class Dir:
    def __init__(self, name: str, parent: Union["Dir", None]):
        self.name = name
        self.parent = parent
        self.children: dict[str, "Dir"] = {}
        self.files: list[int] = []

    def flatten(self) -> list["Dir"]:
        dirs = [e.flatten() for e in self.children.values()]
        return [v for k, v in self.children.items()] + [e for sub in dirs for e in sub]

    def __len__(self):
        return sum([e for e in self.files]) + sum([len(e) for e in self.children.values()])

    def __getitem__(self, item):
        return self.parent if item == ".." else self.children[item]


root = Dir("/", None)
curr_dir: Dir = root

cmds = [e.strip() for e in open("input.txt") if not e.startswith("$ ls")]
for line in cmds[1:]:
    if line.startswith("$ cd"):
        curr_dir = curr_dir[line.split(" ")[2]]
        continue
    if line.startswith("dir"):
        d = Dir(line.split(" ")[1], curr_dir)
        curr_dir.children[d.name] = d
        continue
    curr_dir.files.append(int(line.split(" ")[0]))

print(f"Part 1: {sum([len(e) for e in root.flatten() if len(e) <= 100_000])}")

free_up = 30_000_000 - (70_000_000 - len(root))

dirs = root.flatten()
dirs.sort(key=len)

for e in dirs:
    if len(e) > free_up:
        print(f"Part 2: {len(e)}")
        break
