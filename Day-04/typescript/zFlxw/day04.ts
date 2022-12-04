import { readFileLines } from '../utils.ts';

export function day04_01() {
    const lines = readFileLines('/day04/input.txt');
    let totalOverlaps = 0;
    for (const line of lines) {
        if (line === '') continue;
        const [first, second] = line.split(',');
        const [firstLowerBound, firstUpperBound] = first
            .split('-')
            .map((val) => parseInt(val));
        const [secondLowerBound, secondUpperBound] = second
            .split('-')
            .map((val) => parseInt(val));

        if (
            (firstLowerBound <= secondLowerBound &&
                firstUpperBound >= secondUpperBound) ||
            (secondLowerBound <= firstLowerBound &&
                secondUpperBound >= firstUpperBound)
        ) {
            totalOverlaps++;
        }
    }

    console.log('1.', totalOverlaps);
}

export function day04_02() {
    const lines = readFileLines('/day04/input.txt');
    let totalOverlaps = 0;
    for (const line of lines) {
        if (line === '') continue;
        const [first, second] = line.split(',');
        const [firstLowerBound, firstUpperBound] = first
            .split('-')
            .map((val) => parseInt(val));
        const [secondLowerBound, secondUpperBound] = second
            .split('-')
            .map((val) => parseInt(val));

        if (
            Math.max(firstLowerBound, secondLowerBound) <=
            Math.min(firstUpperBound, secondUpperBound)
        ) {
            totalOverlaps++;
        }
    }

    console.log('2.', totalOverlaps);
}
