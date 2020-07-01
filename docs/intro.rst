.. _chapIntro:

Introduction
============

The J-MIP package provides tools for modeling and solving
`Mixed-Integer Linear Programming Problems
<https://en.wikipedia.org/wiki/Integer_programming>`_ (MIPs) [Wols98]_ in
Java/Kotlin. The default installation includes the `COIN-OR Linear Programming
Solver - CLP <http://github.com/coin-or/Clp>`_, which is currently the
`fastest <http://plato.asu.edu/ftp/lpsimp.html>`_  open source linear
programming solver and the `COIN-OR Branch-and-Cut solver - CBC
<https://github.com/coin-or/Cbc>`_, a highly configurable MIP solver. It
also works with the state-of-the-art `Gurobi <http://www.gurobi.com/>`_
MIP solver. J-MIP was written in Kotlin in a way to ensure it can be easily
used by Java programmers.

Modeling examples for some applications can be viewed in :ref:`<chapExamples>`.

J-MIP eases the development of high-performance MIP based solvers for
custom applications by providing a tight integration with the
branch-and-cut algorithms of the supported solvers. Strong formulations
with an exponential number of constraints can be handled by the inclusion of
:ref:`Cut Generators <cut-generation-label>` and :ref:`Lazy Constraints <lazy-constraints-label>`.
Heuristics can be integrated for :ref:`providing initial feasible solutions
<mipstart-label>` to the MIP solver. These features can be used in all available solver
engines, CBC, CPLEX and GUROBI, without changing a single line of code.
