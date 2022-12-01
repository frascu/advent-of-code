fun main() {

    fun getCalories(input: List<String>): List<Int> {
        val calories = ArrayList<Int>();
        var sum = 0;
        input.forEach() {
            if (it.isEmpty()) {
                calories.add(sum)
                sum = 0
                return@forEach
            }
            sum += it.toInt()
        }
        calories.add(sum)
        return calories
    }

    fun part1(input: List<String>): Int {
        return getCalories(input).max()
    }

    fun part2(input: List<String>): Int {
        return getCalories(input).sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
