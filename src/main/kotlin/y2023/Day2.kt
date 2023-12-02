package y2023

import java.nio.file.Paths

class Day2 {
    private val input = Paths.get("src/main/resources/y2023/day2input").toFile().readLines()

    fun solveFirst(): Int {
        val games = input.mapIndexed { index, line ->
            index + 1 to line.substringAfter(": ").split("; ").map { draw ->
                draw.split(", ").map { cube ->
                    cube.split(" ").first().toInt() to cube.split(" ").last()
                }
            }
        }.toMap()

        val possibleGames: MutableList<Int> = mutableListOf()

        for (game in games) {
            val rounds = game.value

            val valid = rounds.all { draw ->
                draw.all { cube ->
                    when (cube.second) {
                        "red"   -> cube.first <= 12
                        "green" -> cube.first <= 13
                        "blue"  -> cube.first <= 14
                        else -> error("Unexpected cube color: ${cube.second}")
                    }
                }
            }

            if (valid) possibleGames.add(game.key)
        }

        return possibleGames.sum()
    }

    fun solveSecond(): Int {
        val games = input.map { line ->
            line.substringAfter(": ").split("; ").flatMap { draw ->
                draw.split(", ").map { cube ->
                    cube.split(" ").first().toInt() to cube.split(" ").last()
                }
            }
        }

        return games.sumOf { game ->
            val maxRed = game.filter { it.second == "red" }.maxOf { it.first }
            val maxGreen = game.filter { it.second == "green" }.maxOf { it.first }
            val maxBlue = game.filter { it.second == "blue" }.maxOf { it.first }

            maxRed * maxGreen * maxBlue
        }
    }
}

fun main() {
    println("First Result: ${Day2().solveFirst()}")
    println("Second Result: ${Day2().solveSecond()}")
}