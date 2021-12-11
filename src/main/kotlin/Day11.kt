object Day11 {
    fun part1(filename: String) {
        val step = TxtIO.readLinesFromFile(filename)
        val lastStep = (0 until 100).fold(step to 0) {
                s, i ->
                if(i % 10 == 0) println("Index: $i Step: $s")
            val ns = nextStep(s.first)
            ns.first to ns.second + s.second
        }

        println(lastStep)
    }

    fun part2(filename: String) {
        val initialStep = TxtIO.readLinesFromFile(filename)
        var step = initialStep to 0
        var index = 0
        while (step.first.sumOf { it.length } != step.second) {
            index++
            step = nextStep(step.first)
        }

        println("Index: $index")
    }

    fun nextStep(current: List<String>): Pair<List<String>, Int> {
        val nextStep = nextStepFromInts(convertLinesToInts(current))
        return nextStep.map { it.joinToString("") } to nextStep.flatten().count { it == 0 }
    }

    private fun nextStepFromInts(step: List<List<Int>>): List<List<Int>> {
        return processFlashes(step.map { it.map { it + 1 } }).map { it.map { if (it > 9) 0 else it } }
    }

    private fun processFlashes(step: List<List<Int>>, flashPositions: List<Position> = emptyList()): List<List<Int>> {
        val newPositions = arrayListOf<Position>()
        step.mapIndexed { r, i -> i.mapIndexed { c, v ->
            if (v > 9 && !flashPositions.contains(Position(c, r))) {
                newPositions.add(Position(c, r))
            }
        }}

        val processedSteps: List<List<Int>> = addFlashesToStep(step, newPositions)

        return if(processedSteps == step) processedSteps
        else processFlashes(processedSteps, flashPositions+newPositions)
    }

    private fun addFlashesToStep(step: List<List<Int>>, flashPositions: List<Position>): List<List<Int>> {
        val mStep = step.map { it.toMutableList() }

        flashPositions.forEach { (x,y) ->
            (x-1 .. x+1).forEach { mx ->  (y-1 .. y+1).forEach { my ->
                if(mStep.getOrNull(my)?.getOrNull(mx) != null) {
                    mStep[my][mx] += 1
                }
            } }
        }

        return mStep
    }

    fun convertLinesToInts(step: List<String>): List<List<Int>> {
        return step.map { it.toDigits() }
    }

    private fun String.toDigits(): List<Int> {
        return this.map { it.digitToInt() }
    }
}