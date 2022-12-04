package unbekannt

import java.io.File

fun main(){
    val input =
        File("input-day04.txt").readLines().map { it.split(",").map { it.split("-").let { IntRange(it[0].toInt(), it[1].toInt()) } }.let { it[0] to it[1] } }

    input.count {
        (it.first.contains(it.second.first) && it.first.contains(it.second.last)) || (it.second.contains(it.first.first) && it.second.contains(it.first.last))
    }.printLn()

    input.count {
        it.first.contains(it.second.first) || it.second.contains(it.first.first)
    }.printLn()

}