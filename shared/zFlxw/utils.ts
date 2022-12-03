export function readFileLines(path: string): string[] {
    return Deno.readTextFileSync(`${Deno.cwd()}/${path}`).split('\n');
}
