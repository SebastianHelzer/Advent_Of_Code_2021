import java.io.File

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

//    println("getDepthIncreases: ${getDepthIncreases("src/main/resources/depth_test.txt")}")
    println("getDepthIncreases: ${getDepthIncreases("src/main/resources/depth.txt")}")
//    println("getDepthIncreasesWithRollingSum: ${getDepthIncreasesWithRollingSum("src/main/resources/depth_test.txt")}")
    println("getDepthIncreasesWithRollingSum: ${getDepthIncreasesWithRollingSum("src/main/resources/depth.txt")}")
}

fun getDepthIncreases(filename: String): Int {
    val depthValues: List<Int> = getNumbersFromFile(filename)
    val comparisons: List<Comparisons> = getComparisonsFromNumbers(depthValues)
    return comparisons.count { it == Comparisons.MoreThan }
}

fun getDepthIncreasesWithRollingSum(filename: String): Int {
    val depthValues: List<Int> = getNumbersFromFile(filename)
    val rollingAvgs: List<Int> = getRollingAveragesFromNumber(depthValues)
    val comparisons: List<Comparisons> = getComparisonsFromNumbers(rollingAvgs)
    return comparisons.count { it == Comparisons.MoreThan }
}

fun getRollingAveragesFromNumber(values: List<Int>): List<Int> {
    if (values.size < 3) return emptyList()

    val array = ArrayList<Int>()

    array.add(values[0])
    array.add(values[1])

    return values.drop(2).map {
        if(array.size > 2) array.removeFirst()
        array.add(it)
        array.sum()
    }
}

fun getComparisonsFromNumbers(numbers: List<Int>): List<Comparisons> {
    if(numbers.isEmpty()) return emptyList()
    var temp = numbers.first()
    return numbers.drop(1).map {
        val value = if(it < temp) Comparisons.LessThan
        else if(it == temp) Comparisons.Equal
        else Comparisons.MoreThan

        temp = it

        value
    }
}

fun getNumbersFromFile(filename: String): List<Int> = File(filename).readLines().map { it.toInt() }

enum class Comparisons {
    LessThan, Equal, MoreThan,
}

