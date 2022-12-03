const fs = require("fs");

function split(str, index) {
    const result = [str.slice(0, index), str.slice(index)];

    return result;
}

/**
 * Part 1
 */

let priorities = 0;
fs.readFile("input.txt", "utf8", function (err, data) {
    data.split("\n").forEach((element) => {
        let common;
        const [first, second] = split(element, element.length / 2);

        for (let i in first) {
            second.includes(first[i]) ? (common = first[i]) : false;
        }

        if (common == common.toLowerCase())
            priorities += common.charCodeAt(0) - 96;
        if (common == common.toUpperCase())
            priorities += 26 + common.charCodeAt(0) - 64;
    });
    console.log("Part 1: ", priorities);
});

/**
 * Part 2
 */
const alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
let priorities2 = 0;

fs.readFile("input.txt", "utf8", function (err, data) {
    data.match(/(?=[\s\S])(?:.*\n?){1,3}/g).forEach((element) => {
        const items = element.split("\n");
        for (let i in alphabet) {
            if (
                items[0].includes(alphabet[i]) &&
                items[1].includes(alphabet[i]) &&
                items[2].includes(alphabet[i])
            ) {
                if (alphabet[i] == alphabet[i].toLowerCase())
                    priorities2 += alphabet[i].charCodeAt(0) - 96;
                if (alphabet[i] == alphabet[i].toUpperCase())
                    priorities2 += 26 + alphabet[i].charCodeAt(0) - 64;
            }
        }
    });
    console.log("Part 2: ", priorities2);
});
