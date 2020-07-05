/**
 * Bandwidth multi coloring problem, more specifically the Frequency assignment problem as
 * described in http://fap.zib.de/problems/Philadelphia/
 */

package mip.examples

import mip.*
import kotlin.math.roundToInt

private
fun main() {
    // number of channels per node (r) and range of channels (N)
    val r = intArrayOf(3, 5, 8, 3, 6, 5, 7, 3)
    val N = 0 until r.size

    // distance between channels in the same node (i, i) and in adjacent nodes
    val d = arrayOf(
        arrayOf(3, 2, 0, 0, 2, 2, 0, 0),
        arrayOf(2, 3, 2, 0, 0, 2, 2, 0),
        arrayOf(0, 2, 3, 0, 0, 0, 3, 0),
        arrayOf(0, 0, 0, 3, 2, 0, 0, 2),
        arrayOf(2, 0, 0, 2, 3, 2, 0, 0),
        arrayOf(2, 2, 0, 0, 2, 3, 2, 0),
        arrayOf(0, 2, 2, 0, 0, 2, 3, 0),
        arrayOf(0, 0, 0, 2, 0, 0, 0, 3),
    )

    // in complete applications this upper bound should be obtained from a feasible solution
    // produced with some heuristic
    val U = 0 until N.sumBy { i -> N.sumBy { j -> d[i][j] } } + r.sum()

    // creating model and variables
    val m = Model("BMCP")
    val x = N.map { i -> U.map { c -> m.addBinVar("x($i,$c)") } }
    val z = m.addVar("z", obj = 1.0)

    // creating constraints
    for (i in N)
        m += U.map { c -> x[i][c] } eq r[i]

    for (i in N) for (j in N) if (i != j)
        for (c1 in U) for (c2 in U) if (c1 <= c2 && c2 < c1 + d[i][j])
            m += x[i][c1] + x[j][c2] leq 1

    for (i in N)
        for (c1 in U) for (c2 in U) if (c1 < c2 && c2 < c1 + d[i][i])
            m += x[i][c1] + x[i][c2] leq 1

    for (i in N)
        for (c in U)
            m += z ge (c + 1) * x[i][c]

    m.maxNodes = 30
    m.optimize()

    if (m.hasSolution)
        for (i in N)
            println("Channels of node $i: ${U.filter { c -> x[i][c].x >= 0.99 }}")

    // sanity tests
    assert(m.objectiveBound <= 41 + 1e-10)
    if (m.status == OptimizationStatus.Optimal)
        assert(m.objectiveValue.roundToInt() == 41)
    else if (m.status == OptimizationStatus.Feasible)
        assert(m.objectiveValue >= 41 - 1e-10)
    // m.validateOptimizationResult() TODO("Finish validation..")
}

fun BMCP() = main()

/*

"""

from itertools import product
from mip import Model, xsum, minimize, BINARY

# number of channels per node
r = [3, 5, 8, 3, 6, 5, 7, 3]

# distance between channels in the same node (i, i) and in adjacent nodes
#     0  1  2  3  4  5  6  7
d = [[3, 2, 0, 0, 2, 2, 0, 0],   # 0
     [2, 3, 2, 0, 0, 2, 2, 0],   # 1
     [0, 2, 3, 0, 0, 0, 3, 0],   # 2
     [0, 0, 0, 3, 2, 0, 0, 2],   # 3
     [2, 0, 0, 2, 3, 2, 0, 0],   # 4
     [2, 2, 0, 0, 2, 3, 2, 0],   # 5
     [0, 2, 2, 0, 0, 2, 3, 0],   # 6
     [0, 0, 0, 2, 0, 0, 0, 3]]   # 7

N = range(len(r))

# in complete applications this upper bound should be obtained from a feasible
# solution produced with some heuristic
U = range(sum(d[i][j] for (i, j) in product(N, N)) + sum(el for el in r))

m = Model()

x = [[m.add_var('x({},{})'.format(i, c), var_type=BINARY)
for c in U] for i in N]

z = m.add_var('z')
m.objective = minimize(z)

for i in N:
m += xsum(x[i][c] for c in U) == r[i]

for i, j, c1, c2 in product(N, N, U, U):
if i != j and c1 <= c2 < c1+d[i][j]:
m += x[i][c1] + x[j][c2] <= 1

for i, c1, c2 in product(N, U, U):
if c1 < c2 < c1+d[i][i]:
m += x[i][c1] + x[i][c2] <= 1

for i, c in product(N, U):
m += z >= (c+1)*x[i][c]

m.optimize(max_nodes=30)

if m.num_solutions:
for i in N:
print('Channels of node %d: %s' % (i, [c for c in U if x[i][c].x >=
0.99]))

# sanity tests
from mip import OptimizationStatus

assert m.objective_bound <= 41 + 1e-10
if m.status == OptimizationStatus.OPTIMAL:
assert round(m.objective_value) == 41
elif m.status == OptimizationStatus.FEASIBLE:
assert m.objective_value >= 41 - 1e-10
m.check_optimization_results()
*/
