#[derive(Debug, Clone, Copy, PartialEq, Eq)]
enum Unit {
    Rock,
    Sand,
    Air,
}

fn main() {
    let input = include_str!("../../input/day14.txt");

    let mut cave_grid = Box::new([[Unit::Air; 1000]; 300]);

    for line in input.lines() {
        let coordinates: Vec<(usize, usize)> = line
            .split(" -> ")
            .into_iter()
            .map(|s| {
                let mut split = s.split(",");
                (
                    split.next().unwrap().parse().unwrap(),
                    split.next().unwrap().parse().unwrap(),
                )
            })
            .collect();

        for window in coordinates.windows(2) {
            let (x1, y1) = window[0];
            let (x2, y2) = window[1];

            if x1 != x2 {
                let min = x1.min(x2);
                let max = x1.max(x2);
                for x in min..=max {
                    cave_grid[y1][x] = Unit::Rock;
                }
            } else {
                let min = y1.min(y2);
                let max = y1.max(y2);
                for y in min..=max {
                    cave_grid[y][x1] = Unit::Rock;
                }
            }
        }
    }

    let mut part = Box::new(cave_grid.clone());

    'part1: loop {
        let mut current_sand_unit = (0, 500);
        loop {
            match current_sand_unit.find_next(&part) {
                Some(mew) => current_sand_unit = mew,
                None => {
                    part[current_sand_unit.0][current_sand_unit.1] = Unit::Sand;
                    break;
                }
            };
            if current_sand_unit.0 == 299 {
                break 'part1; // endless falling
            }
        }
    }

    println!(
        "part 1: {}",
        part.iter().flatten().filter(|&&u| u == Unit::Sand).count()
    );

    part = Box::new(cave_grid.clone());

    part.insert_floor();

    'part2: loop {
        let mut current_sand_unit = (0, 500);
        loop {
            match current_sand_unit.find_next(&part) {
                Some(mew) => current_sand_unit = mew,
                None => {
                    part[current_sand_unit.0][current_sand_unit.1] = Unit::Sand;
                    if current_sand_unit == (0, 500) {
                        break 'part2; // source blocked
                    }
                    break;
                }
            };
        }
    }

    println!(
        "part 2: {}",
        part.iter().flatten().filter(|&&u| u == Unit::Sand).count()
    );
}

trait FindNext {
    type Type;
    fn find_next(&self, grid: &Box<[[Unit; 1000]; 300]>) -> Option<Self::Type>;
}

impl FindNext for (usize, usize) {
    type Type = (usize, usize);

    fn find_next(&self, grid: &Box<[[Unit; 1000]; 300]>) -> Option<Self::Type> {
        if grid[self.0 + 1][self.1] == Unit::Air {
            return Some((self.0 + 1, self.1));
        } else if grid[self.0 + 1][self.1 - 1] == Unit::Air {
            return Some((self.0 + 1, self.1 - 1));
        } else if grid[self.0 + 1][self.1 + 1] == Unit::Air {
            return Some((self.0 + 1, self.1 + 1));
        } else {
            return None;
        }
    }
}

trait InsertFloor {
    fn insert_floor(&mut self);
}

impl InsertFloor for Box<Box<[[Unit; 1000]; 300]>> {
    fn insert_floor(&mut self) {
        let mut floor = 0;

        for (index, y) in self.iter().enumerate() {
            for x in y {
                if x == &Unit::Rock {
                    if index > floor {
                        floor = index;
                    }
                    break;
                }
            }
        }
        floor = floor + 2;

        for i in 0..1000 {
            self[floor][i] = Unit::Rock;
        }
    }
}
