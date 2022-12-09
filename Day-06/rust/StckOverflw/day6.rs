use std::str::Chars;

fn main() {
    let input = include_str!("../../input/day6.txt");

    println!("part 1: {}", check_first_unique_sequene(input.chars(), 4));
    println!("part 2: {}", check_first_unique_sequene(input.chars(), 14));
}

fn check_first_unique_sequene(chars_input: Chars, window_size: usize) -> i32 {
    let mut count = window_size.clone() as i32;

    for chars in chars_input.collect::<Vec<_>>().windows(window_size) {
        let abc = chars.into_iter().enumerate().any(|(i, x)| {
            let mut chars = chars.to_vec();
            chars.remove(i);
            chars.into_iter().any(|y| x == &y)
        });

        if abc {
            count += 1;
        } else {
            break;
        }
    }

    count
}
