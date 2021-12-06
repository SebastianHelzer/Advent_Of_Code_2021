import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class Day6Tests {

    @Test
    fun simulateFish() {
        val fish = listOf(3,4,3,1,2)
        assertEquals(listOf(2,3,2,0,1), fish.simulateLanternFish(1))
        assertEquals(listOf(1,2,1,6,0,8), fish.simulateLanternFish(2))
        assertEquals(listOf(0,1,0,5,6,7,8), fish.simulateLanternFish(3))
        assertEquals(listOf(6,0,6,4,5,6,7,8,8), fish.simulateLanternFish(4))
        assertEquals(listOf(5,6,5,3,4,5,6,7,7,8), fish.simulateLanternFish(5))
        assertEquals(listOf(4,5,4,2,3,4,5,6,6,7), fish.simulateLanternFish(6))
        assertEquals(listOf(3,4,3,1,2,3,4,5,5,6), fish.simulateLanternFish(7))
        assertEquals(listOf(2,3,2,0,1,2,3,4,4,5), fish.simulateLanternFish(8))
        assertEquals(listOf(1,2,1,6,0,1,2,3,3,4,8), fish.simulateLanternFish(9))
        assertEquals(listOf(0,1,0,5,6,0,1,2,2,3,7,8), fish.simulateLanternFish(10))
        assertEquals(listOf(6,0,6,4,5,6,0,1,1,2,6,7,8,8,8), fish.simulateLanternFish(11))
        assertEquals(listOf(5,6,5,3,4,5,6,0,0,1,5,6,7,7,7,8,8), fish.simulateLanternFish(12))
        assertEquals(listOf(4,5,4,2,3,4,5,6,6,0,4,5,6,6,6,7,7,8,8), fish.simulateLanternFish(13))
        assertEquals(listOf(3,4,3,1,2,3,4,5,5,6,3,4,5,5,5,6,6,7,7,8), fish.simulateLanternFish(14))
        assertEquals(listOf(2,3,2,0,1,2,3,4,4,5,2,3,4,4,4,5,5,6,6,7), fish.simulateLanternFish(15))
        assertEquals(listOf(1,2,1,6,0,1,2,3,3,4,1,2,3,3,3,4,4,5,5,6,8), fish.simulateLanternFish(16))
        assertEquals(listOf(0,1,0,5,6,0,1,2,2,3,0,1,2,2,2,3,3,4,4,5,7,8), fish.simulateLanternFish(17))
        assertEquals(listOf(6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8), fish.simulateLanternFish(18))
        assertEquals(5934, fish.simulateLanternFish(80).size)
    }

    @Test
    fun longSimulation() {
        val fish = listOf(3,4,3,1,2)
        assertEquals(26984457539, fish.simulateLanternFish(256).size)
        // 39222586 and 75 days left before breaking
    }

    @Test
    fun longFastSimulation() {
        val fish = listOf(3,4,3,1,2)
        assertEquals(5, simulateFishFast(fish, 1))
        assertEquals(6, simulateFishFast(fish, 2))
        assertEquals(5934, simulateFishFast(fish, 80))
        assertEquals(26984457539, simulateFishFast(fish, 256))
    }

    @Test
    fun value() {
        assertTrue(26984457539 > Int.MAX_VALUE)
        assertTrue(26984457539 < Long.MAX_VALUE)
    }
}