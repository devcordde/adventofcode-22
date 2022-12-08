package unbekannt

import java.io.File

fun main() {
    val input = File("input-day08.txt").readLines()
//    val input = listOf(
//        "30373",
//        "25512",
//        "65332",
//        "33549",
//        "35390"
//    )

    val matrix = Array(input.size) {
        IntArray(input.first().length)
    }

    input.forEachIndexed { index, values ->
        matrix[index] = values.map { it.digitToInt() }.toIntArray()
    }

    var count = 0
    matrix.forEachIndexed { indexRow, ints ->
        ints.forEachIndexed ints@{ index, int ->
            if(indexRow == 0 || indexRow == matrix.size-1){
                count++
                return@ints
            }
            if(index == 0 || index == ints.size-1){
                count++
                return@ints
            }
            var currentIndex = index -1
            var smaller = true
            while (currentIndex >= 0){
                smaller = ints[currentIndex] < int
                if (smaller) currentIndex--
                else currentIndex = -1
            }
            if(smaller) {
                count++
                return@ints
            }
            currentIndex = index + 1
            smaller = true
            while (currentIndex < ints.size){
                smaller = ints[currentIndex] < int
                if (smaller) currentIndex++
                else currentIndex = ints.size
            }
            if(smaller) {
                count++
                return@ints
            }
            currentIndex = indexRow - 1
            smaller = true
            while (currentIndex >= 0) {
                smaller = matrix[currentIndex][index] < int
                if (smaller) currentIndex--
                else currentIndex = -1
            }
            if(smaller) {
                count++
                return@ints
            }
            currentIndex = indexRow + 1
            smaller = true
            while (currentIndex < ints.size){
                smaller = matrix[currentIndex][index] < int
                if (smaller) currentIndex++
                else currentIndex = ints.size
            }
            if(smaller) {
                count++
                return@ints
            }
        }
    }
    println(count)

    val views = mutableMapOf<String, Long>()

    matrix.forEachIndexed { indexRow, ints ->
        ints.forEachIndexed ints@{ index, int ->
            if(indexRow == 0 || indexRow == matrix.size-1){
                views["$indexRow$index"]= 0
                return@ints
            }
            if(index == 0 || index == ints.size-1){
                views["$indexRow$index"]= 0
                return@ints
            }

            var currentIndex = index -1
            var counter = 0
            while (currentIndex >= 0){
                counter++
                if (ints[currentIndex] < int) currentIndex--
                else currentIndex = -1
            }
            var product = counter.toLong()
            currentIndex = index + 1
            counter = 0
            while (currentIndex < ints.size){
                counter++
                if (ints[currentIndex] < int) currentIndex++
                else currentIndex = ints.size
            }
            product *= counter
            currentIndex = indexRow + 1
            counter = 0
            while (currentIndex < ints.size){
                counter++
                if (matrix[currentIndex][index] < int) currentIndex++
                else currentIndex = ints.size
            }
            product *= counter
            currentIndex = indexRow - 1
            counter = 0
            while (currentIndex >= 0) {
                counter++
                if (matrix[currentIndex][index] < int) currentIndex--
                else currentIndex = -1
            }
            product *= counter
            views["$indexRow$index"]= product
        }
    }
    println(views.values.max())
}