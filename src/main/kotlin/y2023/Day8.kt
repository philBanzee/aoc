package y2023

import java.nio.file.Paths
import kotlin.io.path.readLines

class Day8 {
    private val input = Paths.get("src/main/resources/y2023/day8input").readLines()
    data class Node(val name: String, val left: String, val right: String)

    private val instructions = input[0].toList()

    private val nodes = input.subList(2, input.lastIndex).map { line ->
        val name = line.split(" = ")[0]

        val references = line.split(" = ")[1]
            .replace('(', ' ')
            .replace(')', ' ')
            .replace(',', ' ')
            .trim().split("  ")

        val left = references[0]
        val right = references[1]
        Node(name, left, right)
    }

    fun solveFirst(): Int {
        var current = nodes.find { it.name == "AAA" }!!
        var steps = 0
        var found = false

        while (!found) {
            instructions.forEach { direction ->
                when (direction) {
                    'L' -> current = nodes.find { it.name == current.left }!!
                    'R' -> current = nodes.find { it.name == current.right }!!
                }
                steps++
                if (current.name == "ZZZ") found = true
            }
        }

        return steps
    }
}

fun main() {
    println(Day8().solveFirst())
}