package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

const (
	ScoreRock        = 1
	ScorePaper       = 2
	ScoreScissors    = 3
	ScoreWin         = 6
	ScoreDraw        = 3
	ScoreLose        = 0
	OpponentRock     = "A"
	OpponentPaper    = "B"
	OpponentScissors = "C"
	PlayerRock       = "X"
	PlayerPaper      = "Y"
	PlayerScissors   = "Z"
	WantsLose        = "X"
	WantsDraw        = "Y"
	WantsWin         = "Z"
)

func main() {
	input, err := ioutil.ReadFile("Day02/input.txt")
	if err != nil {
		panic(err)
	}

	lines := strings.Split(string(input), "\n")

	scorePartOne := 0
	scorePartTwo := 0

	for _, line := range lines {
		if line == "" {
			continue
		}

		split := strings.Split(line, " ")
		opponentMove := split[0]
		ownMove := split[1]

		scorePartOne += playerWinScore(opponentMove, ownMove)
		scorePartOne += shapeScore(ownMove)

		shouldPlay := whatToPlay(ownMove, opponentMove)
		scorePartTwo += playerWinScore(opponentMove, shouldPlay)
		scorePartTwo += shapeScore(shouldPlay)
	}

	fmt.Println("Part 1:")
	fmt.Println(scorePartOne)

	fmt.Println("Part 2:")
	fmt.Println(scorePartTwo)
}

func shapeScore(shape string) int {
	if shape == PlayerRock {
		return ScoreRock
	}
	if shape == PlayerPaper {
		return ScorePaper
	}
	if shape == PlayerScissors {
		return ScoreScissors
	}
	panic("Shape invalid")
}

func playerWinScore(opponentMove string, ownMove string) int {
	if opponentMove == OpponentRock && ownMove == PlayerRock {
		// Draw Rock
		return ScoreDraw
	} else if opponentMove == OpponentRock && ownMove == PlayerPaper {
		// Rock vs Paper
		return ScoreWin
	} else if opponentMove == OpponentRock && ownMove == PlayerScissors {
		// Rock vs Scissors
		return ScoreLose
	} else if opponentMove == OpponentPaper && ownMove == PlayerRock {
		// Paper vs Rock
		return ScoreLose
	} else if opponentMove == OpponentPaper && ownMove == PlayerPaper {
		// Draw Paper
		return ScoreDraw
	} else if opponentMove == OpponentPaper && ownMove == PlayerScissors {
		// Paper vs Scissors
		return ScoreWin
	} else if opponentMove == OpponentScissors && ownMove == PlayerRock {
		// Scissors vs Rock
		return ScoreWin
	} else if opponentMove == OpponentScissors && ownMove == PlayerPaper {
		// Scissors vs Paper
		return ScoreLose
	} else if opponentMove == OpponentScissors && ownMove == PlayerScissors {
		// Draw Scissors
		return ScoreDraw
	}
	panic("Invalid input: " + opponentMove + " " + ownMove)
}

func whatToPlay(wantedResult string, opponent string) string {
	if wantedResult == WantsWin && opponent == OpponentScissors {
		return PlayerRock
	}
	if wantedResult == WantsWin && opponent == OpponentPaper {
		return PlayerScissors
	}
	if wantedResult == WantsWin && opponent == OpponentRock {
		return PlayerPaper
	}
	if wantedResult == WantsDraw && opponent == OpponentScissors {
		return PlayerScissors
	}
	if wantedResult == WantsDraw && opponent == OpponentPaper {
		return PlayerPaper
	}
	if wantedResult == WantsDraw && opponent == OpponentRock {
		return PlayerRock
	}
	if wantedResult == WantsLose && opponent == OpponentScissors {
		return PlayerPaper
	}
	if wantedResult == WantsLose && opponent == OpponentPaper {
		return PlayerRock
	}
	if wantedResult == WantsLose && opponent == OpponentRock {
		return PlayerScissors
	}
	panic("Invalid input")
}

