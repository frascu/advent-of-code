fun main() {

    fun solve(input: String, distinctChars: Int): Int {
        return (0..input.length - distinctChars)
            .first { input.subSequence(it, it + distinctChars).toSet().size == distinctChars } + distinctChars
    }

    val testInput = readInput("Day06_test")[0]
    check(solve(testInput, 4) == 7)
    check(solve(testInput, 14) == 19)

    val input = readInput("Day06")[0]
    println(solve(input, 4))
    println(solve(input, 14))
}
