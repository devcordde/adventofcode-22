package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {

	//the total score
	var score = 0

	//setup of the filescanner
	readfile, err := os.Open("input.txt")
	if err != nil {
		fmt.Println(err)
	}
	filescanner := bufio.NewScanner(readfile)
	filescanner.Split(bufio.ScanWords)

	//get opponent choice and suggestion
	var opponentChoices = []int{}
	var suggestions = []string{}

	for filescanner.Scan() {
		switch filescanner.Text() {
		case "A":
			opponentChoices = append(opponentChoices, 1)
		case "B":
			opponentChoices = append(opponentChoices, 2)
		case "C":
			opponentChoices = append(opponentChoices, 3)
		}
		if !filescanner.Scan() {
			fmt.Println("no second value was found")
			return
		}
		suggestions = append(suggestions, filescanner.Text())
	}
	readfile.Close()

	//loop through all matches
	for i := 0; i < len(opponentChoices); i++ {
		score += GetMatchScoreWithSuggestion(opponentChoices[i], suggestions[i])
	}

	fmt.Println(score)

}

func GetMatchScore(myChoice int, opponentChoice int) int {
	//add the points of the chosen tool
	points := myChoice

	/*
		1 = Rock
		2 = Paper
		3 = Scissors
		Substract the first value from the second:
				Rock - Paper		1 - 2		-1			\
				Paper - Scissors	2 - 3		-1			 |> Loose
				Scissors - Rock		3 - 1		+2			/

				Rock - Scissors		1 - 3		-2			\
				Paper - Rock		2 - 1		+1			 |> Win
				Scissors - Paper	3 - 2		+1			/

				1 - 1		0 			\
				2 - 2		0			 |> Draw
				3 - 3		0			/


	*/

	//draw
	if myChoice-opponentChoice == 0 {
		return points + 3
	}
	//win
	if ((myChoice - opponentChoice) == 1) || ((myChoice - opponentChoice) == -2) {
		return points + 6
	}
	//loose
	return points
}

// GetMatchScoreWithSuggestion
// Returns the points of the match based on the opponent's choice and the suggestion of the goblin
func GetMatchScoreWithSuggestion(opponentChoice int, suggestion string) int {
	var myChoiceSummand int

	switch suggestion {
	//Loose
	case "X":
		myChoiceSummand = 2
	//draw
	case "Y":
		myChoiceSummand = 0
	//win
	case "Z":
		myChoiceSummand = 1
	default:
		fmt.Println("Only X,Y,Z are valid suggestions!!")
		return 0
	}

	myChoice := opponentChoice + myChoiceSummand

	if myChoice > 3 {
		myChoice = myChoice % 3
	}

	return GetMatchScore(myChoice, opponentChoice)

}
