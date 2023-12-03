package y2023

import java.nio.file.Paths
import kotlin.io.path.readLines

class Day3 {
    private val input = Paths.get("src/main/resources/y2023/day3input").readLines()

    private val symbols: List<List<Int>> =  input.map { line ->
        line.mapIndexed { index, char ->
            if (char != '.' && !char.isDigit()) index else null
        }.filterNotNull()
    }

    private fun symbolNeighbors(): List<List<Int>> {
        val final: MutableList<MutableList<Int>> = MutableList(symbols.size) { mutableListOf() }

        symbols.mapIndexed { rowNr, line ->
            line.forEach { index ->
                if (rowNr > 0) {
                    final[rowNr - 1].add(index - 1)
                    final[rowNr - 1].add(index)
                    final[rowNr - 1].add(index + 1)
                }
                final[rowNr].add(index - 1)
                final[rowNr].add(index + 1)
                if (rowNr < symbols.size) {
                    final[rowNr + 1].add(index - 1)
                    final[rowNr + 1].add(index)
                    final[rowNr + 1].add(index + 1)
                }
            }
        }
        return final
    }

    private fun digitsInWindows(): List<List<Int>> {
        return symbolNeighbors().mapIndexed { rowNr, row ->
            row.filter { index ->
                input[rowNr].toList()[index].isDigit()
            }.sorted()
        }
    }

    private fun allNumbers(): List<List<Pair<Int, Int>>> {
        return digitsInWindows().mapIndexed { rowNr, row ->
            row.map { currentIndex ->
                var start = currentIndex
                var end = currentIndex

                while (start >= 0 && input[rowNr][start].isDigit()) {
                    start -= 1
                }
                while (input[rowNr].length > end && input[rowNr][end].isDigit()) {
                    end += 1
                }

                var test = ""

                for (index in ((start + 1)..<end)) {
                    test += input[rowNr][index]
                }

                Pair(start + 1, test.toInt())
            }.distinct()
        }
    }

    fun solveFirst(): Int {
        return allNumbers().map { line ->
            line.fold(0) { acc, (_, current) -> acc + current }
        }.sum()
    }
    data class Field(
        val row: Int,
        val index: Int
    )

    data class Neighbor(
        val gear: Field,
        val position: Field
    )

    data class GearNumber(
        val startPosition: Field,
        val number: Int,
    )

    fun solveSecond(): Int {
        val gears = input.flatMapIndexed { rowNr, line ->
            line.mapIndexed { index, char ->
                if (char == '*') {
                    Field(rowNr, index)
                } else null
            }.filterNotNull()
        }

        val neighbors = gears.flatMap { (row, index) ->
            val fields: MutableList<Neighbor> = mutableListOf()

            if (row > 0) {
                if (input[row - 1][index - 1].isDigit()) fields.add(Neighbor(Field(row, index), Field(row - 1, index - 1)))
                if (input[row - 1][index].isDigit()) fields.add(Neighbor(Field(row, index), Field(row - 1, index)))
                if (input[row - 1][index + 1].isDigit()) fields.add(Neighbor(Field(row, index), Field(row - 1, index + 1)))
            }

            if (input[row][index - 1].isDigit()) fields.add(Neighbor(Field(row, index), Field(row, index - 1)))
            if (input[row][index + 1].isDigit()) fields.add(Neighbor(Field(row, index), Field(row, index + 1)))

            if (row < input.size - 1) {
                if (input[row + 1][index - 1].isDigit()) fields.add(Neighbor(Field(row, index), Field(row + 1, index - 1)))
                if (input[row + 1][index].isDigit()) fields.add(Neighbor(Field(row, index), Field(row + 1, index)))
                if (input[row + 1][index + 1].isDigit()) fields.add(Neighbor(Field(row, index), Field(row + 1, index + 1)))
            }

            fields
        }.groupBy( {it.gear}, {it.position})

        return neighbors.map { (gear, neighbors) ->
            val numbers = neighbors.map {(row, index) ->
                var start = index
                var end = index

                while (start >= 0 && input[row][start].isDigit()) {
                    start -= 1
                }
                while (input[row].length > end && input[row][end].isDigit()) {
                    end += 1
                }

                var test = ""

                for (pos in ((start + 1)..<end)) {
                    test += input[row][pos]
                }

                GearNumber(Field(row, start), test.toInt())
            }.distinct()

            Pair(gear, numbers)
        }.toMap()
            .filter { (_, numbers) -> numbers.size == 2 }
            .map { (_, numbers) ->
                numbers.fold(1) { acc, current -> acc * current.number }
            }
            .sum()
    }

}

fun main() {
    println("First Result: ${Day3().solveFirst()}")

    println("Second Result: ${Day3().solveSecond()}")
}