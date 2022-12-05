use std::collections::HashSet;
use std::fs;
use std::io;

fn priority(item: char) -> u32 {
    match item.is_lowercase() {
        true => (item as u32) - 96,
        false => (item as u32) - 38,
    }
}

fn find_duplicate(backpack: &(&str, &str)) -> u32 {
    let (shorter, longer) = if backpack.0.len() > backpack.1.len() {
        (backpack.1, backpack.0)
    } else {
        (backpack.0, backpack.1)
    };

    let set: HashSet<char> = shorter.chars().collect();
    for c in longer.chars() {
        if set.contains(&c) {
            return priority(c);
        }
    }
    0
}

fn find_badge(backpacks: Vec<&str>) -> u32 {
    let sum_badges = backpacks.chunks(3)
        .map(|chunk| {
            let common_char: char = chunk[0].chars()
                .filter(|c|
                    chunk[1].contains(*c)
                ).filter(|c| {
                    chunk[2].contains(*c)
                }).next().unwrap();
            priority(common_char)
        }).sum();
    sum_badges
}

fn main() -> Result<(), io::Error>{

    let data = fs::read_to_string("./resources/input_3_1.txt")?;

    let compartements: Vec<(&str, &str)> = data.split("\n")
        .map(|line| {
            let elems = line.split_at(line.len() / 2);
            elems
        }).collect();
    
    let sum_priorities: u32 = compartements.iter()
        .map(|compartements| {
            find_duplicate(compartements)
        }).sum();

    let sum_badges = find_badge(data.split("\n").collect());
    
    println!("Sum of priorities is: {}. Sum of badges is: {}", sum_priorities, sum_badges);

    Ok(())
}