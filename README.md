# J-MIP (JVM Mixed-Integer Linear Programming) Tools

Package website: **http://j-mip.com**

J-MIP is a collection of Java/Kotlin tools for the modeling and solution
of Mixed-Integer Linear programs (MIPs). The entire package is inspired by
[Python-MIP](https://github.com/coin-or/python-mip), which was also developed
by us. The main goal is to provide the tool for a higher-performance 
environment such as the one represented by the Java Virtual Machine. Just like
Python-MIP, J-MIP provides access to advanced solver features such as cut generation,
lazy constraints, MIPstarts and solution Pools. At first, it is not necessary
to port Python-MIP models to J-MIP. However, depending on the algorithm you aim
at implementing, performance-wise it may be totally worth it.

Some of the main features of J-MIP are:

* high level modeling: write your MIP models in Java or Kotlin as easily as in
  high level languages such as
  [MathProg](https://en.wikibooks.org/wiki/GLPK/GMPL_(MathProg)). Particularly
  for Kotlin programmers: operator overloading makes it very easy to write linear
  expressions;

* full featured:
    - cut generators and lazy constraints: work with strong formulations with a
    large number of constraints by generating only the required inequalities
    during the branch and cut search;
    - solution pool: query the elite set of solutions found during the search;
    - MIPStart: use a problem dependent heuristic to generate initial feasible
    solutions for the MIP search.

* fast: the J-MIP package calls directly the native dynamic loadable library of 
  the installed solver using JNA (Java Native Access); models are efficiently 
  stored and optimized by the solver and J-MIP transparently handles all 
  communication with your Java/Kolin code;

* multi solver: Much like Python-MIP, J-MIP was written to be deeply integrated 
  with the C libraries of the open-source COIN-OR Branch-&-Cut
  [CBC](https://projects.coin-or.org/Cbc) solver and the commercial solver
  [Gurobi](http://www.gurobi.com/); all details of communicating with 
  different solvers are handled by J-MIP and you write only one solver 
  independent code;

* written in Kotlin 1.4.

## Examples

Many J-MIP examples will be documented at https://docs.jmip.com/en/latest/examples.html 

Very soon the code of these examples and additional ones (published in tutorials) can be downloaded at https://github.com/coin-or/jmip/tree/master/examples

## Documentation
 
The full J-MIP documentation is available at
https://docs.j-mip.com/en/latest/

A PDF version is also available:
https://j-mip.readthedocs.io/_/downloads/en/latest/pdf/

## Mailing list

Questions, suggestions and development news can be posted at the [J-MIP google group](https://groups.google.com/forum/#!forum/jmip).
 
## Build status

[![Build Status](https://api.travis-ci.org/coin-or/jmip.svg?branch=master)](https://travis-ci.org/coin-or/jmip)
