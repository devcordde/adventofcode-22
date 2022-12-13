package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const discSize = 70000000
const discSpaceNeeded = 30000000

func main() {

	//create the filesystem
	var filesystem = new(Filesystem)
	filesystem.NewFilesystem("/")

	//open the filescanner for the input file
	readFile, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	filescanner := bufio.NewScanner(readFile)

	//read the input and put the data into the object
	for filescanner.Scan() {
	HOP:
		line := filescanner.Text()
		//if line is "" (aka last line) then break the loop
		if line == "" {
			break
		}
		com, arg := ReadCommand(line)

		//handle the commands
		switch com {
		case "ls":

			//loop until a command occurs
			for filescanner.Scan() {

				//get the current line as string and further as command
				line = filescanner.Text()
				com, _ = ReadCommand(line)

				//if a command is found, the loop should be broken
				if com != "" {
					break
				}

				//create dir or file
				if line[0:3] == "dir" {
					//create dir
					dir := new(Directory)
					dir.NewDirectory(line[4:], filesystem.currentDirectory)
					filesystem.GetCurrentDir().AddContainable(dir)
					//file
				} else {
					//create the file
					nameAndSize := strings.Split(line, " ")
					size, err := strconv.Atoi(nameAndSize[0])
					if err != nil {
						panic(err)
					}
					file := new(File)
					file.NewFile(nameAndSize[1], size)
					filesystem.GetCurrentDir().AddContainable(file)
				}

			}
			goto HOP

		case "cd":

			if arg == ".." {
				filesystem.GoBack()
			} else if arg == "/" {
				filesystem.ToRoot()
			} else {
				if !filesystem.EnterDir(arg) {
					fmt.Println("Something went wrong")
					fmt.Println("Dir not found!")
				}
			}

		//if the command in neither "cd" nor "ls", something went wrong
		default:
			panic("no valid command")

		}

	}

	err = readFile.Close()
	if err != nil {
		return
	}

	var smallestDir = discSize

	//calculate the unused space
	var unusedSpace = discSize - filesystem.GetRootDir().GetSize()

	var bigEnoughDirs []Directory
	//find all dirs, that would free up enough space for the update
	for _, dir := range filesystem.GetRootDir().GetDirs(true) {
		if unusedSpace+dir.GetSize() > discSpaceNeeded {
			bigEnoughDirs = append(bigEnoughDirs, *dir)
		}
	}

	//find the smallest of these dirs
	for _, dir := range bigEnoughDirs {
		if dir.GetSize() < smallestDir {
			smallestDir = dir.GetSize()
		}
	}

	fmt.Println(smallestDir)

}

// ReadCommand the functions returns the command, and if there is an argument, the argument
// ReadCommand returns an empty string ("") if the input doesn't contain a valid command
func ReadCommand(commandInput string) (command string, arg string) {
	command = commandInput[2:4]

	if commandInput[0:1] != "$" {
		return "", ""
	}
	if command != "ls" && command != "cd" {
		return "", ""
	}

	if command == "cd" {
		arg = commandInput[5:]
	}

	return
}
