package y2022

import java.io.File

enum class Option  {
    ROCK, PAPER, SCISSORS
}

enum class Result {
    WIN, DRAW, LOSE
}

class Day2 {
    private val rawInput = File("src/main/resources/y2022/day2input").readLines()

    private val oppOptions = mapOf('A' to Option.ROCK, 'B' to Option.PAPER, 'C' to Option.SCISSORS)

    private fun pointsForSelection(option: Option): Int {
        return when (option) {
            Option.ROCK -> 1
            Option.PAPER -> 2
            Option.SCISSORS -> 3
        }
    }

    private fun pointsForResult(result: Result): Int {
        return when (result) {
            Result.WIN -> 6
            Result.DRAW -> 3
            Result.LOSE -> 0
        }
    }

    private fun gameResult(pair: Pair<Option?, Option?>): Result {
        return when (pair) {
            Pair(Option.ROCK, Option.ROCK)            -> Result.DRAW
            Pair(Option.ROCK, Option.PAPER)           -> Result.WIN
            Pair(Option.ROCK, Option.SCISSORS)        -> Result.LOSE
            Pair(Option.PAPER, Option.ROCK)           -> Result.LOSE
            Pair(Option.PAPER, Option.PAPER)          -> Result.DRAW
            Pair(Option.PAPER, Option.SCISSORS)       -> Result.WIN
            Pair(Option.SCISSORS, Option.ROCK)        -> Result.WIN
            Pair(Option.SCISSORS, Option.PAPER)       -> Result.LOSE
            Pair(Option.SCISSORS, Option.SCISSORS)    -> Result.DRAW
            else -> Result.LOSE
        }
    }

    private fun requiredOption(oppOption: Option, desiredResult: Result): Option {
        return when (Pair(oppOption, desiredResult)) {
            Pair(Option.ROCK, Result.WIN)       -> Option.PAPER
            Pair(Option.ROCK, Result.DRAW)      -> Option.ROCK
            Pair(Option.ROCK, Result.LOSE)      -> Option.SCISSORS
            Pair(Option.PAPER, Result.WIN)      -> Option.SCISSORS
            Pair(Option.PAPER, Result.DRAW)     -> Option.PAPER
            Pair(Option.PAPER, Result.LOSE)     -> Option.ROCK
            Pair(Option.SCISSORS, Result.WIN)   -> Option.ROCK
            Pair(Option.SCISSORS, Result.DRAW)  -> Option.SCISSORS
            Pair(Option.SCISSORS, Result.LOSE)  -> Option.PAPER

            else -> error("Unhandled case: $oppOption, $desiredResult")
        }
    }

    fun solveFirst(): Int {
        val myOptions = mapOf('X' to Option.ROCK, 'Y' to Option.PAPER, 'Z' to Option.SCISSORS)
        return rawInput
            .map { Pair(oppOptions[it[0]], myOptions[it[2]]) }
            .fold(0) { acc, current ->
                acc + ( pointsForResult(gameResult(current)) + pointsForSelection(current.second!!) )
            }
    }

    fun solveSecond(): Int {
        val resultChars = mapOf('X' to Result.LOSE, 'Y' to Result.DRAW, 'Z' to Result.WIN)

        return rawInput
            .map { Pair(oppOptions[it[0]], resultChars[it[2]]) }
            .fold(0) { acc, current ->
                acc + ( pointsForResult(current.second!!) + pointsForSelection(requiredOption(current.first!!, current.second!!)) )
            }
    }
}

fun main() {
    println("First Result: ${Day2().solveFirst()}")
    println("Second Result: ${Day2().solveSecond()}")
}

