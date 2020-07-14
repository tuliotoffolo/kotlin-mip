import org.junit.Test

import mip.examples.*

class ExamplesTest {

    @Test
    fun `Solving BMCP`() = BMCP()

    @Test
    fun `Solving n-Queens`() = Queens()

    @Test
    fun `Solving RCPSP`() = runRCPSP()

    @Test
    fun `Solving TSP-Compact`() = runTSPCompact()
}