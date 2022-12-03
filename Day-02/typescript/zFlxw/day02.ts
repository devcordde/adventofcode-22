import { readFileLines } from '../utils.ts';

export function day02_01() {
    const lines = readFileLines('day02/input.txt');
    let sum = 0;
    for (const line of lines) {
        if (line === '') continue;
        const [opp, mine] = line.split(' ');
        sum += getWinScore(opp, mine) + getShapeScore(mine);
    }

    console.log('1.', sum);
}

export function day02_02() {
    const lines = readFileLines('day02/input.txt');
    let sum = 0;
    for (const line of lines) {
        if (line === '') continue;
        const [opp, res] = line.split(' ');
        sum += getShapeScore(chooseShape(opp, res)) + getWinRating(res);
    }

    console.log('2.', sum);
}

function getShapeName(shape: string): string {
    switch (shape) {
        case 'A':
        case 'X':
            return 'rock';
        case 'B':
        case 'Y':
            return 'paper';
        case 'C':
        case 'Z':
            return 'scissors';
        default:
            return '';
    }
}

function getShapeScore(shape: string): number {
    const name = getShapeName(shape);
    if (name === 'rock') {
        return 1;
    } else if (name === 'paper') {
        return 2;
    } else if (name === 'scissors') {
        return 3;
    }

    return 0;
}

function getWinScore(oppShape: string, myShape: string): number {
    const myName = getShapeName(myShape);
    const oppName = getShapeName(oppShape);

    if (myName === oppName) {
        return 3;
    }

    if (
        (myName === 'rock' && oppName === 'scissors') ||
        (myName === 'paper' && oppName === 'rock') ||
        (myName === 'scissors' && oppName === 'paper')
    ) {
        return 6;
    }

    return 0;
}

function getWinRating(result: string) {
    if (result === 'Z') {
        return 6;
    }

    if (result === 'Y') {
        return 3;
    }

    return 0;
}

function chooseShape(oppShape: string, res: string) {
    const name = getShapeName(oppShape);
    if (res === 'Y') {
        return oppShape;
    }

    if (name === 'rock') {
        if (res === 'X') {
            return 'C';
        } else {
            return 'B';
        }
    } else if (name === 'paper') {
        if (res === 'X') {
            return 'A';
        } else {
            return 'C';
        }
    } else if (name === 'scissors') {
        if (res === 'X') {
            return 'B';
        } else {
            return 'A';
        }
    }

    return '';
}
