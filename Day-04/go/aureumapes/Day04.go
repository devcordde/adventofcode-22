package main

import (
	"os"
	"strconv"
	"strings"
)

var inputBytes, _ = os.ReadFile("Day-04/go/aureumapes/input.txt")
var input = string(inputBytes)

func main() {
	partOne()
	partTwo()
}

func partOne() {
	containingTheOther := 0
	for _, pair := range strings.Split(input, "\n") {
		// Assign the Elves
		elves := strings.Split(pair, ",")
		elvOne := elves[0]
		elvTwo := elves[1]

		// Save the first and last section of each elven as int
		firstSectionFirstElv, _ := strconv.Atoi(strings.Split(elvOne, "-")[0])
		lastSectionFirstElv, _ := strconv.Atoi(strings.Split(elvOne, "-")[1])
		firstSectionSecondElv, _ := strconv.Atoi(strings.Split(elvTwo, "-")[0])
		lastSectionSecondElv, _ := strconv.Atoi(strings.Split(elvTwo, "-")[1])

		// Check if one Range contains the other
		if (firstSectionFirstElv <= firstSectionSecondElv && lastSectionFirstElv >= lastSectionSecondElv) || (firstSectionFirstElv >= firstSectionSecondElv && lastSectionFirstElv <= lastSectionSecondElv) {
			containingTheOther++
		}
	}
	println(containingTheOther)
}

func partTwo() {
	overlappingPairs := 0
	for _, pair := range strings.Split(input, "\n") {
		// Assign the Elves
		elves := strings.Split(pair, ",")
		elvOne := elves[0]
		elvTwo := elves[1]

		// Save the first and last section of each elven as int
		firstElvFirstSection, _ := strconv.Atoi(strings.Split(elvOne, "-")[0])
		firstElvSecondSection, _ := strconv.Atoi(strings.Split(elvOne, "-")[1])
		secondElvFirstSection, _ := strconv.Atoi(strings.Split(elvTwo, "-")[0])
		secondElvSecondSection, _ := strconv.Atoi(strings.Split(elvTwo, "-")[1])

		// Check if the ranges do overlap
		if firstElvFirstSection <= secondElvSecondSection && firstElvSecondSection >= secondElvFirstSection {
			overlappingPairs++
		}
	}
	println(overlappingPairs)
}
