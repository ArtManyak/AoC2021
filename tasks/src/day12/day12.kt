package day12

import common.Common

class Cave(val name: String) {
    val siblings = mutableListOf<Cave>()
    fun isSmall() = name.all { it.isLowerCase() }
    fun addSibling(cave: Cave) = siblings.add(cave)
}

fun dfs1(path: MutableList<Cave>, current: Cave, end: Cave): Int {
    if (current == end) return 1
    var sum = 0
    for (sibling in current.siblings) {
        if (sibling.isSmall() && path.contains(sibling)) continue
        val copy = path.toMutableList()
        copy.add(sibling)
        sum += dfs1(copy, sibling, end)
    }
    return sum
}

fun dfs2(path: MutableList<Cave>, current: Cave, start: Cave, end: Cave): Int {
    if (current == end) return 1
    var sum = 0
    val chosenSmallCave =
        path.filter { it.isSmall() }.groupBy { it.name }.entries.firstOrNull { it.value.size > 1 }?.value?.first()
    for (sibling in current.siblings) {
        if (sibling == start) continue
        if ((chosenSmallCave != null && sibling == chosenSmallCave && path.count { it == sibling } == 2)
            || (chosenSmallCave != null && sibling != chosenSmallCave && sibling.isSmall() && path.contains(sibling)))
            continue
        val copy = path.toMutableList()
        copy.add(sibling)
        sum += dfs2(copy, sibling, start, end)
    }
    return sum
}

fun main() {
    val g = mutableListOf<Cave>()
    val lines = Common.getLines("tasks/src/day12/in.txt")
    lines.forEach {
        val (name1, name2) = it.split("-")
        val cave1 = g.firstOrNull { it.name == name1 } ?: Cave(name1)
        val cave2 = g.firstOrNull { it.name == name2 } ?: Cave(name2)
        cave1.addSibling(cave2)
        cave2.addSibling(cave1)
        if (!g.contains(cave1)) g.add(cave1)
        if (!g.contains(cave2)) g.add(cave2)
    }

    val start = g.first { it.name == "start" }
    val end = g.first { it.name == "end" }

    println(dfs1(mutableListOf(start), start, end))
    println(dfs2(mutableListOf(start), start, start, end))
}