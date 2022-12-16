import re

tunnel = {e[0]: [int(e[1]), e[2:]] for e in [re.findall("[A-Z]{2}|\d+", e) for e in open("example.txt")]}


def measure(target: str) -> dict[str, int]:
    dists = {target: 0}
    queue = [target]

    while len(queue):
        curr = queue.pop(0)
        for e in tunnel[curr][1]:
            if e in dists:
                continue
            dists[e] = dists[curr] + 1
            queue.append(e)

    del dists[target]
    return dists


universes = []


class Universe:
    def __init__(self, min: int, pos: str, open_v: set, released: int):
        self.min = min
        self.pos = pos
        self.open_v = open_v
        self.released = released
        self.simulate()

    def simulate(self):
        if self.min > 0:
            dists = measure(self.pos)
            potential = {k: (self.min - v - 1) * tunnel[k][0] for k, v in dists.items()
                         if k not in self.open_v and self.min >= v + 1}
            if len(potential) == 0:
                return
            for k, v in {k: v for k, v in potential.items() if v > 0}.items():
                next = Universe(self.min - (dists[k] + 1), k, self.open_v.union({k}), self.released + v)
                universes.append(next)

    def __repr__(self) -> str:
        return f"{self.min}, {self.released}"


# universes.append(Universe(30, 'AA', set(), 0))
# print(f"Solutions: {len(universes)}")
# print(f"Part 1: {max([e.released for e in universes])}")

# fuck
# Does not work since there is a really stupid edge case...
minutes = 26
actor: list[str] = ['AA', 'AA']
target: list[str | None] = [None, None]
steps: list[int] = [0, 0]
open_v = set()
released = 0


def get_best_target(i: int) -> tuple[str, int, int] | None:
    a = actor[i]
    dists = measure(a)
    potential = {k: (minutes - min - v) * tunnel[k][0] for k, v in dists.items() if k not in target and k not in open_v}
    next = sorted([v for k, v in potential.items() if v > 0])[::-1]
    if not next:
        return None
    return [(k, v, dists[k]) for k, v in potential.items() if v == next[0]][0]


for min in range(1, minutes + 1):
    print("")
    print(f"== Minute: {min} ==")
    release = sum([tunnel[v][0] for v in open_v])
    print(f"Valves {', '.join(sorted(open_v))} are open, releasing {release} pressure")
    released += release

    new_targets = [(i, a) for i, a in enumerate(actor) if target[i] is None]
    new_steps = [(i, a) for i, a in enumerate(actor) if (i, a) not in new_targets]

    for (i, a) in new_steps:
        steps[i] -= 1
        if steps[i]:
            continue
        actor[i] = target[i]
        open_v.add(actor[i])
        print(f"{'You' if i == 0 else 'The elephant'} open valve {actor[i]}")
        target[i] = None

    if not new_targets:
        continue

    targets = []
    for i, a in new_targets:
        best = get_best_target(i)
        if best is None:
            continue
        targets.append((i,) + best)

    if not targets:
        continue

    if len(targets) == 1:
        t = targets[0]
        steps[t[0]] = t[3]
        target[t[0]] = t[1]
        continue


    # Check if we have multiple suggestions for same target
    if len(new_targets) != len({e[1] for e in targets}):
        max_pot = max([e[1] for e in targets])
        t = [e for e in targets if e[1] == max_pot][0]
        steps[t[0]] = t[3]
        target[t[0]] = t[1]
        del new_targets[t[0]]

    best = get_best_target(new_targets[0][0])
    if best is None:
        continue
    t = (new_targets[0][0],) + best
    steps[t[0]] = t[3]
    target[t[0]] = t[1]

print(f"Part 2: {released}")
