use std::fs;

type NeighborPair<'a> = (Vec<(usize, &'a u32)>, Vec<(usize, &'a u32)>);

fn main() {
    let input = fs::read_to_string("inputs/day8.txt").unwrap();
    let len = input.lines().next().unwrap().len();
    let numbers = input
        .lines()
        .flat_map(|line| line.chars())
        .map(|c| c.to_digit(10).unwrap())
        .collect::<Vec<_>>();

    let horizontal = numbers
        .chunks(len)
        .map(|chunk| chunk.iter().collect::<Vec<_>>())
        .collect::<Vec<_>>();

    let vertical = (0..len)
        .map(|index| horizontal.iter().map(|v| v[index]).collect::<Vec<_>>())
        .collect::<Vec<_>>();

    let neighbors = numbers.iter().enumerate().map(|(index, tree)| {
        let (mut upper, lower): NeighborPair = vertical[index % len]
            .clone()
            .into_iter()
            .enumerate()
            .filter(|(n_index, _)| *n_index != index / len)
            .partition(|(n_index, _)| n_index < &(index / len));
        upper.reverse();

        let (mut left, right): NeighborPair = horizontal[index / len]
            .clone()
            .into_iter()
            .enumerate()
            .filter(|(n_index, _)| *n_index != index % len)
            .partition(|(n_index, _)| n_index < &(index % len));
        left.reverse();

        (
            [upper, lower, left, right]
                .into_iter()
                .map(|v| v.into_iter().map(|(_, n)| n).collect::<Vec<&u32>>())
                .collect::<Vec<Vec<&u32>>>(),
            tree,
        )
    });

    let visible: usize = neighbors
        .clone()
        .filter(|(neighbors, tree)| {
            neighbors
                .iter()
                .any(|n| n.is_empty() || n.iter().all(|n| n < tree))
        })
        .count();
    println!("{visible}");

    let score = neighbors
        .map(|(neighbors, tree)| {
            let result = neighbors
                .iter()
                .map(|n| {
                    if n.is_empty() {
                        return 0;
                    }
                    let distance = n.iter().take_while(|n| n < &&tree).count();
                    if distance != n.len() {
                        distance + 1
                    } else {
                        distance
                    }
                })
                .product::<usize>();
            result
        })
        .max()
        .unwrap();
    println!("{score}");
}
