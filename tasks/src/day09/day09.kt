package day09

import common.Common

fun solve(lines: List<String>): Int {
    var ans = 0
    for (i in lines.indices) {
        for (j in 0 until lines[i].length) {
            val neighbors = listOfNotNull(
                lines[i].getOrNull(j - 1),
                lines[i].getOrNull(j + 1),
                lines.getOrNull(i - 1)?.get(j),
                lines.getOrNull(i + 1)?.get(j),
            )
            if (lines[i][j] < neighbors.minOrNull()!!) ans += lines[i][j] - '0' + 1
        }
    }
    return ans
}

fun solve2(lines: List<String>): Int {
    val basins = mutableListOf<Int>()
    val used = Array(lines.size) { BooleanArray(lines[0].length) }
    fun search(i: Int, j: Int): Int {
        if (i < 0 || i >= lines.size || j < 0 || j >= lines[0].length || used[i][j] || lines[i][j] == '9') return 0
        used[i][j] = true
        return 1 + search(i + 1, j) + search(i - 1, j) + search(i, j + 1) + search(i, j - 1)
    }
    for (i in lines.indices) {
        for (j in 0 until lines[0].length) {
            if (!used[i][j] && lines[i][j] != '9') {
                basins += search(i, j)
            }
        }
    }
    basins.sortDescending()
    return basins[0] * basins[1] * basins[2]
}

fun main() {
    val lines = Common.getLines("tasks/src/day09/in.txt")
    println(solve(lines))
    println(solve2(lines))
}