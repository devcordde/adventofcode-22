trees = [list(map(int, list(e.strip()))) for e in open('input.txt')]


def calc_height(row, col) -> int:
    if row == 0 or col == 0 or col == len(trees[0]) - 1 or row == len(trees) - 1:
        return -1
    left = max(trees[row][:col])
    right = max(trees[row][col + 1:])
    top = max([trees[row][col] for row in range(row)])
    bottom = max([trees[len(trees) - 1 - row][col] for row in range(len(trees) - row - 1)])
    return min([left, right, top, bottom])


height = [[calc_height(row, col) for col in range(len(trees[0]))] for row in range(len(trees))]
visible_trees = [[height[row][col] < trees[row][col] for col in range(len(trees[0]))] for row in range(len(trees))]
print(f"Part 1: {sum([sum(e) for e in visible_trees])}")


def calc_scenic_score(row, col):
    if row == 0 or col == 0 or col == len(trees[0]) - 1 or row == len(trees) - 1:
        return 0
    tree_height = trees[row][col]
    left = 0
    while True:
        left += 1
        if col - left <= 0 or trees[row][col - left] >= tree_height:
            break
    right = 0
    while True:
        right += 1
        if col + right + 1 >= len(trees[0]) or trees[row][col + right] >= tree_height:
            break
    up = 0
    while True:
        up += 1
        if row - up <= 0 or trees[row - up][col] >= tree_height:
            break
    down = 0
    while True:
        down += 1
        if row + down + 1 >= len(trees) or trees[row + down][col] >= tree_height:
            break
    return left * right * up * down


score = [[calc_scenic_score(row, col) for col in range(len(trees[0]))] for row in range(len(trees))]
score = max([max(e) for e in score])
print(f"Part 2: {score}")
