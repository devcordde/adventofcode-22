stream = open('input.txt').read().strip()

start = 0
for i in range(len(stream)):
    if len(set(stream[i:i + 4])) == 4:
        start = i + 4
        break

print(f"Part 1 {start}")

start = 0
for i in range(len(stream)):
    if len(set(stream[i:i + 14])) == 14:
        start = i + 14
        break

print(f"Part 2 {start}")
