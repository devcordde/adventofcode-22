use std::collections::VecDeque;

fn main() {
    let input = include_str!("../../input/day11.txt");

    let input = input.split("\n\n");

    let mut input: Vec<Monkey> = input
        .map(|monkey| {
            let mut monkey = monkey.lines();

            let starting_items: Vec<u64> = monkey
                .nth(1)
                .unwrap()
                .strip_prefix("  Starting items: ")
                .unwrap()
                .split(", ")
                .map(|x| x.parse().unwrap())
                .collect();

            let mut operation = monkey
                .nth(0)
                .unwrap()
                .strip_prefix("  Operation: new = ")
                .unwrap()
                .split(" ");
            let operation = Operation::parse(operation.nth(1).unwrap(), operation.nth(0).unwrap());

            let test_condition: u64 = monkey
                .nth(0)
                .unwrap()
                .strip_prefix("  Test: divisible by ")
                .unwrap()
                .parse()
                .unwrap();

            let test_if_true: u64 = monkey
                .nth(0)
                .unwrap()
                .strip_prefix("    If true: throw to monkey ")
                .unwrap()
                .parse()
                .unwrap();

            let test_if_false: u64 = monkey
                .nth(0)
                .unwrap()
                .strip_prefix("    If false: throw to monkey ")
                .unwrap()
                .parse()
                .unwrap();

            let test: Test = Test {
                test: test_condition,
                if_true: test_if_true,
                if_false: test_if_false,
            };

            Monkey {
                starting_items: starting_items.clone(),
                operation,
                test,
                inspects: 0,
            }
        })
        .collect();

    play_rounds(&mut input, 10000);

    input.sort_by(|a, b| b.inspects.cmp(&a.inspects));

    println!("input: {:#?}", input);

    println!("part 1: {}", input[0].inspects * input[1].inspects);
}

#[derive(Clone, Debug)]
struct Monkey {
    pub starting_items: Vec<u64>,
    pub operation: Operation,
    pub test: Test,
    pub inspects: u64,
}

fn play_rounds(monkeys: &mut Vec<Monkey>, rounds: usize) {
    let magic_number_that_is_so_fucking_stupid = monkeys
        .into_iter()
        .map(|monkey| monkey.test.test)
        .fold(1, |acc, x| acc * x);
    for _ in 0..rounds {
        for monkey_index in 0..monkeys.len() {
            let monkey = monkeys[monkey_index].clone();
            let mut items = VecDeque::from(monkey.starting_items);
            for _ in 0..items.len() {
                let item = items.pop_front().unwrap();
                monkeys[monkey_index].inspects += 1;
                let new_item = match &monkey.operation {
                    Operation::Add(one, two) => {
                        let one = match one {
                            OperationValue::Old => item,
                            OperationValue::Number(number) => *number,
                        };
                        let two = match two {
                            OperationValue::Old => item,
                            OperationValue::Number(number) => *number,
                        };
                        one + two
                    }
                    Operation::Multiply(one, two) => {
                        let one = match one {
                            OperationValue::Old => item,
                            OperationValue::Number(number) => *number,
                        };
                        let two = match two {
                            OperationValue::Old => item,
                            OperationValue::Number(number) => *number,
                        };
                        one * two
                    }
                } % magic_number_that_is_so_fucking_stupid;

                let new_item = new_item;

                let index = match new_item % monkey.test.test == 0 {
                    true => (monkey.test.if_true).clone() as usize,
                    false => (monkey.test.if_false).clone() as usize,
                };

                monkeys[monkey_index].starting_items = vec![];
                monkeys[index].starting_items.push(new_item);
            }
        }
    }
}

#[derive(Clone, Debug)]
enum Operation {
    Add(OperationValue, OperationValue),
    Multiply(OperationValue, OperationValue),
}

impl Operation {
    fn parse(s: &str, value: &str) -> Self {
        let operation = match value {
            "old" => OperationValue::Old,
            _ => OperationValue::Number(value.parse().unwrap()),
        };

        match s {
            "+" => Operation::Add(OperationValue::Old, operation),
            "*" => Operation::Multiply(OperationValue::Old, operation),
            _ => panic!(),
        }
    }
}

#[derive(Clone, Debug)]
enum OperationValue {
    Old,
    Number(u64),
}

#[derive(Clone, Debug)]
struct Test {
    pub test: u64,
    pub if_true: u64,
    pub if_false: u64,
}
