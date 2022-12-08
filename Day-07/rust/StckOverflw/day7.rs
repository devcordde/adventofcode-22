use std::collections::HashMap;

#[derive(Debug, Clone, PartialEq, Eq)]
pub enum DirContent {
    Directory(String),
    File(u64),
}

fn main() {
    let input = include_str!("../../input/day7.txt");

    let lines = input.lines();

    let mut map: HashMap<Vec<String>, Vec<DirContent>> = HashMap::new();

    let mut current_dir = vec!["/".to_string()];

    for line in lines {
        if line.starts_with("$ cd") {
            let dir = line.split(" ").nth(2).unwrap();
            if dir == ".." {
                current_dir.pop();
            } else if dir == "/" {
                current_dir = vec!["/".to_string()];
            } else {
                current_dir.push(dir.to_string());
            }
        }
        if !line.starts_with("$") {
            let mut split = line.split(" ");
            let type_or_size = split.nth(0).unwrap();

            if let Ok(size) = type_or_size.parse::<u64>() {
                if map.contains_key(&current_dir) {
                    let vec = map.get_mut(&current_dir).unwrap();
                    vec.push(DirContent::File(size));
                } else {
                    map.insert(current_dir.clone(), vec![DirContent::File(size)]);
                }
            } else if type_or_size == "dir" {
                let name = split.nth(0).unwrap();
                if map.contains_key(&current_dir) {
                    let vec = map.get_mut(&current_dir).unwrap();
                    vec.push(DirContent::Directory(name.to_string()));
                } else {
                    map.insert(
                        current_dir.clone(),
                        vec![DirContent::Directory(name.to_string())],
                    );
                }
            }
        }
    }

    let input = map;

    let mut sum = 0;

    for dir in input.clone() {
        let result = directory_size(&input, &dir);
        if result <= 100000 {
            sum += result;
        }
    }

    println!("part 1: {}", sum);

    let mut used_space = 0;
    let mut direcories = vec![];

    for dir in input.clone() {
        let result = directory_size(&input, &dir);
        if dir.0 == vec!["/"] {
            used_space = result;
        }
        direcories.push((dir.0, result));
    }

    let unused_space = 70000000 - used_space;

    let mut direcories = direcories
        .into_iter()
        .filter(|a| unused_space + a.1 >= 30000000)
        .collect::<Vec<_>>();

    direcories.sort_by(|a, b| a.1.cmp(&b.1));

    println!("part 2: {:?}", direcories.first().unwrap().1.to_owned());
}

fn directory_size(
    input: &HashMap<Vec<String>, Vec<DirContent>>,
    dir: &(Vec<String>, Vec<DirContent>),
) -> u64 {
    let mut sum = 0;
    for content in dir.1.clone() {
        let mut subdir_size = 0;
        if let DirContent::File(size) = content {
            sum += size;
        } else if let DirContent::Directory(name) = content {
            let mut new_dir = dir.0.clone();
            new_dir.push(name.to_owned());
            let subdir = input.get_key_value(&new_dir).unwrap().clone();
            subdir_size += directory_size(input, &(subdir.0.to_owned(), subdir.1.to_vec()));
        }
        sum += subdir_size;
    }
    sum
}
