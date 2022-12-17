import time

moves = list(open("input.txt").read().strip())

field_width = 7
occupied: set = {(e, 0) for e in range(field_width)}


class Shape:
    def __init__(self, slots: list[tuple[int, int]]):
        self.slots = slots
        self.width = max([e[0] for e in self.slots]) + 1
        self.height = max([e[1] for e in self.slots]) + 1
        self.height_bottom = [min([p[1] for p in slots if p[0] == x]) for x in range(self.width)]
        self.height_top = [max([p[1] for p in slots if p[0] == x]) for x in range(self.width)]

    def __repr__(self):
        return "\n".join(["".join(["#" if (x, y) in self.slots else "."
                                   for x in range(self.width)])
                          for y in range(self.height)][::-1])


def draw():
    max_y = max([e[1] for e in occupied])
    view = [["#" if (x, y) in occupied else "." for x in range(7)] for y in range(1, max_y + 1)]
    print("")
    print("Current:")
    print("\n".join(["".join(e) for e in view][::-1]))


class Stone:
    def __init__(self, pos: list[int, int], shape: Shape):
        self.shape = shape
        self.pos = pos

    def bounds_side(self) -> (int, int):
        return self.pos[0], self.pos[0] + self.shape.width

    def lower_bounds(self):
        sides = self.bounds_side()
        return {e: self.shape.height_bottom[i] + self.pos[1] for i, e in enumerate(list(range(sides[0], sides[1])))}

    def upper_bounds(self):
        sides = self.bounds_side()
        return {e: self.shape.height_top[i] + self.pos[1] for i, e in enumerate(list(range(sides[0], sides[1])))}

    def move(self, direction: str):
        sides = self.bounds_side()
        if direction == '<' and sides[0] != 0 \
                and not [p for p in self.calc_points((self.pos[0] - 1, self.pos[1])) if p in occupied]:
            # print("Move left")
            self.pos[0] -= 1
        elif direction == '>' and sides[1] != field_width \
                and not [p for p in self.calc_points((self.pos[0] + 1, self.pos[1])) if p in occupied]:
            # print("Move right")
            self.pos[0] += 1
        else:
            # print(f"Reached bounds {direction}")
            pass

        if not [p for p in self.calc_points((self.pos[0], self.pos[1] - 1)) if p in occupied]:
            # print("Move down")
            self.pos[1] -= 1
            return False
        return True

    def points(self) -> set[tuple[int, int]]:
        return self.calc_points(self.pos)

    def calc_points(self, pos) -> set[tuple[int, int]]:
        return {(pos[0] + e[0], pos[1] + e[1]) for e in self.shape.slots}


shapes = [
    Shape([(0, 0), (1, 0), (2, 0), (3, 0)]),
    Shape([(1, 0), (0, 1), (1, 1), (2, 1), (1, 2)]),
    Shape([(0, 0), (1, 0), (2, 0), (2, 1), (2, 2)]),
    Shape([(0, 0), (0, 1), (0, 2), (0, 3)]),
    Shape([(0, 0), (1, 0), (0, 1), (1, 1)])
]

for shape in shapes:
    print("")
    print(shape.__repr__())


def highest_point():
    if not occupied:
        return 0
    return max([e[1] for e in occupied])


def next_stone():
    shape = shapes.pop(0)
    shapes.append(shape)
    return Stone([2, highest_point() + 4], shape)


stone = next_stone()
i = 2022
while i != 0:
    move = moves.pop(0)
    moves.append(move)
    if stone.move(move):
        i -= 1
        occupied = occupied.union(stone.points())
        print("Settled")
        print("")
        stone = next_stone()
        print(stone.shape)
        draw()

print(f"Part 1: {highest_point()}")

# time to get a super computer. cba. Will just take around 40 days.
stone = next_stone()
occupied = {(e, 0) for e in range(field_width)}
i = 1000000000000
start = time.time()
while i != 0:
    move = moves.pop(0)
    moves.append(move)
    if stone.move(move):
        i -= 1
        occupied = occupied.union(stone.points())
        stone = next_stone()
        if i % 10000 == 0:
            high = highest_point()
            occupied = {e for e in occupied if e[1] > high - 5000}
            print(i)
            print(f"Took {round(time.time() - start)}")
            start = time.time()

print(f"Part 2: {highest_point()}")
