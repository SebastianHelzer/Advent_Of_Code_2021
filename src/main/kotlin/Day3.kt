import java.io.File
import kotlin.math.pow

fun processPowerConsumption(filename: String): Int {
    val binaryNumbers: List<String> = readBinaryNumbersFromFile(filename)
    val (gamma, epsilon) = getGammaAndEpsilon(binaryNumbers)
    return gamma * epsilon
}

fun getLifeSupportRating(filename: String): Int {
    return 0
}

private fun readBinaryNumbersFromFile(filename: String): List<String> = File(filename).readLines()

private fun getGammaAndEpsilon(numbers: List<String>): Pair<Int, Int> {
    val bytes = numbers.map { it.toInt(2) }
    val allTheBits = convertToBitsUnlimited(bytes)

    val bits = mashBitsIntoBits(allTheBits, numbers.size/2)
    val gamma = getGamma(bits)
    val epsilon = getEpsilon(bits)

    println("gamma $gamma")
    println("epsilon $epsilon")

    return gamma to epsilon
}

private fun convertToBitsUnlimited(ints: List<Int>): List<List<Boolean>> {
    return ints.map { num -> (0 until num.toString(2).length).map {
            num.and(2.0.pow(it).toInt()) > 0
        }
    }
}

private fun mashBitsIntoBits(allTheBits: List<List<Boolean>>, halfSize: Int): List<Boolean> {
    return (0 until allTheBits.maxOf { it.size }).map { index ->
        allTheBits.count { it.getOrNull(index) ?: false } > halfSize
    }
}

private fun getGamma(bits: List<Boolean>): Int {
    return bits.mapIndexed { index, b -> if (b) 2.0.pow(index).toInt() else 0 }.sum()
}

private fun getEpsilon(bits: List<Boolean>): Int {
    return bits.mapIndexed { index, b -> if (!b) 2.0.pow(index).toInt() else 0 }.sum()
}