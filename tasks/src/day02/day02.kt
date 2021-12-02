package day02

import common.Common

fun solve(): Int {
    val lines = Common.getLines("tasks/src/day02/in.txt")
    var x = 0
    var y = 0
    for (line in lines) {
        val (direction, count) = line.split(" ")
        when (direction) {
            "forward" -> x += count.toInt()
            "up" -> y -= count.toInt()
            "down" -> y += count.toInt()
        }
    }
    return x * y
}

fun solve2(): Int {
    val lines = Common.getLines("tasks/src/day02/in.txt")
    var x = 0
    var y = 0
    var aim = 0
    for (line in lines) {
        val (direction, count) = line.split(" ")
        when (direction) {
            "forward" -> {
                x += count.toInt()
                y += aim * count.toInt()
            }
            "up" -> aim -= count.toInt()
            "down" -> aim += count.toInt()
        }
    }
    return x * y
}

fun main() {
    println(solve())
    println(solve2())
}