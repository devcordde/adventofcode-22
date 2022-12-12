from collections import namedtuple

hm = [list(map(ord, list(e.strip()))) for e in open('input.txt')]
rows = len(hm)
cols = len(hm[0])
steps = [[-1 for _ in range(cols)] for _ in range(rows)]

Point = namedtuple("Point", "x, y")
height = lambda point: hm[point.x][point.y]
step = lambda point: steps[point.x][point.y]


def find(t: str):
    # sorry for this. Flat mapping is pain
    return [y for x in [[Point(r, c) for c in range(cols) if hm[r][c] == ord(t)] for r in range(rows)] for y in x]


def get_acceptable_fields(pos: Point) -> list[Point]:
    checks = [Point(pos.x + 1, pos.y), Point(pos.x - 1, pos.y), Point(pos.x, pos.y + 1), Point(pos.x, pos.y - 1)]
    return [p for p in checks if min(p.x, p.y) >= 0 and p.x < rows and p.y < cols
            and step(p) == -1 and height(pos) - height(p) <= 1]


start, end = find('S')[0], find('E')[0]

queue = [end]
steps[end.x][end.y], hm[start.x][start.y], hm[end.x][end.y] = 0, ord('a'), ord('z')
while len(queue) != 0:
    curr = queue.pop(0)
    for x, y in get_acceptable_fields(curr):
        steps[x][y] = step(curr) + 1
        queue.append(Point(x, y))

print(f"Part 1: {step(start)}")

print(f"Part 2: {min([step(e) for e in find('a') if step(e) != -1])}")
