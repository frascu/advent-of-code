fun main() {

    fun getPriority(c: Char) = if (c in 'A'..'Z') c - 'A' + 27 else c - 'a' + 1

    fun part1(input: List<String>): Int {
        return input.map { it.chunked(it.length / 2) }
            .map { it[0].toSet().intersect(it[1].toSet()) }
            .flatten()
            .sumOf(::getPriority)
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf { group ->
                group.map(String::toSet)
                    .reduce(Set<Char>::intersect)
                    .sumOf(::getPriority)
            }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
