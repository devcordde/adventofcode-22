with open('input.txt') as f:
    input = f.read().strip()

weakness = {1: 2, 2: 3, 3: 1}
matches_normalized = [[(ord(letter) + 5) % 23 for letter in letter_list] for letter_list in [match_line.split(' ') for match_line in input.split('\n')]]
points_per_match = [(3 if my == other else 6 if weakness[other] == my else 0) + my for other, my in matches_normalized]

# Task 1
print(sum(points_per_match))

# Task 2
strength = dict((v, k) for k, v in weakness.items())
choice_sums = [(other + 3 if outcome == 2 else strength[other] if outcome == 1 else weakness[other] + 6) for other, outcome in matches_normalized]
print(sum(choice_sums))
