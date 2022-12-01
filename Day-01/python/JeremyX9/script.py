file = open("input.txt", "r")
list_of_calories, counter = [], 0
for line in file:
    if not line == "\n":
        counter += int(line.replace("\n", ""))
    else:
        list_of_calories.append(counter)
        counter = 0
list_of_calories.sort(reverse=True)
print(f"Puzzle 1: {sum(list_of_calories[:1])}\nPuzzle 2: {sum(list_of_calories[:3])}")