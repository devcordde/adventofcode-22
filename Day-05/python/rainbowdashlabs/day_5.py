import re
import copy

split = open('input.txt').read().split("\n\n")

r_container = [re.findall('(\\s{3}|\\[\\w])\\s?', e, re.RegexFlag.M) for e in split[0].splitlines()[:-1]]
columns = max([len(e) for e in r_container])

o_piles = []
for col in range(columns):
    o_piles.append([e[col][1] for e in r_container if len(e) > col and e[col].strip()])

moves = [re.match('move (?P<amount>\\d+) from (?P<source>\\d+) to (?P<target>\\d+)', e) for e in split[1].splitlines()]
moves = [(int(e['amount']), int(e['source']) - 1, int(e['target']) - 1) for e in moves]

piles = copy.deepcopy(o_piles)
for move in moves:
    for e in range(move[0]):
        piles[move[2]].insert(0, piles[move[1]].pop(0))

first = [e[0] for e in piles]
print(f"Part 1: {''.join(first)}")

piles = copy.deepcopy(o_piles)
for move in moves:
    take = piles[move[1]][:move[0]]
    piles[move[1]] = piles[move[1]][move[0]:]
    take.extend(piles[move[2]])
    piles[move[2]] = take

first = [e[0] for e in piles]
print(f"Part 2: {''.join(first)}")

