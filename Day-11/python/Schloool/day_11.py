import re, copy

monkey_inputs = open('input.txt').read().split('\n\n')

class Monkey:
    def __init__(self, start_items, operation, test_divisor, next_monkey_test):
        self.items = start_items
        self.operation = operation
        self.test_divisor = test_divisor
        self.next_monkey_test = next_monkey_test
        self.handled_items = 0

    def __lt__(self, other):
        return self.handled_items < other.handled_items

monkeys = []
for monkey_input in monkey_inputs:
    parts = monkey_input.split('\n')[1:]
    start_items = [level for level in [int(num) for num in re.findall(r'\d+', parts[0])]]
    operation = ' '.join(parts[1].split(' ')[-3:])
    test_divisor = int(parts[2].split(' ')[-1])
    next_monkey_test = {True: int(parts[3].split(' ')[-1]), False: int(parts[4].split(' ')[-1])}
    monkeys.append(Monkey(start_items, operation, test_divisor, next_monkey_test))

def calc_monkey_business(divisor, cycles):
    monkeys_copy = copy.deepcopy(monkeys)
    lcm = 1
    for x in [m.test_divisor for m in monkeys_copy]:
        lcm *= x

    for i in range(cycles):
        for monkey in monkeys_copy:
            for item in monkey.items:
                result = eval(monkey.operation.replace('old', str(item)))
                item = result % lcm if not divisor else result
                item //= divisor if divisor else 1
                monkeys_copy[monkey.next_monkey_test[item % monkey.test_divisor == 0]].items.append(item)
                monkey.handled_items += len(monkey.items)
                monkey.items = []
    monkeys_copy.sort()
    return monkeys_copy[-1].handled_items * monkeys_copy[-2].handled_items

# Task 1
print(calc_monkey_business(3, 20))

# Task 2
print(calc_monkey_business(0, 10_000))
