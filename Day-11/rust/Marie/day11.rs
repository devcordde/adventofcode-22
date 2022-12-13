use std::{fs, str::FromStr};

fn main() {
    let input = fs::read_to_string("inputs/day11.txt").unwrap();
    let monkeys = input
        .split("\n\n")
        .map(|text| Monkey::from_str(text).unwrap())
        .collect::<Vec<_>>();

    let reduction_modulus = monkeys
        .iter()
        .map(|monkey| monkey.test.divider)
        .product::<u64>();

    println!(
        "{}",
        calculate_monkey_business(20, monkeys.clone(), reduction_modulus, true)
    );
    println!(
        "{}",
        calculate_monkey_business(10000, monkeys, reduction_modulus, false)
    );
}

fn calculate_monkey_business(
    rounds: usize,
    monkeys: Vec<Monkey>,
    reduction_modulus: u64,
    shrink_level: bool,
) -> usize {
    let mut monkeys = (0..monkeys.len())
        .cycle()
        .take(rounds * monkeys.len())
        .fold(monkeys, |mut monkeys, index| {
            let monkey = monkeys.get_mut(index).unwrap();
            for (index, level) in monkey.inspect_items(reduction_modulus, shrink_level) {
                monkeys[index].items.push(level);
            }
            monkeys
        });
    monkeys.sort_by_key(|monkey| monkey.inspections);
    monkeys
        .iter()
        .rev()
        .take(2)
        .map(|monkey| monkey.inspections)
        .product()
}

type Item = u64;

#[derive(Clone, Debug)]
struct Monkey {
    items: Vec<Item>,
    operation: Operation,
    test: Test,
    inspections: usize,
}

impl Monkey {
    pub fn inspect_items(
        &mut self,
        reduction_modulus: u64,
        shrink_level: bool,
    ) -> Vec<(usize, u64)> {
        self.items
            .drain(..)
            .map(|item| {
                self.inspections += 1;
                let level = if shrink_level {
                    (self.operation.calculate(item) / 3) % reduction_modulus
                } else {
                    self.operation.calculate(item) % reduction_modulus
                };
                let next_monkey = if level % self.test.divider == 0 {
                    self.test.positive_outcome
                } else {
                    self.test.negative_outcome
                };
                (next_monkey, level)
            })
            .collect()
    }
}

impl FromStr for Monkey {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut lines = s.lines().skip(1);
        let starting_items = lines
            .next()
            .unwrap()
            .split_once(": ")
            .unwrap()
            .1
            .split(", ")
            .map(|value| value.parse::<u64>().unwrap())
            .collect::<Vec<_>>();
        let operation = Operation::from_str(lines.next().unwrap()).unwrap();
        let test = Test::from(lines);
        Ok(Monkey {
            items: starting_items,
            operation,
            test,
            inspections: 0,
        })
    }
}

#[derive(Clone, Debug)]
enum Operation {
    Plus(u64),
    Times(u64),
    TimesOld,
}

impl Operation {
    pub fn calculate(&self, level: u64) -> u64 {
        match self {
            Operation::Plus(value) => level + value,
            Operation::Times(value) => level * value,
            Operation::TimesOld => level * level,
        }
    }
}

impl FromStr for Operation {
    type Err = ();
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let (operator, value) = s
            .trim()
            .strip_prefix("Operation: new = old ")
            .unwrap()
            .split_once(' ')
            .unwrap();
        if operator == "*" && value == "old" {
            return Ok(Operation::TimesOld);
        }
        let value = value.parse().unwrap();
        let operation = match operator {
            "+" => Operation::Plus(value),
            "*" => Operation::Times(value),
            _ => panic!("Invalid Operation"),
        };
        Ok(operation)
    }
}

#[derive(Clone, Debug)]
struct Test {
    divider: u64,
    positive_outcome: usize,
    negative_outcome: usize,
}

impl<'a, T: IntoIterator<Item = &'a str>> From<T> for Test {
    fn from(value: T) -> Self {
        let mut iter = value.into_iter();
        let divider = parse_prefixed_number(iter.next().unwrap(), "Test: divisible by ");
        let positive_outcome =
            parse_prefixed_number(iter.next().unwrap(), "If true: throw to monkey ");
        let negative_outcome =
            parse_prefixed_number(iter.next().unwrap(), "If false: throw to monkey ");
        Test {
            divider,
            positive_outcome,
            negative_outcome,
        }
    }
}

fn parse_prefixed_number<N: FromStr>(value: &str, prefix: &str) -> N {
    value
        .trim()
        .strip_prefix(prefix)
        .and_then(|value| value.parse().ok())
        .unwrap()
}
