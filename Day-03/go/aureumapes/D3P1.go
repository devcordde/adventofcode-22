package main

import (
	"strings"
	"unicode"
)

func main() {
        inputBytes, _ := ioutil.ReadFile("input.txt")
	input := string(inputBytes)
	rucksacks := strings.Split(input, "\n")
	totalPriority := 0
	for _, rucksack := range rucksacks {
		isChecked := ""
		itemsCount := strings.Count(rucksack, "") - 1
		compartment1 := string([]rune(rucksack)[:len(rucksack)-itemsCount/2])
		_, compartment2, _ := strings.Cut(rucksack, compartment1)
		for _, itemRune := range compartment1 {
			item := string(itemRune)
			isDouble := strings.ContainsRune(compartment2, itemRune)
			if isDouble && !strings.ContainsAny(isChecked, item) {
				priority := PriorityCalc(itemRune)
				totalPriority += priority
				isChecked = isChecked + item
			}
		}
	}
	print(totalPriority)
}

func priorityCalc(item rune) int {
	if unicode.IsLower(item) {
		priority := int(item) - 96
		return priority
	} else {
		return int(item) - 38
	}
}