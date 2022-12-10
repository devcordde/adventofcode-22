package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
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

		if ContainsAnyOfArray(firstRange, secondRange) {
			counter++
		}

	}

	fmt.Println(counter)

}

func StringArrayToIntArray(stringArray []string) []int {

	var intArray = []int{}

	for i := 0; i < len(stringArray); i++ {
		number, err := strconv.Atoi(stringArray[i])
		if err != nil {
			panic(err)
		}
		intArray = append(intArray, number)
	}

	return intArray
}

func FillNumberGaps(array []int) []int {

	for i := 1; array[0]+i < array[1]; i++ {
		array = append(array, array[0]+i)
	}

	return array
}

func FullyContainsArray(s1 []int, s2 []int) bool {
	if len(s1) > len(s2) {
		return false
	}
	for _, e := range s1 {
		if !Contains(s2, e) {
			return false
		}
	}
	return true
}

func ContainsAnyOfArray(s1 []int, s2 []int) bool {
	for _, a := range s2 {
		if Contains(s1, a) {
			return true
		}
	}
	return false
}

func Contains(s []int, e int) bool {
	for _, a := range s {
		if a == e {
			return true
		}
	}
	return false
}
