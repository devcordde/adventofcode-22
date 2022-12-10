package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {

	var i int

	readfile, err := os.Open("input")
	if err != nil {
		panic(err)
	}
	fileScanner := bufio.NewScanner(readfile)
	if !fileScanner.Scan() {
		fmt.Println("No input found!")
		return
	}
	input := fileScanner.Text()

	for i = 14; i < len(input); i++ {
		currentSequenze := input[i-14 : i]
		if HasNoDoubleLetter(currentSequenze) {
			break
		}
	}

	fmt.Println(i)

}

func HasNoDoubleLetter(sequenze string) bool {
	for i := 0; i < len(sequenze); i++ {
		if strings.Count(sequenze, string([]rune(sequenze)[i])) > 1 {
			return false
		}
	}
	return true
}
