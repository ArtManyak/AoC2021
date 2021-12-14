package day14

import common.Common

fun commonSolve(template: String, rules: Map<String, String>, count: Int): Long {
    var result = mutableMapOf<String, Long>()
    for (i in 0 until template.length - 1) {
        val c = "${template[i]}${template[i + 1]}"
        result[c] = (result[c] ?: 0) + 1
    }
    for (x in 0 until count) {
        val nexResult = mutableMapOf<String, Long>()
        for ((key, value) in result) {
            val rule = rules[key]
            val x1 = "${key[0]}$rule"
            val x2 = "$rule${key[1]}"
            nexResult[x1] = (nexResult[x1] ?: 0) + value
            nexResult[x2] = (nexResult[x2] ?: 0) + value
        }
        result = nexResult
    }

    val byLetters = mutableMapOf<Char, Long>()
    for ((key, value) in result) {
        byLetters[key[0]] = (byLetters[key[0]] ?: 0) + value
        byLetters[key[1]] = (byLetters[key[1]] ?: 0) + value
    }
    byLetters[template.first()] = byLetters[template.first()]!! + 1
    byLetters[template.last()] = byLetters[template.last()]!! + 1

    val ans = byLetters.toList().sortedByDescending { (_, v) -> v }
    return (ans.first().second - ans.last().second) / 2L
}

fun main() {
    val lines = Common.getLines("tasks/src/day14/in.txt")
    val template = lines[0]
    val rules = lines.drop(2).associate { line ->
        val (from, to) = line.split(" -> ")
        from to to
    }
    println(commonSolve(template, rules, 10))
    println(commonSolve(template, rules, 40))
}