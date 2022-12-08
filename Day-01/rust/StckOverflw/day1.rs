fn main() {
    let input = include_str!("../../input/day1.txt");
    let input = &input.lines().collect::<Vec<&str>>();
    let input = split_delimited(input, &"");

    let input: Vec<Vec<u64>> = input
        .into_iter()
        .map(|str| {
            str.into_iter()
                .map(|str| str.parse().expect("Not A Number"))
                .collect()
        })
        .collect();

    let mut sums: Vec<u64> = input
        .into_iter()
        .map(|numbers| numbers.into_iter().sum())
        .collect();

    sums.sort_by(|a, b| b.cmp(a));

    println!("part 1: {:?}", sums.first().expect("No number").to_owned());

    println!("part 2: {:?}", sums[..3].into_iter().sum::<u64>());
}

pub fn split_delimited<'a, T>(input: &'a [T], delim: &T) -> Vec<&'a [T]>
where
    T: PartialEq<T>,
{
    let elems = input.iter().enumerate();
    let (k, mut r) = elems.fold((0, vec![]), |(i, mut r), (j, x)| {
        if x == delim && j > 0 {
            r.push(&input[(i + 1)..j]);
            return (j, r);
        }
        (i, r)
    });
    if !input.is_empty() {
        r.push(&input[(k + 1)..]);
    }
    r
}
