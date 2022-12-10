package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {

	var port = new(Port)

	readfile, err := os.Open("input")
	if err != nil {
		panic(err)
	}
	filescanner := bufio.NewScanner(readfile)

	//read the data
	var creates []string
	for filescanner.Scan() {
		if filescanner.Text() == "" {
			break
		}
		creates = append(creates, filescanner.Text())
	}

	//read the instruction given for the movement
	var instructions []string

	for filescanner.Scan() {
		instructions = append(instructions, filescanner.Text())
	}
	readfile.Close()

	port.NewPort(creates)

	for _, instructionInput := range instructions {
		instruction := strings.Replace(instructionInput, "move ", "", -1)
		instruction = strings.Replace(instruction, "from ", "", -1)
		instruction = strings.Replace(instruction, "to ", "", -1)
		splitedInstructions := strings.Split(instruction, " ")

		amount, err := strconv.Atoi(splitedInstructions[0])
		if err != nil {
			panic(err)
		}
		fromStack, err := strconv.Atoi(splitedInstructions[1])
		if err != nil {
			panic(err)
		}
		toStack, err := strconv.Atoi(splitedInstructions[2])
		if err != nil {
			panic(err)
		}

		port.MoveCreates(amount, fromStack-1, toStack-1)
	}

	solutionBuilder := strings.Builder{}

	for _, stack := range port.stacks {
		solutionBuilder.WriteString(stack.GetCreate(stack.Height() - 1).GetName())
	}

	fmt.Println(solutionBuilder.String())

}
