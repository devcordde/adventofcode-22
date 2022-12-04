with open('input.txt') as f:
    input = [i.strip() for i in f.readlines()]

range_sets = [[set(range(int(value[0]), int(value[1]) + 1)) for value in [e.split('-') for e in p]] for p in [l.split(',') for l in input]]
subset_flags = [rs[0].issubset(rs[1]) or rs[1].issubset(rs[0]) for rs in range_sets]

# Task 1
print(sum(subset_flags))

# Task 2
intersections = [i for i in [rs[0].intersection(rs[1]) for rs in range_sets] if i]
print(len(intersections))
