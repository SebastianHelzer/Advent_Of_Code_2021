import java.io.File

fun simulateLanternFish(filename: String, days: Int = 80): Int {
    val fish: List<Int> = getNumbersFromFile(filename)
    return simulateFishFast(fish, days)
}

private fun getNumbersFromFile(filename: String): List<Int> = File(filename).readLines().map { it.split(",").map { it.toInt() } }.flatten()

fun List<Int>.simulateLanternFish(days: Int): List<Int> {

    if(days <= 0) return this
    println("Days left: $days ${this.size}")
    var newFish = 0
    return this.map { if(it == 0){ newFish++; 6} else it - 1 }.plus((0 until newFish).map { 8 }).simulateLanternFish(days - 1)

}

fun simulateFishFast(list: List<Int>, days: Int): Int {
    val fish: Map<Long, Long> = list.groupingBy { it }.eachCount().map { it.key.toLong() to it.value.toLong() }.toMap()
    return simulateFishFast(fish, days).values.sum().toInt()
}

fun simulateFishFast(map: Map<Long, Long>, days: Int): Map<Long, Long> {
    if(days <= 0) return map
    return simulateFishFast(mapOf(
        0L to map.getOrDefault(1L, 0),
        1L to map.getOrDefault(2L, 0),
        2L to map.getOrDefault(3L, 0),
        3L to map.getOrDefault(4L, 0),
        4L to map.getOrDefault(5L, 0),
        5L to map.getOrDefault(6L, 0),
        6L to map.getOrDefault(7L, 0) + map.getOrDefault(0L, 0),
        7L to map.getOrDefault(8L, 0),
        8L to map.getOrDefault(0L, 0),
    ),days - 1)
}

