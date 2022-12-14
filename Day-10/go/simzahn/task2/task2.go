package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {

	//get the filescanner
	readfile, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	filescanner := bufio.NewScanner(readfile)
	filescanner.Split(bufio.ScanLines)

	//create program
	program := program{
		cycle:     0,
		spritePos: 1,
		bitmap:    [240]bool{},
	}

	//set all pixels to false (dark)
	for i := range program.bitmap {
		program.bitmap[i] = false
	}

	//fill the bitmap
	for filescanner.Scan() {
		program.ExecuteInstruction(filescanner.Text())
	}

	//draw
	for line := 0; line < 240/40; line++ {
		builder := strings.Builder{}
		for i := 0; i < 40; i++ {
			if program.bitmap[line*40+i] {
				builder.WriteString("#")
			} else {
				builder.WriteString(".")
			}
		}
		fmt.Println(builder.String())
	}

	/****Output:

	####..##..#....#..#.###..#....####...##.
	#....#..#.#....#..#.#..#.#....#.......#.
	###..#....#....####.###..#....###.....#.
	#....#.##.#....#..#.#..#.#....#.......#.
	#....#..#.#....#..#.#..#.#....#....#..#.
	####..###.####.#..#.###..####.#.....##..

	=> EGLHBLFJ

	*/

}

type program struct {
	cycle     int
	spritePos int
	bitmap    [240]bool
}

func (p *program) ExecuteInstruction(instruction string) {

	//get the command
	command := instruction[:4]

	if command == "noop" {
		//draw a pixel without moving the sprite
		p.Next(0)
	} else if command == "addx" {
		//get the number
		arg, err := strconv.Atoi(instruction[5:])
		if err != nil {
			panic(err)
		}
		//draw a pixel without moving the sprite
		p.Next(0)
		//move the sprite
		p.Next(arg)
	}

}

// Next perform next cycle.
// First draws the pixel.
// Then readjusts the SpritePosition, if necessary
func (p *program) Next(x int) {

	/*draw pixel*/
	//check if sprite contains current pixel
	if p.cycle%40 >= p.spritePos-1 && p.cycle%40 <= p.spritePos+1 {
		//set the pixel to true -> lit
		p.bitmap[p.cycle] = true
	}

	//increase program
	p.cycle++
	//add spritePos
	p.spritePos += x
}
