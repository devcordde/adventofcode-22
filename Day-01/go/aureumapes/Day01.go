package main

import (
	"os"
	"sort"
	"strconv"
	"strings"
)

func main() {
	partOne()
	partTwo()
}

func partOne() {
	inputBytes, _ := os.ReadFile("Day-01/go/aureumapes/input.txt")
	input := string(inputBytes)
	inventories := strings.Split(input, "\n\n")
	highestCalories := 0
	for _, inventory := range inventories {
		items := strings.Split(inventory, "\n")
		inventoryCalories := 0
		for _, item := range items {
			itemCalories, _ := strconv.Atoi(item)
			inventoryCalories += itemCalories
		}
		if inventoryCalories > highestCalories {
			highestCalories = inventoryCalories
		}
	}
	println(highestCalories)
}

func partTwo() {
	inputBytes, _ := os.ReadFile("/home/aureum/GolandProjects/adventofcode-22/Day-01/go/aureumapes/input.txt")
	input := string(inputBytes)
	inventories := strings.Split(input, "\n\n")
	var inventoriesCalories []int
	for _, inventory := range inventories {
		items := strings.Split(inventory, "\n")
		inventoryCalories := 0
		for _, item := range items {
			itemCalories, _ := strconv.Atoi(item)
			inventoryCalories += itemCalories
		}
		inventoriesCalories = append(inventoriesCalories, inventoryCalories)
	}
	sort.Ints(inventoriesCalories)
	print(inventoriesCalories[len(inventoriesCalories)-3] + inventoriesCalories[len(inventoriesCalories)-2] + inventoriesCalories[len(inventoriesCalories)-1])
}
