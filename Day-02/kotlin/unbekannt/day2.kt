package unbekannt

import java.io.File
import kotlin.math.hypot


fun main() {
    var score = 0
    var score2 = 0
    val input = File("input-day02.txt").readLines().map {
        it.split(" ").let { rounds ->
            Pair(rounds[0].trim(), rounds[1].trim())
        }
    }.forEach {
        var roundScore =  getResultScore(it)
        roundScore += getSelectionScore(it.second)
        var roundscrore2 = getSelectionScore2(it)
        roundscrore2 += getResultScore2(it.second)
        score += roundScore
        score2 += roundscrore2
    }

    println(score)
    println(score2)
}

fun getResultScore2(you: String): Int {
    return when(you){
        "X"  -> 0
        "Y" -> 3
        "Z" -> 6
        else -> 0
    }
}

fun getSelectionScore2(it: Pair<String, String>): Int {
    val opponent = it.first
    val you = it.second

    return when(you){
        "X"  -> when(opponent){
            "A" -> 3
            "B" -> 1
            "C" -> 2
            else -> 0
        }
        "Y" -> when(opponent){
            "A" -> 1
            "B" -> 2
            "C" -> 3
            else -> 0
        }
        "Z" -> when(opponent){
            "A" -> 2
            "B" -> 3
            "C" -> 1
            else -> 0
        }
        else -> 0
    }
}

fun getSelectionScore(second: String): Int {
    return when(second){
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> 0
    }
}
/*
 * Values
 *  YOU
 *  Rock x 1
 *  Paper Y 2
 *  Sissors Z 3
 *
 *  Oponent
 *  Rock A
 *  Paper B
 *  Sissors C
 *
 *  Outcome
 *  Loose 0
 *  Draw 3
 *  Win 6
 */
fun getResultScore(it: Pair<String, String>): Int {
    val opponent = it.first
    val you = it.second

    return when(opponent){
        "A"  -> when(you) { // Rock
                "X" -> 3 // Rock
                "Y" -> 6 // Paper
                "Z" -> 0 // Sisors
                else -> 0
            }
        "B" -> when (you) {
            "X" -> 0
            "Y" -> 3
            "Z" -> 6
            else -> 0
        }
        "C" -> when(you) {
            "X" -> 6
            "Y" -> 0
            "Z" -> 3
            else -> 0
        }
        else -> 0
    }
}
