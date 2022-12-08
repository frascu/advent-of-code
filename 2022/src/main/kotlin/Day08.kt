fun main() {

    fun isVisible(currentChar: Char, rows: List<String>, currentIndexCol: Int, currentIndexRow: Int) =
        (rows[currentIndexRow].subSequence(0, currentIndexCol).all { it.code < currentChar.code }
                || rows[currentIndexRow].subSequence(currentIndexCol + 1, rows.size).all { it.code < currentChar.code }
                || (0 until currentIndexRow).map { rows[it][currentIndexCol] }.all { it.code < currentChar.code }
                || (currentIndexRow + 1 until rows.size).map { rows[it][currentIndexCol] }.all { it.code < currentChar.code })

    fun part1(rows: List<String>): Int {
        val visibleTreesInside = rows.subList(1, rows.size - 1)
            .withIndex()
            .sumOf { (indexRow, row) ->
                row.subSequence(1, row.length - 1)
                    .withIndex()
                    .count { (indexCol, currentChar) ->
                        isVisible(currentChar, rows, indexCol + 1, indexRow + 1)
                    }
            }
        return visibleTreesInside + rows.size * 2 + rows[0].length * 2 - 4
    }

    fun List<Char>.countVisibleTree(currentChar: Char): Int {
        val count = this.takeWhile { it.code < currentChar.code }.count()
        return if (count < this.size) count + 1 else count
    }

    fun calculateVisibility(currentChar: Char, rows: List<String>, currentIndexCol: Int, currentIndexRow: Int): Int {
        val left = rows[currentIndexRow].subSequence(0, currentIndexCol).reversed().toList().countVisibleTree(currentChar)
        val right = rows[currentIndexRow].subSequence(currentIndexCol + 1, rows.size).toList().countVisibleTree(currentChar)
        val up = (0 until currentIndexRow).map { rows[it][currentIndexCol] }.reversed().countVisibleTree(currentChar)
        val down = (currentIndexRow + 1 until rows.size).map { rows[it][currentIndexCol] }.countVisibleTree(currentChar)
        return up * left * right * down
    }

    fun part2(rows: List<String>): Int {
        return rows.subList(1, rows.size - 1)
            .withIndex()
            .maxOf { (indexRow, row) ->
                row.subSequence(1, row.length - 1)
                    .withIndex()
                    .maxOf { (indexCol, currentChar) ->
                        calculateVisibility(currentChar, rows, indexCol + 1, indexRow + 1)
                    }
            }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

