# this version is double as fast since no named tuple is used.
import time

lines = [[tuple(map(int, a.split(","))) for a in e.split("->")] for e in open("input.txt").read().splitlines()]

stones: set[(int, int)] = set()
for line in lines:
    for i, (xs, ys) in enumerate(line):
        if i == len(line) - 1:
            continue
        xe, ye = line[i + 1]
        p = [[(x, y) for y in range(min(ys, ye), max(ys, ye) + 1)] for x in range(min(xs, xe), max(xs, xe) + 1)]
        stones = {*stones, *{e for x in p for e in x}}

floor = max([e[1] for e in stones]) + 2

has_floor = False


def is_free(pos: (int, int)):
    return False if has_floor and pos[1] == floor else not (pos in stones or pos in sand)


def simulate_sand():
    c = (500, 0)
    while True:
        if not has_floor and c[1] > floor:
            return False
        free = [e for e in [(c[0], c[1] + 1), (c[0] - 1, c[1] + 1), (c[0] + 1, c[1] + 1)] if is_free(e)]
        if len(free) != 0:
            c = free[0]
            continue
        sand.add(c)
        return False if c == (500, 0) else True


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
