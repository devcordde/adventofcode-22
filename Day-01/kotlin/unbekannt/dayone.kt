package unbekannt

import java.io.File

val input = File("input.txt")

fun main() {
    val elves = mutableListOf<Elf>()
    var currentElf = Elf()
    input.readLines().forEachIndexed { indexLine, line ->
        if (line.isBlank()) {
            elves.add(currentElf)
            currentElf = Elf()
            return@forEachIndexed
        }
        currentElf.calories += line.toInt()
    }
    elves.sortByDescending { it.calories }
    print("Most Calories on Single Elfe:")
    println(elves.first().calories)

    print("Calories Top3 Elves:")
    var topCalories = 0
    var elfCounter = 0
    while (elfCounter < 3){
        topCalories += elves[elfCounter].calories
        elfCounter++
    }
    println(topCalories)
}


data class Elf(var calories: Int = 0)