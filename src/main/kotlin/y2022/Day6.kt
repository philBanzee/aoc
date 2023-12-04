package y2022

import java.nio.file.Paths

class Day6 {
    private val input = Paths.get("src/main/resources/y2022/day6input").toFile().readText()


    fun solveFirst(): Int {
        var firstPacket: Int = -1
        val windows = input.windowed(4)

        windows.forEachIndexed { index, window ->
            if (window.toList().distinct().size == 4 && firstPacket == -1) firstPacket = index
        }

        return firstPacket + 4
    }

    fun solveSecond(): Int {
        var firstPacket: Int = -1
        val windows = input.windowed(14)

        windows.forEachIndexed { index, window ->
            if (window.toList().distinct().size == 14 && firstPacket == -1) firstPacket = index
        }

        return firstPacket + 14
    }
}

fun main() {
    println("First Result: ${Day6().solveFirst()}")
    println("Second Result: ${Day6().solveSecond()}")
}