with open('input.txt') as f:
    input = f.read().strip()

input_per_elf = input.split('\n\n')
calories_per_elf = [[int(calories) for calories in elf_input.split('\n')] for elf_input in input_per_elf]
calories_sums = [sum(calories) for calories in calories_per_elf]

# Task 1
print(max(calories_sums))

# Task 2
calories_sums.sort(reverse=True)
print(sum(calories_sums[:3]))
