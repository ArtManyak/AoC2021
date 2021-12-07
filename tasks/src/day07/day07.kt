package day07

import common.Common
import kotlin.math.abs

fun commonSolve(numbers: List<Int>, calcFuel: (x: Int, y: Int) -> Int): Int {
    return (numbers.minOrNull()!!..numbers.maxOrNull()!!).minOf { possiblePosition ->
        numbers.fold(0) { acc, curPosition ->
            acc + calcFuel(curPosition, possiblePosition)
        }
    }

}

fun main() {
    val numbers = Common.getAllText("tasks/src/day07/in.txt").split(",").map { it.toInt() }
    println(commonSolve(numbers) { x, y -> abs(x - y) })
    println(commonSolve(numbers) { x, y ->
        val an = abs(x - y)
        (1 + an) * an / 2
    })
}