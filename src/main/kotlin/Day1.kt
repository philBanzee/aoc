import java.io.File

fun solveFirst(): Int {
    val input = File("src/main/resources/day1input").readLines()

    return input.fold(0) { sum, current ->
        val first = current.find { it.isDigit() }
        val last = current.findLast { it.isDigit() }
        sum + "$first$last".toInt()
    }
}

fun solveSecond(): Int {
    val input = File("src/main/resources/day1input").readLines()

    val validDigits = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

    return input.fold(0) { sum, current ->
        val first = current.findAnyOf(validDigits.keys + validDigits.values.map { it.toString() })
            ?.let {(_, value) -> validDigits.getOrDefault(value, value).toString() }
        val last = current.findLastAnyOf(validDigits.keys + validDigits.values.map { it.toString() })
           ?.let {(_, value) -> validDigits.getOrDefault(value, value).toString() }

        sum + "$first$last".toInt()
    }
}

fun main() {
    println("Result Task 1: ${solveFirst()}")

    println("Result Task 2: ${solveSecond()}")
}