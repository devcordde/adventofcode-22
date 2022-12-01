calories = [sum([int(num) for num in e.split('\n')]) for e in open('input.txt').read().split('\n\n')]

print(f"Part 1: {max(calories)}")

calories.sort()

print(f"Part 2: {sum(calories[-3:])}")
