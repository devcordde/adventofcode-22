#![feature(iter_array_chunks)]

use std::{fs, str::FromStr};

fn main() {
    let (stacks, moves) = parse_input();
    let cargo_crane = CargoCrane::new(stacks, moves);
    println!("{}", cargo_crane.calculate_top_crates(true));
    println!("{}", cargo_crane.calculate_top_crates(false));
}

struct CargoCrane {
    moves: Vec<Move>,
    stacks: Vec<Vec<char>>,
}

impl CargoCrane {
    pub fn new(columns: Vec<Vec<char>>, moves: Vec<Move>) -> CargoCrane {
        CargoCrane {
            moves,
            stacks: columns,
        }
    }

    pub fn calculate_top_crates(&self, reverse_move_order: bool) -> String {
        let mut stacks = self.stacks.clone();
        for m in &self.moves {
            let column = &mut stacks[m.from - 1];
            let mut moved = column.split_off(column.len() - m.amount);
            if reverse_move_order {
                moved.reverse();
            }
            stacks[m.to - 1].extend(moved);
        }

        stacks
            .into_iter()
            .map(|column| *column.last().unwrap())
            .collect::<String>()
    }
}

struct Move {
    pub from: usize,
    pub to: usize,
    pub amount: usize,
}

impl FromStr for Move {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut splitted = s.split(' ');
        let amount = splitted.nth(1).and_then(|s| s.parse().ok()).unwrap();
        let from = splitted.nth(1).and_then(|s| s.parse().ok()).unwrap();
        let to = splitted.nth(1).and_then(|s| s.parse().ok()).unwrap();
        Ok(Move { from, to, amount })
    }
}

fn parse_input() -> (Vec<Vec<char>>, Vec<Move>) {
    let input = fs::read_to_string("inputs/day5.txt").unwrap();
    let mut input = input.split("\n\n");
    let rows = input
        .next()
        .unwrap()
        .lines()
        .rev()
        .skip(1)
        .map(|line| {
            line.chars()
                .chain(std::iter::once(' '))
                .array_chunks::<4>()
                .map(parse_crate)
                .collect::<Vec<_>>()
        })
        .collect::<Vec<_>>();

    let columns = rows
        .iter()
        .fold(Vec::with_capacity(rows[0].len()), |mut columns, row| {
            for (i, c) in row.iter().enumerate() {
                if columns.len() <= i {
                    columns.push(Vec::new());
                }
                if let Some(c) = c {
                    columns[i].push(*c);
                }
            }
            columns
        });

    let moves = parse_moves(&mut input);
    (columns, moves)
}

fn parse_crate(line: [char; 4]) -> Option<char> {
    if line.iter().all(|char| char.is_whitespace()) {
        return None;
    }
    line.get(1).cloned()
}

fn parse_moves<'a, T: Iterator<Item = &'a str>>(mut input: T) -> Vec<Move> {
    input
        .next()
        .unwrap()
        .lines()
        .map(|line| Move::from_str(line).unwrap())
        .collect::<Vec<_>>()
}
