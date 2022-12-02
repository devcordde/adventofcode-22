win, loose = {3: 1, 2: 3, 1: 2}, {1: 3, 3: 2, 2: 1}
choices = [[ord(e.strip().split(" ")[0]) - 64, ord(e.strip().split(" ")[1]) - 87] for e in open('input.txt') if e]


def check(other: int, my: int) -> int:
    if not other - my:
        return 3 + my
    if win[other] == my:
        return 6 + my
    return my


print(f"Part 1 {sum([check(*e) for e in choices])}")

print(f"Part 2 {sum([check(c[0], {1: loose[c[0]], 2: c[0], 3: win[c[0]]}[c[1]]) for c in choices])}")
