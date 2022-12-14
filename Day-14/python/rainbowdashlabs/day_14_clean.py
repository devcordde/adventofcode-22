import time
from collections import namedtuple

lines = [[tuple(map(int, a.split(","))) for a in e.split("->")] for e in open("input.txt").read().splitlines()]

Point = namedtuple("Point", "x, y")

stones: set[Point] = set()
for line in lines:
    for i, (xs, ys) in enumerate(line):
        if i == len(line) - 1:
            continue
        xe, ye = line[i + 1]
        p = [[Point(x, y) for y in range(min(ys, ye), max(ys, ye) + 1)] for x in range(min(xs, xe), max(xs, xe) + 1)]
        stones = {*stones, *{e for x in p for e in x}}

floor = max([e.y for e in stones]) + 2

has_floor = False


def is_free(pos: Point):
    return False if has_floor and pos.y == floor else not (pos in stones or pos in sand)


def simulate_sand():
    c = Point(500, 0)
    while True:
        if not has_floor and c.y > floor:
            return False
        free = [e for e in [Point(c.x, c.y + 1), Point(c.x - 1, c.y + 1), Point(c.x + 1, c.y + 1)] if is_free(e)]
        if len(free) != 0:
            c = free[0]
            continue
        sand.add(c)
        return False if c == Point(500, 0) else True


sand = set()
start = time.time()
while simulate_sand():
    pass

print(f"Part 1: {len(sand)} took {time.time() - start}")

sand = set()
has_floor = True

start = time.time()
while simulate_sand():
    pass

print(f"Part 2: {len(sand)} took {time.time() - start}")
