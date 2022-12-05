fun main() {

    data class Movement(val amount: Int, val from: Int, val to: Int)

    fun getStacks(input: List<String>): List<MutableList<Char>> {
        val numberOfStacks = (input[0].length - input[0].length % 4) / 4 + 1
        val stacks = (0 until numberOfStacks).map { mutableListOf<Char>() }
        input.subList(0, input.indexOf("") - 1).forEach { line ->
            line.chunked(4)
                .withIndex()
                .filter { it.value.isNotBlank() }
                .forEach {
                    stacks[it.index].add(it.value[1])
                }
        }
        return stacks
    }

    fun getMovements(input: List<String>): List<Movement> =
        input.subList(input.indexOf("") + 1, input.size)
            .map { it.split(" ") }
            .map { Movement(it[1].toInt(), it[3].toInt(), it[5].toInt()) }

    fun part(input: List<String>, crateMover: (MutableList<Char>) -> List<Char>): String {
        val stacks = getStacks(input)
        val movements = getMovements(input)
        movements.forEach {
            val movement = stacks[it.from - 1].subList(0, it.amount)
            stacks[it.to - 1].addAll(0, crateMover(movement))
            movement.clear()
        }
        return stacks.map { it.removeFirst() }.joinToString("")
    }

    fun part1(input: List<String>): String = part(input) { it.reversed() }
    fun part2(input: List<String>): String = part(input) { it }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
