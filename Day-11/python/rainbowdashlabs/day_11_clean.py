from dataclasses import dataclass
from typing import Callable


@dataclass(repr=True)
class Monkey:
    id: int
    items: list[int]
    operation: str
    test: int
    t_true: int
    t_false: int
    inspects: int = 0

    @staticmethod
    def parse(monkey: str) -> "Monkey":
        monkey = monkey.splitlines()
        return Monkey(int(monkey[0][-2]), list(map(int, monkey[1].split(":")[1].split(","))),
                      monkey[2].split("=")[1].strip(), int(monkey[3].split("by")[1]),
                      int(monkey[4][-1]), int(monkey[5][-1]))

    def eval_item(self, item: int, reduce: Callable[[int], int]) -> (int, int):
        new = reduce(int(eval(self.operation.replace("old", str(item)))))
        return self.t_true if not new % self.test else self.t_false, new

    def inspect(self, reduce: Callable[[int], int]) -> list[(int, int)]:
        throws, self.items = [self.eval_item(i, reduce) for i in self.items], []
        self.inspects += len(throws)
        return throws


def simulate(monkeys: list[Monkey], rounds: int, reduce: Callable[[int], int]):
    [[[monkeys[t].items.append(item) for t, item in monkey.inspect(reduce)] for monkey in monkeys] for _ in range(rounds)]
    monkeys.sort(key=lambda x: x.inspects)
    return monkeys[-1].inspects * monkeys[-2].inspects


r_monkeys = [e for e in open("input.txt").read().split("\n\n")]

print(f"Part 1: {simulate([Monkey.parse(e) for e in r_monkeys], 20, lambda x: int(x / 3))}")

monkeys = [Monkey.parse(e) for e in r_monkeys]

mod = 1
for m in monkeys:
    mod *= m.test

print(f"Part 2: {simulate(monkeys, 10000, lambda x: x % mod)}")
