package day08

import common.Common

fun solve(lines: List<String>): Int {
    return lines.fold(0) { acc, line ->
        val (_, second) = line.split(" | ")
        acc + second
            .split(" ")
            .filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
            .size
    }
}

fun solve2(lines: List<String>): Int {
    return lines.fold(0) { acc, line ->
        val (first, second) = line.split(" | ")
        val firstParts = first.split(" ").map { it.toHashSet() }
        val dict = mutableMapOf<Char, Int>()
        firstParts.forEach { firstPart ->
            firstPart.forEach { letter ->
                dict[letter] = (dict[letter] ?: 0) + 1
            }
        }
        val top = firstParts.first { it.size == 3 }.subtract(firstParts.first { it.size == 2 }).first()
        val middle = firstParts.first { it.size == 4 }.first { dict[it] == 7 }
        dict.remove(top)
        dict.remove(middle)
        val reversedDict = dict.entries.associate { (k, v) -> v to k }
        val secondParts = second.split(" ")
        acc + secondParts.map {
            when (it.length) {
                2 -> 1
                4 -> 4
                3 -> 7
                7 -> 8
                6 -> if (!it.contains(reversedDict[8]!!)) 6 else if (!it.contains(reversedDict[4]!!)) 9 else 0
                5 -> if (!it.contains(reversedDict[8]!!)) 5 else if (!it.contains(reversedDict[4]!!)) 3 else 2
                else -> throw Error()
            }
        }.joinToString("").toInt()
    }
}

fun main() {
    val lines = Common.getLines("tasks/src/day08/in.txt")
    println(solve(lines))
    println(solve2(lines))
}