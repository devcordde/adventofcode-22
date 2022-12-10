package unbekannt

import java.io.File

fun main() {
    val input = File("input-day10.txt").readLines()

    var X = 1
    var adds: Addx? = null
    val signals = mutableListOf<Int>()
    val lines = mutableListOf<String>()

    var signal = 1
    var index = 0
    var currentLine = ""
    while (index < input.size){
        var wasChanged = false
        val command = input[index]
        if (signal == 20 || (signal - 20) % 40 == 0) {
            signals.add(signal * X)
        }
        currentLine += if (signal % 40 in (X .. X+2) || (signal % 40 == 0 && 40 in (X .. X+2) )) "#" else "."

        if (currentLine.length == 40) {
            lines.add(currentLine)
            currentLine = ""
        }

        if(adds != null){
            if (adds.siganlIndex == signal) {
                X += adds.value
                adds = null
                wasChanged = true
            }
        }


        if (command.startsWith("addx") && !wasChanged && adds == null) {
            adds = command.split(" ").let { Addx(it.last().toInt(), signal + 1) }
        }
        signal++
        if(adds == null) index++
    }
    println(signals.sum())
    lines.forEach {
        println(it)
    }
}


data class Addx(val value: Int, val siganlIndex: Int)