import re, copy
from itertools import zip_longest

crates, procedures = open('input.txt').read().split('\n\n')
crates_per_row = [[l[i:i + 4] for i in range(0, len(l), 4)] for l in crates.splitlines()[:-1]]
cols = [[e for e in col if e.strip()] for col in list(zip_longest(*crates_per_row, fillvalue=' '))]

procedure_nums = [list(map(int, num)) for num in [re.findall(r'\d+', p) for p in procedures.splitlines()]]

def start_procedure(cols, is_crane_mover_9001):
    for procedure in procedure_nums:
        amount, take, to = procedure
        cols[to - 1] = (cols[take - 1][:amount] if is_crane_mover_9001 else cols[take - 1][:amount][::-1]) + cols[to - 1]
        cols[take - 1] = cols[take - 1][amount:]
    return cols

# Task 1
print(''.join([col[0][1] for col in start_procedure(copy.copy(cols), False)]))

# Task 2
print(''.join([col[0][1] for col in start_procedure(copy.copy(cols), True)]))