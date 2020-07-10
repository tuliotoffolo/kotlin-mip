//package mip.solvers;
//
//import org.bytedeco.javacpp.annotation.*;
//
//@Platform(include="/opt/cbc/github/Cbc/src/Cbc_C_Interface.h")
//@Namespace("CbcJni")
//public class CbcJni {
//
//    //static {
//    //    String library;
//    //    String libLocation = System.getProperty("user.dir") + File.separatorChar;
//    //
//    //    Platform platform = Platform.getNativePlatform();
//    //    if (platform.getOS() == Platform.OS.DARWIN) {
//    //        library = "cbc-c-darwin-x86-64.dylib";
//    //        libLocation += "libraries";
//    //    }
//    //    else if (platform.getOS() == Platform.OS.LINUX) {
//    //        library = "cbc-c-darwin-x86-64.dylib";
//    //        libLocation += "libraries";
//    //    }
//    //    else if (platform.getOS() == Platform.OS.WINDOWS) {
//    //        library = "libCbcSolver-0.dll";
//    //        libLocation += "libraries\\win64";
//    //    }
//    //    else {
//    //        library = null;
//    //        libLocation = null;
//    //    }
//    //
//    //    System.load(libLocation + File.separatorChar + library);
//    //}
//
//    /*
//     * ! Dobule parameters
//     * enum DblParam {
//     *     DBL_PARAM_PRIMAL_TOL    = 0,  ! Tollerance to consider a solution feasible in the linear programming solver.
//     *     DBL_PARAM_DUAL_TOL      = 1,  ! Tollerance for a solution to be considered optimal in the linear programming solver.
//     *     DBL_PARAM_ZERO_TOL      = 2,  ! Coefficients less that this value will be ignored when reading instances
//     *     DBL_PARAM_INT_TOL       = 3,  ! Maximum allowed distance from integer value for a variable to be considered integral
//     *     DBL_PARAM_PRESOLVE_TOL  = 4,  ! Tollerance used in the presolver, should be increased if the pre-solver is declaring infeasible a feasible problem
//     *     DBL_PARAM_TIME_LIMIT    = 5,  ! Time limit in seconds
//     *     DBL_PARAM_PSI           = 6,  ! Two dimensional princing factor in the Positive Edge pivot strategy.
//     *     DBL_PARAM_CUTOFF        = 7,  ! Only search for solutions with cost less-or-equal to this value.
//     *     DBL_PARAM_ALLOWABLE_GAP = 8,  ! Allowable gap between the lower and upper bound to conclude the search
//     *     DBL_PARAM_GAP_RATIO     = 9   ! Stops the search when the difference between the upper and lower bound is less than this fraction of the larger value
//     * };
//     * #define N_DBL_PARAMS 10
//     */
//    public final int DBL_PARAM_PRIMAL_TOL = 0;
//    public final int DBL_PARAM_DUAL_TOL = 1;
//    public final int DBL_PARAM_ZERO_TOL = 2;
//    public final int DBL_PARAM_INT_TOL = 3;
//    public final int DBL_PARAM_PRESOLVE_TOL = 4;
//    public final int DBL_PARAM_TIME_LIMIT = 5;
//    public final int DBL_PARAM_PSI = 6;
//    public final int DBL_PARAM_CUTOFF = 7;
//    public final int DBL_PARAM_ALLOWABLE_GAP = 8;
//    public final int DBL_PARAM_GAP_RATIO = 9;
//    public final int N_DBL_PARAMS = 10;
//
//    /*
//     * ! Integer parameters
//     * enum IntParam {
//     *     INT_PARAM_PERT_VALUE       = 0,  ! Method of perturbation, -5000 to 102, default 50
//     *     INT_PARAM_IDIOT            = 1,  ! Parameter of the "idiot" method to try to produce an initial feasible basis. -1 let the solver decide if this should be applied; 0 deactivates it and >0 sets number of passes.
//     *     INT_PARAM_STRONG_BRANCHING = 2,  ! Number of variables to be evaluated in strong branching.
//     *     INT_PARAM_CUT_DEPTH        = 3,  ! Sets the application of cuts to every depth multiple of this value. -1, the default value, let the solve decide.
//     *     INT_PARAM_MAX_NODES        = 4,  ! Maximum number of nodes to be explored in the search tree
//     *     INT_PARAM_NUMBER_BEFORE    = 5,  ! Number of branche before trusting pseudocodes computed in strong branching.
//     *     INT_PARAM_FPUMP_ITS        = 6,  ! Maximum number of iterations in the feasibility pump method.
//     *     INT_PARAM_MAX_SOLS         = 7,  ! Maximum number of solutions generated during the search. Stops the search when this number of solutions is found.
//     *     INT_PARAM_CUT_PASS_IN_TREE = 8,  ! Maxinum number of cuts passes in the search tree (with the exception of the root node). Default 1.
//     *     INT_PARAM_THREADS          = 9,  ! Number of threads that can be used in the branch-and-bound method.
//     *     INT_PARAM_CUT_PASS         = 10, ! Number of cut passes in the root node. Default -1, solver decides
//     *     INT_PARAM_LOG_LEVEL        = 11, ! Verbosity level, from 0 to 2
//     *     INT_PARAM_MAX_SAVED_SOLS   = 12, ! Size of the pool to save the best solutions found during the search.
//     *     INT_PARAM_MULTIPLE_ROOTS   = 13, ! Multiple root passes to get additional cuts and solutions.
//     *     INT_PARAM_ROUND_INT_VARS   = 14, ! If integer variables should be round to remove small infeasibilities. This can increase the overall amount of infeasibilities in problems with both continuous and integer variables
//     *     INT_PARAM_RANDOM_SEED      = 15, ! When solving LP and MIP, randomization is used to break ties in some decisions. This changes the random seed so that multiple executions can produce different results
//     *     INT_PARAM_ELAPSED_TIME     = 16  ! When =1 use elapsed (wallclock) time, otherwise use CPU time
//     * };
//     * #define N_INT_PARAMS 17
//     */
//    public final int INT_PARAM_PERT_VALUE = 0;
//    public final int INT_PARAM_IDIOT = 1;
//    public final int INT_PARAM_STRONG_BRANCHING = 2;
//    public final int INT_PARAM_CUT_DEPTH = 3;
//    public final int INT_PARAM_MAX_NODES = 4;
//    public final int INT_PARAM_NUMBER_BEFORE = 5;
//    public final int INT_PARAM_FPUMP_ITS = 6;
//    public final int INT_PARAM_MAX_SOLS = 7;
//    public final int INT_PARAM_CUT_PASS_IN_TREE = 8;
//    public final int INT_PARAM_THREADS = 9;
//    public final int INT_PARAM_CUT_PASS = 10;
//    public final int INT_PARAM_LOG_LEVEL = 11;
//    public final int INT_PARAM_MAX_SAVED_SOLS = 12;
//    public final int INT_PARAM_MULTIPLE_ROOTS = 13;
//    public final int INT_PARAM_ROUND_INT_VARS = 14;
//    public final int INT_PARAM_RANDOM_SEED = 15;
//    public final int INT_PARAM_ELAPSED_TIME = 16;
//    public final int N_INT_PARAMS = 17;
//
//    /*
//     * enum LPMethod {
//     *     LPM_Auto    = 0,  ! Solver will decide automatically which method to use
//     *     LPM_Dual    = 1,  ! Dual simplex
//     *     LPM_Primal  = 2,  ! Primal simplex
//     *     LPM_Barrier = 3   ! The barrier algorithm.
//     * };
//     */
//    public final int LPM_Auto = 0;
//    public final int LPM_Dual = 1;
//    public final int LPM_Primal = 2;
//    public final int LPM_Barrier = 3;
//
//    /*
//     * enum CutType {
//     *     CT_Gomory         = 0,  ! Gomory cuts obtained from the tableau
//     *     CT_MIR            = 1,  ! Mixed integer rounding cuts
//     *     CT_ZeroHalf       = 2,  ! Zero-half cuts
//     *     CT_Clique         = 3,  ! Clique cuts
//     *     CT_KnapsackCover  = 4,  ! Knapsack cover cuts
//     *     CT_LiftAndProject = 5   ! Lift and project cuts
//     * };
//     */
//    public final int CT_Gomory = 0;
//    public final int CT_MIR = 1;
//    public final int CT_ZeroHalf = 2;
//    public final int CT_Clique = 3;
//    public final int CT_KnapsackCover = 4;
//    public final int CT_LiftAndProject = 5;
//
//
//    /**
//     * void *Cbc_newModel();
//     */
//    public native long Cbc_newModel();
//
//    /**
//     * void Cbc_readLp(Cbc_Model *model, const char *file);
//     */
//    public native void Cbc_readLp(long model, String file);
//
//    /**
//     * void Cbc_readMps(Cbc_Model *model, const char *file);
//     */
//    public native void Cbc_readMps(long model, String file);
//
//    /**
//     * char Cbc_supportsGzip();
//     */
//    public native byte Cbc_supportsGzip();
//
//    /**
//     * char Cbc_supportsBzip2();
//     */
//    public native byte Cbc_supportsBzip2();
//
//    /**
//     * void Cbc_writeLp(Cbc_Model *model, const char *file);
//     */
//    public native void Cbc_writeLp(long model, String file);
//
//    /**
//     * void Cbc_writeMps(Cbc_Model *model, const char *file);
//     */
//    public native void Cbc_writeMps(long model, String file);
//
//    /**
//     * int Cbc_getNumCols(Cbc_Model *model);
//     */
//    public native int Cbc_getNumCols(long model);
//
//    /**
//     * int Cbc_getNumRows(Cbc_Model *model);
//     */
//    public native int Cbc_getNumRows(long model);
//
//    /**
//     * int Cbc_getNumIntegers(Cbc_Model *model);
//     */
//    public native int Cbc_getNumIntegers(long model);
//
//    /**
//     * int Cbc_getNumElements(Cbc_Model *model);
//     */
//    public native int Cbc_getNumElements(long model);
//
//    /**
//     * int Cbc_getRowNz(Cbc_Model *model, int row);
//     */
//    public native int Cbc_getRowNz(long model, int row);
//
//    /**
//     * int *Cbc_getRowIndices(Cbc_Model *model, int row);
//     */
//    public native long Cbc_getRowIndices(long model, int row);
//
//    /**
//     * double *Cbc_getRowCoeffs(Cbc_Model *model, int row);
//     */
//    public native long Cbc_getRowCoeffs(long model, int row);
//
//    /**
//     * double Cbc_getRowRHS(Cbc_Model *model, int row);
//     */
//    public native double Cbc_getRowRHS(long model, int row);
//
//    /**
//     * void Cbc_setRowRHS(Cbc_Model *model, int row, double rhs);
//     */
//    public native void Cbc_setRowRHS(long model, int row, double rhs);
//
//    /**
//     * char Cbc_getRowSense(Cbc_Model *model, int row);
//     */
//    public native byte Cbc_getRowSense(long model, int row);
//
//    /**
//     * const double *Cbc_getRowActivity(Cbc_Model *model);
//     */
//    public native long Cbc_getRowActivity(long model);
//
//    /**
//     * const double *Cbc_getRowSlack(Cbc_Model *model);
//     */
//    public native long Cbc_getRowSlack(long model);
//
//    /**
//     * int Cbc_getColNz(Cbc_Model *model, int col);
//     */
//    public native int Cbc_getColNz(long model, int col);
//
//    /**
//     * int *Cbc_getColIndices(Cbc_Model *model, int col);
//     */
//    public native long Cbc_getColIndices(long model, int col);
//
//    /**
//     * double *Cbc_getColCoeffs(Cbc_Model *model, int col);
//     */
//    public native long Cbc_getColCoeffs(long model, int col);
//
//    /**
//     * void Cbc_addCol(Cbc_Model *model, const char *name, double lb, double ub, double obj, char
//     * isInteger, int nz, int *rows, double *coefs);
//     */
//    public native void Cbc_addCol(long model, String name, double lb, double ub, double obj, byte isInteger, int nz, int[] rows, double[] coefs);
//
//    /**
//     * void Cbc_addRow(Cbc_Model *model, const char *name, int nz, const int *cols, const double
//     * *coefs, char sense, double rhs);
//     */
//    public native void Cbc_addRow(long model, String name, int nz, int[] cols, double[] coefs, byte sense, double rhs);
//
//    /**
//     * void Cbc_addLazyConstraint(Cbc_Model *model, int nz, int *cols, double *coefs, char sense,
//     * double rhs);
//     */
//    public native void Cbc_addLazyConstraint(long model, int nz, int[] cols, double[] coefs, byte sense, double rhs);
//
//    /**
//     * void Cbc_addSOS(Cbc_Model *model, int numRows, const int *rowStarts, const int *colIndices,
//     * const double *weights, const int type);
//     */
//    public native void Cbc_addSOS(long model, int numRows, int[] rowStarts, int[] colIndices, double[] weights, int type);
//
//    /**
//     * int Cbc_numberSOS(Cbc_Model *model);
//     */
//    public native int Cbc_numberSOS(long model);
//
//    /**
//     * void Cbc_setObjCoeff(Cbc_Model *model, int index, double value);
//     */
//    public native void Cbc_setObjCoeff(long model, int index, double value);
//
//    /**
//     * double Cbc_getObjSense(Cbc_Model *model);
//     */
//    public native double Cbc_getObjSense(long model);
//
//    /**
//     * const double *Cbc_getObjCoefficients(Cbc_Model *model);
//     */
//    public native long Cbc_getObjCoefficients(long model);
//
//    /**
//     * const double *Cbc_getColSolution(Cbc_Model *model);
//     */
//    public native long Cbc_getColSolution(long model);
//
//    /**
//     * const double *Cbc_getReducedCost(Cbc_Model *model);
//     */
//    public native long Cbc_getReducedCost(long model);
//
//    /**
//     * double *Cbc_bestSolution(Cbc_Model *model);
//     */
//    public native long Cbc_bestSolution(long model);
//
//    /**
//     * int Cbc_numberSavedSolutions(Cbc_Model *model);
//     */
//    public native int Cbc_numberSavedSolutions(long model);
//
//    /**
//     * const double *Cbc_savedSolution(Cbc_Model *model, int whichSol);
//     */
//    public native long Cbc_savedSolution(long model, int whichSol);
//
//    /**
//     * double Cbc_savedSolutionObj(Cbc_Model *model, int whichSol);
//     */
//    public native double Cbc_savedSolutionObj(long model, int whichSol);
//
//    /**
//     * double Cbc_getObjValue(Cbc_Model *model);
//     */
//    public native double Cbc_getObjValue(long model);
//
//    /**
//     * void Cbc_setObjSense(Cbc_Model *model, double sense);
//     */
//    public native void Cbc_setObjSense(long model, double sense);
//
//    /**
//     * int Cbc_isProvenOptimal(Cbc_Model *model);
//     */
//    public native int Cbc_isProvenOptimal(long model);
//
//    /**
//     * int Cbc_isProvenInfeasible(Cbc_Model *model);
//     */
//    public native int Cbc_isProvenInfeasible(long model);
//
//    /**
//     * int Cbc_isContinuousUnbounded(Cbc_Model *model);
//     */
//    public native int Cbc_isContinuousUnbounded(long model);
//
//    /**
//     * int Cbc_isAbandoned(Cbc_Model *model);
//     */
//    public native int Cbc_isAbandoned(long model);
//
//    /**
//     * const double *Cbc_getColLower(Cbc_Model *model);
//     */
//    public native long Cbc_getColLower(long model);
//
//    /**
//     * const double *Cbc_getColUpper(Cbc_Model *model);
//     */
//    public native long Cbc_getColUpper(long model);
//
//    /**
//     * double Cbc_getColObj(Cbc_Model *model, int colIdx);
//     */
//    public native double Cbc_getColObj(long model, int colIdx);
//
//    /**
//     * double Cbc_getColLB(Cbc_Model *model, int colIdx);
//     */
//    public native double Cbc_getColLB(long model, int colIdx);
//
//    /**
//     * double Cbc_getColUB(Cbc_Model *model, int colIdx);
//     */
//    public native double Cbc_getColUB(long model, int colIdx);
//
//    /**
//     * void Cbc_setColLower(Cbc_Model *model, int index, double value);
//     */
//    public native void Cbc_setColLower(long model, int index, double value);
//
//    /**
//     * void Cbc_setColUpper(Cbc_Model *model, int index, double value);
//     */
//    public native void Cbc_setColUpper(long model, int index, double value);
//
//    /**
//     * int Cbc_isInteger(Cbc_Model *model, int i);
//     */
//    public native int Cbc_isInteger(long model, int i);
//
//    /**
//     * void Cbc_getColName(Cbc_Model *model, int iColumn, char *name, size_t maxLength);
//     */
//    public native void Cbc_getColName(long model, int iColumn, String name, long maxLength);
//
//    /**
//     * void Cbc_getRowName(Cbc_Model *model, int iRow, char *name, size_t maxLength);
//     */
//    public native void Cbc_getRowName(long model, int iRow, String name, long maxLength);
//
//    /**
//     * void Cbc_setContinuous(Cbc_Model *model, int iColumn);
//     */
//    public native void Cbc_setContinuous(long model, int iColumn);
//
//    /**
//     * void Cbc_setInteger(Cbc_Model *model, int iColumn);
//     */
//    public native void Cbc_setInteger(long model, int iColumn);
//
//    /**
//     * void Cbc_setIntParam(Cbc_Model *model, enum IntParam which, const int val);
//     */
//    public native void Cbc_setIntParam(long model, int which, int val);
//
//    /**
//     * void Cbc_setDblParam(Cbc_Model *model, enum DblParam which, const double val);
//     */
//    public native void Cbc_setDblParam(long model, int which, double val);
//
//    /**
//     * void Cbc_setParameter(Cbc_Model *model, const char *name, const char *value);
//     */
//    public native void Cbc_setParameter(long model, String name, String value);
//
//    /**
//     * double Cbc_getCutoff(Cbc_Model *model);
//     */
//    public native double Cbc_getCutoff(long model);
//
//    /**
//     * void Cbc_setCutoff(Cbc_Model *model, double cutoff);
//     */
//    public native void Cbc_setCutoff(long model, double cutoff);
//
//    /**
//     * double Cbc_getAllowableGap(Cbc_Model *model);
//     */
//    public native double Cbc_getAllowableGap(long model);
//
//    /**
//     * void Cbc_setAllowableGap(Cbc_Model *model, double allowedGap);
//     */
//    public native void Cbc_setAllowableGap(long model, double allowedGap);
//
//    /**
//     * double Cbc_getAllowableFractionGap(Cbc_Model *model);
//     */
//    public native double Cbc_getAllowableFractionGap(long model);
//
//    /**
//     * void Cbc_setAllowableFractionGap(Cbc_Model *model, double allowedFracionGap);
//     */
//    public native void Cbc_setAllowableFractionGap(long model, double allowedFracionGap);
//
//    /**
//     * double Cbc_getAllowablePercentageGap(Cbc_Model *model);
//     */
//    public native double Cbc_getAllowablePercentageGap(long model);
//
//    /**
//     * void Cbc_setAllowablePercentageGap(Cbc_Model *model, double allowedPercentageGap);
//     */
//    public native void Cbc_setAllowablePercentageGap(long model, double allowedPercentageGap);
//
//    /**
//     * double Cbc_getMaximumSeconds(Cbc_Model *model);
//     */
//    public native double Cbc_getMaximumSeconds(long model);
//
//    /**
//     * void Cbc_setMaximumSeconds(Cbc_Model *model, double maxSeconds);
//     */
//    public native void Cbc_setMaximumSeconds(long model, double maxSeconds);
//
//    /**
//     * int Cbc_getMaximumNodes(Cbc_Model *model);
//     */
//    public native int Cbc_getMaximumNodes(long model);
//
//    /**
//     * void Cbc_setMaximumNodes(Cbc_Model *model, int maxNodes);
//     */
//    public native void Cbc_setMaximumNodes(long model, int maxNodes);
//
//    /**
//     * int Cbc_getMaximumSolutions(Cbc_Model *model);
//     */
//    public native int Cbc_getMaximumSolutions(long model);
//
//    /**
//     * void Cbc_setMaximumSolutions(Cbc_Model *model, int maxSolutions);
//     */
//    public native void Cbc_setMaximumSolutions(long model, int maxSolutions);
//
//    /**
//     * int Cbc_getLogLevel(Cbc_Model *model);
//     */
//    public native int Cbc_getLogLevel(long model);
//
//    /**
//     * void Cbc_setLogLevel(Cbc_Model *model, int logLevel);
//     */
//    public native void Cbc_setLogLevel(long model, int logLevel);
//
//    /**
//     * double Cbc_getBestPossibleObjValue(Cbc_Model *model);
//     */
//    public native double Cbc_getBestPossibleObjValue(long model);
//
//    /**
//     * void Cbc_setMIPStart(Cbc_Model *model, int count, const char **colNames, const double
//     * colValues[]);
//     */
//    public native void Cbc_setMIPStartI(long model, int count, int colIdxs[], double colValues[]);
//
//    /**
//     * void Cbc_setLPmethod(Cbc_Model *model, enum LPMethod lpm );
//     */
//    public native void Cbc_setLPmethod(long model, int lpm);
//
//    /**
//     * void Cbc_updateConflictGraph( Cbc_Model *model );
//     */
//    public native void Cbc_updateConflictGraph(long model);
//
//    /**
//     * const void *Cbc_conflictGraph( Cbc_Model *model );
//     */
//    public native long Cbc_conflictGraph(long model);
//
//    /**
//     * int Cbc_solve(Cbc_Model *model);
//     */
//    public native int Cbc_solve(long model);
//
//    /**
//     * int Cbc_solveLinearProgram(Cbc_Model *model);
//     */
//    public native int Cbc_solveLinearProgram(long model);
//
//    /**
//     * void Cgl_generateCuts( void *osiClpSolver, enum CutType ct, void *osiCuts, int strength );
//     */
//    public native void Cgl_generateCuts(long osiClpSolver, int ct, long osiCuts, int strength);
//
//    /**
//     * void Cbc_strengthenPacking(Cbc_Model *model);
//     */
//    public native void Cbc_strengthenPacking(long model);
//
//    /**
//     * void Cbc_strengthenPackingRows(Cbc_Model *model, size_t n, const size_t rows[]);
//     */
//    public native void Cbc_strengthenPackingRows(long model, long n, long rows[]);
//
//    /**
//     * void *Cbc_getSolverPtr(Cbc_Model *model);
//     */
//    public native long Cbc_getSolverPtr(long model);
//
//    /**
//     * void *Cbc_deleteModel(Cbc_Model *model);
//     */
//    public native long Cbc_deleteModel(long model);
//
//    /**
//     * int Osi_getNumIntegers( void *osi );
//     */
//    public native int Osi_getNumIntegers(long osi);
//
//    /**
//     * const double *Osi_getReducedCost( void *osi );
//     */
//    public native long Osi_getReducedCost(long osi);
//
//    /**
//     * const double *Osi_getObjCoefficients();
//     */
//    public native long Osi_getObjCoefficients();
//
//    /**
//     * double Osi_getObjSense();
//     */
//    public native double Osi_getObjSense();
//
//    /**
//     * void *Osi_newSolver();
//     */
//    public native long Osi_newSolver();
//
//    /**
//     * void Osi_deleteSolver( void *osi );
//     */
//    public native void Osi_deleteSolver(long osi);
//
//    /**
//     * void Osi_initialSolve(void *osi);
//     */
//    public native void Osi_initialSolve(long osi);
//
//    /**
//     * void Osi_resolve(void *osi);
//     */
//    public native void Osi_resolve(long osi);
//
//    /**
//     * void Osi_branchAndBound(void *osi);
//     */
//    public native void Osi_branchAndBound(long osi);
//
//    /**
//     * char Osi_isAbandoned(void *osi);
//     */
//    public native byte Osi_isAbandoned(long osi);
//
//    /**
//     * char Osi_isProvenOptimal(void *osi);
//     */
//    public native byte Osi_isProvenOptimal(long osi);
//
//    /**
//     * char Osi_isProvenPrimalInfeasible(void *osi);
//     */
//    public native byte Osi_isProvenPrimalInfeasible(long osi);
//
//    /**
//     * char Osi_isProvenDualInfeasible(void *osi);
//     */
//    public native byte Osi_isProvenDualInfeasible(long osi);
//
//    /**
//     * char Osi_isPrimalObjectiveLimitReached(void *osi);
//     */
//    public native byte Osi_isPrimalObjectiveLimitReached(long osi);
//
//    /**
//     * char Osi_isDualObjectiveLimitReached(void *osi);
//     */
//    public native byte Osi_isDualObjectiveLimitReached(long osi);
//
//    /**
//     * char Osi_isIterationLimitReached(void *osi);
//     */
//    public native byte Osi_isIterationLimitReached(long osi);
//
//    /**
//     * double Osi_getObjValue( void *osi );
//     */
//    public native double Osi_getObjValue(long osi);
//
//    /**
//     * void Osi_setColUpper (void *osi, int elementIndex, double ub);
//     */
//    public native void Osi_setColUpper(long osi, int elementIndex, double ub);
//
//    /**
//     * void Osi_setColLower(void *osi, int elementIndex, double lb);
//     */
//    public native void Osi_setColLower(long osi, int elementIndex, double lb);
//
//    /**
//     * int Osi_getNumCols( void *osi );
//     */
//    public native int Osi_getNumCols(long osi);
//
//    /**
//     * void Osi_getColName( void *osi, int i, char *name, int maxLen );
//     */
//    public native void Osi_getColName(long osi, int i, String name, int maxLen);
//
//    /**
//     * const double *Osi_getColLower( void *osi );
//     */
//    public native long Osi_getColLower(long osi);
//
//    /**
//     * const double *Osi_getColUpper( void *osi );
//     */
//    public native long Osi_getColUpper(long osi);
//
//    /**
//     * int Osi_isInteger( void *osi, int col );
//     */
//    public native int Osi_isInteger(long osi, int col);
//
//    /**
//     * int Osi_getNumRows( void *osi );
//     */
//    public native int Osi_getNumRows(long osi);
//
//    /**
//     * int Osi_getRowNz(void *osi, int row);
//     */
//    public native int Osi_getRowNz(long osi, int row);
//
//    /**
//     * const int *Osi_getRowIndices(void *osi, int row);
//     */
//    public native long Osi_getRowIndices(long osi, int row);
//
//    /**
//     * const double *Osi_getRowCoeffs(void *osi, int row);
//     */
//    public native long Osi_getRowCoeffs(long osi, int row);
//
//    /**
//     * double Osi_getRowRHS(void *osi, int row);
//     */
//    public native double Osi_getRowRHS(long osi, int row);
//
//    /**
//     * char Osi_getRowSense(void *osi, int row);
//     */
//    public native byte Osi_getRowSense(long osi, int row);
//
//    /**
//     * void Osi_setObjCoef(void *osi, int index, double obj);
//     */
//    public native void Osi_setObjCoef(long osi, int index, double obj);
//
//    /**
//     * void Osi_setObjSense(void *osi, double sense);
//     */
//    public native void Osi_setObjSense(long osi, double sense);
//
//    /**
//     * const double *Osi_getColSolution(void *osi);
//     */
//    public native long Osi_getColSolution(long osi);
//
//    /**
//     * void *OsiCuts_new();
//     */
//    public native long OsiCuts_new();
//
//    /**
//     * void OsiCuts_addRowCut( void *osiCuts, int nz, const int *idx, const double *coef, char
//     * sense, double rhs );
//     */
//    public native void OsiCuts_addRowCut(long osiCuts, int nz, int[] idx, double[] coef, byte sense, double rhs);
//
//    /**
//     * void OsiCuts_addGlobalRowCut( void *osiCuts, int nz, const int *idx, const double *coef,
//     * char sense, double rhs );
//     */
//    public native void OsiCuts_addGlobalRowCut(long osiCuts, int nz, int[] idx, double[] coef, byte sense, double rhs);
//
//    /**
//     * int OsiCuts_sizeRowCuts( void *osiCuts );
//     */
//    public native int OsiCuts_sizeRowCuts(long osiCuts);
//
//    /**
//     * int OsiCuts_nzRowCut( void *osiCuts, int iRowCut );
//     */
//    public native int OsiCuts_nzRowCut(long osiCuts, int iRowCut);
//
//    /**
//     * const int * OsiCuts_idxRowCut( void *osiCuts, int iRowCut );
//     */
//    public native long OsiCuts_idxRowCut(long osiCuts, int iRowCut);
//
//    /**
//     * const double *OsiCuts_coefRowCut( void *osiCuts, int iRowCut );
//     */
//    public native long OsiCuts_coefRowCut(long osiCuts, int iRowCut);
//
//    /**
//     * double OsiCuts_rhsRowCut( void *osiCuts, int iRowCut );
//     */
//    public native double OsiCuts_rhsRowCut(long osiCuts, int iRowCut);
//
//    /**
//     * char OsiCuts_senseRowCut( void *osiCuts, int iRowCut );
//     */
//    public native byte OsiCuts_senseRowCut(long osiCuts, int iRowCut);
//
//    /**
//     * void OsiCuts_delete( void *osiCuts );
//     */
//    public native void OsiCuts_delete(long osiCuts);
//
//    /**
//     * void Osi_addCol(void *osi, const char *name, double lb, double ub, double obj, char
//     * isInteger, int nz, int *rows, double *coefs);
//     */
//    public native void Osi_addCol(long osi, String name, double lb, double ub, double obj, byte isInteger, int nz, int[] rows, double[] coefs);
//
//    /**
//     * void Osi_addRow(void *osi, const char *name, int nz, const int *cols, const double *coefs,
//     * char sense, double rhs);
//     */
//    public native void Osi_addRow(long osi, String name, int nz, int[] cols, double[] coefs, byte sense, double rhs);
//
//    /**
//     * void Cbc_deleteRows(Cbc_Model *model, int numRows, const int rows[]);
//     */
//    public native void Cbc_deleteRows(long model, int numRows, int rows[]);
//
//    /**
//     * void Cbc_deleteRows( long model, int numRows, const int rows[]);
//     */
//    public native void Cbc_deleteCols(long model, int numCols, int cols[]);
//
//    /**
//     * void Cbc_storeNameIndexes(Cbc_Model *model, char _store);
//     */
//    public native void Cbc_storeNameIndexes(long model, byte _store);
//
//    /**
//     * int Cbc_getColNameIndex(Cbc_Model *model, const char *name);
//     */
//    public native int Cbc_getColNameIndex(long model, String name);
//
//    /**
//     * int Cbc_getRowNameIndex(Cbc_Model *model, const char *name);
//     */
//    public native int Cbc_getRowNameIndex(long model, String name);
//
//    /**
//     * void Cbc_problemName(Cbc_Model *model, int maxNumberCharacters, char *array);
//     */
//    public native void Cbc_problemName(long model, int maxNumberCharacters, String array);
//
//    /**
//     * int Cbc_setProblemName(Cbc_Model *model, const char *array);
//     */
//    public native int Cbc_setProblemName(long model, String array);
//
//    /**
//     * double Cbc_getPrimalTolerance(Cbc_Model *model);
//     */
//    public native double Cbc_getPrimalTolerance(long model);
//
//    /**
//     * void Cbc_setPrimalTolerance(Cbc_Model *model, double tol);
//     */
//    public native void Cbc_setPrimalTolerance(long model, double tol);
//
//    /**
//     * double Cbc_getDualTolerance(Cbc_Model *model);
//     */
//    public native double Cbc_getDualTolerance(long model);
//
//    /**
//     * void Cbc_setDualTolerance(Cbc_Model *model, double tol);
//     */
//    public native void Cbc_setDualTolerance(long model, double tol);
//
//    /**
//     * void Cbc_addCutCallback(Cbc_Model *model, cbc_cut_callback cutcb, const char *name, void
//     * *appData, int howOften, char atSolution );
//     */
//    // TODO public native void Cbc_addCutCallback( long model, cbc_cut_callback cutcb,  String name, long appData, int howOften, char atSolution);
//
//    /**
//     * void Cbc_addIncCallback(void *model, cbc_incumbent_callback inccb, void *appData );
//     */
//    // TODO public native void Cbc_addIncCallback( long model, cbc_incumbent_callback inccb, long appData);
//
//    /**
//     * void Cbc_registerCallBack(Cbc_Model *model, cbc_callback userCallBack);
//     */
//    // TODO public native void Cbc_registerCallBack( long model, cbc_callback userCallBack);
//
//    /**
//     * void Cbc_addProgrCallback(void *model, cbc_progress_callback prgcbc, void *appData);
//     */
//    // TODO public native void Cbc_addProgrCallback( long model, cbc_progress_callback prgcbc, long appData);
//
//    /**
//     * void Cbc_clearCallBack(Cbc_Model *model);
//     */
//    public native void Cbc_clearCallBack(long model);
//
//    /**
//     * const double *Cbc_getRowPrice(Cbc_Model *model);
//     */
//    public native long Cbc_getRowPrice(long model);
//
//    /**
//     * const double *Osi_getRowPrice(void *osi);
//     */
//    public native long Osi_getRowPrice(long osi);
//
//    /**
//     * double Osi_getIntegerTolerance(void *osi);
//     */
//    public native double Osi_getIntegerTolerance(long osi);
//
//    /**
//     * void Osi_checkCGraph( void *osi );
//     */
//    public native void Osi_checkCGraph(long osi);
//
//    /**
//     * const void * Osi_CGraph( void *osi );
//     */
//    public native long Osi_CGraph(long osi);
//
//    /**
//     * size_t CG_nodes( void *cgraph );
//     */
//    public native long CG_nodes(long cgraph);
//
//    /**
//     * char CG_conflicting( void *cgraph, int n1, int n2 );
//     */
//    public native byte CG_conflicting(long cgraph, int n1, int n2);
//
//    /**
//     * double CG_density( void *cgraph );
//     */
//    public native double CG_density(long cgraph);
//
//    /**
//     * CGNeighbors CG_conflictingNodes(Cbc_Model *model, void *cgraph, size_t node);
//     */
//    //public native CGNeighbors CG_conflictingNodes( long model,   long cgraph, long node);
//
//
//    /**
//     * typedef struct { size_t n; const size_t *neigh; } CGNeighbors;
//     */
//    //public static final class CGNeighbors extends Struct {
//    //
//    //    public final size_t n = new size_t();
//    //    public final long neigh = new long();
//    //
//    //    public CGNeighbors(Runtime runtime) {
//    //        super(runtime);
//    //    }
//    //}
//
//    /*
//     * TODO: implement callbacks
//     *
//     * typedef int(*cbc_progress_callback)(void *model,
//     *                                     int phase,
//     *                                     int step,
//     *                                     const char *phaseName,
//     *                                     double seconds,
//     *                                     double lb,
//     *                                     double ub,
//     *                                     int nint,
//     *                                     int *vecint,
//     *                                     void *cbData
//     *                                     );
//     *
//     *
//     *     typedef void(*cbc_callback)(void *model, int msgno, int ndouble,
//     *         const double *dvec, int nint, const int *ivec,
//     *         int nchar, char **cvec);
//     *
//     *     typedef void(*cbc_cut_callback)(void *osiSolver,
//     *         void *osiCuts, void *appdata);
//     *
//     *     typedef int (*cbc_incumbent_callback)(void *cbcModel,
//     *         double obj, int nz,
//     *         char **vnames, double *x, void *appData);
//     */
//}
//
//
//// Quick conversion regex:
////
//// ([a-z A-Z*(),_0-9]+);\n\n   ->   /\*\*\n \* $1;\n */\npublic native $1;\n\n
//// (const )*void[ ]*\*  ->  long
//// etc...
