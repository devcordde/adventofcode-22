import json
from functools import cmp_to_key

pairs = [[json.loads(p) for p in e.splitlines()] for e in open("input.txt").read().split("\n\n")]


def equal(pair: list[list]):
    return compare(*pair)


def compare(left: list | int, right: list | int) -> int:
    if type(left) == int and type(right) == int:
        if left == right: # on equal continue
            return 0
        return 1 if left < right else -1 # lower integer first
    if type(left) == list and type(right) == list:
        for i, e in enumerate(left):
            if len(right) - 1 < i: # right list ran out of items first
                return -1
            res = compare(e, right[i]) # compare value of list
            if res == 0:
                continue
            return res
        if len(left) == len(right): # same length and no decision results in next element
            return 0
        return 1 # left list ran out of items first
    return compare(left if type(left) == list else [left], right if type(right) == list else [right])


print(f"Part 1: {sum([i + 1 for i, e in enumerate(pairs) if equal(e) == 1])}")

packets = [y for x in pairs for y in x]
packets.extend([[[2]], [[6]]])
packets = sorted(packets, key=cmp_to_key(compare))[::-1]
print(f"Part 2: {(packets.index([[2]]) + 1) * (packets.index([[6]]) + 1)}")