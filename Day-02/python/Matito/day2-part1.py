f = open("day2_input.txt", "r").read().splitlines()

points = 0

for i in range(len(f)):

    round = f[i].split(" ")

    if round[1] == 'X':
        round[1] = 'A'
        points = points + 1
    elif round[1] == 'Y':
        round[1] = 'B'
        points = points + 2
    elif round[1] == 'Z':
        round[1] = 'C'
        points = points + 3


    if round[0] == round[1]:
        points = points + 3
    elif round[0] == 'A' and round[1] == 'B':
        points = points + 6
    elif round[0] == 'B' and round[1] == 'C':
        points = points + 6
    elif round[0] == 'C' and round[1] == 'A':
        points = points +6


print(points)