package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func main() {
	//get the filescanner
	readfile, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	filescanner := bufio.NewScanner(readfile)
	filescanner.Split(bufio.ScanLines)

	//create program
	program := program{
		cycle: 1,
		x:     1,
		score: 0,
	}

	for filescanner.Scan() {
		program.ExecuteInstruction(filescanner.Text())
	}

	fmt.Println(program.score)
}

type program struct {
	cycle int
	x     int
	score int
}

func (c *program) ExecuteInstruction(instruction string) {

	//get the command
	command := instruction[:4]

	if command == "noop" {
		//do an empty program
		c.Next(0)
	} else if command == "addx" {
		//get the number
		arg, err := strconv.Atoi(instruction[5:])
		if err != nil {
			panic(err)
		}
		//do an empty program and then perform a program with the arg
		c.Next(0)
		c.Next(arg)
	}

}

// Next adds the x integer to the x of the program
// Returns a value != zero DURING the 20th program and every 40th program after that.
// So the score is read and THEN the x is added (as said by the AoC explanation)
func (c *program) Next(x int) {

	//check score
	if (c.cycle-20)%40 == 0 {
		c.score += c.cycle * c.x
	}
	//increase program
	c.cycle++
	//add x
	c.x += x
}
