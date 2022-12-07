fun main() {

    data class Node(val name: String, val weight: Int, val parent: Node?, var children: List<Node>)

    fun getFiles(input: List<String>, indexStart: Int, parent: Node) =
        input.subList(indexStart + 1, input.size)
            .takeWhile { line -> line[0] != '$' }
            .map { file ->
                val (weight, fileName) = file.split(" ")
                Node(fileName, if ("dir" == weight) 0 else weight.toInt(), parent, listOf())
            }

    fun getTree(input: List<String>): Node {
        val root = Node("/", 0, null, mutableListOf())
        var parent = root
        input.withIndex()
            .filter { it.value[0] == '$' }
            .forEach {
                val command = it.value.split(" ")
                when {
                    command[1] == "cd" && command[2] == ".." -> parent = parent.parent!!
                    command[1] == "cd" && command[2] != "/" -> parent =
                        parent.children.first { d -> d.name == command[2] }
                    command[1] == "ls" -> parent.children = getFiles(input, it.index, parent)
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

    fun part1(input: List<String>) = calculateWeights(getTree(input)).filter { w -> w < 100000 }.sum()

    fun part2(input: List<String>): Int {
        val weights = calculateWeights(getTree(input)).sorted()
        val neededFreeSpace = 30000000 - (70000000 - weights[weights.size - 1])
        return calculateWeights(getTree(input)).sorted().first { w -> w >= neededFreeSpace }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
