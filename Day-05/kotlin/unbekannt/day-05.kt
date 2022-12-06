import java.io.File

fun main() {
    val input = File("input-day05.txt").readLines()
    val cargoPart1 = mutableMapOf<Int, MutableList<Char>>()
    val cargoPart2 = mutableMapOf<Int, MutableList<Char>>()
    val instructions = mutableListOf<Instruction>()
    var cargoHandled = false
    input.forEach {
        if(it.isBlank()) {
            cargoHandled = true
            return@forEach
        }
        if(it.trim().startsWith("1")){
            return@forEach
        }
        if(!cargoHandled){
            it.chunked(4).forEachIndexed { index, value ->
                if(value.isBlank()) return@forEachIndexed
                val indexs = index+1
                if(cargoPart1.containsKey(indexs)){
                    cargoPart1[indexs]!!.add(value[1])
                    cargoPart2[indexs]!!.add(value[1])
                } else {
                    cargoPart1[indexs] = mutableListOf(value[1])
                    cargoPart2[indexs] = mutableListOf(value[1])
                }
            }
        }
        else {
            val result = "move (?<move>\\d+) from (?<from>\\d+) to (?<to>\\d+)".toRegex()
            val instruction = result.matchEntire(it)!!.groups.let { Instruction(it["move"]!!.value.toInt(), it["from"]!!.value.toInt(), it["to"]!!.value.toInt() ) }
            instructions.add(instruction)
        }
    }

    instructions.forEach {
        val from = cargoPart1[it.from]!!
        val to = cargoPart1[it.to]!!
        var amount = it.amount
        while (amount> 0){
            val move = from.first()
            to.add(0, move)
            from.removeFirst()
            amount--
        }
    }

    instructions.forEach {
        val from = cargoPart2[it.from]!!
        val to = cargoPart2[it.to]!!
        var amount = it.amount
        while (amount> 0){
            val move = from[amount-1]
            to.add(0, move)
            from.removeAt(amount-1)
            amount--
        }
    }

    val resultStringPart1 = cargoPart1.toSortedMap().map {
        it.value.first()
    }.joinToString("")

    val resultStringPart2 = cargoPart2.toSortedMap().map {
        it.value.first()
    }.joinToString("")

    println(resultStringPart1)
    println(resultStringPart2)
}

data class Instruction(val amount: Int, val from: Int, val to: Int)