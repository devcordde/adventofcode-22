package main

import (
	"errors"
)

type Filesystem struct {
	rootDirectory    Directory
	currentDirectory *Directory
}

// NewFilesystem create a new filesystem
func (f *Filesystem) NewFilesystem(rootName string) {

	//create and set the root dir
	rootDir := new(Directory)
	rootDir.NewDirectory(rootName, nil)
	f.rootDirectory = *rootDir
	//set the current directory to the root directory
	f.currentDirectory = &f.rootDirectory

}

// GetCurrentDir returns the current directory
func (f *Filesystem) GetCurrentDir() *Directory {
	return f.currentDirectory
}

// GetRootDir returns the root directory
func (f *Filesystem) GetRootDir() *Directory {
	return &f.rootDirectory
}

func (f *Filesystem) GoBack() {
	f.currentDirectory = f.currentDirectory.GetParentDirectory()
}

func (f *Filesystem) ToRoot() {
	f.currentDirectory = &f.rootDirectory
}

// EnterDir Enter a dir inside the current dir.
// returns false, if dir was not found
func (f *Filesystem) EnterDir(name string) bool {
	dir, err := f.GetCurrentDir().GetDirInside(name)
	if err != nil {
		return false
	}
	f.currentDirectory = dir
	return true
}

// is added to every type, a dir should be able to contain
type containable interface {
	GetName() string
}

type File struct {
	name string
	size int
}

func (f *File) NewFile(name string, size int) {
	f.size = size
	f.name = name
}

func (f File) GetName() string {
	return f.name
}

// GetSize returns the size of the file
func (f *File) GetSize() int {
	return f.size
}

type Directory struct {
	name      string
	parentDir *Directory
	content   []containable
}

func (d Directory) GetName() string {
	return d.name
}

func (d *Directory) NewDirectory(name string, parentDir *Directory) {
	d.name = name
	d.parentDir = parentDir
	d.content = []containable{}
}

// GetParentDirectory get the paren directory.
// In hierarchy view, the dir that contains the current dir
func (d *Directory) GetParentDirectory() *Directory {
	return d.parentDir
}

// GetDirInside returns a pointer to the dir with the matching name inside
// returns an error if no matching dir was found
func (d *Directory) GetDirInside(name string) (dir *Directory, error error) {

	for _, dir := range d.GetDirs(false) {
		if dir.GetName() == name {
			return dir, nil
		}
	}

	error = errors.New("Could not find directory with the name: " + name)
	return nil, error
}

// GetDirs get all the dirs, the dir containes.
// Sorts out all the Files.
// if recursive is set to true, all dirs will be returned, also the dirs inside dirs and the dirs inside those dirs.
// if recursive is set to false, only the dirs DIRECTLY inside this dir will be returned
func (d *Directory) GetDirs(recursive bool) []*Directory {
	var dirs = []*Directory{}

	for _, content := range d.content {
		//check if the current containable is a dir
		currentDir, ok := content.(*Directory)
		if ok {
			dirs = append(dirs, currentDir)

			//search for dirs inside this dir as well
			if recursive {
				//add all the found divs to the returned slice
				currentDirValue := *currentDir
				dirs = append(dirs, currentDirValue.GetDirs(true)...)
			}

		}
	}
	return dirs
}

// GetFiles get all the dirs, the dir containes.
// Sorts out all the Dirs.
// if recursive is set to true, all files will be returned, also the files inside dirs and the files inside those dirs.
// if recursive is set to false, only the files DIRECTLY inside this dir will be returned
func (d *Directory) GetFiles(recursive bool) []*File {
	var files = []*File{}

	//loop through the content of the dir
	for _, content := range d.content {

		file, ok := content.(*File)
		if ok {
			files = append(files, file)
		}
	}

	//if recursive search is necessary, research all other dirs as well
	//get all dirs inside this dir and loop through this
	if recursive {

		for _, dir := range d.GetDirs(true) {
			files = append(files, dir.GetFiles(false)...)
		}

	}

	return files
}

// AddContainable add a file or dir to the Directory
// returns true, if the file/dir already exists
func (d *Directory) AddContainable(containable containable) bool {
	//check if file is already contained
	if d.Contains(containable) {
		return true
	}

	//otherwise add the file and return false
	d.content = append(d.content, containable)
	return false
}

// Contains checks if dir contains a specific file or directory
func (d *Directory) Contains(containable containable) bool {
	for _, a := range d.content {
		if a.GetName() == containable.GetName() {
			return true
		}
	}
	return false
}
