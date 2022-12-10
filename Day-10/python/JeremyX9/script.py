answer = 0
commands = [n.strip() for n in open('input.txt')]
x_value = 1
cycle_iterator = 0
cycles = [20, 60, 100, 140, 180, 220]
for command in commands:
    if command == "noop":
        cycle_iterator += 1
        if cycles.__contains__(cycle_iterator):
            answer += (cycle_iterator * x_value)
    if command.startswith("addx"):
        for i in range(2):
            cycle_iterator += 1
            if cycles.__contains__(cycle_iterator):
                answer += (cycle_iterator * x_value)
            if i == 1:
                x_value += int(command.split(" ")[1])

print(f"Part 1: {answer}")

x_value = 1
cycle_iterator = 0
sprite = [["." for n in range(40)] for i in range(6)]


def is_on_marker(row, iterator, x):
    x_values = [x - 1, x + 1, x]
    if x_values.__contains__(iterator):
        sprite[row][iterator] = "#"


row = 0
for command in commands:
    if command == "noop":
        cycle_iterator += 1
        if cycle_iterator == 40:
            row += 1
            cycle_iterator = 0
        is_on_marker(row, cycle_iterator, x_value)
    if command.startswith("addx"):
        for i in range(2):
            cycle_iterator += 1
            if i == 1:
                x_value += int(command.split(" ")[1])
            if cycle_iterator == 40:
                row += 1
                cycle_iterator = 0
            is_on_marker(row, cycle_iterator, x_value)
print(f"Part 2:")
for sprit in sprite:
    print("".join(sprit))
