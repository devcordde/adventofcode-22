package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
)

var all map[string]string = make(map[string]string)

func main() {
	input, err := ioutil.ReadFile("Day21/input.txt")
	if err != nil {
		panic(err)
	}

	lines := strings.Split(string(input), "\n")

	for _, v := range lines {
		if v == "" {
			continue
		}
		split := strings.Split(v, ": ")
		all[split[0]] = split[1]
	}

	fmt.Println("Part 1:")
	fmt.Println(getNumber("root"))

	// Part 2
	root := all["root"]
	name1 := strings.Split(root, " ")[0]
	name2 := strings.Split(root, " ")[2]
	if containsMoney(name1, "humn") {
		fmt.Println(name2, "needs to be changed to humn")
	} else if containsMoney(name2, "humn") {
		fmt.Println(name1, "needs to be changed to humn")
	}
}

func containsMoney(name string, targetName string) bool {
	data := all[name]
	if strings.Contains(data, " ") {
		name1 := strings.Split(data, " ")[0]
		name2 := strings.Split(data, " ")[2]
		if targetName == name1 || targetName == name2 {
			return true
		} else {
			return containsMoney(name1, targetName) || containsMoney(name2, targetName)
		}
	}
	return false
}

func getNumber(name string) int {
	data := all[name]
	if strings.Contains(data, " ") {
		name1 := strings.Split(data, " ")[0]
		name2 := strings.Split(data, " ")[2]
		operator := strings.Split(data, " ")[1]
		switch operator {
		case "+":
			return getNumber(name1) + getNumber(name2)
		case "*":
			return getNumber(name1) * getNumber(name2)
		case "/":
			return getNumber(name1) / getNumber(name2)
		case "-":
			return getNumber(name1) - getNumber(name2)
		}
	}
	value, _ := strconv.Atoi(data)
	return value
}
