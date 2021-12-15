package day15

import common.Common


fun solve(matrix: Array<IntArray>): Int {
    val solution = Array(matrix.size) { IntArray(matrix[0].size) }
    for (y in matrix.indices) {
        for (x in 0 until matrix[y].size) {
            //This value is the cost of this cell PLUS the min of both the existing left/up sums
            val left: Int? = if (x == 0) null else solution[y][x - 1]
            val top: Int? = if (y == 0) null else solution[y - 1][x]

            val thisSum = (listOfNotNull(left, top).minOrNull() ?: 0) + matrix[y][x]
            solution[y][x] = thisSum
        }
    }
    return solution.last().last()
}

fun main() {
    val lines = Common.getLines("tasks/src/day15/in.txt")
    val matrix = Array(lines.size * 5) { IntArray(lines[0].length * 5) { 0 } }
    for (row in lines.indices) {
        val rowLength = lines[row].length
        for (col in 0 until rowLength) {
            matrix[row][col] = lines[row][col] - '0'
            for (k in 1 .. 4) {
                matrix[row + k * lines.size][col] = (matrix[row + (k - 1) * lines.size][col] % 9) + 1
                for (l in 1 .. 4) {
                    matrix[row][col + l * rowLength] = (matrix[row][col + (l - 1) * rowLength] % 9) + 1
                    matrix[row + k * lines.size][col + l * rowLength] = (matrix[row + k * lines.size][col + (l - 1) * rowLength] % 9) + 1
                }
            }
        }
    }
    matrix[0][0] = 0
    println(solve(matrix))
}