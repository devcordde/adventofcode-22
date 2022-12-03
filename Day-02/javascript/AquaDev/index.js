const fs = require("fs");

//
// Rock = A | X (+ 1)
// Paper = B | Y (+ 2)
// Scissor = C | Z (+ 3)
//
// X = (loose)
// Y = (draw)
// Z = (win)
//

const win = {
    A: ["C"],
    X: ["C"],
    B: ["A"],
    Y: ["A"],
    C: ["B"],
    Z: ["B"],
};

const loose = {
    A: ["B"],
    X: ["B"],
    B: ["C"],
    Y: ["C"],
    C: ["A"],
    Z: ["A"],
};

const points = {
    A: 1,
    X: 1,
    B: 2,
    Y: 2,
    C: 3,
    Z: 3,
};

/**
 * Part 1
 */

let score = 0;

fs.readFile("input.txt", "utf8", function (err, data) {
    data.split("\n").forEach((element, index) => {
        let prev;
        element.split(" ").forEach((data) => {
            if (prev) {
                if (win[data].includes(prev)) {
                    score += 6 + points[data];
                } else if (loose[data].includes(prev)) {
                    score += 0 + points[data];
                } else {
                    score += 3 + points[data];
                }
            } else {
                prev = data;
            }
        });
    });
    console.log("Part 1: ", score);
});

/**
 * Part 2
 */

let score2 = 0;

fs.readFile("input.txt", "utf8", function (err, data) {
    data.split("\n").forEach((element, index) => {
        let prev;
        element.split(" ").forEach((data) => {
            if (prev) {
                // loose
                if (data == "X") {
                    score2 += 0 + points[win[prev][0]];
                    // Draw
                } else if (data == "Y") {
                    score2 += 3 + points[prev];
                    // Win
                } else {
                    score2 += 6 + points[loose[prev][0]];
                }
            } else {
                prev = data;
            }
        });
    });
    console.log("Part 2: ", score2);
});
