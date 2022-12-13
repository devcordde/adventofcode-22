use std::collections::HashMap;

fn main() {
    let mut input: Vec<Vec<char>> = include_str!("../../input/day12.txt")
        .lines()
        .map(|line| line.chars().collect())
        .collect();

    let mut start: Position = (0, 0);
    let mut goal: Position = (0, 0);

    for (i, row) in input.clone().iter().enumerate() {
        for (j, c) in row.iter().enumerate() {
            if *c == 'S' {
                start = (i as Int, j as Int);
                let _ = std::mem::replace(&mut input.get_mut(i).unwrap()[j], 'a');
            }
            if *c == 'E' {
                goal = (i as Int, j as Int);
                let _ = std::mem::replace(&mut input.get_mut(i).unwrap()[j], 'z');
            }
        }
    }

    let steps = a_star(&input, start, goal).expect("No path found");

    println!("part 1: {}", steps);

    let a_s: Vec<Position> = input
        .iter()
        .enumerate()
        .flat_map(|(i, row)| {
            row.iter().enumerate().filter_map(move |(j, c)| {
                if *c == 'a' {
                    Some((i as Int, j as Int))
                } else {
                    None
                }
            })
        })
        .collect();

    let mut paths: Vec<i32> = Vec::new();

    for a in a_s {
        let path = a_star(&input, a, goal);
        if let Some(path) = path {
            paths.push(path);
        }
    }

    paths.sort();

    println!("part 2: {}", paths.first().unwrap());
}

type Field = Vec<Vec<char>>;
type Int = i32; // fuck you esther
type Position = (Int, Int);

fn a_star(field: &Field, start: Position, goal: Position) -> Option<Int> {
    type Element = (Int, Position);

    let mut queue: Vec<Element> = Vec::new();
    let mut chain: HashMap<Position, Position> = HashMap::new();
    let mut cost_so_far = HashMap::<Position, Int>::new();

    queue.push((0, start));
    chain.insert(start, start);
    cost_so_far.insert(start, 0);

    while !queue.is_empty() {
        let current: Position = queue.last().unwrap().1;
        queue.pop();

        if current == goal {
            break; // found
        }

        for next in neighbors(&field, current) {
            let new_cost = cost_so_far[&current] + 1;
            if !cost_so_far.contains_key(&next) || new_cost < cost_so_far[&next] {
                cost_so_far.insert(next, new_cost);
                chain.insert(next, current);
                queue.push((new_cost, next));
                queue.sort_by(|a, b| b.0.cmp(&a.0));
            }
        }
    }

    let mut len: Int = 0;
    let mut current: Position = goal;
    if !chain.contains_key(&goal) {
        return None;
    }
    while current != start {
        len += 1;
        current = chain[&current];
    }
    return Some(len);
}

fn neighbors(grid: &Field, from: Position) -> Vec<Position> {
    let directions: Vec<Position> = vec![(1, 0), (-1, 0), (0, 1), (0, -1)];
    let mut results: Vec<Position> = Vec::new();
    for pos in directions {
        let next = (from.0 + pos.0, from.1 + pos.1);
        if next.0 >= 0
            && next.0 < grid.len() as i32
            && next.1 >= 0
            && next.1 < grid[0].len() as i32
            && grid[next.0 as usize][next.1 as usize] as u32 - 1
            <= grid[from.0 as usize][from.1 as usize] as u32
        {
            results.push(next);
        }
    }
    return results;
}
