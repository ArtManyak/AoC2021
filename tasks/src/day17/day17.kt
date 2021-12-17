package day17

import common.Common
import kotlin.math.max

data class State(val x: Int, val y: Int, val dx: Int, val dy: Int)

fun solve(x1: Int, x2: Int, y1: Int, y2: Int): MutableList<List<State>> {
    val ans = mutableListOf<List<State>>()
    for (dx in 0..x2) {
        for (dy in y1..-2 * y2) {
            val allStates = generateSequence(State(0, 0, dx, dy)) {
                State(it.x + it.dx, it.y + it.dy, max(0, it.dx - 1), it.dy - 1)
            }
            val targetStates = allStates
                .takeWhile { it.x <= x2 && it.y >= y1 }
                .takeIf {
                    val last = it.last()
                    last.x in x1..x2 && last.y in y1..y2
                }
            if (targetStates?.any() == true) {
                ans.add(targetStates.toList())
            }
        }
    }
    return ans
}

fun main() {
    val parts = Common.getLines("tasks/src/day17/in.txt")[0].split(" ")
    val x1 = parts[2].substringAfter("=").takeWhile { it != '.' }.toInt()
    val x2 = parts[2].substringAfter("..").takeWhile { it != ',' }.toInt()
    val y1 = parts[3].substringAfter("=").takeWhile { it != '.' }.toInt()
    val y2 = parts[3].substringAfter("..").toInt()
    val solve = solve(x1, x2, y1, y2)
    println(solve.maxOf { it.maxOf { it.y } })
    println(solve.count())
}