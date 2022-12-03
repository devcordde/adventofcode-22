import { readFileLines } from '../utils.ts';

const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

export function day03_01() {
    const lines = readFileLines('/day03/input.txt');
    let sum = 0;
    for (const line of lines) {
        const share: string[] = [];
        const firstComp = line.slice(0, line.length / 2);
        const secondComp = line.slice(line.length / 2).split('');

        for (const char of firstComp) {
            if (
                secondComp.find((c) => c === char) &&
                !share.find((c) => c === char)
            ) {
                share.push(char);
            }
        }

        for (const elem of share) {
            sum += chars.indexOf(elem) + 1;
        }
    }

    console.log('1.', sum);
}

export function day03_02() {
    const lines = readFileLines('/day03/input.txt');
    const chunks = [];
    for (let i = 0; i < lines.length; i += 3) {
        chunks.push(lines.slice(i, i + 3));
    }

    let sum = 0;
    for (const chunk of chunks) {
        let shared = '';
        for (const char of chunk[0]) {
            if (chunk[1].includes(char) && chunk[2].includes(char)) {
                shared = char;
            }
        }

        sum += chars.indexOf(shared) + 1;
    }

    console.log('2.', sum);
}
