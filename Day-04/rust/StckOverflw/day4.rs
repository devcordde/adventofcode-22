use std::ops::RangeInclusive;

fn main() {
    let input = include_str!("../../input/day4.txt");

    let ranges = input
        .lines()
        .map(|line| {
            line.to_string()
                .split(",")
                .map(|x| {
                    x.split("-")
                        .map(|x| x.parse::<u32>().unwrap())
                        .collect::<Vec<u32>>()
                })
                .map(|x| {
                    RangeInclusive::new(x.first().unwrap().to_owned(), x.last().unwrap().to_owned())
                })
                .collect::<Vec<RangeInclusive<u32>>>()
        })
        .collect::<Vec<Vec<RangeInclusive<u32>>>>();

    let input: Vec<(RangeInclusive<u32>, RangeInclusive<u32>)> = ranges
        .into_iter()
        .map(|ranges| {
            (
                ranges.first().unwrap().to_owned(),
                ranges.last().unwrap().to_owned(),
            )
        })
        .collect();

    let count = input
        .clone()
        .into_iter()
        .filter(|x| {
            (x.0.contains(x.1.start()) && x.0.contains(x.1.end()))
                || (x.1.contains(x.0.start()) && x.1.contains(x.0.end()))
        })
        .count();

    println!("part 1: {}", count);

    let count = input
        .into_iter()
        .filter(|x| {
            (x.0.contains(x.1.start()) || x.0.contains(x.1.end()))
                || (x.1.contains(x.0.start()) || x.1.contains(x.0.end()))
        })
        .count();

    println!("part 2: {}", count);
}
