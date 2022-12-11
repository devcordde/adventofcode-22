from dataclasses import dataclass

r_monkeys = [e for e in open("input.txt").read().split("\n\n")]


@dataclass(repr=True, unsafe_hash=True)
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
        id = int(monkey[0][-2])
        items = list(map(int, monkey[1].split(":")[1].split(",")))
        operation = monkey[2].split("=")[1].strip()
        test = int(monkey[3].split("by")[1])
        t_true = int(monkey[4][-1])
        t_false = int(monkey[5][-1])
        return Monkey(id, items, operation, test, t_true, t_false)

    def inspect(self, mod=3) -> list[(int, int)]:
        next = []
        for item in self.items:
            new = int(eval(self.operation.replace("old", str(item))))
            new %= mod
            next.append((self.t_true if not new % self.test else self.t_false, new))
            self.inspects += 1
        self.items = []
        return next


monkeys = {monkey.id: monkey for monkey in [Monkey.parse(e) for e in r_monkeys]}

for _ in range(20):
    for monkey in monkeys.values():
        for t, item in monkey.inspect():
            monkeys[t].items.append(item)

monkey_list = list(monkeys.values())
monkey_list.sort(key=lambda x: x.inspects)
print(f"Part 1: {monkey_list[-1].inspects * monkey_list[-2].inspects}")

monkeys = {monkey.id: monkey for monkey in [Monkey.parse(e) for e in r_monkeys]}

mod = 1
for t in monkeys.values():
    mod *= t.test

for _ in range(10000):
    for monkey in monkeys.values():
        for t, item in monkey.inspect(mod):
            monkeys[t].items.append(item)

monkey_list = list(monkeys.values())
monkey_list.sort(key=lambda x: x.inspects)
print(f"Part 2: {monkey_list[-1].inspects * monkey_list[-2].inspects}")
