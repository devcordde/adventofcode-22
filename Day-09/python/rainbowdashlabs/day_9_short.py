from dataclasses import dataclass


@dataclass(unsafe_hash=True)
class Point:
    x: int
    y: int

    def __add__(self, other: "Point"):
        self.x += other.x
        self.y += other.y
        return self

    def follow(self, head: "Point"):
        if abs(self.x - head.x) <= 1 and abs(self.y - head.y) <= 1:
            return
        elif self.y == head.y or self.x == head.x:
            self + Point(0 if self.x == head.x else 1 if self.x < head.x else -1, 0 if self.y == head.y else 1 if self.y < head.y else -1)
        else:
            self + Point(1 if self.x < head.x else -1, 1 if self.y < head.y else -1)


class Rope:
    def __init__(self, length: int):
        self.knots: list[Point] = [Point(0, 0) for e in range(length)]
        self.visited = {Point(0, 0)}

    def batch_move(self, moves: list[(str, int)]) -> int:
        [[self.move(move) for step in range(move[1])] for move in moves]
        return len(self.visited)

    def move(self, move: (str, int)):
        head = self.knots[0] + {'R': Point(1, 0), 'L': Point(-1, 0), 'U': Point(0, 1), 'D': Point(0, -1)}[move[0]]
        for tail in self.knots[1:]:
            tail.follow(head)
            head = tail
        self.visited.add(Point(head.x, head.y))


moves = [(e[0], int(e[1])) for e in [e.strip().split(" ") for e in open('input.txt')]]

print(f"Part 1 {Rope(2).batch_move(moves)}")
print(f"Part 2: {Rope(10).batch_move(moves)}")
