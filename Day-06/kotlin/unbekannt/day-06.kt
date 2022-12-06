import java.io.File

fun main(){
    val currentStackPart1 = mutableListOf<Char>()
    val currentStackPart2 = mutableListOf<Char>()
    var part1Found = false
    val input = File("input-day06.txt").readText()
    input.forEachIndexed { index, char ->
        currentStackPart1.add(char)
        currentStackPart2.add(char)
        if (currentStackPart1.size > 4){
            currentStackPart1.removeFirst()
        }
        if(currentStackPart2.size > 14){
            currentStackPart2.removeFirst()
        }
        val sumPart1 = currentStackPart1.sumOf { currentStackPart1.count { c: Char -> c == it } }
        val sumPart2 = currentStackPart2.sumOf { currentStackPart2.count { c: Char -> c == it } }
        if(sumPart1 == 4 && currentStackPart1.size == 4 && !part1Found){
            println(index+1)
            part1Found = true
        }
        if(sumPart2 == 14 && currentStackPart2.size == 14){
            println(index+1)
            return
        }
    }
}