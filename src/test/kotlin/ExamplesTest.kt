import org.junit.Test

import mip.examples.*

class ExamplesTest {

    @Test
    fun `Solving BMCP`() = BMCP()

    @Test
    fun `Solving n-Queens`() = Queens()

    @Test
    fun `Solving RCPSP`() = RCPSP()

    @Test
    fun `Solving TSP-Compact`() = TSPCompact()

    @Test
    fun `Solving BMCP 2`() = BMCP()

    @Test
    fun `Solving n-Queens 2`() = Queens()

    @Test
    fun `Solving RCPSP 2`() = RCPSP()

    @Test
    fun `Solving TSP-Compact 2`() = TSPCompact()
}