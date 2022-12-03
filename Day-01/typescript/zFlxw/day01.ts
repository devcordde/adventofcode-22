import { readFileLines } from '../utils.ts';

export function day01_01() {
    const lines = readFileLines('day01/input.txt');
    let currentMaxCal = 0;
    let temp = 0;

    for (const line of lines) {
        if (line == '') {
            if (temp > currentMaxCal) {
                currentMaxCal = temp;
            }

            temp = 0;
        } else {
            temp += Number(line);
        }
    }

    console.log('1.', currentMaxCal);
}

export function day01_02() {
    const lines = readFileLines('day01/input.txt');
    const currentMaxCals: number[] = [];
    let temp = 0;

    for (const line of lines) {
        if (line == '') {
            if (currentMaxCals.length !== 3) {
                currentMaxCals.push(Number(temp));
                temp = 0;
                continue;
            }

            currentMaxCals.sort((x, y) => x - y);
            if (temp > currentMaxCals[0]) {
                currentMaxCals[0] = temp;
            }

            temp = 0;
        } else {
            temp += Number(line);
        }
    }

    currentMaxCals.sort((x, y) => x - y);
    if (temp > currentMaxCals[0]) {
        currentMaxCals[0] = temp;
    }

    let sum = 0;
    currentMaxCals.forEach((entry) => (sum += entry));
    console.log('2.', sum);
}
