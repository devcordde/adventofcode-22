use std::fs;
use std::io;

fn rps_points(strategy: &(&str, &str)) -> u32 {
    match strategy.0 {
        "A" => match strategy.1 {
            "X" => 4,
            "Y" => 8,
            "Z" => 3,
            _ => panic!("Invalid Move"),
        },
        "B" => match strategy.1 {
            "X" => 1,
            "Y" => 5,
            "Z" => 9,
            _ => panic!("Invalid Move"),
        },
        "C" => match strategy.1 {
            "X" => 7,
            "Y" => 2,
            "Z" => 6,
            _ => panic!("Invalid Move"),
        },
        _ => panic!("Invalid Move"),
    }
}

fn points_strategy_guide(strategy: &(&str, &str)) -> u32 {
    match strategy.1 {
        "X" => match strategy.0 {
            "A" => 3,
            "B" => 1,
            "C" => 2,
            _ => panic!("Invalid Move"),
        },
        "Y" => match strategy.0 {
            "A" => 4,
            "B" => 5,
            "C" => 6,
            _ => panic!("Invalid Move"),
        }
        "Z" => match strategy.0 {
            "A" => 8,
            "B" => 9,
            "C" => 7,
            _ => panic!("Invalid Move"),
        },
        _ => panic!("Invalid Move"),
    }
}

fn main() -> Result<(), io::Error> {
    let data = fs::read_to_string("./resources/input_2_1.txt")?;
    let pairs: Vec<(&str, &str)> = data.split("\n")
        .map(|pairstr| {
            let elem: Vec<&str> = pairstr.split(" ").collect();
            (elem[0], elem[1])
        }).collect();

    let first_total_score: u32 = pairs.iter()
        .map(rps_points)
        .sum();

    let second_total_score: u32 = pairs.iter()
        .map(points_strategy_guide)
        .sum();

    println!("First Score: {}. Second Score: {}.", first_total_score, second_total_score);

    Ok(())
}
