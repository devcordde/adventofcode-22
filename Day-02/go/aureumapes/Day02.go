package main

import (
	"os"
	"strings"
)

var inputBytes, _ = os.ReadFile("Day-02/go/aureumapes/input.txt")
var input = string(inputBytes)

func main() {
	partOne()
	partTwo()
}

func partOne() {
	points := 0
	for _, round := range strings.Split(input, "\n") {
		enemy := strings.Split(round, " ")[0]
		me := strings.Split(round, " ")[1]
		switch me {
		case "X":
			points += 1
			if enemy == "A" {
				points += 3
			}
			if enemy == "C" {
				points += 6
			}
			break
		case "Y":
			points += 2
			if enemy == "A" {
				points += 6
			}
			if enemy == "B" {
				points += 3
			}
			break
		case "Z":
			points += 3
			if enemy == "C" {
				points += 3
			}
			if enemy == "B" {
				points += 6
			}
			break
		default:
			break
		}
	}
	println(points)
}

/*
0 = X = Lose
3 = Y = Tie
6 = Z = Win
1 = A = Stone
2 = B = Paper
3 = C = Scissors
*/
func partTwo() {
	points := 0
	for _, round := range strings.Split(input, "\n") {
		enemy := strings.Split(round, " ")[0]
		result := strings.Split(round, " ")[1]
		switch result {
		case "X":
			if enemy == "A" {
				points += 3
			}
			if enemy == "B" {
				points += 1
			}
			if enemy == "C" {
				points += 2
			}
			break
		case "Y":
			points += 3
			if enemy == "A" {
				points += 1
			}
			if enemy == "B" {
				points += 2
			}
			if enemy == "C" {
				points += 3
			}
			break
		case "Z":
			points += 6
			if enemy == "A" {
				points += 2
			}
			if enemy == "B" {
				points += 3
			}
			if enemy == "C" {
				points += 1
			}
			break
		default:
			break
		}
	}
	println(points)
}
