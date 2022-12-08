package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

const (
	alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
)

func main() {
	input, err := ioutil.ReadFile("Day03/input.txt")
	if err != nil {
		panic(err)
	}

	lines := strings.Split(string(input), "\n")

	// Part 1
	sum := 0
	for _, line := range lines {
		if line == "" {
			continue
		}

		partOne, partTwo := splitInHalf(line)
		sum += getPriority(getMatchingChar(partOne, partTwo))
	}

	fmt.Println("Part 1:")
	fmt.Println(sum)

	// Part 2
	sum = 0
	
	for i := 0; i < len(lines)-1; i += 3 {
		group := []string{lines[i], lines[i+1], lines[i+2]}
		var matchingChars []string
		for _, entry := range group {
			matchingChars = append(matchingChars, strings.Split(entry, "")...)
		}
		for _, char := range matchingChars {
			if containsAll(group, char) {
				sum += getPriority(char)
				break
			}
		}
	}

	fmt.Println("Part 2:")
	fmt.Println(sum)
}

func splitInHalf(input string) (string, string) {
	return input[0 : len(input)/2], input[len(input)/2:]
}

func getPriority(char string) int {
	return strings.Index(alphabet, char) + 1
}

func getMatchingChar(first, second string) string {
	for _, char := range alphabet {
		if strings.Contains(first, string(char)) && strings.Contains(second, string(char)) {
			return string(char)
		}
	}
	return ""
}

func containsAll(input []string, char string) bool {
	for _, entry := range input {
		if !strings.Contains(entry, char) {
			return false
		}
	}
	return true
}
