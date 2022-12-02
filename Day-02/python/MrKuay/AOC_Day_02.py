final_score = 0
final_score_task2 = 0

# Declaring win and choice values

SCORE_ROCK = 1
SCORE_PAPER = 2
SCORE_SCISSORS = 3

SCORE_WIN = 6
SCORE_DRAW = 3
SCORE_LOSE = 0

# Determining outcome values

results = {
    'AX': SCORE_ROCK + SCORE_DRAW,
    'AY': SCORE_PAPER + SCORE_WIN,
    'AZ': SCORE_SCISSORS + SCORE_LOSE,
    'BX': SCORE_ROCK + SCORE_LOSE,
    'BY': SCORE_PAPER + SCORE_DRAW,
    'BZ': SCORE_SCISSORS + SCORE_WIN,
    'CX': SCORE_ROCK + SCORE_WIN,
    'CY': SCORE_PAPER + SCORE_LOSE,
    'CZ': SCORE_SCISSORS + SCORE_DRAW,
}

results_task2 = {
    'AX': SCORE_SCISSORS + SCORE_LOSE,
    'AY': SCORE_ROCK + SCORE_DRAW,
    'AZ': SCORE_PAPER + SCORE_WIN,
    'BX': SCORE_ROCK + SCORE_LOSE,
    'BY': SCORE_PAPER + SCORE_DRAW,
    'BZ': SCORE_SCISSORS + SCORE_WIN,
    'CX': SCORE_PAPER + SCORE_LOSE,
    'CY': SCORE_SCISSORS + SCORE_DRAW,
    'CZ': SCORE_ROCK + SCORE_WIN,
}

# Checking with the given examples

print(results['AY'])
print(results['BX'])
print(results['CZ'])
print('------')
print(results_task2['AY'])
print(results_task2['BX'])
print(results_task2['CZ'])
print('------')

with open('DATA_AoC_02.txt') as f:
    lines = f.readlines()

for entry in lines:
    final_score += results[f'{entry.split()[0]}{entry.split()[1]}']

print(f'Score Task 1: {final_score}')
print('------')

for entry in lines:
    final_score_task2 += results_task2[f'{entry.split()[0]}{entry.split()[1]}']

print(f'Score Task 2: {final_score_task2}')
