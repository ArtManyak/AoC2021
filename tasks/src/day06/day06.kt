package day06

import common.Common
import java.util.*

fun commonSolve(list: List<Long>, days: Int): Long {
    //0,1,2,3,4,5,6,7,8
    val groups = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0)
    for (x in list) {
        groups[x.toInt()]++
    }
    for (i in 0 until days) {
        groups[7] += groups[0]
        Collections.rotate(groups, -1)
    }
    return groups.sum()
}

fun main() {
    val numbers = Common.getAllText("tasks/src/day06/in.txt").split(',').map { it.toLong() }
    println(commonSolve(numbers, 80))
    println(commonSolve(numbers, 256))
}