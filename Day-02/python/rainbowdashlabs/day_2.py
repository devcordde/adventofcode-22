first = {
    "A": "Rock",
    "B": "Paper",
    "C": "Scissor"
}

second = {
    "X": "Rock",
    "Y": "Paper",
    "Z": "Scissor"
}

points = {
    "Rock": 1,
    "Paper": 2,
    "Scissor": 3
}

choices = [e.strip().split(" ") for e in open('input.txt')]

print(choices)
ico = [[first[e[0]], second[e[1]]] for e in choices]


def check(other, my) -> int:
    point = points[my]

    if other == my:
        return 3 + point

    if other == "Scissor" and my == "Paper":
        return 0 + point
    if other == "Scissor" and my == "Rock":
        return 6 + point

    if other == "Paper" and my == "Rock":
        return 0 + point

    if other == "Paper" and my == "Scissor":
        return 6 + point

    if other == "Rock" and my == "Paper":
        return 6 + point

    if other == "Rock" and my == "Scissor":
        return 0 + point


print([check(*e) for e in ico])

print(f"Part 1 {sum([check(*e) for e in ico])}")

win = {
    "Scissor": "Rock",  # Loose
    "Paper": "Scissor",  # draw
    "Rock": "Paper"  # Win
}

loose = {
    "Rock": "Scissor",  # Loose
    "Scissor": "Paper",  # draw
    "Paper": "Rock"  # Win
}


def win_con(other, my):
    if my == "X":
        return [other,loose[other]]

    if my == "Y":
        return [other,other]

    if my == "Z":
        return [other, win[other]]

print(f"Part 1 {sum([check(*win_con(first[e[0]], e[1])) for e in choices])}")