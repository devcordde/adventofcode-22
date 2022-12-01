// See https://aka.ms/new-console-template for more information

using System.Collections;

var lines = File.ReadAllLines("input.txt");
var calories = new List<int>();
var currentCalories = 0;
foreach (var line in lines)
{
    if (line == "")
    {
        calories.Add(currentCalories);
        currentCalories = 0;
        continue;
    }

    currentCalories += Int32.Parse(line);
}

var sortedCalories = calories.OrderByDescending(i => i );

Console.WriteLine(sortedCalories.First());

var caloriesIndex = 0;
currentCalories = 0;
foreach (var sortedCalory in sortedCalories)
{
    if(caloriesIndex > 2) break;

    currentCalories += sortedCalory;
    caloriesIndex++;
}

Console.WriteLine(currentCalories);