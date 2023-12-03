package y2022

import java.nio.file.Paths

class Day3 {
    private val letters: List<Char> = ('a'..'z').toList() + ('A'..'Z').toList()
    private val numbers: List<Int> = (1..52).toList()
    private val priorities = letters.associateWith { letter -> numbers[letters.indexOf(letter)] }

    private val input = Paths.get("src/main/resources/y2022/day3input").toFile().readLines()

    fun solveFirst(): Int {
        return input.flatMap { line ->
            val compartments = Pair(line.substring(0, line.length / 2).toSet(), line.substring(line.length / 2).toSet())
            compartments.first.intersect(compartments.second).map { priorities[it] }
        }.filterNotNull().sum()
    }

    fun solveSecond(): Int {
        return input.chunked(3).flatMap {group ->
            group[0].toList().intersect(group[1].toSet()).intersect(group[2].toSet()).map { priorities[it] }
        }.filterNotNull().sum()
    }
}

fun main() {
    println("First Result: ${Day3().solveFirst()}")
    println("Second Result: ${Day3().solveSecond()}")
}