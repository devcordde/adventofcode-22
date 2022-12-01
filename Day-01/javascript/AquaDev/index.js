const fs = require("fs");

let calories = [];

fs.readFile("input.txt", "utf8", function (err, data) {
    data.split("\n\n").forEach((element, index) => {
        let counter = 0;
        element.split("\n").forEach((data) => {
            counter += parseInt(data);
        });
        calories = [...calories, counter];
    });
    calories.sort((a, b) => b - a);
    console.log("Part 1: ", calories[0]);
    console.log("Part 2: ", calories[0] + calories[1] + calories[2]);
});
