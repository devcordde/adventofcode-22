use std::fs;

fn main() {
    let input = fs::read_to_string("inputs/day1.txt").unwrap();
    let mut elves: Vec<i32> = input
        .split("\n\n")
        .map(|text| {
            text.lines()
                .map(|line| line.parse::<i32>().unwrap())
                .sum::<i32>()
        })
        .collect();

    elves.sort();

    let most = elves.last().unwrap();
    println!("{most}");

    let last_three: i32 = elves.iter().rev().take(3).sum();
    println!("{last_three}");
}
