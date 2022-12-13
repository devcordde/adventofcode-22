package main

import (
	"strconv"
)

type Create struct {
	name string
}

type Stack struct {
	creates []Create
}

type Port struct {
	stacks []Stack
}

func (c *Create) NewCreate(name string) {
	c.name = name
}

func (p *Port) NewPort(input []string) {

	//get the port length
	portLength, err := strconv.Atoi(input[len(input)-1][len(input[len(input)-1])-1:]) //get the last char of the last element of the array
	if err != nil {
		panic(err)
	}
	//generate all stacks
	for i := 0; i < portLength; i++ {
		p.addStack()
	}

	//put data into port struct
	for i := len(input) - 2; i >= 0; i-- {

		for j := 0; 4*j+1 < len(input[i]); j++ {
			letter := string([]rune(input[i])[4*j+1])
			if !(letter == " ") {
				p.GetStack(j).addCreate(Create{name: letter})
			}
		}

	}

}

func (p *Port) GetPortLengh() int {
	return len(p.stacks)
}

func (p *Port) addStack() {
	p.stacks = append(p.stacks, Stack{creates: []Create{}})
}

func (s *Stack) addCreate(create Create) {
	s.creates = append(s.creates, create)
}

func (s *Stack) removeCreate() Create {
	create := s.creates[len(s.creates)-1]
	s.creates = s.creates[:len(s.creates)-1]
	return create
}

func (s *Stack) GetCreate(position int) Create {
	return s.creates[position]
}

func (s *Stack) Height() int {
	return len(s.creates)
}

func (c Create) GetName() string {
	return c.name
}

func (p *Port) GetStack(index int) *Stack {
	return &(p.stacks[index])
}

// View display the port as sting
func (p *Port) View() string {
	//@TODO add view
	return ""
}

// MoveCreates moves the given of creates from the first stack (fromStack) to the second Stack (toStack)
func (p *Port) MoveCreates(amount int, fromStack int, toStack int) {
	movedCreates := p.GetStack(fromStack).creates[len(p.GetStack(fromStack).creates)-amount:]
	for i := 0; i < amount; i++ {
		p.GetStack(fromStack).removeCreate()
	}
	for i := 0; i < len(movedCreates); i++ {
		p.GetStack(toStack).addCreate(movedCreates[i])
	}
}
