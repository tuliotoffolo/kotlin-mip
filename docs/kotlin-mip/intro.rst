.. _chapIntro:

Introduction
============

The Kotlin-MIP package is inspired by `Python-MIP <http://www.python-mip.com>`_, developed by the same authors.
It provides tools for modeling and solving `Mixed-Integer Linear Programming Problems <https://en.wikipedia.org/wiki/Integer_programming>`_ (MIPs) [Wols98]_ in Java/Kotlin.
The default installation includes the `COIN-OR Linear Programming Solver - CLP <http://github.com/coin-or/Clp>`_, which is currently the `fastest <http://plato.asu.edu/ftp/lpsimp.html>`_  open source linear programming solver and the `COIN-OR Branch-and-Cut solver - CBC <https://github.com/coin-or/Cbc>`_, a highly configurable MIP solver.
It also works with the state-of-the-art `Gurobi <http://www.gurobi.com/>`_ MIP solver.
Kotlin-MIP was written in Kotlin in a way to ensure it can be easily used by Java programmers.
In fact, **Java-MIP** is an alias for Kotlin-MIP in which all documentation and examples are written in Java.

In the modeling layer, models can be written very concisely, as in high-level mathematical programming languages such as `MathProg <http://gusek.sourceforge.net/gmpl.pdf>`_.
Many examples and applications can be viewed in :ref:`Modelling Examples <chapExamples>`.

Kotlin-MIP eases the development of high-performance MIP based solvers for custom applications by providing a tight integration with the branch-and-cut algorithms of the supported solvers.
Strong formulations with an exponential number of constraints can be handled by the inclusion of
:ref:`Cut Generators <cut-generation-label>` and :ref:`Lazy Constraints <lazy-constraints-label>`.
Heuristics can be integrated for :ref:`providing initial feasible solutions <mipstart-label>` to the MIP solver.
These features can be used in all supported solver engines, CBC, Cplex and Gurobi, without changing a single line of code.

This document is organized as follows: in the :ref:`next Chapter <chapInstall>` installation and configuration instructions for different platforms are presented.
In :ref:`Chapter 3 <chapQuick>` an overview of some common model creation and optimization code are included.
Commented examples are included in :ref:`Chapter 4 <chapExamples>`.
:ref:`Chapter 5 <chapCustom>` includes some common solver customizations that can be done to improve the performance of application specific solvers.
Finally, the detailed reference information for the main classes is included in :ref:`Chapter
6 <chapClasses>`.

Acknowledgments
---------------

We would like to thank the `GOAL <http://goal.ufop.br>`_ research group and the `Department of Computing <http://www.decom.ufop.br>`_ of the `Federal University of Ouro Preto (UFOP) <https://www.ufop.br/>`_ for the support.

