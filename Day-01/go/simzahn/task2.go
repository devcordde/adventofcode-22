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

	//get the three highest values
	maxCalories := [3]int{0, 0, 0}
	for _, calories := range elfes {
		if calories > maxCalories[2] {
			if calories > maxCalories[1] {
				if calories > maxCalories[0] {
					maxCalories[2] = maxCalories[1]
					maxCalories[1] = maxCalories[0]
					maxCalories[0] = calories
				} else {
					maxCalories[2] = maxCalories[1]
					maxCalories[1] = calories
				}
			} else {
				maxCalories[2] = calories
			}
		}
	}

	fmt.Println(maxCalories[0] + maxCalories[1] + maxCalories[2])

}
