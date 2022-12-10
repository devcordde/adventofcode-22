ops = [[a[0], int(a[1])] if len(a) == 2 else a[0] for a in [e.split(" ") for e in open("input.txt").read().splitlines()]]

cycles = [1]

for op in ops:
    cycles.append(cycles[-1])
    if op != "noop":
        cycles.append(cycles[-1] + op[1])


def signals(cycle: list[int]) -> list[int]:
    return [cycles[c - 1] * c for c in cycle]


print(f"Part 1: {sum(signals([20, 60, 100, 140, 180, 220]))}")


def draw(cycle: int) -> str:
    pos, reg = (cycle - 1) % 40, cycles[cycle - 1] + 1
    return '#' if reg >= pos and reg - pos < 3 else '.'


crt = [[draw(40 * row + col) for col in range(1, 41)] for row in range(0, 6)]
print("Part 2")
print("\n".join(["".join(e) for e in crt]))
