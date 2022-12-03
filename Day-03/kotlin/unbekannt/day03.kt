package unbekannt

import java.io.File


val values = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
fun main() {
    val input = File("input-day03.txt").readLines()

    input.map { it.substring(0 until (it.length/2)) to it.substring(it.length/2) }.sumOf {
            val first = it.first
            val second = it.second
            var score = 0
            val checked = mutableListOf<Char>()
            first.forEach { char ->
                if(char in checked) return@forEach
                if(char in second){
                    score += values.indexOf(char)+1
                }
                checked.add(char)
            }
            score
        }.printLn()

    input
        .chunked(3).sumOf {
        var score = 0
            val checked = mutableListOf<Char>()
        it.first().forEach {char ->
            if(char in checked) return@forEach
            val isInSecond = char in it[1]
            if(isInSecond){
                if(char in it.last()) {
                    score += values.indexOf(char)+1
                }
            }
            checked.add(char)
        }
        score
    }.printLn()

}

fun Int.printLn(){
    println(this)
}