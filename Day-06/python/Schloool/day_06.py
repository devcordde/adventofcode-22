input = open('input.txt').read()

def get_marker_index(u):
    for i in range(u - 1, len(input)):
        if len(set(input[i - (u - 1):i + 1])) == u:
            return i + 1

# Task 1
print(get_marker_index(4))

# Task 2
print(get_marker_index(14))
