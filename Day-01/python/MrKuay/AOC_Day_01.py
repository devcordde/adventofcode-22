# list of lines from input text file
lines = []
# list summed up calories per elf
elf_calories = []
temp = 0

with open('DATA_AoC_01.txt') as f:
    lines = f.readlines()

# --------------------------
# Task 01:Print highest sum

for line in lines:
    if line != '\n':
        temp += int(line)
    else:
        elf_calories.append(temp)
        temp = 0

# Sorts from low to high
elf_calories.sort()
# Reverse to high to low
elf_calories.reverse()

print(f'Highest Value: {elf_calories[0]}')

# --------------------------
# Task 02:Combine the top 3 entries

temp = 0

for i in elf_calories[0:3]:
    temp += i

print(f'Top 3 combined: {temp}')

