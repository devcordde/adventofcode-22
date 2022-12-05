f = open("day4_input.txt").read().splitlines()

pairs = 0
pairs_part2 = 0

for i in range(len(f)):
    fs = f[i].split(',')
    fss_list = []
    for i2 in range(len(fs)):
        fss = fs[i2].split('-')
        fss_list.append(fss)
    first = int(fss_list[0][0])
    second = int(fss_list[0][1])
    third = int(fss_list[1][0])
    forth = int(fss_list[1][1])
    if first <= third <= second and first <= forth <= second:
        pairs += 1
        pairs_part2 += 1
    elif third <= first <= forth and third <= second <= forth:
        pairs += 1
        pairs_part2 += 1

    elif second >= third >= first:
        pairs_part2 += 1
    elif forth >= first >= third:
        pairs_part2 += 1

print(pairs)
print(pairs_part2)