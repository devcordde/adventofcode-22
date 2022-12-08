use std::fs;

fn main() {
    let input = fs::read_to_string("inputs/day2.txt").unwrap();
    let total: i32 = input
        .lines()
        .map(|line| {
            let mut splitted = line.split(' ');
            let first = splitted.next().unwrap();
            let second = splitted.next().unwrap();

            let first_score = match first.chars().next().unwrap() {
                'A' => 1, // Rock
                'B' => 2, // Paper
                'C' => 3, // Scissors
                _ => panic!(),
            };
            let second_score = match second.chars().next().unwrap() {
                'X' => 1, // Rock
                'Y' => 2, // Paper
                'Z' => 3, // Scissors
                _ => panic!(),
            };

            match first_score - second_score {
                -1 | 2 => second_score + 6,
                1 | -2 => second_score,
                0 => second_score + 3,
                _ => panic!(),
            }
        })
        .sum();
    println!("{total}");

    let total: i32 = input
        .lines()
        .map(|line| {
            let mut splitted = line.split(' ');
            let first = splitted.next().unwrap();
            let second = splitted.next().unwrap();

            let first_score = match first.chars().next().unwrap() {
                'A' => 1, // Rock
                'B' => 2, // Paper
                'C' => 3, // Scissors
                _ => panic!(),
            };
            match second.chars().next().unwrap() {
                'X' => match first_score {
                    1 => 3,
                    i => i - 1,
                }, // Loose
                'Y' => first_score + 3, // Draw
                'Z' => {
                    let score = match first_score {
                        3 => 1,
                        i => i + 1,
                    };
                    score + 6
                } // Win
                _ => panic!(),
            }
        })
        .sum();
    println!("{total}");
}
