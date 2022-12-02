f = open("day1_input.txt", "r").read().splitlines()

elves = []
calories = 0


for i in range(len(f)):

    if f[i]  == '':
        elves.append(calories)
        calories = 0
    else:
        calories = calories + int(f[i])

print(max(elves)) # part 1


elves.sort(reverse=True)
print(elves[0] + elves[1] + elves[2]) #part 2