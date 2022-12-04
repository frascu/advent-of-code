fun main() {

    fun getRanges(input: List<String>): List<Pair<Set<Int>, Set<Int>>> {
        return input
            .map {
                val ranges = it.split(",")
                val range1 = ranges[0].split("-")
                val range2 = ranges[1].split("-")
                range1[0].toInt()..range1[1].toInt() to range2[0].toInt()..range2[1].toInt()
            }
            .map { it.first.toSet() to it.second.toSet() }
    }

    fun part1(input: List<String>): Int {
        return getRanges(input).count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }

    }

    fun part2(input: List<String>): Int {
        return getRanges(input).count { it.first.intersect(it.second).isNotEmpty() }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
