import mip.*
import mip.examples.*
import org.spekframework.spek2.Spek

object ExampleTest: Spek({
    // selecting available solvers from list
    val solvers = mutableListOf(GUROBI, CBC)
    for (solver in solvers) {
        try {
            Model(solverName = solver)
        }
        catch (e: Exception) {
            solvers.remove(solver)
        }
    }

    for (solver in solvers) {

        group("Running examples with $solver") {
            beforeEachTest {
                System.setProperty("SOLVER_NAME", solver)
            }

            test("Solve BMCP Example") {
                runBMCP()
            }

            test("Solve Queens Example") {
                runQueens()
            }

            test("Solve RCPSP Example") {
                runRCPSP()
            }

            test("Solve TSPCompact Example") {
                runTSPCompact()
            }

            test("Solve TSPLarge Example") {
                runTSPCompact()
            }
        }
    }
})
