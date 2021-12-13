package day13

import common.Common

data class Dot(val x: Int, val y: Int)

enum class FoldDirection {
    X, Y
}
data class Fold(val direction: FoldDirection, val position: Int)

fun solve(dots: List<Dot>, fold: Fold): MutableList<Dot> {
    val dotsCopy = mutableListOf<Dot>()
    dots.forEach { dot ->
        when (fold.direction) {
            FoldDirection.X -> {
                if (fold.position > dot.x)
                    dotsCopy.add(Dot(dot.x, dot.y))
                else
                    dotsCopy.add(Dot(fold.position - (dot.x - fold.position), dot.y))
            }
            FoldDirection.Y -> {
                if (fold.position > dot.y)
                    dotsCopy.add(Dot(dot.x, dot.y))
                else
                    dotsCopy.add(Dot(dot.x, fold.position - (dot.y - fold.position)))
            }
        }
    }
    return dotsCopy.distinct().toMutableList()
}

fun solve2(dots: List<Dot>, folds: List<Fold>): Int {
    var dotsCopy = dots.toMutableList()
    folds.forEach { fold ->
        dotsCopy = solve(dotsCopy, fold)
    }
    val maxx = dotsCopy.maxByOrNull { it.x }!!.x
    val maxy = dotsCopy.maxByOrNull { it.y }!!.y
    val matrix = Array(maxy+1) {CharArray(maxx+1) {' '}}
    dotsCopy.forEach { dot ->
        matrix[dot.y][dot.x] = '#'
    }
    for (y in 0..maxy) {
        val line = matrix[y].joinToString(" ")
        println(line)
    }
    return 0
}

fun main() {
    val text = Common.getAllText("tasks/src/day13/in.txt")
    val (dots, folds) = text.split("\n\n")
    val dotsList = dots.split("\n").map {
        val (x, y) = it.split(",").map { it.toInt() }
        Dot(x, y)
    }
    val foldsList = folds.split("\n").map {
        val fold = it.split(" ").last()
        val (dir, pos) = fold.split('=')
        if (dir == "y") Fold(FoldDirection.Y, pos.toInt()) else Fold(FoldDirection.X, pos.toInt())
    }
    println(solve(dotsList, foldsList.first()).size)
    println(solve2(dotsList, foldsList))
}