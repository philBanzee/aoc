package y2023

import java.nio.file.Paths
import kotlin.io.path.readLines

class Day5 {
    data class Range(val sourceFrom: Long, val sourceTo: Long, val destFrom: Long, val destTo: Long)

    private val input = Paths.get("src/main/resources/y2023/day5input").readLines()
    private val seeds = input[0].split("seeds: ")[1].split(" ").map { it.toLong() }

    private val seeds2 = input[0].split("seeds: ")[1].split(" ").map { it.toLong() }.windowed(2, 2)

    private val seedToSoil = input.subList(input.indexOf("seed-to-soil map:") + 1, input.indexOf("soil-to-fertilizer map:") - 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private val soilToFertilizer = input.subList(input.indexOf("soil-to-fertilizer map:") + 1, input.indexOf("fertilizer-to-water map:") - 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private val fertilizerToWater = input.subList(input.indexOf("fertilizer-to-water map:") + 1, input.indexOf("water-to-light map:") - 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private val waterToLight = input.subList(input.indexOf("water-to-light map:") + 1, input.indexOf("light-to-temperature map:") - 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private val lightToTemperature = input.subList(input.indexOf("light-to-temperature map:") + 1, input.indexOf("temperature-to-humidity map:") - 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private val temperatureToHumidity = input.subList(input.indexOf("temperature-to-humidity map:") + 1, input.indexOf("humidity-to-location map:") - 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private val humidityToLocation = input.subList(input.indexOf("humidity-to-location map:") + 1, input.lastIndex + 1)
        .map { line ->
            val data = line.split(" ").map { it.toLong() }
            Range(data[1], data[1] + data[2] - 1, data[0], data[0] + data[2] - 1)
        }

    private fun seedToSoil(seed: Long): Long {
        return seedToSoil.find { (sourceFrom, sourceTo, _) ->
            seed in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + seed - it.sourceFrom
            } else {
                seed
            }
        }
    }

    private fun soilToFertilizer(soil: Long): Long {
        return soilToFertilizer.find { (sourceFrom, sourceTo, _) ->
            soil in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + soil - it.sourceFrom
            } else {
                soil
            }
        }
    }

    private fun fertilizerToWater(fertilizer: Long): Long {
        return fertilizerToWater.find { (sourceFrom, sourceTo, _) ->
            fertilizer in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + fertilizer - it.sourceFrom
            } else {
                fertilizer
            }
        }
    }

    private fun waterToLight(water: Long): Long {
        return waterToLight.find { (sourceFrom, sourceTo, _) ->
            water in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + water - it.sourceFrom
            } else {
                water
            }
        }
    }

    private fun lightToTemperature(light: Long): Long {
        return lightToTemperature.find { (sourceFrom, sourceTo, _) ->
            light in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + light - it.sourceFrom
            } else {
                light
            }
        }
    }

    private fun temperatureToHumidity(temperature: Long): Long {
        return temperatureToHumidity.find { (sourceFrom, sourceTo, _) ->
            temperature in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + temperature - it.sourceFrom
            } else {
                temperature
            }
        }
    }

    private fun humidityToLocation(humidity: Long): Long {
        return humidityToLocation.find { (sourceFrom, sourceTo, _) ->
            humidity in sourceFrom..sourceTo
        }.let {
            if (it != null) {
                it.destFrom + humidity - it.sourceFrom
            } else {
                humidity
            }
        }
    }

    private fun getLocation(seed: Long): Long {
        return humidityToLocation(
            temperatureToHumidity(
                lightToTemperature(
                    waterToLight(
                        fertilizerToWater(
                            soilToFertilizer(
                                seedToSoil(
                                    seed
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    fun solveFirst(): Long {
        return seeds.map { seed -> getLocation(seed) }.min()
    }

    fun solveSecond(): Long {
        return seeds2.asSequence().flatMap { seedRange ->
            seedRange[0]..<(seedRange[0] + seedRange[1])
        }.map { seed ->
            getLocation(seed)
        }.min()
    }
}

fun main() {
    println("First Result: ${Day5().solveFirst()}")
    println("Second Result: ${Day5().solveSecond()}")
}