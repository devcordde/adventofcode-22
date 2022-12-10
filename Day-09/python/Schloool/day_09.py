import copy

moves = [line.split(' ') for line in open('input.txt').read().splitlines()]
sign = lambda num: -1 if num < 0 else 1 if num > 0 else 0

class Coordinate:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def get_snap_coordinate(self, other):
        return self + Coordinate(sign(other.x - self.x), sign(other.y - self.y)) if abs(self.x - other.x) > 1 or abs(self.y - other.y) > 1 else self

    def __add__(self, other):
        return Coordinate(self.x + other.x, self.y + other.y)

    def __str__(self):
        return f"{self.x}-{self.y}"

def get_unique_tail_visits(length):
    pieces = [Coordinate(0, 0) for _ in range(length)]
    visited = set()
    for dir, amount in moves:
        for move in range(int(amount)):
            c = Coordinate(1 if dir == 'R' else -1 if dir == 'L' else 0, 1 if dir == 'U' else -1 if dir == 'D' else 0)
            for i in range(len(pieces)):
                if i == 0:
                    pieces[i] += c
                    continue
                previous = copy.copy(pieces[i - 1])
                pieces[i] = pieces[i].get_snap_coordinate(previous)
                if i == len(pieces) - 1:
                    visited.add(str(copy.copy(pieces[i])))
    return visited

# Task 1
print(len(get_unique_tail_visits(2)))

# Task 2
print(len(get_unique_tail_visits(10)))
