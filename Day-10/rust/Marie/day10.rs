#![feature(iter_array_chunks)]

use std::{fs, str::FromStr};

const TARGET_CYCLES: &[usize] = &[20, 60, 100, 140, 180, 220];

fn main() {
    let input = fs::read_to_string("inputs/day10.txt").unwrap();
    let registers: Vec<i32> = input
        .lines()
        .map(|line| Instruction::from_str(line).unwrap())
        .fold(vec![1], |mut cpu, instruction| {
            match instruction {
                Instruction::Addx(amount) => {
                    let x = *cpu.last().unwrap();
                    cpu.push(x);
                    cpu.push(x + amount);
                }
                Instruction::Noop => {
                    cpu.push(*cpu.last().unwrap());
                }
            }
            cpu
        });
    let sum: usize = registers
        .iter()
        .enumerate()
        .filter_map(|(index, value)| {
            if TARGET_CYCLES.iter().any(|v| *v == index + 1) {
                Some(*value as usize * (index + 1))
            } else {
                None
            }
        })
        .sum();
    println!("{sum}");

    (0..240)
        .into_iter()
        .map(|pixel| {
            let x = registers[pixel as usize];
            let sprite_pos = x % 40;
            let sprite_pixel = pixel % 40;
            if sprite_pos == sprite_pixel
                || sprite_pos - 1 == sprite_pixel
                || sprite_pos + 1 == sprite_pixel
            {
                '#'
            } else {
                '.'
            }
        })
        .array_chunks::<40>()
        .for_each(|line| println!("{}", line.iter().collect::<String>()))
}

enum Instruction {
    Addx(i32),
    Noop,
}

impl FromStr for Instruction {
    type Err = ();

    fn from_str(value: &str) -> Result<Self, Self::Err> {
        let mut line = value.split(' ');
        let instruction = match line.next().unwrap() {
            "addx" => {
                let amount = line.next().and_then(|a| a.parse::<i32>().ok()).unwrap();
                Instruction::Addx(amount)
            }
            "noop" => Instruction::Noop,
            _ => panic!("Invalid instruction"),
        };
        Ok(instruction)
    }
}
