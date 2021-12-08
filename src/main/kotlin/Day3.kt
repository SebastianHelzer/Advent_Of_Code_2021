import TxtIO.readLinesFromFile
import kotlin.math.pow

fun processPowerConsumption(filename: String): Int {
    val binaryNumbers: List<String> = readLinesFromFile(filename)
    val (gamma, epsilon) = getGammaAndEpsilon(binaryNumbers)
    return gamma * epsilon
}

fun getLifeSupportRating(filename: String): Int {
    val binaryNumbers: List<String> = readLinesFromFile(filename)
    val allTheBits = intsToBits(binaryNumbers.map { it.toInt(2) })
    val oxyBits = filterNumberByBit(allTheBits)
    val oxygen = bitsToInt(oxyBits)

    val co2Bits = filterNumberByBitBackward(allTheBits)
    val cO2 = bitsToInt(co2Bits)

    return oxygen * cO2
}

private fun filterNumberByBit(bits: List<List<Boolean>>, bitIndex: Int = bits.maxOf { it.size - 1 }): List<Boolean> {
    if(bits.size == 1) return bits.first()
    if(bitIndex < 0) throw Error("We were not able to filter well")
    val bitCount = bits.count { it.getOrNull(bitIndex) ?: false }
    return if (bitCount >= bits.size - bitCount) {
        filterNumberByBit(bits.filter { it.getOrNull(bitIndex) ?: false }, bitIndex - 1)
    } else {
        filterNumberByBit(bits.filter { !(it.getOrNull(bitIndex) ?: false) }, bitIndex - 1)
    }
}

private fun filterNumberByBitBackward(bits: List<List<Boolean>>, bitIndex: Int = bits.maxOf { it.size - 1}): List<Boolean> {
    if(bits.size == 1) return bits.first()
    if(bitIndex < 0) throw Error("We were not able to filter well")
    val bitCount = bits.count { it.getOrNull(bitIndex) ?: false }
    return if (bitCount >= bits.size - bitCount) {
        filterNumberByBitBackward(bits.filter { !(it.getOrNull(bitIndex) ?: false) }, bitIndex - 1)
    } else {
        filterNumberByBitBackward(bits.filter { (it.getOrNull(bitIndex) ?: false) }, bitIndex - 1)
    }
}


private fun getGammaAndEpsilon(numbers: List<String>): Pair<Int, Int> {
    val bytes = numbers.map { it.toInt(2) }
    val allTheBits = intsToBits(bytes)

    val bits = mashBitsIntoBits(allTheBits, numbers.size/2)
    val gamma = getGamma(bits)
    val epsilon = getEpsilon(bits)

    println("gamma $gamma")
    println("epsilon $epsilon")

    return gamma to epsilon
}

private fun intsToBits(ints: List<Int>): List<List<Boolean>> = ints.map { num -> intToBits(num) }

private fun intToBits(int: Int): List<Boolean> = (0 until int.toString(2).length).map {
    int.and(2.0.pow(it).toInt()) > 0
}

private fun mashBitsIntoBits(bits: List<List<Boolean>>, halfSize: Int): List<Boolean> {
    return (0 until bits.maxOf { it.size }).map { index ->
        bits.count { it.getOrNull(index) ?: false } > halfSize
    }
}

private fun getGamma(bits: List<Boolean>): Int = bitsToInt(bits)

private fun bitsToInt(bits: List<Boolean>): Int {
    return bits.mapIndexed { index, b -> if (b) 2.0.pow(index).toInt() else 0 }.sum()
}

private fun getEpsilon(bits: List<Boolean>): Int {
    return bits.mapIndexed { index, b -> if (!b) 2.0.pow(index).toInt() else 0 }.sum()
}