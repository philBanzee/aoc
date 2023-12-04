package y2023

import java.nio.file.Paths
import kotlin.io.path.readLines

class Day4 {
    private val input = Paths.get("src/main/resources/y2023/day4input").readLines()

    data class CardGame (
        val amount: Int,
        val drawn: List<Int>,
        val guessed: List<Int>
    )

    private val cardInput = input.map { it.split(": ")[1] }
    private val section = cardInput.map { it.split(" | ") }

    private val cards = section.map {
        CardGame(
            1,
            it[0].chunked(3).map { number -> number.trim().toInt() },
            it[1].chunked(3).map { number -> number.trim().toInt() }
        )
    }.toMutableList()

    fun solveFirst(): Int {
        return cards.map { (_, drawn, guessed) ->
            val correct = guessed.filter { drawn.contains(it) }
            correct.fold(0) { acc, _ ->
                if (acc == 0) 1 else acc * 2
            }
        }.sum()
    }

    fun solveSecond(): Int {
        cards.forEachIndexed { index, (amount, drawn, guessed) ->
            val correct = guessed.filter { drawn.contains(it) }
            for (x in (index + 1)..index + correct.size) {
                val old = cards[x]
                cards[x] = CardGame(old.amount + amount, old.drawn, old.guessed)
            }
        }

        return cards.fold(0) { acc, (amount, _) -> acc + amount }
    }
}

fun main() {
    println("First Result: ${Day4().solveFirst()}")
    println("Second Result: ${Day4().solveSecond()}")
}