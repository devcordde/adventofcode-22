use std::{collections::HashSet, fs, str::FromStr};

fn main() {
    let input = fs::read_to_string("inputs/day9.txt").unwrap();
    let steps: Vec<Step> = input
        .lines()
        .map(|line| Step::from_str(line).unwrap())
        .collect();

    let positions = Positions::<2>::default().count_positions(&steps);
    println!("{positions}");
    let positions = Positions::<10>::default().count_positions(&steps);
    println!("{positions}");
}

type Position = (i32, i32);

struct Positions<const NODES: usize> {
    nodes: [Position; NODES],
    positions: HashSet<Position>,
}

impl<const NODES: usize> Default for Positions<NODES> {
    fn default() -> Self {
        Self {
            nodes: [(0, 0); NODES],
            positions: Default::default(),
        }
    }
}

impl<const NODES: usize> Positions<NODES> {
    pub fn move_head(&mut self, direction: &Direction) {
        let mut head = &mut self.nodes.get_mut(0).unwrap();
        match direction {
            Direction::Up => head.1 += 1,
            Direction::Down => head.1 -= 1,
            Direction::Left => head.0 -= 1,
            Direction::Right => head.0 += 1,
        }

        let length = self.nodes.len();
        for index in 1..length {
            self.move_knot(index, index == length - 1);
        }
    }

    fn move_knot(&mut self, knot_index: usize, is_last: bool) {
        let previous_knot = *self.nodes.get(knot_index - 1).unwrap();
        let knot = self.nodes.get_mut(knot_index).unwrap();

        let diff_x = (previous_knot.0 - knot.0).clamp(-2, 2);
        let diff_y = (previous_knot.1 - knot.1).clamp(-2, 2);

        if diff_x.abs() + diff_y.abs() > 2 {
            knot.0 += diff_x.signum();
            knot.1 += diff_y.signum();
        } else if diff_x.abs() > 1 {
            knot.0 += diff_x.signum();
        } else if diff_y.abs() > 1 {
            knot.1 += diff_y.signum();
        }

        if is_last {
            self.positions.insert(*knot);
        }
    }

    pub fn count_positions(&mut self, steps: &[Step]) -> usize {
        for step in steps {
            for _ in 0..step.amount {
                self.move_head(&step.direction);
            }
        }
        self.positions.len()
    }
}

struct Step {
    amount: u32,
    direction: Direction,
}

impl FromStr for Step {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let (direction, amount) = s.split_once(' ').unwrap();
        let direction = match direction {
            "R" => Direction::Right,
            "L" => Direction::Left,
            "U" => Direction::Up,
            "D" => Direction::Down,
            _ => panic!("Invalid direction"),
        };
        let amount = amount.parse().unwrap();
        Ok(Step { amount, direction })
    }
}

enum Direction {
    Up,
    Down,
    Left,
    Right,
}
