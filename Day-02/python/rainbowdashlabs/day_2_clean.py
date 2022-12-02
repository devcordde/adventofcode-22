win, loose = {3: 1, 2: 3, 1: 2}, {1: 3, 3: 2, 2: 1}
choices = [[ord(e.strip().split(" ")[0]) - (ord('A') - 1), ord(e.strip().split(" ")[1]) - (ord('X') - 1)] for e in open('input.txt') if e]


def check(other: int, my: int) -> int:
    return 3 + my if not other - my else 6 + my if win[other] == my else my


print(f"Part 1 {sum([check(*e) for e in choices])}")

print(f"Part 2 {sum([check(c[0], {1: loose[c[0]], 2: c[0], 3: win[c[0]]}[c[1]]) for c in choices])}")
