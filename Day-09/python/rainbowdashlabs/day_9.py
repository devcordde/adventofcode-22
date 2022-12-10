from dataclasses import dataclass


@dataclass(unsafe_hash=True)
class Point:
    x: int
    y: int

    def adjacent(self, point: "Point") -> bool:
        return abs(self.x - point.x) <= 1 and abs(self.y - point.y) <= 1


@dataclass
class Move:
    dir: str
    steps: int


class Rope:
    def __init__(self, length: int):
        self.knots: list[Point] = [Point(0, 0) for e in range(length)]

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

        next_head = head
        for tail in self.knots[1:]:
            head = next_head
            next_head = tail
            if tail.adjacent(head):
                head = tail
                continue

            # horizontal movement
            if tail.y == head.y:
                if tail.x < head.x:
                    tail.x = head.x - 1
                else:
                    tail.x = head.x + 1
                continue

            # vertical movement
            if tail.x == head.x:
                if tail.y < head.y:
                    tail.y = head.y - 1
                else:
                    tail.y = head.y + 1
                continue

            # diagonal movement
            if tail.x < head.x:
                tail.x += 1
            else:
                tail.x -= 1

            if tail.y < head.y:
                tail.y += 1
            else:
                tail.y -= 1

    def tail(self) -> Point:
        return self.knots[-1]

    def head(self) -> Point:
        return self.knots[0]

    def maker(self, point: Point):
        if point == self.head():
            return "H"
        if point in self.knots:
            return str(self.knots.index(point))
        return "."


moves = [Move(e[0], int(e[1])) for e in [e.strip().split(" ") for e in open('input.txt')]]

rope = Rope(2)

visited = {rope.tail()}


def show_map(rows: int = 6, cols: int = 6):
    v_map = [[rope.maker(Point(col, row)) for col in range(cols)] for row in range(rows)]
    print("\n".join(["".join(e) for e in v_map[::-1]]))
    print("")


for move in moves:
    for step in range(move.steps):
        rope.move(move)
        tail = rope.tail()
        visited.add(Point(tail.x, tail.y))

print(f"Part 1 {len(visited)}")

moves = [Move(e[0], int(e[1])) for e in [e.strip().split(" ") for e in open('input.txt')]]

rope = Rope(10)
visited = {rope.tail()}

for move in moves:
    print(move)
    for step in range(move.steps):
        rope.move(move)
        tail = rope.tail()
        visited.add(Point(tail.x, tail.y))
        show_map(6, 6)

print(f"Part 2: {len(visited)}")