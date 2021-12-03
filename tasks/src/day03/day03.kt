package day03

import common.Common

fun solve(): Int {
    val lines = Common.getLines("tasks/src/day03/in.txt")
    var gamma = ""
    var epsilon = ""
    for (i in 0 until lines[0].length) {
        val mostCommon = lines.map { it[i] }.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key
        gamma += mostCommon
        epsilon += if (mostCommon == '1') "0" else "1"
    }
    return gamma.toInt(2) * epsilon.toInt(2)
}

fun solve2(): Int {
    var oxygen = Common.getLines("tasks/src/day03/in.txt").toMutableList()
    var co2 = oxygen.toMutableList()

    fun filter(arr: MutableList<String>, i: Int, common: Boolean): MutableList<String> {
        if (arr.size == 1) return arr
        val eachCount = arr.map { it[i] }.groupingBy { it }.eachCount()
        val filter = if (eachCount['1']!! >= eachCount['0']!!) if (common) '1' else '0' else if (common) '0' else '1'
        return arr.filter { it[i] == filter }.toMutableList()
    }

    for (i in 0 until oxygen[0].length) {
        oxygen = filter(oxygen, i, true)
        co2 = filter(co2, i, false)
    }

    return oxygen[0].toInt(2) * co2[0].toInt(2)
}

fun main() {
    println(solve())
    println(solve2())
}