package y2022

import java.nio.file.Paths

class Day5 {
    data class Rearrangement(val amount: Int, val from: Int, val to: Int)

    private val input = Paths.get("src/main/resources/y2022/day5input").toFile().readLines()
    private val stackInput = input.subList(0, input.indexOf("") - 1)
    private val procedureInput = input.subList(input.indexOf("") + 1, input.size)

    private val stacks = stackInput.fold(MutableList<MutableList<String>>(stackInput[0].chunked(4).size) { mutableListOf() }) { acc, current ->
        current.chunked(4).mapIndexed { index, crate ->
            if (crate.isNotBlank()) acc[index].addFirst(crate)
        }
        acc
    }

    private val procedure = procedureInput.map { step ->
        val rearrangement = step.split(" ").filter { it != "move" && it != "from" && it != "to" }.map(String::toInt)
        Rearrangement(rearrangement[0], rearrangement[1] - 1, rearrangement[2] - 1)
    }

    private fun moveFirst(rearrangement: Rearrangement) {
        for (x in 1..rearrangement.amount) {
            stacks[rearrangement.to].addLast(
                stacks[rearrangement.from].removeLast()
            )
        }
    }

    private fun moveSecond(rearrangement: Rearrangement) {
        stacks[rearrangement.to].addAll(
            stacks[rearrangement.from].drop(stacks[rearrangement.from].size - rearrangement.amount)
        )

        stacks[rearrangement.from] = stacks[rearrangement.from].dropLast(rearrangement.amount).toMutableList()
    }

    fun solveFirst() {
        procedure.forEach { moveFirst(it) }
        stacks.forEach { stack -> print(stack.last().trim().filter { it != '[' && it != ']' }) }
    }

    fun solveSecond() {
        procedure.forEach { moveSecond(it) }
        stacks.forEach { stack -> print(stack.last().trim().filter { it != '[' && it != ']' }) }
    }
}

fun main() {
    Day5().solveFirst()
    println()
    Day5().solveSecond()
}