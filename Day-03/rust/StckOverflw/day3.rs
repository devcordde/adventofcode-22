fn main() {
    let input = include_str!("../../input/day3.txt").lines();

    let sum: u64 = input
        .clone()
        .into_iter()
        .map(|line| {
            let (first, second) = line.split_at(line.len() / 2);
            common_char_priority(&[first, second]).unwrap()
        })
        .sum();

    println!("part 1: {}", sum);

    let sum: u64 = input
        .into_iter()
        .map(|string| string)
        .collect::<Vec<_>>()
        .chunks(3)
        .map(|chunk| common_char_priority(chunk).unwrap())
        .sum();

    println!("part 2: {}", sum);
}

fn common_char_priority(strings: &[&str]) -> Option<u64> {
    let first = strings.first()?;
    let char = first.chars().nth(
        first
            .find(|char| strings.iter().all(|line| line.contains(char)))
            .unwrap(),
    )?;
    if char.is_uppercase() {
        Some(char as u64 - 38)
    } else {
        Some(char as u64 - 96)
    }
}
