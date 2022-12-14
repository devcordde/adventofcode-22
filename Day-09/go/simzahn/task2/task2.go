package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
)

func main() {

	//create snake at P(0|0)
	var snake = [10]vector{}
	var tailPositions = []vector{}

	//set all snake parts to the starting position
	for i := range snake {
		snake[i] = vector{x: 0, y: 0}
	}

	//parse the input
	readfile, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	filescanner := bufio.NewScanner(readfile)
	filescanner.Split(bufio.ScanLines)

	for filescanner.Scan() {
		//get amount and direction
		direction := filescanner.Text()[:1]
		amount, err := strconv.Atoi(filescanner.Text()[2:])
		if err != nil {
			panic(err)
		}

		//move the snake one by one
		for i := 0; i < amount; i++ {
			//move the head to the correct position
			switch direction {
			case "R":
				snake[0].Add(vector{x: 1, y: 0})
			case "L":
				snake[0].Add(vector{x: -1, y: 0})
			case "U":
				snake[0].Add(vector{x: 0, y: 1})
			case "D":
				snake[0].Add(vector{x: 0, y: -1})
			}

			//move the rest of the snake
			for j := 1; j < len(snake); j++ {
				snake[j].moveTo(snake[j-1])
			}

			//save the position of the last part
			tailPositions = appendNew(tailPositions, snake[9])
		}

	}
	fmt.Println(len(tailPositions))
}

type vector struct {
	x int
	y int
}

func (v *vector) moveTo(target vector) {

	//get the difference between the head and the tail
	difference := substrateVector(target, *v)

	//check if the tail isn't near the head
	if math.Abs(float64(difference.x)) > 1 || math.Abs(float64(difference.y)) > 1 {

		if math.Abs(float64(difference.x)) > 1 {
			difference.x = goToZero(difference.x)
		}
		if math.Abs(float64(difference.y)) > 1 {
			difference.y = goToZero(difference.y)
		}

		v.Add(difference)
	}
}

func (v *vector) Add(vec2 vector) {
	v.x += vec2.x
	v.y += vec2.y
}

// subtracts the second vector from the first
func substrateVector(vec1 vector, vec2 vector) vector {

	return vector{
		x: vec1.x - vec2.x,
		y: vec1.y - vec2.y,
	}

}

// adds a vector to the array, if it does not already contain the vector
func appendNew(array []vector, vec vector) []vector {

	if !contains(array, vec) {
		array = append(array, vec)
	}
	return array
}

func contains(s []vector, e vector) bool {
	for _, a := range s {
		if a == e {
			return true
		}
	}
	return false
}

// decreases a positive number, increases a negative number
func goToZero(i int) int {
	if i == 0 {
		return 0
	}

	if i > 0 {
		return i - 1
	} else {
		return i + 1
	}
}
