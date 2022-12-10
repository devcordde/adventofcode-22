use std::{collections::HashMap, str::FromStr};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Instruction {
    quantity: u32,
    origin: u32,
    desination: u32,
}

impl FromStr for Instruction {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let split: Vec<&str> = s.split(" ").collect();
        let quantity = split.get(1).unwrap().parse().unwrap();
        let origin = split.get(3).unwrap().parse().unwrap();
        let desination = split.last().unwrap().parse().unwrap();

        Ok(Instruction {
            quantity,
            origin,
            desination,
        })
    }
}

fn main() {
    let input = include_str!("../../input/day5.txt");

    let split: Vec<&str> = input.split("\n\n").collect();

    let crates = *split.first().unwrap();
    let instructions: Vec<String> = split
        .last()
        .unwrap()
        .lines()
        .map(|line| line.to_string())
        .collect();

    let mut crate_lines: HashMap<u32, Vec<char>> = HashMap::new();

    for (index, char) in crates.lines().last().unwrap().chars().enumerate() {
        if char != ' ' {
            let mut crate_chars: Vec<char> = Vec::new();
            for crate_line in crates.lines().rev().skip(1) {
                if let Some(char) = crate_line.chars().nth(index) {
                    if char != ' ' {
                        crate_chars.push(char);
                    }
                }
            }
            crate_lines.insert(char.to_string().parse().unwrap(), crate_chars);
        }
    }

    let input: (HashMap<u32, Vec<char>>, Vec<Instruction>) = (
        crate_lines,
        instructions
            .into_iter()
            .map(|instruction_str| instruction_str.parse().unwrap())
            .collect(),
    );

    let instructions = input.1.clone();

    let mut crates = input.0.clone();

    for instruction in instructions {
        let mut crate_chars = crates.get(&instruction.origin).unwrap().clone();
        let mut new_crate_chars = crates.get(&instruction.desination).unwrap().clone();

        for _ in 0..instruction.quantity {
            new_crate_chars.push(crate_chars.pop().unwrap());
        }

        crates.insert(instruction.origin, crate_chars);
        crates.insert(instruction.desination, new_crate_chars);
    }

    let mut result = String::new();

    let mut crates = crates.into_iter().collect::<Vec<(_, _)>>();
    crates.sort_by(|a, b| a.0.cmp(&b.0));

    for crate_ele in crates {
        result += crate_ele.1.last().unwrap().to_string().as_str();
    }

    println!("part 1: {}", result);
}
