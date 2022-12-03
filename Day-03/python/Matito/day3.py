f = open("day3_input.txt").read().splitlines()

both = []
prio = 0
prio2 = 0
group = []
badges = []

alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
for i in range(len(f)):

    group.append(f[i])
    if len(group) == 3:
        longeststr = max(group, key=len)
        for i5 in range(len(longeststr)):
            if longeststr[i5] in group[0]: #sehr unclean aber mit if x in a and b and c gings nicht
                if longeststr[i5] in group[1]:
                    if longeststr[i5] in group[2]:
                        badges.append(longeststr[i5])
                        break
        group = []

    compartment = len(f[i]) / 2
    first_compartment = []
    second_compartment = []
    for i2 in range(int(compartment)):
        first_compartment.append(f[i][i2])
        second_compartment.append(f[i][int(i2 + compartment)])

    for i3 in range(int(compartment)):
        if first_compartment[i3] in second_compartment:
            both.append(first_compartment[i3])
            break

for i4 in range(len(both)):
    prio += alphabet.find(both[i4]) +1

for i6 in range(len(badges)):
    prio2 += alphabet.find(badges[i6]) +1

print(prio)
print(prio2)