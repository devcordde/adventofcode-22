input = open('input.txt').read().splitlines()
grid = [[int(num) for num in row] for row in input]

outside = 2 * len(grid) + 2 * (len(grid) - 2)

def calc_score(h, direction):
    s = 1
    for tree in direction:
        if tree >= h: return s
        s += 1
    return s - 1

visible = 0
scores = []
for r in range(1, len(grid) - 1):
    for c in range(1, len(grid[r]) - 1):
        h = grid[r][c]
        col = list(zip(*grid))[c]
        left, right, up, down = [grid[r][:c][::-1], grid[r][c + 1:], col[:r][::-1], col[r + 1:]]

        if all(h > t for t in left) or all(h > t for t in right) or all(h > t for t in down) or all(h > t for t in up):
            visible += 1

        scores.append(calc_score(h, left) * calc_score(h, right) * calc_score(h, up) * calc_score(h, down))

# Task 1
print(visible + outside)

# Task 2
print(max(scores))
