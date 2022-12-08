fn main() {
    let input = include_str!("../../input/day2.txt");
    let input: Vec<(u32, u32)> = input
        .lines()
        .map(|line| {
            let split: Vec<&str> = line.split(" ").collect();

            let first = match *split.first().unwrap() {
                "A" => 1,
                "B" => 2,
                "C" => 3,
                _ => panic!("Invalid input 1"),
            };

            let second = match *split.last().unwrap() {
                "X" => 1,
                "Y" => 2,
                "Z" => 3,
                _ => panic!("Invalid input 2"),
            };

            return (first, second);
        })
        .into_iter()
        .collect();

    let mut score = 0;

    for game in input.clone() {
        score += game.1;
        if game.0 == game.1 {
            score += 3;
        } else if (game.1 + 1) % 3 == game.0 - 1 {
            score += 6;
        }
    }

    println!("part 1: {:?}", score);

    let mut score = 0;

    for game in input.clone() {
        let chosen_move = match game.1 {
            1 => (game.0 + 1) % 3,
            2 => game.0 - 1,
            3 => game.0 % 3,
            _ => panic!(),
        };

        score += chosen_move + 1 + (game.1 - 1) * 3;
    }

    println!("part 2: {:?}", score);
}

pub fn split_delimited<'a, T>(input: &'a [T], delim: &T) -> Vec<&'a [T]>
where
    T: PartialEq<T>,
{
    let elems = input.iter().enumerate();
    let (k, mut r) = elems.fold((0, vec![]), |(i, mut r), (j, x)| {
        if x == delim && j > 0 {
            r.push(&input[(i + 1)..j]);
            return (j, r);
        }
        (i, r)
    });
    if !input.is_empty() {
        r.push(&input[(k + 1)..]);
    }
    r
}
