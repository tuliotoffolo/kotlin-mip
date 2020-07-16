import mip.*
import mip.examples.*
import org.junit.Test

class ExamplesTest {

    var solvers = mutableListOf(GUROBI)//, CPLEX, CBC)

    init {
        for (solver in solvers) {
            try {
                Model(solverName = solver)
            }
            catch (e: Exception) {
                solvers.remove(solver)
            }
        }
    }

    @Test
    fun `Solve BMCP Example`() {
        for (solver in solvers) {
            System.setProperty("SOLVER_NAME", solver)
            runBMCP()
        }
    }

    @Test
    fun `Solve Queens Example`() {
        for (solver in solvers) {
            System.setProperty("SOLVER_NAME", solver)
            runQueens()
        }
    }

    @Test
    fun `Solve RCPSP Example`() {
        for (solver in solvers) {
            System.setProperty("SOLVER_NAME", solver)
            runRCPSP()
        }
    }

    @Test
    fun `Solve TSPCompact Example`() {
        for (solver in solvers) {
            System.setProperty("SOLVER_NAME", solver)
            runTSPCompact()
        }
    }

    @Test
    fun `Solve TSPLarge Example`() {
        for (solver in solvers) {
            System.setProperty("SOLVER_NAME", solver)
            runTSPLarge()
        }
    }
}