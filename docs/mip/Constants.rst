Documentation for mip/Constants.kt
==================================


.. kotlin:enum_class:: CutType

Different type of cuts (for enabling/disabling)

   - Gomory = 0
   - MIR = 1
   - ZeroHalf = 2
   - Clique = 3
   - KnapsackCover = 4
   - LiftAndProject = 5

.. kotlin:enum_class:: LPMethod

Different methods to solve the linear programming problem.

   - Auto = 0
   - Dual = 1
   - Primal = 2
   - Barrier = 3

.. kotlin:enum_class:: Parameter

Parameters to set

   - Cutoff

.. kotlin:enum_class:: OptimizationStatus

Possible optimization status.

   - Error
   - Optimal = 0
   - Infeasible = 1
   - Unbounded = 2
   - Feasible = 3
   - IntInfeasible = 4
   - NoSolutionFound = 5
   - Loaded = 6
   - Cutoff = 7
   - Other = 10000

.. kotlin:enum_class:: SearchEmphasis

Possible search emphasis options

   - Default = 0
   - Feasibility = 1
   - Optimality = 2

.. kotlin:enum_class:: VarType

Possible variable types.

   - Binary
   - Continuous
   - Integer

.. kotlin:enum_class:: BooleanParam

Possible boolean parameters.

   - NumericalEmphasis

.. kotlin:enum_class:: DoubleParam

Possible double parameters.

   - Cutoff
   - MIPGap
   - MIPGapAbs
   - ObjDif
   - RelObjDif
   - TimeLimit

.. kotlin:enum_class:: IntParam

Possible integer parameters.

   - LogToConsole
   - PopulateLim
   - RootAlg
   - Threads

.. kotlin:enum_class:: LongParam

Possible long parameters.

   - IntSolLim
   - IterLimit

.. kotlin:enum_class:: StringParam

Possible String parameters.

   - LogFile

