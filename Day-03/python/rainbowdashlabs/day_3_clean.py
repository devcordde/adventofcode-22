input = [e.strip() for e in open('input.txt')]

prio = lambda char:  ord(list(char)[0]) - (ord('a') - 1) if ord(list(char)[0]) >= ord('a') else 27 + ord(list(char)[0]) - (ord('A'))

print(f"Part 1 {sum([prio(set(e[:len(e.strip()) // 2]).intersection(set(e[len(e.strip()) // 2:]))) for e in input])}")

groups = [input[group:group + 3] for group in range(0, len(input), 3)]

print(f"Part 2 {sum([prio(set(e[0]).intersection(set(e[1])).intersection(set(e[2]))) for e in groups])}")
