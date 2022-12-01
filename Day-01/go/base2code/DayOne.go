package main

import (
	"fmt"
	"io/ioutil"
	"sort"
	"strings"
)

func main() {
	// Read file into array sepeerated by new line
	input, err := ioutil.ReadFile("Day01/input.txt")
	if err != nil {
		panic(err)
	}

	lines := strings.Split(string(input), "\n")

	elfes := make(map[int]int)
	counter := 0

	for _, line := range lines {
		// Check if line is empty and start a new elf
		if line == "" {
			counter++
			continue
		}

		inputLineInt := 0
		fmt.Sscanf(line, "%d", &inputLineInt)

		// check if inputLineInt is in map
		if _, ok := elfes[counter]; ok {
			elfes[counter] += inputLineInt
		} else {
			elfes[counter] = inputLineInt
		}
	}

	// sort elfes by value
	var elfesSorted []int
	for _, value := range elfes {
		elfesSorted = append(elfesSorted, value)
	}

	sort.Ints(elfesSorted)

	// print result
	fmt.Println("Part 1:")
	fmt.Println(elfesSorted[len(elfesSorted)-1])

	topThree := elfesSorted[len(elfesSorted)-3:]
	topThreeSum := topThree[0] + topThree[1] + topThree[2]
	fmt.Println("Part 2:")
	fmt.Println(topThreeSum)
}
