with open('input.txt') as f:
    input = [i.strip() for i in f.readlines()]

calculate_letter_value = lambda l: ord(l.swapcase()) - (64 if l.islower() else 70)

compartements_per_rucksack = [[r[:len(r)//2], r[len(r)//2:]] for r in input]
duplicate_compartement_items = [next(item for item in c1 if item in c2) for c1, c2 in compartements_per_rucksack]
duplicate_values = [calculate_letter_value(duplicate) for duplicate in duplicate_compartement_items]

# Task 1
print(sum(duplicate_values))

# Task 2
elf_groups = [input[group:group + 3] for group in range(0, len(input), 3)]
duplicate_group_item = [next(item for item in g1 if item in g2 and item in g3) for g1, g2, g3 in elf_groups]
duplicate_group_values = [calculate_letter_value(duplicate) for duplicate in duplicate_group_item]

print(sum(duplicate_group_values))
