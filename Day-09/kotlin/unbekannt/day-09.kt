package unbekannt

import java.io.File

fun main(){
    val visited = mutableListOf<Pos>()
    val input = File("input-day09.txt").readLines()

    var hPos = Pos(0,0)
    var tPos = Pos(0,0)
    visited.add(tPos)

    val betterInput = input.map { it.split(" ").map { it.trim() } }.map { Pair(it.first(), it.last().toInt()) }

    betterInput.forEach {
        var i = 0
        while (i < it.second){
            hPos = hPos.move(it.first)

            val safeZone = hPos.inSafeZone(tPos)
            if(!safeZone){
                val div = hPos.div(tPos)
                val rowDiv = div.first
                val colDiv = div.second
                if( rowDiv != 0)
                    tPos = if(rowDiv < 0){
                    tPos.move("U")
                } else {
                    tPos.move("D")
                }

                if(colDiv != 0)
                tPos = if(colDiv < 0){
                    tPos.move("R")
                }else {
                    tPos.move("L")
                }


                if(!visited.contains(tPos)){
                    visited.add(tPos)
                }
            }
            i++
        }
    }
    println(visited.size)

        val visitedPart2 = mutableListOf<Pos>()
        val rope = Rope(
            mutableListOf(
                RopeKnot(Pos(0,0), -1),
                RopeKnot(Pos(0,0), 0),
                RopeKnot(Pos(0,0), 1),
                RopeKnot(Pos(0,0), 2),
                RopeKnot(Pos(0,0), 3),
                RopeKnot(Pos(0,0), 4),
                RopeKnot(Pos(0,0), 5),
                RopeKnot(Pos(0,0), 6),
                RopeKnot(Pos(0,0), 7),
                RopeKnot(Pos(0,0), 8),
            )
        )

        betterInput.forEach {
        var i = 0
        while (i < it.second){
            rope.moveHead(it.first)
            if(!visitedPart2.contains(rope.getLastPos())){
                visitedPart2.add(rope.getLastPos())
            }
            i++
        }
    }
    println(visitedPart2.size)

}

class Rope(val knots: MutableList<RopeKnot>){

    init {
        knots.sortBy { it.parent }
    }
    fun moveHead(direction: String) {
        knots.first().move(direction)
        updateKnots()
    }
    fun getLastPos() = knots.last().pos

    private fun updateKnots(){
        knots.forEach {
            val pos = knots.getOrNull(it.parent) ?: return@forEach
            it.updateBasedOnNewPos(pos.pos)
        }
    }
}

class RopeKnot(var pos: Pos, val parent: Int) {
    fun move(direction: String) {
        pos = pos.move(direction)
    }

    fun updateBasedOnNewPos(newPos: Pos){
        val safeZone = newPos.inSafeZone(pos)
        if(!safeZone) {
            val div = newPos.div(pos)
            val rowDiv = div.first
            val colDiv = div.second
            if (rowDiv != 0)
                pos = if (rowDiv < 0) {
                    pos.move("U")
                } else {
                    pos.move("D")
                }

            if (colDiv != 0)
                pos = if (colDiv < 0) {
                    pos.move("R")
                } else {
                    pos.move("L")
                }
        }
    }
}

data class Pos(val row: Int, val col:Int){

    fun move(direction: String): Pos{
        return when(direction){
            "R" -> Pos(row, col+1)
            "L" -> Pos(row, col-1)
            "U" -> Pos(row+1, col)
            else -> Pos(row-1, col)
        }
    }
    fun div(other: Pos): Pair<Int, Int> {
        val rowDiv = (other.row - row)
        val colDiv = (other.col - col)
        return rowDiv to colDiv
    }

    fun inSafeZone(other: Pos): Boolean {
        val up = other.row == row+1 && other.col == col
        val down = other.row == row-1 && other.col == col
        val right = other.col == col+1 && other.row == row
        val left = other.col == col-1 && other.row == row
        val topright = other.row == row+1 && other.col == col+1
        val topleft = other.row == row+1 && other.col == col-1
        val bottomRight = other.row == row-1 && other.col == col+1
        val bottomLeft = other.row == row-1 && other.col == col-1
        val ontop = other.row == row && other.col == col
        return topleft || topright || bottomRight || bottomLeft || up || down || left || right || ontop
    }
}