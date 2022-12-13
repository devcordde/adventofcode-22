use std::{iter::Peekable, str::Chars};

#[derive(Debug, PartialEq, Eq, Clone)]
enum Packet {
    List(Vec<Packet>),
    Number(i32),
}

type Pair = (Packet, Packet);

fn main() {
    let input: Vec<Vec<&str>> = include_str!("../../input/day13.txt")
        .split("\n\n")
        .map(|split| split.lines().collect())
        .collect();

    let mut input: Vec<Pair> = input
        .into_iter()
        .map(|split| {
            (
                split.first().unwrap().parse().unwrap(),
                split.last().unwrap().parse().unwrap(),
            )
        })
        .collect();

    let mut sum = 0;

    for (index, (left, right)) in (&input).into_iter().enumerate() {
        if left < right {
            sum += index + 1;
        }
    }

    println!("part 1: {}", sum);

    let mut part_2 = Vec::new();

    for packets in input {
        part_2.push(packets.0);
        part_2.push(packets.1);
    }

    part_2.push(Packet::List(vec![Packet::List(vec![Packet::Number(2)])]));
    part_2.push(Packet::List(vec![Packet::List(vec![Packet::Number(6)])]));

    part_2.sort();

    let mut decoder_key = 1;
    for (index, packet) in part_2.into_iter().enumerate() {
        if packet == Packet::List(vec![Packet::List(vec![Packet::Number(2)])])
            || packet == Packet::List(vec![Packet::List(vec![Packet::Number(6)])])
        {
            decoder_key *= index + 1;
        }
    }

    println!("part 2: {}", decoder_key);
}

impl Ord for Packet {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        self.partial_cmp(other).unwrap()
    }
}

impl PartialOrd for Packet {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        let left = self;
        let right = other;

        match (left, right) {
            (Packet::Number(left), Packet::Number(right)) => left.partial_cmp(right),
            (Packet::List(left), Packet::List(right)) => {
                for packets in left.iter().zip(right.iter()) {
                    if packets.0 < packets.1 {
                        return Some(std::cmp::Ordering::Less);
                    } else if packets.0 > packets.1 {
                        return Some(std::cmp::Ordering::Greater);
                    }
                }
                if left.len() < right.len() {
                    return Some(std::cmp::Ordering::Less);
                } else if left.len() > right.len() {
                    return Some(std::cmp::Ordering::Greater);
                }

                return None; // This should not happen
            }
            (Packet::Number(left), Packet::List(right)) => {
                let left = Packet::List(vec![Packet::Number(*left)]);
                let right = Packet::List(right.clone());
                if left < right {
                    return Some(std::cmp::Ordering::Less);
                } else if left > right {
                    return Some(std::cmp::Ordering::Greater);
                }

                return None; // This should not happen
            }
            (Packet::List(left), Packet::Number(right)) => {
                let left = Packet::List(left.clone());
                let right = Packet::List(vec![Packet::Number(*right)]);
                if left < right {
                    return Some(std::cmp::Ordering::Less);
                } else if left > right {
                    return Some(std::cmp::Ordering::Greater);
                }

                return None; // This should not happen
            }
        }
    }
}

impl std::str::FromStr for Packet {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut chars = s.chars();
        let chars = &mut chars;
        Ok((&mut chars.peekable()).into())
    }
}

impl From<&mut Peekable<&mut Chars<'_>>> for Packet {
    fn from(chars: &mut Peekable<&mut Chars<'_>>) -> Self {
        let a = chars.next().unwrap();
        if a == '[' {
            if chars.peek() == Some(&']') {
                chars.next();
                return Packet::List(vec![]);
            } else {
                let mut results: Vec<Packet> = Vec::new();

                while chars.peek() != Some(&']') {
                    results.push(chars.into());
                    if chars.peek() != Some(&']') {
                        chars.next();
                    }
                }

                chars.next();

                return Packet::List(results);
            }
        } else {
            if chars.peek() == Some(&',') || chars.peek() == Some(&']') {
                return Packet::Number(a.to_string().parse().unwrap());
            } else {
                let second_char = chars.next().unwrap();
                let number = format!("{}{}", a, second_char).parse().unwrap();
                return Packet::Number(number);
            }
        }
    }
}
