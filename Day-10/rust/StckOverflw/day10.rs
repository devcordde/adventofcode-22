fn main() {
    let input = include_str!("../../input/day10.txt").lines();

    let mut cycles = Vec::new();

    for instruction in input {
        if instruction.starts_with("addx") {
            let value = instruction.split(" ").nth(1).unwrap().parse().unwrap();
            cycles.push(Cycle {
                instruction: Some(value),
            });
            cycles.push(Cycle { instruction: None });
        } else {
            cycles.push(Cycle { instruction: None });
        }
    }

    let mut cycle_count = 1;
    let mut x = 1;

    let mut signal_strenghts: i32 = 0;

    for cycle in &cycles {
        cycle_count += 1;
        if cycle_count > 0 && (cycle_count == 20 || cycle_count % 40 == 20) {
            signal_strenghts += cycle_count * x;
        }
        if let Some(instruction) = &cycle.instruction {
            x += instruction;
        }
    }

    println!("part 1 {}", signal_strenghts);

    let mut cycle_count = 0;
    let mut x = 0;

    let mut crt = String::new();

    for cycle in &cycles {
        if ((x - 1)..=(x + 1)).contains(&(&cycle_count % 40)) {
            crt.push_str("#");
        } else {
            crt.push_str(".");
        }

        cycle_count += 1;

        if let Some(instruction) = &cycle.instruction {
            x += instruction;
        }
    }

    println!(
        "part 2: {:#?}",
        crt.chars()
            .collect::<Vec<char>>()
            .chunks(40)
            .map(|chars| chars.iter().collect::<String>())
            .collect::<Vec<String>>()
    );
}

struct Cycle {
    instruction: Option<i32>,
}
