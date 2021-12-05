package day05

import common.Common
import kotlin.math.abs

data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

fun parseLines(line: String): Line {
    val (x1, y1, x2, y2) = line.split(",", " -> ").map { it.toInt() }
    return Line(x1, y1, x2, y2)
}

fun solve(): Int {
    val lines =
        Common.getLines("tasks/src/day05/in.txt").map { parseLines(it) }.filter { it.x1 == it.x2 || it.y1 == it.y2 }
    val maxByX = lines.maxByOrNull { if (it.x1 > it.x2) it.x1 else it.x2 }!!
    val maxX = if (maxByX.x1 > maxByX.x2) maxByX.x1 else maxByX.x2
    val maxByY = lines.maxByOrNull { if (it.y1 > it.y2) it.y1 else it.y2 }!!
    val maxY = if (maxByY.y1 > maxByY.y2) maxByY.y1 else maxByY.y2
    var res = 0
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            val containsLines = lines.count {
                it.x1 == x && it.y1 <= y && it.y2 >= y
                        || it.x1 == x && it.y2 <= y && it.y1 >= y
                        || it.y1 == y && it.x1 <= x && it.x2 >= x
                        || it.y1 == y && it.x2 <= x && it.x1 >= x
            }
            if (containsLines > 1) {
                res++
            }
        }
    }
    return res
}

fun solve2(): Int {
    val lines =
        Common.getLines("tasks/src/day05/in.txt").map { parseLines(it) }
            .filter { it.x1 == it.x2 || it.y1 == it.y2 || abs(it.x1 - it.x2) == abs(it.y1 - it.y2) }
    val maxByX = lines.maxByOrNull { if (it.x1 > it.x2) it.x1 else it.x2 }!!
    val maxX = if (maxByX.x1 > maxByX.x2) maxByX.x1 else maxByX.x2
    val maxByY = lines.maxByOrNull { if (it.y1 > it.y2) it.y1 else it.y2 }!!
    val maxY = if (maxByY.y1 > maxByY.y2) maxByY.y1 else maxByY.y2
    var res = 0
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            val containsLines = lines.count {
                it.x1 == x && it.x2 == x && it.y1 <= y && it.y2 >= y
                        || it.x1 == x && it.x2 == x && it.y2 <= y && it.y1 >= y
                        || it.y1 == y && it.y2 == y && it.x1 <= x && it.x2 >= x
                        || it.y1 == y && it.y2 == y && it.x2 <= x && it.x1 >= x
                        || (abs(it.x1 - x) == abs(it.y1 - y)
                            && abs(it.x2 - x) == abs(it.y2 - y)
                            && (it.x1 <= x && it.x2 >= x || it.x1 >= x && it.x2 <= x)
                            && (it.y1 <= y && it.y2 >= y || it.y1 >= y && it.y2 <= y))
            }
            if (containsLines > 1) {
                res++
            }
        }
    }
    return res
}

fun main() {
    println(solve())
    println(solve2())
}