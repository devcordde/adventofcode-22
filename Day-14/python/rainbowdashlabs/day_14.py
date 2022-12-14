from collections import namedtuple

lines = [[tuple(map(int, a.split(","))) for a in e.split("->")] for e in open("input.txt").read().splitlines()]

print(lines)
Point = namedtuple("Point", "x, y")

stones: set[Point] = set()
for line in lines:
    for i, (xs, ys) in enumerate(line):
        if i == len(line) - 1:
            continue
        xe, ye = line[i + 1]
        for x in range(min(xs, xe), max(xs, xe) + 1):
            for y in range(min(ys, ye), max(ys, ye) + 1):
                stones.add(Point(x, y))

xv = [e.x for e in stones]
xmin = min(xv) - 10
xmax = max(xv) + 10
yv = [e.y for e in stones]
ymin = 0
ymax = max(yv) + 2

print(xmin, xmax, ymin, ymax)

print(stones)

sand = set()


def get_marker(pos: Point):
    if (pos == Point(500, 0)):
        return "+"
    if pos in stones:
        return "#"
    if pos in sand:
        return 'o'
    if floor and pos.y == ymax:
        return "#"
    return "."


floor = False


def is_free(pos: Point):
    if floor:
        if pos.y == ymax:
            return False
    return not (pos in stones or pos in sand)


def draw():
    map = [[get_marker(Point(x, y)) for x in range(xmin - 1, xmax + 1)] for y in range(ymin - 1, ymax + 1)]
    print("\n".join(["".join(e) for e in map]))


def simulate_sand():
    curr = Point(500, 0)
    while True:
        if not floor and curr.y > ymax:
            return False
        if is_free(Point(curr.x, curr.y + 1)):
            curr = Point(curr.x, curr.y + 1)
            continue
        if is_free(Point(curr.x - 1, curr.y + 1)):
            curr = Point(curr.x - 1, curr.y + 1)
            continue
        if is_free(Point(curr.x + 1, curr.y + 1)):
            curr = Point(curr.x + 1, curr.y + 1)
            continue
        if curr == Point(500, 0):
            return False
        sand.add(curr)
        return True


draw()

while simulate_sand():
    draw()
    print("")

print(f"Part 1: {len(sand)}")

sand = set()
floor = True

while simulate_sand():
    pass

print(f"Part 2: {len(sand) + 1}")
