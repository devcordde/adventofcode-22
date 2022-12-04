sections = [e.strip().split(",") for e in open("input.txt")]

sections = [[a.split("-") for a in e] for e in sections]

sections = [[set(range(int(a[0]), int(a[1]) + 1)) for a in e] for e in sections]

subsets = [e for e in sections if e[1].issubset(e[0]) or e[0].issubset(e[1])]

print(f"Part 1: {len(subsets)}")

intersections = [e for e in sections if e[0].intersection(e[1])]
print(f"Part 2: {len(intersections)}")
