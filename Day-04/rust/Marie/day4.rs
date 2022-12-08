use std::{fs, ops::RangeInclusive};

fn main() {
    let input = fs::read_to_string("inputs/day4.txt").unwrap();
    let a = input
        .lines()
        .filter(|line| matches_range(line, false))
        .count();
    println!("{a}");

    let a = input
        .lines()
        .filter(|line| matches_range(line, true))
        .count();
    println!("{a}");
}

fn matches_range(line: &str, include_overlaps: bool) -> bool {
    let mut splitted = line.split(',');
    let first = parse_range(splitted.next().unwrap());
    let second = parse_range(splitted.next().unwrap());

    if include_overlaps {
        first.contains(second.start())
            || first.contains(second.end())
            || (second.contains(first.start()) || second.contains(first.end()))
    } else {
        first.contains(second.start()) && first.contains(second.end())
            || (second.contains(first.start()) && second.contains(first.end()))
    }
}

fn parse_range(text: &str) -> RangeInclusive<i32> {
    let mut split = text.split('-');
    split.next().unwrap().parse().unwrap()..=split.next().unwrap().parse().unwrap()
}
