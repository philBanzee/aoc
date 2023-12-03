package y2022

import java.nio.file.Paths

class Day4 {
    private val input = Paths.get("src/main/resources/y2022/day4input").toFile().readLines()

    private fun parseToIntLists() = input.map { line ->
        line.split(",").map { range ->
            val rangeList = range.split("-").map { it.toInt() }
            IntRange(rangeList.first(), rangeList.last()).toList()
        }
    }

    fun solveFirst(): Int {
        return parseToIntLists().count { ranges ->
            ranges.first().containsAll(ranges.last()) || ranges.last().containsAll(ranges.first())
        }
    }

    fun solveSecond(): Int {
        return parseToIntLists().count() { ranges ->
            ranges.first().any { it in ranges.last() } || ranges.last().any { it in ranges.first() }
        }
    }
}

fun main() {
    println("First result: ${Day4().solveFirst()}")
    println("Second result: ${Day4().solveSecond()}")
}