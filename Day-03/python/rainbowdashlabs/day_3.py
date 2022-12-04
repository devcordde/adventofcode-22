input = [e.strip() for e in open('input.txt')]

splitted = [[e[:int(len(e.strip()) // 2)], e[int(len(e.strip()) // 2):]] for e in input]

unique = [[set(a) for a in e] for e in splitted]

unique = [e[0].intersection(e[1]) for e in unique]


def prio(char: str):
    if ord(char) >= ord('a'):
        return ord(char) - (ord('a') - 1)
    return 26 + ord(char) - (ord('A') - 1)


print(unique)

score = [prio(next(iter(e))) for e in unique]

print(score)
print(f"Part 1 {sum(score)}")

groups = []
group = []
for i, e in enumerate(input):
    group.append(e)
    if not (i + 1) % 3:
        groups.append(group)
        group = []

print(groups)

unique = [[set(a) for a in e] for e in groups]

print(unique)
unique = [e[0].intersection(e[1]).intersection(e[2]) for e in unique]

print(unique)

score = [prio(next(iter(e))) for e in unique]

print(f"Part 2 {sum(score)}")
