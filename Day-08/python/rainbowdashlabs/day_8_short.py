trees = [list(map(int, list(e.strip()))) for e in open('input.txt')]

tree = lambda row, col: trees[row][col]
cols, rows = len(trees[0]), len(trees)
all_trees = [e for sub in [[(row, col) for col in range(cols)] for row in range(rows)] for e in sub]
l_trees = lambda row, col: trees[row][:col][::-1]
r_trees = lambda row, col: trees[row][col + 1:]
b_trees = lambda row, col: [trees[row][col] for row in range(row + 1, rows)]
t_trees = lambda row, col: [tree(row, col) for row in range(row)][::-1]
is_bound = lambda row, col: min(row, col) == 0 or col == cols - 1 or row == rows - 1


def calc_height(row, col) -> int:
    return -1 if is_bound(row, col) else min([max(l_trees(row, col)), max(r_trees(row, col)), max(t_trees(row, col)), max(b_trees(row, col))])


tree_height = {t: calc_height(*t) for t in all_trees}
print(f"Part 1: {sum([tree_height[t] < tree(*t) for t in all_trees])}")


def score(trees: list[int], height: int) -> int:
    steps = 0
    for t in trees:
        steps += 1
        if t >= height:
            break
    return steps


def scenic_score(r, c):
    t, bound = tree(r, c), is_bound(r, c)
    return 0 if bound else score(l_trees(r, c), t) * score(r_trees(r, c), t) * score(t_trees(r, c), t) * score(b_trees(r, c), t)


print(f"Part 2: {max([scenic_score(*t) for t in all_trees])}")
