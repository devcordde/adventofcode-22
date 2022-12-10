package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {

	var sum = 0

	readfile, error := os.Open("input")
	if error != nil {
		fmt.Println(error)
		return
	}

	filescanner := bufio.NewScanner(readfile)
	for filescanner.Scan() {
		//split into the two compartments
		firstCompartment := filescanner.Text()[0 : len(filescanner.Text())/2]
		secondCompartment := filescanner.Text()[len(filescanner.Text())/2 : len(filescanner.Text())]

		var doubleLetter uint8 = 0
		//get the acii index of the doubled letter
		for i := 0; doubleLetter == 0; i++ {
			for j := 0; j < len(firstCompartment); j++ {

				if firstCompartment[i] == secondCompartment[j] {
					doubleLetter = firstCompartment[i]
				}

			}
		}

		//add the priority to the final sum
		sum += int(AsciiToBackpackPriority(doubleLetter))
		doubleLetter = 0
	}

	fmt.Println(sum)

}
