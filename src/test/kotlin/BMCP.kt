import br.ufop.jmip.*

/**
 * Bandwidth multi coloring problem, more specificially the Frequency assignment problem as
 * described here: http://fap.zib.de/problems/Philadelphia/
 */
fun main(args: Array<String>) {
    // number of channels per node
    val r = intArrayOf(3, 5, 8, 3, 6, 5, 7, 3)

    // distance between channels in the same node (i, i) and in adjacent nodes
    val d = arrayOf(
        intArrayOf(3, 2, 0, 0, 2, 2, 0, 0),
        intArrayOf(2, 3, 2, 0, 0, 2, 2, 0),
        intArrayOf(0, 2, 3, 0, 0, 0, 3, 0),
        intArrayOf(0, 0, 0, 3, 2, 0, 0, 2),
        intArrayOf(2, 0, 0, 2, 3, 2, 0, 0),
        intArrayOf(2, 2, 0, 0, 2, 3, 2, 0),
        intArrayOf(0, 2, 2, 0, 0, 2, 3, 0),
        intArrayOf(0, 0, 0, 2, 0, 0, 0, 3),
    )

    val N = 0 until r.size

    // in complete applications this upper bound should be obtained from a feasible
    // solution produced with some heuristic
    val U = 0 until N.sumBy { i -> N.sumBy { j -> d[i][j] } } + r.sum()

    val m = Model(solverName = GUROBI)

    val x = Array(r.size) { i ->
        Array(U.last + 1) { c -> m.addVar("x($i,$c", VarType.BINARY) }
    }

    val z = m.addVar("z", obj = 1.0)

    for (i in N)
        m += eq(U.map { c -> x[i][c] }, r[i])

    for (i in N) for (j in N) if (i != j) {
        for (c1 in U) for (c2 in U) if (c1 <= c2 && c2 < c1 + d[i][j]) {
            m += leq(x[i][c1] + x[j][c2], 1)
        }
    }

    for (i in N) {
        for (c1 in U) for (c2 in U) if (c1 < c2 && c2 < c1 + d[i][i]) {
            m += leq(x[i][c1] + x[i][c2], 1)
        }
    }

    for (i in N) for (c in U) {
        m += geq(z, (c + 1) * x[i][c])
    }

    m.optimize()

    if (m.hasSolution)
        for (i in N)
            println("Channels of node $i: ${U.filter { c -> x[i][c].x >= 0.99 }}")

    // sanity tests
    // assert m.objective_bound <= 41+1e-10
    // if m.status == OptimizationStatus.OPTIMAL:
    // assert round (m.objective_value) == 41
    // elif m . status == OptimizationStatus . FEASIBLE :
    // assert m . objective_value >= 41-1e-10
    // m.check_optimization_results()

}

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