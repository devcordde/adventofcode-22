trees = [list(map(int, list(e.strip()))) for e in open('input.txt')]

tree = lambda row, col: trees[row][col]
cols = len(trees[0])
rows = len(trees)
all_trees = [e for sub in [[(row, col) for col in range(cols)] for row in range(rows)] for e in sub]
left_trees = lambda row, col: trees[row][:col][::-1]
right_trees = lambda row, col: trees[row][col + 1:]
bottom_trees = lambda row, col: [trees[rows - 1 - row][col] for row in range(rows - row - 1)][::-1]
top_trees = lambda row, col: [tree(row, col) for row in range(row)][::-1]
is_bound = lambda row, col: min(row, col) == 0 or col == cols - 1 or row == rows - 1


def calc_height(row, col) -> int:
    if is_bound(row, col):
        return -1
    return min([max(left_trees(row, col)),
                max(right_trees(row, col)),
                max(top_trees(row, col)),
                max(bottom_trees(row, col))])


tree_height = {t: calc_height(*t) for t in all_trees}
print(f"Part 1: {sum([tree_height[t] < tree(*t) for t in all_trees])}")


def walk_score(trees: list[int], height: int) -> int:
    steps = 0
    for t in trees:
        steps += 1
        if t >= height:
            break
    return steps


def calc_scenic_score(row, col):
    if is_bound(row, col):
        return 0
    tree_height = tree(row, col)
    return walk_score(left_trees(row, col), tree_height) * walk_score(right_trees(row, col), tree_height) \
           * walk_score(top_trees(row, col), tree_height) * walk_score(bottom_trees(row, col), tree_height)


print(f"Part 2: {max([calc_scenic_score(*t) for t in all_trees])}")
