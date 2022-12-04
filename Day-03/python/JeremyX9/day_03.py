alphabet = "*abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
counter = 0
counter_part_two = 0
groups = []
for index, n in enumerate([n.strip() for n in open("input.txt", "r")], start=0):
    split_string = [n[:int(len(n) / 2)], n[int(len(n) / 2):]]
    counter += alphabet.find(str((set(split_string[0]) & set(split_string[1]))).__getitem__(2))
    q, mod = divmod(index, 3)
    if mod == 0:
        groups.append([n])
    else:
        groups[q].append(n)
for group in groups:
    counter_part_two += alphabet.find(str((set(group[0]) & set(group[1]) & set(group[2]))).__getitem__(2))
print(f"Part 1: {counter}")
print(f"Part 2: {counter_part_two}")