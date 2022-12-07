fun main() {

    data class Node(val name: String, val weight: Int, val parent: Node?, var children: List<Node>)

    fun getFiles(input: List<String>, indexStart: Int, parent: Node) =
        input.subList(indexStart + 1, input.size)
            .takeWhile { line -> line[0] != '$' }
            .map { file ->
                val (firstArg, fileName) = file.split(" ")
                Node(fileName, if ("dir" == firstArg) 0 else firstArg.toInt(), parent, listOf())
            }

    fun getTree(input: List<String>): Node {
        val root = Node("/", 0, null, listOf())
        var parent = root
        input.withIndex()
            .filter { it.value[0] == '$' }
            .forEach {(index, command) ->
                when {
                    command.startsWith("$ ls") -> parent.children = getFiles(input, index, parent)
                    command == "$ cd .." -> parent = parent.parent!!
                    command == "$ cd /" -> parent = root
                    else -> {
                        val arg = command.takeLastWhile { it != ' ' }
                        parent = parent.children.first { it.name == arg }
                    }
                }
            }
        return root
    }

    fun calculateWeight(node: Node): Int {
        if (node.children.isEmpty()) {
            return node.weight
        }
        return node.children.sumOf { calculateWeight(it) }
    }

    fun calculateWeights(parent: Node): List<Int> {
        val weights = mutableListOf(calculateWeight(parent))
        val directories = parent.children.filter { it.weight == 0 }
        if (directories.isNotEmpty()) {
            weights.addAll(directories.flatMap { calculateWeights(it) })
        }
        return weights
    }

    fun part1(input: List<String>) = calculateWeights(getTree(input)).filter { it < 100000 }.sum()

    fun part2(input: List<String>): Int {
        val weights = calculateWeights(getTree(input)).sorted()
        val neededFreeSpace = 30000000 - (70000000 - weights[weights.size - 1])
        return calculateWeights(getTree(input)).sorted().first { it >= neededFreeSpace }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
