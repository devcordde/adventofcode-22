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

	//read the choices and convert into int
	var opponentChoices = []int{}
	var myChoices = []int{}

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
		switch filescanner.Text() {
		case "X":
			myChoices = append(myChoices, 1)
		case "Y":
			myChoices = append(myChoices, 2)
		case "Z":
			myChoices = append(myChoices, 3)
		}
	}
	readfile.Close()

	//get score
	for i := 0; i < len(opponentChoices); i++ {
		score += GetMatchScore(myChoices[i], opponentChoices[i])
	}

	fmt.Println(score)

	/*
		//testing purposes
		fmt.Println("------------------------------")
		fmt.Println(getMatchScore(2, 1))
		fmt.Println(getMatchScore(1, 2))
		fmt.Println(getMatchScore(3, 3))
	*/
}
