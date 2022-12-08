fn main() {
    let input = include_str!("../../input/day8.txt").lines();

    let input: Vec<Vec<u32>> = input
        .map(|a| {
            a.chars()
                .map(|char| char.to_string().parse().unwrap())
                .collect()
        })
        .collect();

    let mut result = 0;

    for (row_index, trees) in input.clone().into_iter().enumerate() {
        for (column_index, tree) in trees.clone().into_iter().enumerate() {
            let directions = get_sides(input.clone(), row_index, column_index);

            let visible = is_visible(
                tree,
                directions.left,
                directions.right,
                directions.top,
                directions.bottom,
            );

            if visible {
                result += 1;
            }
        }
    }

    println!("part 1: {}", result);

    let mut result = 0;

    for (row_index, trees) in input.clone().into_iter().enumerate() {
        for (column_index, tree) in trees.clone().into_iter().enumerate() {
            let directions = get_sides(input.clone(), row_index, column_index);

            let left = viewing_distance(tree, directions.left);
            let right = viewing_distance(tree, directions.right);
            let top = viewing_distance(tree, directions.top);
            let bottom = viewing_distance(tree, directions.bottom);

            let scenic_score = left * right * top * bottom;

            if result < scenic_score {
                result = scenic_score;
            }
        }
    }

    println!("part 2: {}", result);
}

fn is_visible(tree: u32, left: Vec<u32>, right: Vec<u32>, top: Vec<u32>, bottom: Vec<u32>) -> bool {
    if left.is_empty() || right.is_empty() || top.is_empty() || bottom.is_empty() {
        return true;
    }

    if left.into_iter().all(|any| any < tree)
        || right.into_iter().all(|any| any < tree)
        || top.into_iter().all(|any| any < tree)
        || bottom.into_iter().all(|any| any < tree)
    {
        return true;
    }
    return false;
}

fn viewing_distance(tree: u32, direction: Vec<u32>) -> u32 {
    if direction.is_empty() {
        return 0;
    }

    let mut distance = 0;

    for view in direction {
        distance += 1;
        if view >= tree {
            break;
        }
    }

    return distance;
}

#[derive(Debug)]
struct Directions {
    left: Vec<u32>,
    right: Vec<u32>,
    top: Vec<u32>,
    bottom: Vec<u32>,
}

fn get_sides(input: Vec<Vec<u32>>, row: usize, column: usize) -> Directions {
    let enumerated_row: Vec<(usize, u32)> = input
        .get(row)
        .unwrap()
        .to_owned()
        .into_iter()
        .enumerate()
        .collect();
    let enumerated_column: Vec<(usize, u32)> = input
        .into_iter()
        .map(|x| x.get(column).unwrap().clone())
        .enumerate()
        .collect();

    let left = enumerated_row
        .clone()
        .into_iter()
        .filter(|(i, _)| i < &column)
        .map(|(_, x)| x)
        .rev()
        .collect();

    let right = enumerated_row
        .into_iter()
        .filter(|(i, _)| i > &column)
        .map(|(_, x)| x)
        .collect();

    let top = enumerated_column
        .clone()
        .into_iter()
        .filter(|(i, _)| i < &row)
        .map(|(_, x)| x)
        .rev()
        .collect();

    let bottom = enumerated_column
        .clone()
        .into_iter()
        .filter(|(i, _)| i > &row)
        .map(|(_, x)| x)
        .collect();

    Directions {
        left,
        right,
        top,
        bottom,
    }
}
