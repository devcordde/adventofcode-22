// why use a GC'ed language when you can have fun with reference counted smart pointers :3
// i've never used RefCell before, don't be mean uwu

use std::{cell::RefCell, fmt::Debug, fs, rc::Rc};

fn main() {
    let input = fs::read_to_string("inputs/day7.txt").unwrap();
    let root = DirectoryEntry::new("/");
    let root = Rc::new(RefCell::new(root));
    let mut current_dir = root.clone();
    for line in input.lines().skip(2) {
        if let Some(target) = line.strip_prefix("$ cd ") {
            if target == ".." {
                let parent = current_dir.borrow().parent.as_ref().unwrap().clone();
                current_dir = parent;
            } else {
                let child = Rc::new(RefCell::new(DirectoryEntry {
                    name: target.to_string(),
                    parent: Some(current_dir.clone()),
                    ..Default::default()
                }));
                current_dir
                    .borrow_mut()
                    .children
                    .push(Rc::new(FileSystemEntry::Directory(child.clone())));
                current_dir = child;
            }
        } else {
            let mut splitted = line.split(' ');
            if let Some(size) = splitted.next().and_then(|s| s.parse::<u64>().ok()) {
                let file = FileEntry { size };
                current_dir
                    .borrow_mut()
                    .children
                    .push(Rc::new(FileSystemEntry::File(file)));
            }
        }
    }

    let result: u64 = FileSystemEntryIterator::from(root.clone())
        .filter_map(|value| match value.as_ref() {
            FileSystemEntry::Directory(dir) => Some(dir.clone()),
            _ => None,
        })
        .map(|dir| dir.borrow().size())
        .filter(|size| size <= &100000)
        .sum();
    println!("{result}");

    let current_size = 70000000 - root.borrow().size();
    let needed_size = 30000000 - current_size;
    let result: u64 = FileSystemEntryIterator::from(root)
        .filter_map(|value| match value.as_ref() {
            FileSystemEntry::Directory(dir) => Some(dir.clone()),
            _ => None,
        })
        .map(|dir| dir.borrow().size())
        .filter(|size| size >= &needed_size)
        .min()
        .unwrap();
    println!("{result}");
}

#[derive(Debug)]
enum FileSystemEntry {
    File(FileEntry),
    Directory(Rc<RefCell<DirectoryEntry>>),
}

#[derive(Debug)]
struct FileEntry {
    size: u64,
}

#[derive(Default)]
struct DirectoryEntry {
    parent: Option<Rc<RefCell<DirectoryEntry>>>,
    name: String,
    children: Vec<Rc<FileSystemEntry>>,
}

impl DirectoryEntry {
    pub fn new(name: &str) -> Self {
        DirectoryEntry {
            name: name.to_string(),
            ..Default::default()
        }
    }

    pub fn size(&self) -> u64 {
        self.children
            .iter()
            .map(|child| match child.as_ref() {
                FileSystemEntry::Directory(dir) => dir.borrow().size(),
                FileSystemEntry::File(file) => file.size,
            })
            .sum()
    }
}

impl Debug for DirectoryEntry {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        f.debug_struct("DirectoryEntry")
            .field("name", &self.name)
            .field("children", &self.children)
            .finish()
    }
}

struct FileSystemEntryIterator {
    entries: Vec<Rc<FileSystemEntry>>,
    index: usize,
}

impl FileSystemEntryIterator {
    pub fn new(root: Rc<FileSystemEntry>) -> Self {
        let mut entries = Vec::new();
        add_children(&mut entries, root);
        FileSystemEntryIterator { entries, index: 0 }
    }
}

impl From<Rc<RefCell<DirectoryEntry>>> for FileSystemEntryIterator {
    fn from(value: Rc<RefCell<DirectoryEntry>>) -> Self {
        FileSystemEntryIterator::new(Rc::new(FileSystemEntry::Directory(value)))
    }
}

fn add_children(entries: &mut Vec<Rc<FileSystemEntry>>, root: Rc<FileSystemEntry>) {
    entries.push(root.clone());
    if let FileSystemEntry::Directory(dir) = root.as_ref() {
        for child in &dir.borrow().children {
            add_children(entries, child.clone());
        }
    }
}

impl Iterator for FileSystemEntryIterator {
    type Item = Rc<FileSystemEntry>;

    fn next(&mut self) -> Option<Self::Item> {
        let value = self.entries.get(self.index).cloned();
        self.index += 1;
        value
    }
}
