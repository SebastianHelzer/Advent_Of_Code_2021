import TxtIO.getNumbersFromFileWithDDelimiter
import kotlin.math.abs

fun findLowestCrabSubmarineFuelConsumption(filename: String, trueConsumption: Boolean = false): Int {
    val crabSubsPos = getNumbersFromFileWithDDelimiter(filename)
    return ((crabSubsPos.minOrNull() ?: 0)..(crabSubsPos.maxOrNull() ?: 0))
        .minOfOrNull {
            if(trueConsumption) {
                calculateTrueFuelConsumption(crabSubsPos, it)
            } else {
                calculateFuelConsumption(crabSubsPos, it)
            }
        } ?: throw Error("Unable to find min position")
}

private fun calculateFuelConsumption(subs: List<Int>, target: Int): Int {
    return subs.sumOf { abs(it - target) }
}

private fun calculateTrueFuelConsumption(subs: List<Int>, target: Int): Int {
    return subs.sumOf { (0 .. abs(it - target)).sum() }
}