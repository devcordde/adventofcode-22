import re
import copy

split = open('input.txt').read().split("\n\n")

r_container = [re.findall('(\\s{3}|\\[\\w])\\s?', e, re.RegexFlag.M) for e in split[0].splitlines()[:-1]]
o_piles = [[e[col][1] for e in r_container if len(e) > col and e[col].strip()] for col in range(max([len(e) for e in r_container]))]

moves = [re.match('.+?(\\d+).+?(\\d+).+?(\\d+)', e) for e in split[1].splitlines()]
moves = [(e[0], e[1] - 1, e[2] - 1) for e in [list(map(int, e.groups())) for e in moves]]

piles = copy.deepcopy(o_piles)
[[piles[move[2]].insert(0, piles[move[1]].pop(0)) for e in range(move[0])] for move in moves]

print(f"Part 1: {''.join([e[0] for e in piles])}")

piles = copy.deepcopy(o_piles)
for move in moves:
    piles[move[2]] = piles[move[1]][:move[0]] + piles[move[2]]
    piles[move[1]] = piles[move[1]][move[0]:]

print(f"Part 2: {''.join([e[0] for e in piles])}")
