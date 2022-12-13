#![feature(iter_array_chunks)]

use serde::Deserialize;
use std::{cmp::Ordering, fmt::Debug, fs};

macro_rules! signal {
    ($int:literal) => {
        SignalValue::Integer($int)
    };
    ([$($tt:tt),*]) => {
        SignalValue::List(vec![
            $(
                signal!($tt)
            )*
        ])
    };
}

fn main() {
    let input = fs::read_to_string("inputs/day13.txt").unwrap();
    let mut signals = input
        .lines()
        .filter(|line| !line.is_empty())
        .map(|line| serde_json::from_str::<SignalValue>(line).unwrap())
        .collect::<Vec<_>>();

    let sum = signals
        .iter()
        .array_chunks::<2>()
        .map(|[left, right]| left.cmp(right))
        .enumerate()
        .filter(|(_, order)| order == &Ordering::Less)
        .map(|(index, _)| index + 1)
        .sum::<usize>();
    println!("{sum}");

    let divider_signals = vec![signal!([[2]]), signal!([[6]])];
    signals.extend(divider_signals.clone());
    signals.sort();

    let decoder_key = signals
        .iter()
        .enumerate()
        .filter(|(_, signal)| divider_signals.contains(signal))
        .map(|(index, _)| index + 1)
        .product::<usize>();
    println!("{decoder_key}");
}

#[derive(Deserialize, Eq, PartialEq, Clone)]
#[serde(untagged)]
enum SignalValue {
    Integer(u32),
    List(Vec<SignalValue>),
}

impl Debug for SignalValue {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::Integer(value) => f.write_str(value.to_string().as_str()),
            Self::List(values) => f.debug_list().entries(values).finish(),
        }
    }
}

impl PartialOrd for SignalValue {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl Ord for SignalValue {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        match (self, other) {
            (SignalValue::Integer(left), SignalValue::Integer(right)) => left.cmp(right),
            (SignalValue::List(left), SignalValue::Integer(_)) => compare_signals(left, vec![other]),
            (SignalValue::Integer(_), SignalValue::List(right)) => compare_signals(vec![self], right),
            (SignalValue::List(left), SignalValue::List(right)) => compare_signals(left, right),
        }
    }
}

fn compare_signals<'a, Left, Right>(left: Left, right: Right) -> std::cmp::Ordering
where
    Left: IntoIterator<Item = &'a SignalValue>,
    Right: IntoIterator<Item = &'a SignalValue>,
{
    let left = left.into_iter().collect::<Vec<_>>();
    let right = right.into_iter().collect::<Vec<_>>();
    if let Some(ord) = left
        .iter()
        .zip(&right)
        .map(|(left, right)| left.cmp(right))
        .find(|ord| ord != &Ordering::Equal)
    {
        return ord;
    }

    left.len().cmp(&right.len())
}
