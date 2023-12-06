package y2023

import java.nio.file.Paths
import kotlin.io.path.readLines

class Day6 {
    private val input = Paths.get("src/main/resources/y2023/day6input").readLines()

    private val durations = input[0].split(" ").drop(1).filter { it.isNotBlank() }.map { it.trim().toInt() }
    private val records = input[1].split(" ").drop(1).filter { it.isNotBlank() }.map { it.trim().toInt() }

    private val races = durations.associateWith { duration -> records[durations.indexOf(duration)] }

    fun solveFirst(): Int {
        return races.map { (duration, record) ->
            val winning: MutableList<Int> = mutableListOf()
            for (time in 0..duration) {
                if (time * (duration - time) > record) winning.add(time * (duration - time))
            }
            winning.size
        }.fold(1) { acc, current -> acc * current }
    }

    fun solveSecond(): Int {
        val newDuration = durations.fold("") { acc, current -> "$acc$current" }.toLong()
        val newRecord = records.fold("") { acc, current -> "$acc$current" }.toLong()

        val winning: MutableList<Long> = mutableListOf()

        for (time in 0..newDuration) {
            if (time * (newDuration - time) > newRecord) winning.add(time * (newDuration - time))
        }

        return winning.size
    }
}

fun main() {
    println(Day6().solveFirst())
    println(Day6().solveSecond())

}