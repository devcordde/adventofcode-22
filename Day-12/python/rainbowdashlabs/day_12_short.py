from collections import namedtuple

hm = [list(map(ord, list(e.strip()))) for e in open('input.txt')]
rows = len(hm)
cols = len(hm[0])
steps = [[-1 for _ in range(cols)] for _ in range(rows)]

Point = namedtuple("Point", "x, y")
height = lambda point: hm[point.x][point.y]
step = lambda point: steps[point.x][point.y]
find = lambda t: [y for x in [[Point(r, c) for c in range(cols) if hm[r][c] == ord(t)] for r in range(rows)] for y in x]


start, end = find('S')[0], find('E')[0]
queue, steps[end.x][end.y], hm[start.x][start.y], hm[end.x][end.y] = [end], 0, ord('a'), ord('z')
while len(queue) != 0:
    curr = queue.pop(0)
    checks = [Point(curr.x + 1, curr.y), Point(curr.x - 1, curr.y), Point(curr.x, curr.y + 1), Point(curr.x, curr.y - 1)]
    for x, y in [p for p in checks if min(p.x, p.y) >= 0 and p.x < rows and p.y < cols and step(p) == -1 and height(curr) - height(p) <= 1]:
        steps[x][y] = step(curr) + 1
        queue.append(Point(x, y))

print(f"Part 1: {step(start)}")

print(f"Part 2: {min([step(e) for e in find('a') if step(e) != -1])}")
