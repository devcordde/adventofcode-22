with open('input.txt') as file:
  input = file.read().strip()
  
calories = []
i = 0

for inventory in input.split('\n\n'):
  calories.append(0)
  for line in inventory.splitlines():
    calories[i] += int(line)
  i += 1

print(f'Part 1: {max(calories)}')

calories.sort(reverse=True)
print(f'Part 2: {sum(calories[:3])}')
