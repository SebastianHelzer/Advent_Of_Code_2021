import TxtIO.readLinesFromFile
import kotlin.math.pow

fun decipherSignalsFromFile(filename: String): Int {
    return readLinesFromFile(filename).sumOf {
        val parts = it.split(" | ")
        val input = parts[0]
        val output = parts[1]
        val numbers = decipherSevenSegmentDisplay(input)
        countDigits(numbers.filterKeys { key -> listOf(1, 4, 7, 8).contains(key) }, output)
    }
}

fun sumSevenSegsFromFIle(filename: String): Int {
    return readLinesFromFile(filename).sumOf {
        val parts = it.split(" | ")
        val input = parts[0]
        val output = parts[1]
        val numbers = decipherSevenSegmentDisplay(input)
        mapToNumber(numbers, output)
    }
}

fun mapToNumber(number: Map<Int, String>, output: String): Int {
    return output.split(" ").mapNotNull { num ->
        number.map { it.value.toSortedSet() to it.key }.toMap()[num.toSortedSet()] ?: 0
    }.reversed().foldRightIndexed(0) { i, v: Int, a: Int ->
        a + v * (10.0.pow(i).toInt())
    }
}

fun countDigits(number: Map<Int, String>, output: String): Int {
    return output.split(" ").mapNotNull { num ->
        number.map { it.value.toSortedSet() }.contains(num.toSortedSet())
    }.count { it }
}

fun decipherSevenSegmentDisplay(codex: String): Map<Int, String> {
    val nums = codex.split(" ")
    val one = nums.first { it.length == 2 }
    val eight = nums.first { it.length == 7 }
    val four = nums.first { it.length == 4 }
    val seven = nums.first { it.length == 3 }
    val nine = nums.filter { it.length == 6 }.first { it.toSet().containsAll(four.toSet()) }
    val three = nums.filter { it.length == 5 }.first { it.toSet().containsAll(seven.toSet()) }
    val e = (eight.toSet() - nine.toSet()).single()
    val two = nums.filter { it.length == 5 }.first{ it.contains(e) }
    val five = nums.filter { it.length == 5 }.minus(two).minus(three).single()
    val six = nums.filter { it.length == 6 }.minus(nine).first { it.toSet().containsAll(five.toSet()) }
    val zero = nums.filter { it.length == 6 }.minus(six).minus(nine).single()

    return mapOf(
        0 to zero,
        1 to one,
        2 to two,
        3 to three,
        4 to four,
        5 to five,
        6 to six,
        7 to seven,
        8 to eight,
        9 to nine,
    )
}