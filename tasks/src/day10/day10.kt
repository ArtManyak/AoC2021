package day10

import common.Common
import java.util.*

fun solve(lines: List<String>): Int {
    val costs = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val pairs = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    var ans = 0
    lines.forEach { line ->
        val stack = Stack<Char>()
        for (char in line) {
            when (char) {
                '(', '{', '[', '<' -> stack.push(char)
                else -> {
                    if (stack.peek() != pairs[char]) {
                        ans += costs[char]!!
                        break
                    } else stack.pop()
                }
            }
        }
    }
    return ans
}

fun solve2(lines: List<String>): Long {
    val costs = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    val pairs = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    val pairsReversed = pairs.entries.associate { (k, v) -> v to k }
    val answers = mutableListOf<Long>()
    lines.forEach { line ->
        val stack = Stack<Char>()
        var lineBroken = false
        for (char in line) {
            when (char) {
                '(', '{', '[', '<' -> stack.push(char)
                else -> {
                    if (stack.peek() != pairs[char]) {
                        lineBroken = true
                        break
                    } else stack.pop()
                }
            }
        }
        if (!lineBroken) {
            var ans = 0L
            while (!stack.empty()) {
                ans = ans * 5 + costs[pairsReversed[stack.pop()]]!!
            }
            answers.add(ans)
        }
    }
    answers.sort()
    return answers[answers.size / 2]
}

fun main() {
    val lines = Common.getLines("tasks/src/day10/in.txt")
    println(solve(lines))
    println(solve2(lines))
}