f = open("day2_input.txt", "r").read().splitlines()

points = 0

for i in range(len(f)):

    round = f[i].split(" ")
#X - lose
#Y - draw
#Z - win
    if round[1] == 'X':
        if round[0] == 'A':
            round[1] = 'C'
            points = points + 3
        elif round[0] == 'B':
            round[1] = 'A'
            points = points + 1
        elif round[0] == 'C':
            round[1] = 'B'
            points = points + 2
    elif round[1] == 'Y':
        round[1] = round[0]
        if round[1] == 'A':
            points = points + 1
        elif round[1] == 'B':
                points = points + 2
        elif round[1] == 'C':
                points = points + 3
    elif round[1] == 'Z':
        if round[0] == 'A':
            round[1] = 'B'
            points = points + 2
        elif round[0] == 'B':
            round[1] = 'C'
            points = points + 3
        elif round[0] == 'C':
            round[1] = 'A'
            points = points + 1


    if round[0] == round[1]:
        points = points + 3
    elif round[0] == 'A' and round[1] == 'B':
        points = points + 6
    elif round[0] == 'B' and round[1] == 'C':
        points = points + 6
    elif round[0] == 'C' and round[1] == 'A':
        points = points + 6


print(points)