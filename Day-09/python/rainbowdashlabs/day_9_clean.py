from dataclasses import dataclass

from imath import sign


@dataclass(unsafe_hash=True)
class Point:
    x: int
    y: int

    def __add__(self, other: "Point") -> "Point":
        self.x += other.x
        self.y += other.y
        return self

    def adjacent(self, point: "Point") -> bool:
        return abs(self.x - point.x) <= 1 and abs(self.y - point.y) <= 1

    def follow(self, head: "Point"):
        self if self.adjacent(head) else self + Point(sign(head.x - self.x), sign(head.y - self.y))


@dataclass
class Move:
    dir: str
    steps: int


class Rope:
    def __init__(self, length: int):
        self.knots: list[Point] = [Point(0, 0) for e in range(length)]
        self.visited = {Point(0, 0)}

    def batch_move(self, moves: list[Move]):
        for move in moves:
            for step in range(move.steps):
                self.move(move)
                tail = self.knots[-1]
                self.visited.add(Point(tail.x, tail.y))

    def move(self, move: Move):
        head = self.knots[0]
        match move.dir:
            case 'R':
                head.x += 1
            case 'L':
                head.x -= 1
            case 'U':
                head.y += 1
            case 'D':
                head.y -= 1

        for tail in self.knots[1:]:
            tail.follow(head)
            head = tail


moves = [Move(e[0], int(e[1])) for e in [e.strip().split(" ") for e in open('input.txt')]]

rope = Rope(2)
rope.batch_move(moves)

print(f"Part 1 {len(rope.visited)}")

rope = Rope(10)
rope.batch_move(moves)

print(f"Part 2: {len(rope.visited)}")
