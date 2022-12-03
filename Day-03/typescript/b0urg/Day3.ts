import * as fs from 'fs';

let priorities = 0;
let priorities2 = 0;
const alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
let groups: string[] = [];

function split(str: string, index: number){
    return [str.slice(0, index), str.slice(index)];
}

fs.readFile('input.txt', 'utf8' , (err, data) => {
    if(err){
        return console.error(err);
    }
    data.split("\n").forEach((line) => {
        let common: string = "";
        const [first, second] = split(line, line.length / 2);

        for (let alphabetElement of alphabet) {
            if(first.includes(alphabetElement) && second.includes(alphabetElement)){
                priorities += alphabet.indexOf(alphabetElement) + 1;
            }
        }
        // part 2
        groups.push(line)
        if(groups.length == 3){
            for (let alphabetElement of alphabet) {
                if(groups[0].includes(alphabetElement) && groups[1].includes(alphabetElement) && groups[2].includes(alphabetElement)){
                    priorities2 += alphabet.indexOf(alphabetElement) + 1;
                }
            }
            groups = [];
        }
    });

    console.log("Part 1 : " + priorities);
    console.log("Part 2 : " + priorities2);
});