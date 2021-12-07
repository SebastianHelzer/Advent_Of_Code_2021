import TxtIO.getNumbersFromFileWithDDelimiter
import kotlin.math.abs

fun findLowestCrabSubmarineFuelConsumption(filename: String, trueConsumption: Boolean = false): Int {
    val crabSubsPos = getNumbersFromFileWithDDelimiter(filename)
    return ((crabSubsPos.minOrNull() ?: 0)..(crabSubsPos.maxOrNull() ?: 0))
        .minOfOrNull { calculateTrueFuelConsumption(crabSubsPos, it, trueConsumption) }
        ?: throw Error("Unable to find min position")
}

private fun calculateTrueFuelConsumption(subs: List<Int>, target: Int, trueConsumption: Boolean = false): Int {
    return subs.sumOf { if (trueConsumption) (0 .. abs(it - target)).sum() else abs(it - target) }
}