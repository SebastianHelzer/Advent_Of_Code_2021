object Day12 {

    fun part1(filename: String): Int {
        val connections = getConnectionsFromFile(filename)
        val paths = getPathsFromConnections(connections, FilterType.SmallCaveOnce)
        return paths.count()
    }

    fun part2(filename: String): Int {
        val connections = getConnectionsFromFile(filename)
        val paths = getPathsFromConnections(connections, FilterType.OneSmallCaveTwice)
        return paths.count()
    }

    fun getConnectionsFromFile(filename: String): List<Pair<String, String>> {
        val lines = TxtIO.readLinesFromFile(filename)

        val connections = lines.map {
            val parts = it.split("-")
            parts[0] to parts[1]
        }
        return connections
    }

    fun getPathsFromConnections(connections: List<Pair<String, String>>, filterType: FilterType): List<String> {
        return getDestinationsFromNode(connections, "start")
            .map { getPaths(connections, listOf("start", it), filterType) }
            .map { it.map { it.joinToString(",") } }
            .flatten()
    }

    private fun getPaths(connections: List<Pair<String, String>>, path: List<String>, filterType: FilterType): List<List<String>> {
        if(path.contains("end")) return listOf(path)

        return getDestinationsFromNode(connections, path.last())
            .filter { node ->
                when (filterType) {
                    FilterType.SmallCaveOnce -> {
                        if(node.lowercase() == node) !path.contains(node) else true
                    }
                    FilterType.OneSmallCaveTwice -> {
                        if(node.lowercase() == node && path.filter { it.lowercase() == it }.groupBy { it }.map { it.value.size }.any { it > 1 }) {
                            !path.contains(node)
                        } else node != "start"
                    }
                }
            }
            .map { getPaths(connections, path + it, filterType) }
            .flatten()
    }

    fun getDestinationsFromNode(connections: List<Pair<String, String>>, node: String): List<String> {
        return connections.filter { it.first == node }.map { it.second } + connections.filter { it.second == node }.map { it.first }
    }

    enum class FilterType {
        SmallCaveOnce, OneSmallCaveTwice
    }
}