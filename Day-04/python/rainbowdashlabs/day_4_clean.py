sections = [[set(range(int(a[0]), int(a[1]) + 1)) for a in [x.split("-") for x in e]] for e in [e.strip().split(",") for e in open("input.txt")]]

print(f"Part 1: {len([e for e in sections if e[1].issubset(e[0]) or e[0].issubset(e[1])])}")

print(f"Part 2: {len([e for e in sections if e[0].intersection(e[1])])}")
