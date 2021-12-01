package day01

import common.Common

fun solve(): Int {
    val numbers = Common.getNumbers("tasks/src/day01/in.txt")
    var ans = 0
    for (i in 1 until numbers.size) {
        if (numbers[i] > numbers[i - 1]) ans++
    }
    return ans
}

fun solve2(): Int {
    val numbers = Common.getNumbers("tasks/src/day01/in.txt")
    var ans = 0
    for (i in 1 until numbers.size - 2) {
        val sum1 = numbers[i - 1] + numbers[i] + numbers[i + 1]
        val sum2 = sum1 - numbers[i - 1] + numbers[i + 2]
        if (sum1 < sum2) ans++
    }
    return ans
}

fun main() {
    println(solve())
    println(solve2())
}