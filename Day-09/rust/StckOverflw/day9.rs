use std::{collections::HashSet, str::FromStr};

fn main() {
    let input = include_str!("../../input/day9.txt");
    let input: Vec<Motion> = input.lines().map(|line| line.parse().unwrap()).collect();

    let result = Rope::<2>::default().run_simulation(&input);
    println!("part 1: {}", result);

    let result = Rope::<10>::default().run_simulation(&input);

    println!("part 2: {}", result);
}

type Position = (i32, i32);

#[derive(Debug)]
pub struct Motion {
    pub direction: Direction,
    pub count: i32,
}

#[derive(Debug)]
pub enum Direction {
    Right,
    Left,
    Up,
    Down,
}

impl FromStr for Motion {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut split = s.split(" ");
        let direction = split.next().unwrap();
        let count = split.next().unwrap().parse().unwrap();

        let motion = Motion {
            direction: match direction {
                "R" => Direction::Right,
                "L" => Direction::Left,
                "U" => Direction::Up,
                "D" => Direction::Down,
                _ => return Err(()),
            },
            count,
        };

        Ok(motion)
    }
}

struct Rope<const KNOTS: usize> {
    knots: [Position; KNOTS],
    positions: HashSet<Position>,
}

impl<const KNOTS: usize> Default for Rope<KNOTS> {
    fn default() -> Self {
        Self {
            knots: [(0, 0); KNOTS],
            positions: Default::default(),
        }
    }
}

impl<const KNOTS: usize> Rope<KNOTS> {
    pub fn run_simulation(&mut self, motions: &[Motion]) -> usize {
        for motion in motions {
            for _ in 0..motion.count {
                let mut head = &mut self.knots.get_mut(0).unwrap();
                match motion.direction {
                    Direction::Up => head.1 += 1,
                    Direction::Down => head.1 -= 1,
                    Direction::Left => head.0 -= 1,
                    Direction::Right => head.0 += 1,
                }

                let length = self.knots.len();
                for index in 1..length {
                    self.move_knot(index, index == length - 1);
                }
            }
        }
        return self.positions.len();
    }

    fn move_knot(&mut self, knot_index: usize, is_last: bool) {
        let previous_knot = *self.knots.get(knot_index - 1).unwrap();
        let knot = self.knots.get_mut(knot_index).unwrap();

        let diff_x = previous_knot.0 - knot.0;
        let diff_y = previous_knot.1 - knot.1;

        if diff_x.abs() > 1 || diff_y.abs() > 1 {
            knot.0 += diff_x.signum();
            knot.1 += diff_y.signum();
        }

        if is_last {
            self.positions.insert(*knot);
        }
    }
}
