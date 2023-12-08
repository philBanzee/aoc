package y2023

import java.nio.file.Paths
import kotlin.io.path.readLines

class Day7 {
    data class Hand(val cards: List<Char>, val bid: Int, val numberOfWins: Int)
    enum class HandType { FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard }
    private val cardStrengthPartOne: Map<Char, Int> = mapOf(
        'A' to 13, 'K' to 12, 'Q' to 11, 'J' to 10, 'T' to 9, '9' to 8, '8' to 7, '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1
    )

    private val handStrength: Map<HandType, Int> = mapOf(
        HandType.HighCard to 1, HandType.OnePair to 2, HandType.TwoPair to 3, HandType.ThreeOfAKind to 4,
        HandType.FullHouse to 5, HandType.FourOfAKind to 6, HandType.FiveOfAKind to 7
    )

    private val input = Paths.get("src/main/resources/y2023/day7input").readLines()

    private val hands = input.map { inputLine ->
        val elements = inputLine.split(" ")
        Hand(elements[0].toList(), elements[1].toInt(), 0)
    }

    private fun getHandTypePartOne(hand: Hand): HandType {
        val cardCounts = hand.cards.groupingBy { it }.eachCount()

        return when {
            cardCounts.size == 1 -> HandType.FiveOfAKind
            cardCounts.any { it.value == 4 } -> HandType.FourOfAKind
            cardCounts.any { it.value == 3 } && cardCounts.any { it.value == 2} -> HandType.FullHouse
            cardCounts.any { it.value == 3 } && cardCounts.size > 2 -> HandType.ThreeOfAKind
            cardCounts.filter { it.value == 2 }.size == 2 -> HandType.TwoPair
            cardCounts.filter { it.value == 2 }.size == 1 -> HandType.OnePair
            else -> HandType.HighCard
        }
    }

    private fun doesWin(hand: Hand, opponent: Hand): Boolean {
        val typeComparison = handStrength[getHandTypePartOne(hand)]!! - handStrength[getHandTypePartOne(opponent)]!!
        if (typeComparison > 0) return true
        if (typeComparison < 0) return false

        // Equal HandTypes, using TieBreaker
        for (i in hand.cards.indices) {
            val cardComparison = cardStrengthPartOne[hand.cards[i]]!! - cardStrengthPartOne[opponent.cards[i]]!!
            if (cardComparison > 0) return true
            if (cardComparison < 0) return false
        }

        error("Couldn't compare cards: ${hand.cards} vs ${opponent.cards}")
    }

    private fun doesWinPartTwo(hand: Hand, opponent: Hand): Boolean {
        val typeComparison = handStrength[getHandTypePartTwo(hand)]!! - handStrength[getHandTypePartTwo(opponent)]!!
        if (typeComparison > 0) return true
        if (typeComparison < 0) return false

        // Equal HandTypes, using TieBreaker
        for (i in hand.cards.indices) {
            val cardComparison = cardStrengthPartTwo[hand.cards[i]]!! - cardStrengthPartTwo[opponent.cards[i]]!!
            if (cardComparison > 0) return true
            if (cardComparison < 0) return false
        }

        error("Couldn't compare cards: ${hand.cards} vs ${opponent.cards}")
    }

    private val cardStrengthPartTwo: Map<Char, Int> = mapOf(
        'A' to 13, 'K' to 12, 'Q' to 11, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2, 'J' to 1
    )

    private fun getHandTypePartTwo(hand: Hand): HandType {
        val cardCounts = hand.cards.groupingBy { it }.eachCount()
        val joker = cardCounts['J'] ?: 0

        return when {
            cardCounts.size == 1 || cardCounts.any { it.value + joker == 5 && it.key != 'J' }
                -> HandType.FiveOfAKind

            cardCounts.any { it.value == 4 } || cardCounts.any { it.value + joker == 4 && it.key != 'J' }
                -> HandType.FourOfAKind

            cardCounts.any { it.value == 3 } && cardCounts.any { it.value == 2}
                    || (cardCounts.filter { it.key != 'J' }.size == 2 && joker >= 1)
                -> HandType.FullHouse

            cardCounts.any { it.value == 3 } || cardCounts.any { it.value + joker == 3 && it.key != 'J' }
                -> HandType.ThreeOfAKind

            cardCounts.filter { it.value == 2 }.size == 2
                -> HandType.TwoPair

            cardCounts.filter { it.value == 2 }.size == 1 || cardCounts.any { it.value + joker == 2 && it.key != 'J' }
                -> HandType.OnePair

            else -> HandType.HighCard
        }
    }


    fun solveFirst(): Int {
        return hands.mapIndexed { index, hand ->
            val numberOfWins = hands.mapIndexed { innerIndex, opponent ->
                if (innerIndex != index) {
                    val doesWin = doesWin(hand, opponent)
                    doesWin
                } else {
                    null
                }
            }.filter { it != null && it }.size
            Hand(hand.cards, hand.bid, numberOfWins)
        }.sortedBy { it.numberOfWins }.mapIndexed { index, hand ->
            hand.bid * (index + 1)
        }.sum()
    }

    fun solveSecond(): Int {
        return hands.mapIndexed { index, hand ->
            val numberOfWins = hands.mapIndexed { innerIndex, opponent ->
                if (innerIndex != index) {
                    val doesWin = doesWinPartTwo(hand, opponent)
                    doesWin
                } else {
                    null
                }
            }.filter { it != null && it }.size
            Hand(hand.cards, hand.bid, numberOfWins)
        }.sortedBy { it.numberOfWins }.mapIndexed { index, hand ->
            hand.bid * (index + 1)
        }.sum()
    }


}

fun main() {
    println(Day7().solveFirst())
    println(Day7().solveSecond())
}