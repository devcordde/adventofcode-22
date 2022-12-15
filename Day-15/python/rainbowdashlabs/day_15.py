import time
from pprint import pprint

file = "input.txt"

pos = [[a.split(" ") for a in e.split(":")] for e in open(file).read().splitlines()]
pos = [[(e[0][2][2:][:-1], e[0][3][2:]), (e[1][5][2:][:-1], e[1][6][2:])] for e in pos]
pos = [[tuple(map(int, a)) for a in e] for e in pos]
pprint(pos)


def man_dist(pos_a: (int, int), pos_b: (int, int)) -> int:
    return abs(pos_a[0] - pos_b[0]) + abs(pos_a[1] - pos_b[1])


pos: list[(int, int), (int, int), int] = [e + [man_dist(*e)] for e in pos]
pprint(pos)
beacons = {e[1] for e in pos}
sensors = {e[0] for e in pos}


def is_covered(pos_a: (int, int)) -> bool | None:
    if pos_a in beacons:
        return None
    for e in pos:
        if man_dist(e[0], pos_a) <= e[2]:
            return True
    return False


y = 10 if file == "example.txt" else 2_000_000

start_x = min([e[0] for e in beacons])
end_x = max([e[0] for e in beacons])
max_dist = max(e[2] for e in pos)

covered = 0

for x in range(start_x - max_dist, end_x + 1 + max_dist):
    if is_covered((x, y)):
        covered += 1

print(f"Part 1: {covered}")

max_c = 20 if file == "example.txt" else 4_000_000


def best_coverage_sensor(pos_a: (int, int)) -> ((int, int), int):
    # find sensors which cover this area
    cover = [e for e in pos if man_dist(e[0], pos_a) <= e[2]]
    dists = [e + [man_dist(e[0], pos_a)] for e in cover]
    max_b = max([e[3] for e in dists])
    for sensor, beacon, dist, dist_b in dists:
        if dist_b == max_b:
            return sensor, dist, max_b


def find_distress():
    for y in range(max_c + 1):
        if not y % 100_000:
            print(f"{round(time.time() - start)} : {y}")
        x = 0
        while x <= max_c:
            covered = is_covered((x, y))
            if covered:
                sensor, b_dist, dist = best_coverage_sensor((x, y))
                y_dist = abs(y - sensor[1])
                x = sensor[0] + (b_dist - y_dist) + 1
                continue
            print(f"Part 2: {x * 4_000_000 + y}")
            return

start = time.time()
find_distress()
print(f"Took {time.time() - start} seconds")
