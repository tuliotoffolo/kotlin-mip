# J-MIP (JVM Mixed-Integer Linear Programming) Tools

🛑 **Package is currently under construction and has (very) limited functionality**

Package website: **http://j-mip.com**

J-MIP is a collection of Java/Kotlin tools for the modeling and solution of Mixed-Integer Linear programs (MIPs). 
The entire package is inspired by [Python-MIP](https://github.com/coin-or/python-mip), which was also developed by us.
The main goal here is to provide the tool for a higher-performance  environment such as the one represented by the Java Virtual Machine -- which may be necessary to develop certain hybrid heuristics, for example. 

Just like Python-MIP, J-MIP provides access to advanced solver features such as cut generation, lazy constraints, MIPstarts and solution Pools. 
While Python-MIP provides a very fast platform for modeling MIP problems, depending on the kind of algorithm you aim at implementing, performance-wise it may be worth porting your code to J-MIP.

Some of the main features of J-MIP are:

* high level modeling: write your MIP models in Java or Kotlin as easily as in high level languages such as [MathProg](https://en.wikibooks.org/wiki/GLPK/GMPL_(MathProg)). This is particularly true for Kotlin programmers: operator overloading makes it very easy to write constraints;

* full featured:
    - cut generators and lazy constraints: work with strong formulations with a large number of constraints by generating only the required inequalities during the branch and cut search;
    - solution pool: query the elite set of solutions found during the search;
    - MIPStart: use a problem dependent heuristic to generate initial feasible solutions for the MIP search.

* fast: the J-MIP package calls directly the native dynamic loadable library of the installed solver using JNR (Java Native Runtime); models are efficiently stored and optimized by the solver and J-MIP transparently handles all communication with your Java/Kolin code;

* multi solver: Much like Python-MIP, J-MIP was written to be deeply integrated with the C libraries of the open-source COIN-OR Branch-&-Cut [CBC](https://projects.coin-or.org/Cbc) solver and the commercial solvers [Gurobi](http://www.gurobi.com/) and [Cplex](http://www.cplex.com/); all details of communicating with different solvers are handled by J-MIP and you write only one solver independent code;

* written mostly in Kotlin 1.4.

## Examples

Many J-MIP mip.examples will be documented at https://docs.jmip.com/en/latest/mip.examples.html 

Very soon the code of these mip.examples and additional ones (published in tutorials) can be downloaded at https://github.com/coin-or/jmip/tree/master/mip.examples

## Documentation
 
The full J-MIP documentation will be available at https://docs.j-mip.com/en/latest/

A PDF version will be available at: https://j-mip.readthedocs.io/_/downloads/en/latest/pdf/

## Mailing list

Questions, suggestions and development news can be posted to the [J-MIP google group](https://groups.google.com/forum/#!forum/jmip).

