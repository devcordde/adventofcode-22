#![feature(array_windows)]

use std::fs;

fn main() {
    let input = fs::read_to_string("inputs/day6.txt").unwrap();
    let lines = input.chars().collect::<Vec<_>>();
    let a = count_first_different::<4>(&lines).unwrap();
    println!("{a}");
    let a = count_first_different::<14>(&lines).unwrap();
    println!("{a}");
}

fn count_first_different<const N: usize>(lines: &[char]) -> Option<usize> {
    lines
        .array_windows::<N>()
        .enumerate()
        .find_map(|(index, window)| {
            if window
                .iter()
                .all(|c| window.iter().filter(|x| x == &c).count() == 1)
            {
                Some(index + N)
            } else {
                None
            }
        })
}
