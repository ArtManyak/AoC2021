package day04

import common.Common

class Board {
    val rows = mutableListOf<MutableSet<Int>>()
    val columns = mutableListOf<MutableSet<Int>>()

    fun removeNumber(number: Int) {
        rows.forEach { it.remove(number) }
        columns.forEach { it.remove(number) }
    }

    fun isWon() = rows.any { it.size == 0 } || columns.any { it.size == 0 }
    fun calcResult() = rows.sumOf { it.sum() }
}

fun getNumbersAndBoards(): Pair<List<Int>, MutableList<Board>> {
    val lines = Common.getLines("tasks/src/day04/in.txt")
    val numbers = lines[0].split(",").map { it.toInt() }
    val boards = mutableListOf<Board>()
    var board = Board()
    for (line in lines.drop(2)) {
        if (line == "") {
            boards.add(board)
            board = Board()
            continue
        }
        val row = line.split(" ").filter { it != "" }.map { it.toInt() }.toMutableSet()
        board.rows.add(row)
        row.forEachIndexed { i, num ->
            if (board.columns.size <= i) board.columns.add(mutableSetOf())
            board.columns[i].add(num)
        }
    }
    return Pair(numbers, boards)
}

fun solve(): Int {
    val (numbers, boards) = getNumbersAndBoards()
    for (number in numbers) {
        for (board in boards) {
            board.removeNumber(number)
            if (board.isWon()) {
                return board.calcResult() * number
            }
        }
    }
    return 0
}

fun solve2(): Int {
    val (numbers, boards) = getNumbersAndBoards()
    val wonBoards = mutableSetOf<Board>()
    for (number in numbers) {
        for (board in boards) {
            if (board in wonBoards) continue
            board.removeNumber(number)
            if (board.isWon()) {
                wonBoards.add(board)
                if (wonBoards.size == boards.size)
                    return board.calcResult() * number
            }
        }
    }
    return 0
}

fun main() {
    println(solve())
    println(solve2())
}