package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func main() {

	var elfes = []int{0}

	readFile, err := os.Open("input.txt")

	if err != nil {
		fmt.Println(err)
	}

	filescanner := bufio.NewScanner(readFile)

	filescanner.Split(bufio.ScanLines)

	counter := 0
	for filescanner.Scan() {
		line := filescanner.Text()
		if line == "" {
			counter++
			elfes = append(elfes, 0)
		} else {
			calories, err := strconv.Atoi(line)
			if err != nil {
				fmt.Println(err)
			}
			elfes[counter] += calories
		}
	}

	err = readFile.Close()
	if err != nil {
		fmt.Println(err)
	}

	//get the maximum value
	maxCalories := 0
	for _, calories := range elfes {
		if calories > maxCalories {
			maxCalories = calories
		}
	}

	println(maxCalories)

}
