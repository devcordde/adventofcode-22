package main

import (
	"os"
	"strings"
	"unicode"
)

var inputBytes, _ = os.ReadFile("Day-03/go/aureumapes/input.txt")
var input = string(inputBytes)
var elves = strings.Split(input, "\n")

func main() {
	partOne()
	partTwo()
}

func partTwo() {
	groups := []string{}
	currentElv := -1
	badgesPriorities := 0
	for i := 0; i < len(elves)/3; i++ {
		group := ""
		currentElv++
		group += elves[currentElv] + "\n"
		currentElv++
		group += elves[currentElv] + "\n"
		currentElv++
		group += elves[currentElv]
		groups = append(groups, group)
	}
	for _, group := range groups {
		alreadyChecked := ""
		for _, item := range strings.Split(strings.Split(group, "\n")[0], "") {
			if !strings.ContainsAny(alreadyChecked, item) {
				if strings.Contains(strings.Split(group, "\n")[1], item) && strings.ContainsAny(strings.Split(group, "\n")[2], item) {
					badgesPriorities += priorityCalc([]rune(item)[0])
					alreadyChecked += item
				}
			}
		}
	}
	print(badgesPriorities)
	return
}

func partOne() {
	totalPriority := 0
	for _, rucksack := range elves {
		isChecked := ""
		itemsCount := strings.Count(rucksack, "") - 1
		compartment1 := string([]rune(rucksack)[:len(rucksack)-itemsCount/2])
		_, compartment2, _ := strings.Cut(rucksack, compartment1)
		for _, itemRune := range compartment1 {
			item := string(itemRune)
			isDouble := strings.ContainsRune(compartment2, itemRune)
			if isDouble && !strings.ContainsAny(isChecked, item) {
				priority := priorityCalc(itemRune)
				totalPriority += priority
				isChecked = isChecked + item
			}
		}
	}
	println(totalPriority)

}

func priorityCalc(item rune) int {
	if unicode.IsLower(item) {
		priority := int(item) - 96
		return priority
	} else {
		return int(item) - 38
	}
}
