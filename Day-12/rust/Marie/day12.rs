use std::fs;

use pathfinding::prelude::Matrix;

fn main() {
    let input = fs::read_to_string("inputs/day12.txt").unwrap();
    let matrix: Matrix<Node> = Matrix::from_iter(input.lines().enumerate().map(|(y, line)| {
        line.chars().enumerate().map(move |(x, char)| Node {
            char,
            position: (x, y),
        })
    }));

    let starting_point = matrix
        .values()
        .find(|node| node.is_starting_point())
        .expect("No starting point was found");

    let destination = matrix
        .values()
        .find(|node| node.is_destination())
        .expect("No destination was found");

    let path = pathfinding::directed::bfs::bfs(
        &starting_point,
        |node| node.successors(&matrix, false),
        |node| node.position == destination.position,
    )
    .unwrap();
    println!("{}", path.len() - 1);

    let path = pathfinding::directed::bfs::bfs(
        &destination,
        |node| node.successors(&matrix, true),
        |node| node.char == 'a' || node.is_starting_point(),
    )
    .unwrap();
    println!("{}", path.len() - 1);
}

type Position = (usize, usize);

#[derive(PartialEq, Eq, Hash, Clone, Debug)]
struct Node {
    char: char,
    position: Position,
}

impl Node {
    fn successors<'a>(&self, grid: &'a Matrix<Node>, reverse: bool) -> Vec<&'a Node> {
        let (x, y) = self.position;
        grid.neighbours((y, x), false)
            .map(|(x, y)| grid.get((x, y)).unwrap())
            .filter(|neighbor| self.is_valid_neighbor(neighbor, reverse))
            .collect()
    }

    fn is_valid_neighbor(&self, neighbor: &Node, reverse: bool) -> bool {
        let real_self = self.real_char();
        let real_neighbor = neighbor.real_char();
        if !reverse {
            real_neighbor as i32 - real_self as i32 <= 1
        } else {
            real_self as i32 - real_neighbor as i32 <= 1
        }
    }

    fn is_starting_point(&self) -> bool {
        self.char == 'S'
    }

    fn is_destination(&self) -> bool {
        self.char == 'E'
    }

    fn real_char(&self) -> char {
        match self.char {
            'S' => 'a',
            'E' => 'z',
            a => a,
        }
    }
}
