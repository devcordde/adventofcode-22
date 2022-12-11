package unbekannt

import java.io.File

fun main() {
    val input = File("input-day11.txt").readLines()

    var currentMonkey = MonkeyBuilder(-1)
    val monkeys = mutableListOf<Monkey>()
    val monkeysPart2 = mutableListOf<Monkey>()
    input.forEach {
        val command = it.trim()
        if (command.startsWith("Monkey")){
            if(currentMonkey.id != -1){
                monkeys.add(currentMonkey.build())
                monkeysPart2.add(currentMonkey.build())
            }
            var monkeyNumber = command.split(" ")[1]
            monkeyNumber = monkeyNumber.substring(0, monkeyNumber.indexOf(":"))
            currentMonkey = MonkeyBuilder(monkeyNumber.toInt())
        }
        if(command.startsWith("Starting items:")){
            val items = command.split(": ")[1].split(",")
            currentMonkey.items = items.map { it.trim() }.map { it.toLong() }.toMutableList()
        }
        if(command.startsWith("Operation: ")){
            val operation = command.split(": ")[1].split("=")[1].trim()
            currentMonkey.operation = operation
        }
        if(command.startsWith("Test: ")){
            val test = command.split(": ")[1].split(" ")[2].trim().toLong()
            currentMonkey.test = test
        }
        if(command.startsWith("If true:")){
            val trueMonkey = command.split(":")[1].split(" ")[4].trim().toInt()
            currentMonkey.trueMonkey = trueMonkey
        }
        if(command.startsWith("If false:")){
            val falseMonkey = command.split(":")[1].split(" ")[4].trim().toInt()
            currentMonkey.falseMonkey = falseMonkey
        }
    }
    monkeys.add(currentMonkey.build())
    monkeysPart2.add(currentMonkey.build())
    val objMonkeys = Monkeys(monkeys)
    val objMonkeysPart2 = Monkeys(monkeysPart2)
    var i = 0
    while (i < 20){
        objMonkeys.playRound()
        i++
    }
    var j = 0
    while (j < 10000){
        objMonkeysPart2.playRound(true)
        if((j+1) % 1000 == 0 || j+1 == 1 || j+1 == 20){
            println("Round ${j+1}")
            objMonkeysPart2.maxItems.forEach { (id, times) ->
                println("Monkey $id: $times")
//                println("Items: ${objMonkeysPart2.monkeys.first { it.id == id }.items.joinToString(", ")}")
            }
        }
        j++
    }
    val itemCount = objMonkeys.maxItems.values.sortedDescending().chunked(2)[0]
    val itemCountPart2 = objMonkeysPart2.maxItems.values.sortedDescending().chunked(2)[0]

    println(itemCount.let { it[0] * it [1] })
    println(itemCountPart2.let { it[0] * it [1] })

    
}

class Monkeys(val monkeys: List<Monkey>){
    val maxItems = mutableMapOf<Int, Long>()
    private val modulu by lazy {
        var modo = 1L
        monkeys.forEach {
            modo *= it.test
        }
        modo
    }
    fun playRound(part2: Boolean = false){
        monkeys.forEach {monkey ->
            val items = monkey.items.toMutableList()
            items.forEach {
                val worry = monkey.handleItem(it){newWorry ->
                    if(!part2) newWorry / 3L else newWorry % modulu
                }
                if(maxItems.containsKey(monkey.id)){
                    maxItems[monkey.id] = maxItems[monkey.id]!! + 1
                } else maxItems[monkey.id] = 1
                val newMonkey = if(worry % monkey.test == 0L){
                    monkeys.first { it.id == monkey.trueMonkey }
                } else {
                    monkeys.first { it.id == monkey.falseMonkey }
                }
                newMonkey.items.add(worry)
                monkey.items.removeFirst()
            }
        }
    }
}


data class Monkey(val id: Int, val items: MutableList<Long>, val operation: String, val test: Long, val trueMonkey: Int, val falseMonkey: Int){
    fun handleItem(item: Long, modolo: (Long) -> Long): Long{
        val operationList = operation.split(" ")
        val operator1 = if(operationList[0] == "old") item else operationList[0].toLong()
        val operator2 = if(operationList[2] == "old") item else operationList[2].toLong()
        val newWorry: Long = when(operationList[1]){
            "+"  -> operator1 + operator2
            "-" -> operator1 - (operator2)
            "*" -> operator1 * (operator2)
            else -> operator1 / (operator2)
        }
        return modolo(newWorry)
    }
}

class MonkeyBuilder(val id: Int, var items: MutableList<Long>? = null, var operation: String? = null, var test: Long? = null, var trueMonkey: Int? = null, var falseMonkey: Int? = null){
    fun build() = Monkey(id, items!!.toMutableList(), operation!!, test!!, trueMonkey!!, falseMonkey!!)
}