package day16

import common.Common

class Packet(val version: Int, val typeId: Int) {
    val packets = mutableListOf<Packet>()
    var value: Long? = null
    var binLength: Int = 0
}

fun solve(binLine: String, packetCount: Int? = null): MutableList<Packet> {
    val packets = mutableListOf<Packet>()
    var start = 0
    while (start + 10 < binLine.length && packets.size < (packetCount ?: Int.MAX_VALUE)) {
        val version = binLine.substring(start..start + 2).toInt(2)
        val typeId = binLine.substring(start + 3..start + 5).toInt(2)
        val packet = Packet(version, typeId)
        packet.binLength += 6
        if (packet.typeId == 4) {
            var keepReading = true
            val sb = StringBuilder()
            var i = start + 6
            while (keepReading) {
                if (binLine[i] == '0') keepReading = false
                sb.append(binLine.substring(i + 1..i + 4))
                i += 5
                packet.binLength += 5
            }
            packet.value = sb.toString().toLong(2)
            start = i
        } else {
            if (binLine[start + 6] == '0') {
                val totalLength = binLine.substring(start + 7..start + 21).toInt(2)
                packet.packets.addAll(solve(binLine.substring(start + 22, start + 22 + totalLength)))
                packet.binLength += 16 + packet.packets.sumOf { it.binLength }
                start += 22 + totalLength
            } else {
                val internalPacketsCount = binLine.substring(start + 7..start + 17).toInt(2)
                packet.packets.addAll(solve(binLine.substring(start + 18), internalPacketsCount))
                packet.binLength += 12 + packet.packets.sumOf { it.binLength }
                start += packet.binLength
            }
        }
        packets.add(packet)
    }
    return packets
}

fun getBinLine(hexLine: String): String {
    val sb = StringBuilder()
    for (char in hexLine) {
        when (char) {
            '0' -> sb.append("0000")
            '1' -> sb.append("0001")
            '2' -> sb.append("0010")
            '3' -> sb.append("0011")
            '4' -> sb.append("0100")
            '5' -> sb.append("0101")
            '6' -> sb.append("0110")
            '7' -> sb.append("0111")
            '8' -> sb.append("1000")
            '9' -> sb.append("1001")
            'A' -> sb.append("1010")
            'B' -> sb.append("1011")
            'C' -> sb.append("1100")
            'D' -> sb.append("1101")
            'E' -> sb.append("1110")
            'F' -> sb.append("1111")
        }
    }
    return sb.toString()
}

fun calcAns(packet: Packet): Int = if (packet.typeId == 4) packet.version else packet.packets.fold(packet.version) { acc, p -> acc + calcAns(p) }

fun calcAns2(packet: Packet): Long {
    return when (packet.typeId) {
        4 -> packet.value!!
        0 -> packet.packets.sumOf { pac -> calcAns2(pac) }
        1 -> packet.packets.fold(1) { acc, pac -> acc * calcAns2(pac) }
        2 -> packet.packets.minOf { calcAns2(it) }
        3 -> packet.packets.maxOf { calcAns2(it) }
        5 -> if (calcAns2(packet.packets.first()) > calcAns2(packet.packets.last())) 1 else 0
        6 -> if (calcAns2(packet.packets.first()) < calcAns2(packet.packets.last())) 1 else 0
        7 -> if (calcAns2(packet.packets.first()) == calcAns2(packet.packets.last())) 1 else 0
        else -> throw Error()
    }
}

fun main() {
    val hexLine = Common.getLines("tasks/src/day16/in.txt")[0]
    val binLine = getBinLine(hexLine)
    val packets = solve(binLine)
    println(calcAns(packets.first()))
    println(calcAns2(packets.first()))
}