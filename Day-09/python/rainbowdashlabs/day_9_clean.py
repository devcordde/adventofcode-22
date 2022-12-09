from dataclasses import dataclass


@dataclass(unsafe_hash=True)
class Point:
    x: int
    y: int

    def adjacent(self, point: "Point") -> bool:
        return abs(self.x - point.x) <= 1 and abs(self.y - point.y) <= 1

    def follow(self, head: "Point"):
        if self.adjacent(head):
            pass
        elif self.y == head.y:
            self.x = head.x - 1 if self.x < head.x else head.x + 1
        elif self.x == head.x:
            self.y = head.y - 1 if self.y < head.y else head.y + 1
        else:
            self.x += 1 if self.x < head.x else -1
            self.y += 1 if self.y < head.y else -1


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
