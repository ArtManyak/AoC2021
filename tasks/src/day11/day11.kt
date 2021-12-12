package day11

import common.Common
import java.util.*

fun makeStep(matrix: MutableList<MutableList<Int>>): Int {
    val flashed = mutableListOf<Pair<Int, Int>>()
    val flashedStack = Stack<Pair<Int, Int>>()
    for (row in 0 until matrix.size) {
        for (col in 0 until matrix[row].size) {
            matrix[row][col] += 1
            if (matrix[row][col] > 9) {
                val pair = Pair(row, col)
                flashed.add(pair)
                flashedStack.push(pair)
            }
        }
    }
    while (flashedStack.isNotEmpty()) {
        val cur = flashedStack.pop()
        for (dx in listOf(-1, 0, 1)) {
            for (dy in listOf(-1, 0, 1)) {
                if (dx == 0 && dy == 0) continue
                val neighbor = matrix.getOrNull(cur.first + dy)?.getOrNull(cur.second + dx)
                if (neighbor != null && neighbor <= 9) {
                    matrix[cur.first + dy][cur.second + dx] += 1
                    if (matrix[cur.first + dy][cur.second + dx] > 9) {
                        val pair = Pair(cur.first + dy, cur.second + dx)
                        flashed.add(pair)
                        flashedStack.push(pair)
                    }
                }
            }
        }
    }
    for (cur in flashed) {
        matrix[cur.first][cur.second] = 0
    }
    return flashed.size
}

fun solve(matrix: MutableList<MutableList<Int>>): Int {
    var ans = 0
    for (step in 1..100) {
        ans += makeStep(matrix)
    }
    return ans
}

fun solve2(matrix: MutableList<MutableList<Int>>): Int {
    var stepCount = 0
    while (true) {
        stepCount++
        makeStep(matrix)
        if (matrix.all { row -> row.all { col -> col == 0 } })
            return stepCount
    }
}

fun main() {
    val lines = Common.getLines("tasks/src/day11/in.txt")
    val matrix = mutableListOf<MutableList<Int>>()
    lines.forEach {
        matrix.add(it.map { it - '0' }.toMutableList())
    }
    val matrix2 = matrix.map { it.toMutableList() }.toMutableList()
    println(solve(matrix))
    println(solve2(matrix2))
}