#![feature(iter_array_chunks)]

use std::fs;

fn main() {
    let input = fs::read_to_string("inputs/day3.txt").unwrap();
    let priorities: u64 = input
        .lines()
        .map(|line| {
            let (first, second) = line.split_at(line.len() / 2);
            find_common_char_priority(&[first, second]).unwrap()
        })
        .sum();
    println!("{priorities}");

    let priorities: u64 = input
        .lines()
        .array_chunks::<3>()
        .map(|rucksacks| find_common_char_priority(&rucksacks).unwrap())
        .sum();
    println!("{priorities}");
}

fn find_common_char_priority(lines: &[&str]) -> Option<u64> {
    let first = lines.first()?;
    let index = first.find(|a| lines.iter().all(|line| line.contains(a)))?;
    let char = first.chars().nth(index)?;
    if char.is_lowercase() {
        Some(char as u64 - 96)
    } else {
        Some(char as u64 - 38)
    }
}
