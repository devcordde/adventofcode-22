package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {

	var counter = 0

	readfile, err := os.Open("input")
	if err != nil {
		panic(err)
	}

	filescanner := bufio.NewScanner(readfile)
	filescanner.Split(bufio.ScanLines)

	//read the input
	for filescanner.Scan() {

		input := strings.Split(filescanner.Text(), ",")

		var firstRange, secondRange = []int{}, []int{}

		firstRange = StringArrayToIntArray(strings.Split(input[0], "-"))
		secondRange = StringArrayToIntArray(strings.Split(input[1], "-"))

		firstRange = FillNumberGaps(firstRange)
		secondRange = FillNumberGaps(secondRange)

		if FullyContainsArray(firstRange, secondRange) || FullyContainsArray(secondRange, firstRange) {
			counter++
		}
	}

	fmt.Println(counter)
}
