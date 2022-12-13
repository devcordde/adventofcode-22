import json
from functools import cmp_to_key

from imath import sign


def compare(left: list | int, right: list | int) -> int:
    if type(left) == int and type(right) == int:
        return sign(right - left)
    if type(left) == list and type(right) == list:
        for i, l in enumerate(left):
            if len(right) - 1 < i:
                return -1
            if compare(l, right[i]) == 0:
                continue
            return compare(l, right[i])
        return sign(len(right) - len(left))
    return compare(left if type(left) == list else [left], right if type(right) == list else [right])


pairs = [[json.loads(p) for p in e.splitlines()] for e in open("input.txt").read().split("\n\n")]

print(f"Part 1: {sum([i + 1 for i, (left, right) in enumerate(pairs) if compare(left, right) > 0])}")

packets = sorted([y for x in pairs for y in x] + [[[2]], [[6]]], key=cmp_to_key(compare))[::-1]
print(f"Part 2: {(packets.index([[2]]) + 1) * (packets.index([[6]]) + 1)}")
