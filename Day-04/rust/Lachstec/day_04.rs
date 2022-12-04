use std::fs;
use std::io;

fn check_overlap_fully(section: &(&str, &str)) -> bool {
    let left_limits = section.0.split_once("-").unwrap();
    let right_limits = section.1.split_once("-").unwrap();

    let left_limits = {
        (left_limits.0.parse::<u32>().unwrap(), left_limits.1.parse::<u32>().unwrap())
    };

    let right_limits = {
        (right_limits.0.parse::<u32>().unwrap(), right_limits.1.parse::<u32>().unwrap())
    };

    (left_limits.0 <= right_limits.0 && left_limits.1 >= right_limits.1) || (left_limits.0 >= right_limits.0 && left_limits.1 <= right_limits.1)
}

fn check_overlap(section: &(&str, &str)) -> bool {
    let left_limits = section.0.split_once("-").unwrap();
    let right_limits = section.1.split_once("-").unwrap();

    let left_limits = {
        (left_limits.0.parse::<u32>().unwrap(), left_limits.1.parse::<u32>().unwrap())
    };

    let right_limits = {
        (right_limits.0.parse::<u32>().unwrap(), right_limits.1.parse::<u32>().unwrap())
    };

    (left_limits.0 >= right_limits.0 && left_limits.0 <= right_limits.1) || (right_limits.0 >= left_limits.0 && right_limits.0 <= left_limits.1)
}

fn main() -> Result<(), io::Error>{
    let data = fs::read_to_string("./resources/input_4_1.txt")?;
    let sections: Vec<(&str, &str)> = data.split("\n")
        .map(|line| {
            let elem: Vec<&str> = line.split(",").collect();
            (elem[0], elem[1])
        }).collect();

    let sum_full_overlays: u32 = sections.iter()
        .filter(|line| {
            check_overlap_fully(line)
        }).count() as u32;

    let sum_overlays: u32 = sections.iter()
        .filter(|line| {
            check_overlap(line)
        }).count() as u32;

    println!("Sum of full Overlays: {}. Sum of Overlays: {}", sum_full_overlays, sum_overlays);

    Ok(())
}