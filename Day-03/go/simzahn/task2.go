package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {

	var sum = 0

	readfile, error := os.Open("input.txt")
	if error != nil {
		fmt.Println(error)
		return
	}

	filescanner := bufio.NewScanner(readfile)
	for filescanner.Scan() {
		elfe1 := filescanner.Text()
		if !filescanner.Scan() {
			fmt.Println("No Line Found!")
			return
		}
		elfe2 := filescanner.Text()
		if !filescanner.Scan() {
			fmt.Println("No Line Found!")
			return
		}
		elfe3 := filescanner.Text()

		var badge uint8 = 0

		//get the acii index of the triple letter
		for i := 0; badge == 0; i++ {
			for j := 0; j < len(elfe2); j++ {
				for k := 0; k < len(elfe3); k++ {

					if elfe1[i] == elfe2[j] && elfe1[i] == elfe3[k] {
						badge = elfe1[i]
					}

				}
			}
		}

		sum += int(AsciiToBackpackPriority(badge))

	}

	fmt.Println("---------------------------------")
	fmt.Println(sum)

}

func AsciiToBackpackPriority(asciiIndex uint8) uint8 {

	//the lowercase letters
	if asciiIndex >= 97 && asciiIndex <= 122 {
		return asciiIndex - 96
	} else /*the uppercase letters*/ if asciiIndex >= 65 && asciiIndex <= 90 {
		return asciiIndex - 64 + 26
		//minus 64 to get to 1 and at 26, because the uppercase letters do have priorities from 27 to 57
	}

	fmt.Println("The ASCII index does not belong to any upper or lowercase letter!")
	return 0
}
