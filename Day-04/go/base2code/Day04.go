package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
)

func main() {
	input, err := ioutil.ReadFile("Day04/input.txt")
	if err != nil {
		panic(err)
	}

	lines := strings.Split(string(input), "\n")

	sum := 0

	// Part 1
	for _, entry := range parseInput(lines) {
		if doesPair2ContainPair1(entry[:2], entry[2:]) || doesPair2ContainPair1(entry[2:], entry[:2]) {
			sum++
		}
	}

	fmt.Println("Part 1:")
	fmt.Println(sum)

	// Part 2
	sum = 0
	for _, entry := range parseInput(lines) {
		if doesOverlap(entry[:2], entry[2:]) || doesOverlap(entry[2:], entry[:2]) {
			sum++
		}
	}

	fmt.Println("Part 2:")
	fmt.Println(sum)
}

func doesPair2ContainPair1(pair1, pair2 []int) bool {
	return pair1[0] >= pair2[0] && pair1[1] <= pair2[1]
}

func parseInput(input []string) (retunedMap [][]int) {
	for _, line := range input {
		if line == "" {
			continue
		}
		parts := strings.Split(line, ",")
		left := strings.Split(parts[0], "-")
		right := strings.Split(parts[1], "-")
		retunedMap = append(retunedMap, []int{
			castToInt(left[0]), castToInt(left[1]),
			castToInt(right[0]), castToInt(right[1]),
		})
	}
	return retunedMap
}

func castToInt(s string) int {
	i, err := strconv.Atoi(s)
	if err != nil {
		panic(err)
	}
	return i
}

func doesOverlap(pair1, pair2 []int) bool {
	return pair1[0] >= pair2[0] && pair1[0] <= pair2[1] ||
		pair1[1] >= pair2[0] && pair1[1] <= pair2[1]
}
