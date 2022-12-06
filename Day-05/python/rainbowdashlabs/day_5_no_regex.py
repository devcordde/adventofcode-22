import copy

con, moves = open('input.txt').read().split("\n\n")

con = [list(e)[1:][::4] for e in con.splitlines()[:-1]]
o_piles = [[r[c] for r in con if len(r) > c and r[c].strip()] for c in range(max([len(e) for e in con]))]
moves = [list([int(e[1]), int(e[3]) - 1, int(e[5]) - 1]) for e in [e.split(" ") for e in moves.splitlines()]]

piles = copy.deepcopy(o_piles)
[[piles[move[2]].insert(0, piles[move[1]].pop(0)) for e in range(move[0])] for move in moves]

print(f"Part 1: {''.join([e[0] for e in piles])}")

piles = copy.deepcopy(o_piles)
for move in moves:
    piles[move[2]] = piles[move[1]][:move[0]] + piles[move[2]]
    piles[move[1]] = piles[move[1]][move[0]:]

print(f"Part 2: {''.join([e[0] for e in piles])}")
