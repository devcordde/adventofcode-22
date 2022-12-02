package unbekannt

import java.io.File

val win = mapOf("A" to "Y", "B" to "Z", "C" to "X")
val loose = mapOf("A" to "Z", "B" to "X", "C" to "Y")
val points = mapOf("X" to 1, "Y" to 2, "Z" to 3, "A" to 1, "B" to 2, "C" to 3)

fun main(){

    val input = File("input-day02.txt").readLines().map {
        it.split(" ").let { rounds ->
            Pair(rounds[0].trim(), rounds[1].trim())
        }
    }
    println(input.sumOf {
        when {
            (win[it.first] == it.second) -> {
                6
            }

            loose[it.first] == it.second -> {
                0
            }

            else -> 3
        }+ points[it.second]!!
    })
    println(input.sumOf {
        when(it.second){
            "X" -> 0+ points[loose[it.first]]!!
            "Y" -> 3+ points[it.first]!!
            else -> 6 + points[win[it.first]]!!
        }
    })
}