export function day06_01() {
    const line = Deno.readTextFileSync(Deno.cwd() + '/day06/input.txt');
    let chars: string[] = [];
    let skip = 0;
    for (let i = 0; i < line.length; i++) {
        if (chars.length === 4) {
            skip = i - 1;
            break;
        }

        const char = line[i];
        if (chars.includes(char)) {
            chars = [];
        } else {
            chars.push(char);
        }
    }

    console.log(skip);
}

export function day06_02() {
    const line = Deno.readTextFileSync(Deno.cwd() + '/day06/input.txt');
    const amount = 14;
    let skip = 0;
    for (let i = 0; i < line.length; i++) {
        const sub = line.slice(i, i + amount);
        if (sub.length !== String.prototype.concat(...new Set(sub)).length) {
            skip++;
        } else {
            break;
        }
    }

    console.log(skip + amount);
}
