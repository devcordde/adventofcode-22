input = open('input.txt').read().splitlines()

def get_cycle_output(cycle, x):
    return cycle * x if cycle in [20, 60, 100, 140, 180, 220] else 0

def crt_is_drawing(cycle, x):
    return cycle % 40 in range(x - 1, x + 2)

cycle = output_sum = 0
x = 1
drawing = ''
for i in range(len(input)):
    split_input = input[i].split(' ')
    for i in range(2 if len(split_input) > 1 else 1):
        drawing += '#' if crt_is_drawing(cycle, x) else '.'
        cycle += 1
        output_sum += get_cycle_output(cycle, x)
        if i == 1: x += int(split_input[1])

# Task 1
print(output_sum)

# Task 2
print('\n'.join([drawing[i:i + 40] for i in range(0, len(drawing), 40)]))
