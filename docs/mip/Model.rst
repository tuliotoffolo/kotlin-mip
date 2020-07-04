Model
==============================


.. kotlin:class:: Model

Model class

Creates a Mixed-Integer Linear Programming Model. The default model optimization direction is
Minimization. To store and optimize the model the MIP package automatically searches and
connects in runtime to the dynamic library of some MIP solver installed on your computer.
Nowadays Gurobi and CBC are supported. The solver is automatically selected, but you can
force the selection of a specific solver with the parameter [solverName].


   .. kotlin:constructor:: constructor(name: String = "Model",  sense String , override var solverName String )
   
    Main constructor
    
   
   .. kotlin:fun:: validateOptimizationResult(): Boolean
   
    Checks the consistency of the optimization results, i.e., if the solution(s) produced by
    the MIP solver respects all constraints and variable values are within acceptable bounds,
    being integral whenever requested.
    
   

