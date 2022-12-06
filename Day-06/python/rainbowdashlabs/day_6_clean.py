stream = open('input.txt').read().strip()

find = lambda s: min([i + s for i in range(len(stream)) if len(set(stream[i:i + s])) == s])

print(f"Part 1 {find(4)}")
print(f"Part 2 {find(14)}")
