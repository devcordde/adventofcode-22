ops = [[a[0], int(a[1])] if len(a) == 2 else a[0] for a in
       [e.split(" ") for e in open("input.txt").read().splitlines()]]

cycles = [1]

for op in ops:
    cycles.append(cycles[-1])
    if op == "noop":
        continue
    cycles.append(cycles[-1] + op[1])


def signal_strength(cycle: int) -> int:
    return cycles[cycle - 1] * cycle


def signals(cycles: list[int]) -> list[int]:
    return [signal_strength(e) for e in cycles]


print(f"Part 1: {sum(signals([20, 60, 100, 140, 180, 220]))}")


def draw(cycle: int) -> str:
    pos = (cycle - 1) % 40
    reg = cycles[cycle - 1] + 1

    if reg >= pos and reg - pos < 3:
        return '#'
    return '.'


crt = [[draw(40 * row + col) for col in range(1, 41)] for row in range(0, 6)]
print("Part 2")
print("\n".join(["".join(e) for e in crt]))
