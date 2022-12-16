use std::str::FromStr;

fn main() {
    let input = include_str!("../../input/day15.txt").lines();

    let input: Vec<Sensor> = input
        .map(|line| {
            let mut parts = line.split(": ");
            let sensor = parts.next().unwrap().strip_prefix("Sensor at ").unwrap();
            let beacon = parts
                .next()
                .unwrap()
                .strip_prefix("closest beacon is at ")
                .unwrap();

            let position: Position = sensor.parse().unwrap();
            let closest_beacon = beacon.parse().unwrap();
            let distance = position.taxicab_distance(&closest_beacon);

            Sensor {
                position,
                closest_beacon,
                distance,
            }
        })
        .collect();

    let mut covered_positions: Vec<Position> = Vec::new();

    for sensor in &input {
        let min_x = sensor.position.x - sensor.distance;
        let max_x = sensor.position.x + sensor.distance;

        for x in min_x..=max_x {
            let position = Position { x, y: 2000000 };

            if sensor.position.is_in_reach(&position, &sensor.distance) {
                covered_positions.push(position);
            }
        }
    }

    let mut covered_positions: Vec<Position> = covered_positions
        .into_iter()
        .filter(|position| {
            !(&input)
                .into_iter()
                .any(|sensor| sensor.closest_beacon == *position)
        })
        .collect();

    covered_positions.sort_by(|a, b| a.x.cmp(&b.x));

    covered_positions.dedup();

    println!("Part 1: {}", covered_positions.len());

    let mut not_covered_position: Option<Position> = None;

    'outer: for sensor in &input {
        let min = sensor.position.y - sensor.distance - 1;
        let max = sensor.position.y + sensor.distance + 1;
        for y in min..=max {
            let outer_min = (sensor.position.x - sensor.distance) - 1;
            let outer_max = (sensor.position.x + sensor.distance) + 1;

            let min_position = Position {
                x: outer_min + (sensor.position.y - y).abs(),
                y,
            };
            let max_position = Position {
                x: outer_max - (sensor.position.y - y).abs(),
                y,
            };

            if !(&input).into_iter().any(|sensor| {
                min_position.is_in_reach(&sensor.position, &sensor.distance)
                    || sensor.closest_beacon == min_position
            }) {
                if min_position.bigger_than(4000000) {
                    continue;
                }
                not_covered_position = Some(min_position.clone());
                break 'outer;
            }
            if !(&input).into_iter().any(|sensor| {
                max_position.is_in_reach(&sensor.position, &sensor.distance)
                    || sensor.closest_beacon == max_position
            }) {
                if max_position.bigger_than(4000000) {
                    continue;
                }
                not_covered_position = Some(max_position.clone());
                break 'outer;
            }
        }
    }

    let not_covered_position = not_covered_position.unwrap();

    println!(
        "Part 2: {}",
        (not_covered_position.x * 4000000) + not_covered_position.y
    );
}

#[derive(Debug, PartialEq, Eq, Clone)]
struct Position {
    x: i64,
    y: i64,
}

#[derive(Debug, PartialEq, Eq)]
struct Sensor {
    pub position: Position,
    pub closest_beacon: Position,
    pub distance: i64,
}

impl FromStr for Position {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut parts = s.split(", ");
        let x = parts
            .next()
            .unwrap()
            .strip_prefix("x=")
            .unwrap()
            .parse()
            .unwrap();
        let y = parts
            .next()
            .unwrap()
            .strip_prefix("y=")
            .unwrap()
            .parse()
            .unwrap();

        Ok(Position { x, y })
    }
}

impl Position {
    fn taxicab_distance(&self, other: &Position) -> i64 {
        (self.x - other.x).abs() + (self.y - other.y).abs()
    }

    fn is_in_reach(&self, other: &Position, max: &i64) -> bool {
        self.taxicab_distance(other) <= *max
    }

    fn bigger_than(&self, cmp: i64) -> bool {
        (self.x > cmp || self.y > cmp) || (self.x < 0 || self.y < 0)
    }
}
