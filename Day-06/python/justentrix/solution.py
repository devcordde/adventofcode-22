with open('input.txt') as file:
  input = file.read().strip()

def has_duplicate_letter(text):
  return len({i for i in text}) < len(text)

def task(input, marker):
  for i in range(marker, len(input)):
    part = input[i - marker:i]

    if not has_duplicate_letter(part):
      return i

print(f'Part 1: {task(input, 4)}')
print(f'Part 2: {task(input, 14)}')
