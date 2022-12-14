hm = [list(map(ord, list(e.strip()))) for e in open('input.txt')]
steps = [[-1 for _ in range(len(hm[0]))] for _ in range(len(hm))]

start = [e for e in range(len(hm)) if ord('S') in hm[e]][0], \
        [hm[e].index(ord('S')) for e in range(len(hm)) if ord('S') in hm[e]][0]
end = [e for e in range(len(hm)) if ord('E') in hm[e]][0], \
      [hm[e].index(ord('E')) for e in range(len(hm)) if ord('E') in hm[e]][0]

queue = [end]


def get_acceptable_fields(pos: (int, int)) -> list[(int, int)]:
    curr_height = hm[pos[0]][pos[1]]
    checks = [(pos[0] + 1, pos[1]), (pos[0] - 1, pos[1]), (pos[0], pos[1] + 1), (pos[0], pos[1] - 1)]
    checks = [e for e in checks if min(e[0], e[1]) >= 0 and e[0] < len(hm) and e[1] < len(hm[0])]
    checks = [e for e in checks if steps[e[0]][e[1]] == -1]
    checks = [e for e in checks if e not in queue]
    return [e for e in checks if curr_height - hm[e[0]][e[1]] <= 1]


steps[end[0]][end[1]] = 0
hm[start[0]][start[1]] = ord('a')
hm[end[0]][end[1]] = ord('z')
while len(queue) != 0:
    curr = queue.pop(0)
    fields = get_acceptable_fields(curr)
    for f in fields:
        steps[f[0]][f[1]] = steps[curr[0]][curr[1]] + 1
    queue.extend(fields)

print(f"Part 1: {steps[start[0]][start[1]]}")

start_pos = min([min([steps[row][col] for col in range(len(hm[0])) if hm[row][col] == ord('a') and steps[row][col] != -1]) for row in range(len(hm))])
print(f"Part 2: {start_pos}")
