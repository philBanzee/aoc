package y2022

import java.io.File

fun readInput(): List<String> = File("src/main/resources/y2022/day1input").readLines()

fun summedCalories(): List<Int> {
    val results: MutableList<Int> = mutableListOf()

    readInput().fold(0) { acc, current ->
        if (current == "") {
            results.add(acc)
            0
        } else {
            acc + current.toInt()
        }
    }

    return results
}
fun solveFirst(): Int {
    return summedCalories().max()
}

fun solveSecond(): Int {
    return summedCalories().sortedDescending().take(3).fold(0) { acc, current -> acc + current}
}

fun main() {
    println("Result Task 1: ${solveFirst()}")

    println("Result Task 2: ${solveSecond()}")
}