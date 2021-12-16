package day16

import common.Common

class Packet(val version: Int, val typeId: Int) {
    val packets = mutableListOf<Packet>()
    var value: Long? = null
    var binLength: Int = 0
}

fun parsePackets(binLine: String, packetCount: Int? = null): MutableList<Packet> {
    val packets = mutableListOf<Packet>()
    var start = 0
    while (start + 10 < binLine.length && packets.size < (packetCount ?: Int.MAX_VALUE)) {
        val version = binLine.substring(start..start + 2).toInt(2)
        val typeId = binLine.substring(start + 3..start + 5).toInt(2)
        val packet = Packet(version, typeId)
        packet.binLength += 6
        when (packet.typeId) {
            4 -> {
                var keepReading = true
                val sb = StringBuilder()
                var pos = start + 6
                while (keepReading) {
                    if (binLine[pos] == '0') keepReading = false
                    sb.append(binLine.substring(pos + 1..pos + 4))
                    pos += 5
                    packet.binLength += 5
                }
                packet.value = sb.toString().toLong(2)
                start = pos
            }
            else -> {
                if (binLine[start + 6] == '0') {
                    val totalLength = binLine.substring(start + 7..start + 21).toInt(2)
                    packet.packets.addAll(parsePackets(binLine.substring(start + 22 .. start + 22 + totalLength)))
                    packet.binLength += 16 + packet.packets.sumOf { it.binLength }
                    start += 22 + totalLength
                } else {
                    val internalPacketsCount = binLine.substring(start + 7..start + 17).toInt(2)
                    packet.packets.addAll(parsePackets(binLine.substring(start + 18), internalPacketsCount))
                    packet.binLength += 12 + packet.packets.sumOf { it.binLength }
                    start += packet.binLength
                }
            }
        }
        packets.add(packet)
    }
    return packets
}

fun solve(packet: Packet): Int = if (packet.typeId == 4) packet.version else packet.packets.fold(packet.version) { acc, p -> acc + solve(p) }

fun solve2(packet: Packet): Long {
    return when (packet.typeId) {
        4 -> packet.value!!
        0 -> packet.packets.sumOf { pac -> solve2(pac) }
        1 -> packet.packets.fold(1) { acc, pac -> acc * solve2(pac) }
        2 -> packet.packets.minOf { solve2(it) }
        3 -> packet.packets.maxOf { solve2(it) }
        5 -> if (solve2(packet.packets.first()) > solve2(packet.packets.last())) 1 else 0
        6 -> if (solve2(packet.packets.first()) < solve2(packet.packets.last())) 1 else 0
        7 -> if (solve2(packet.packets.first()) == solve2(packet.packets.last())) 1 else 0
        else -> throw Error()
    }
}

fun main() {
    val hexLine = Common.getLines("tasks/src/day16/in.txt")[0]
    val binLine = hexLine.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")
    val packet = parsePackets(binLine).first()
    println(solve(packet))
    println(solve2(packet))
}