package mip.solvers;

import jnr.ffi.Runtime;
import jnr.ffi.*;
import jnr.ffi.annotations.*;
import jnr.ffi.types.*;

import java.io.*;

public interface CbcJnrJava {

    public static CbcJnrJava loadLibrary() {
        String library;
        String libLocation = System.getProperty("user.dir") + File.separatorChar;

        Platform platform = Platform.getNativePlatform();
        if (platform.getOS() == Platform.OS.DARWIN) {
            library = "cbc-c-darwin-x86-64.dylib";
            libLocation += "libraries";
        }
        else if (platform.getOS() == Platform.OS.LINUX) {
            library = "cbc-c-darwin-x86-64.dylib";
            libLocation += "libraries";
        }
        else if (platform.getOS() == Platform.OS.WINDOWS) {
            library = "libCbcSolver-0.dll";
            libLocation += "libraries\\win64";
        }
        else {
            library = null;
            libLocation = null;
        }

        return LibraryLoader
          .create(CbcJnrJava.class)
          .failImmediately()
          .load(libLocation + File.separatorChar + library);
    }

    /*
     * ! Dobule parameters
     * enum DblParam {
     *     DBL_PARAM_PRIMAL_TOL    = 0,  ! Tollerance to consider a solution feasible in the linear programming solver.
     *     DBL_PARAM_DUAL_TOL      = 1,  ! Tollerance for a solution to be considered optimal in the linear programming solver.
     *     DBL_PARAM_ZERO_TOL      = 2,  ! Coefficients less that this value will be ignored when reading instances
     *     DBL_PARAM_INT_TOL       = 3,  ! Maximum allowed distance from integer value for a variable to be considered integral
     *     DBL_PARAM_PRESOLVE_TOL  = 4,  ! Tollerance used in the presolver, should be increased if the pre-solver is declaring infeasible a feasible problem
     *     DBL_PARAM_TIME_LIMIT    = 5,  ! Time limit in seconds
     *     DBL_PARAM_PSI           = 6,  ! Two dimensional princing factor in the Positive Edge pivot strategy.
     *     DBL_PARAM_CUTOFF        = 7,  ! Only search for solutions with cost less-or-equal to this value.
     *     DBL_PARAM_ALLOWABLE_GAP = 8,  ! Allowable gap between the lower and upper bound to conclude the search
     *     DBL_PARAM_GAP_RATIO     = 9   ! Stops the search when the difference between the upper and lower bound is less than this fraction of the larger value
     * };
     * #define N_DBL_PARAMS 10
     */
    public final int DBL_PARAM_PRIMAL_TOL = 0;
    public final int DBL_PARAM_DUAL_TOL = 1;
    public final int DBL_PARAM_ZERO_TOL = 2;
    public final int DBL_PARAM_INT_TOL = 3;
    public final int DBL_PARAM_PRESOLVE_TOL = 4;
    public final int DBL_PARAM_TIME_LIMIT = 5;
    public final int DBL_PARAM_PSI = 6;
    public final int DBL_PARAM_CUTOFF = 7;
    public final int DBL_PARAM_ALLOWABLE_GAP = 8;
    public final int DBL_PARAM_GAP_RATIO = 9;
    public final int N_DBL_PARAMS = 10;

    /*
     * ! Integer parameters
     * enum IntParam {
     *     INT_PARAM_PERT_VALUE       = 0,  ! Method of perturbation, -5000 to 102, default 50
     *     INT_PARAM_IDIOT            = 1,  ! Parameter of the "idiot" method to try to produce an initial feasible basis. -1 let the solver decide if this should be applied; 0 deactivates it and >0 sets number of passes.
     *     INT_PARAM_STRONG_BRANCHING = 2,  ! Number of variables to be evaluated in strong branching.
     *     INT_PARAM_CUT_DEPTH        = 3,  ! Sets the application of cuts to every depth multiple of this value. -1, the default value, let the solve decide.
     *     INT_PARAM_MAX_NODES        = 4,  ! Maximum number of nodes to be explored in the search tree
     *     INT_PARAM_NUMBER_BEFORE    = 5,  ! Number of branche before trusting pseudocodes computed in strong branching.
     *     INT_PARAM_FPUMP_ITS        = 6,  ! Maximum number of iterations in the feasibility pump method.
     *     INT_PARAM_MAX_SOLS         = 7,  ! Maximum number of solutions generated during the search. Stops the search when this number of solutions is found.
     *     INT_PARAM_CUT_PASS_IN_TREE = 8,  ! Maxinum number of cuts passes in the search tree (with the exception of the root node). Default 1.
     *     INT_PARAM_THREADS          = 9,  ! Number of threads that can be used in the branch-and-bound method.
     *     INT_PARAM_CUT_PASS         = 10, ! Number of cut passes in the root node. Default -1, solver decides
     *     INT_PARAM_LOG_LEVEL        = 11, ! Verbosity level, from 0 to 2
     *     INT_PARAM_MAX_SAVED_SOLS   = 12, ! Size of the pool to save the best solutions found during the search.
     *     INT_PARAM_MULTIPLE_ROOTS   = 13, ! Multiple root passes to get additional cuts and solutions.
     *     INT_PARAM_ROUND_INT_VARS   = 14, ! If integer variables should be round to remove small infeasibilities. This can increase the overall amount of infeasibilities in problems with both continuous and integer variables
     *     INT_PARAM_RANDOM_SEED      = 15, ! When solving LP and MIP, randomization is used to break ties in some decisions. This changes the random seed so that multiple executions can produce different results
     *     INT_PARAM_ELAPSED_TIME     = 16  ! When =1 use elapsed (wallclock) time, otherwise use CPU time
     * };
     * #define N_INT_PARAMS 17
     */
    public final int INT_PARAM_PERT_VALUE = 0;
    public final int INT_PARAM_IDIOT = 1;
    public final int INT_PARAM_STRONG_BRANCHING = 2;
    public final int INT_PARAM_CUT_DEPTH = 3;
    public final int INT_PARAM_MAX_NODES = 4;
    public final int INT_PARAM_NUMBER_BEFORE = 5;
    public final int INT_PARAM_FPUMP_ITS = 6;
    public final int INT_PARAM_MAX_SOLS = 7;
    public final int INT_PARAM_CUT_PASS_IN_TREE = 8;
    public final int INT_PARAM_THREADS = 9;
    public final int INT_PARAM_CUT_PASS = 10;
    public final int INT_PARAM_LOG_LEVEL = 11;
    public final int INT_PARAM_MAX_SAVED_SOLS = 12;
    public final int INT_PARAM_MULTIPLE_ROOTS = 13;
    public final int INT_PARAM_ROUND_INT_VARS = 14;
    public final int INT_PARAM_RANDOM_SEED = 15;
    public final int INT_PARAM_ELAPSED_TIME = 16;
    public final int N_INT_PARAMS = 17;

    /*
     * enum LPMethod {
     *     LPM_Auto    = 0,  ! Solver will decide automatically which method to use
     *     LPM_Dual    = 1,  ! Dual simplex
     *     LPM_Primal  = 2,  ! Primal simplex
     *     LPM_Barrier = 3   ! The barrier algorithm.
     * };
     */
    public final int LPM_Auto = 0;
    public final int LPM_Dual = 1;
    public final int LPM_Primal = 2;
    public final int LPM_Barrier = 3;

    /*
     * enum CutType {
     *     CT_Gomory         = 0,  ! Gomory cuts obtained from the tableau
     *     CT_MIR            = 1,  ! Mixed integer rounding cuts
     *     CT_ZeroHalf       = 2,  ! Zero-half cuts
     *     CT_Clique         = 3,  ! Clique cuts
     *     CT_KnapsackCover  = 4,  ! Knapsack cover cuts
     *     CT_LiftAndProject = 5   ! Lift and project cuts
     * };
     */
    public final int CT_Gomory = 0;
    public final int CT_MIR = 1;
    public final int CT_ZeroHalf = 2;
    public final int CT_Clique = 3;
    public final int CT_KnapsackCover = 4;
    public final int CT_LiftAndProject = 5;


    /**
     * void *Cbc_newModel();
     */
    public Pointer Cbc_newModel();

    /**
     * void Cbc_readLp(Cbc_Model *model, const char *file);
     */
    public void Cbc_readLp(@In Pointer model, @In @Transient String file);

    /**
     * void Cbc_readMps(Cbc_Model *model, const char *file);
     */
    public void Cbc_readMps(@In Pointer model, @In @Transient String file);

    /**
     * char Cbc_supportsGzip();
     */
    public byte Cbc_supportsGzip();

    /**
     * char Cbc_supportsBzip2();
     */
    public byte Cbc_supportsBzip2();

    /**
     * void Cbc_writeLp(Cbc_Model *model, const char *file);
     */
    public void Cbc_writeLp(@In Pointer model, @In @Transient String file);

    /**
     * void Cbc_writeMps(Cbc_Model *model, const char *file);
     */
    public void Cbc_writeMps(@In Pointer model, @In @Transient String file);

    /**
     * int Cbc_getNumCols(Cbc_Model *model);
     */
    public int Cbc_getNumCols(@In Pointer model);

    /**
     * int Cbc_getNumRows(Cbc_Model *model);
     */
    public int Cbc_getNumRows(@In Pointer model);

    /**
     * int Cbc_getNumIntegers(Cbc_Model *model);
     */
    public int Cbc_getNumIntegers(@In Pointer model);

    /**
     * int Cbc_getNumElements(Cbc_Model *model);
     */
    public int Cbc_getNumElements(@In Pointer model);

    /**
     * int Cbc_getRowNz(Cbc_Model *model, int row);
     */
    public int Cbc_getRowNz(@In Pointer model, int row);

    /**
     * int *Cbc_getRowIndices(Cbc_Model *model, int row);
     */
    public Pointer Cbc_getRowIndices(@In Pointer model, int row);

    /**
     * double *Cbc_getRowCoeffs(Cbc_Model *model, int row);
     */
    public Pointer Cbc_getRowCoeffs(@In Pointer model, int row);

    /**
     * double Cbc_getRowRHS(Cbc_Model *model, int row);
     */
    public double Cbc_getRowRHS(@In Pointer model, int row);

    /**
     * void Cbc_setRowRHS(Cbc_Model *model, int row, double rhs);
     */
    public void Cbc_setRowRHS(@In Pointer model, int row, double rhs);

    /**
     * char Cbc_getRowSense(Cbc_Model *model, int row);
     */
    public byte Cbc_getRowSense(@In Pointer model, int row);

    /**
     * const double *Cbc_getRowActivity(Cbc_Model *model);
     */
    public Pointer Cbc_getRowActivity(@In Pointer model);

    /**
     * const double *Cbc_getRowSlack(Cbc_Model *model);
     */
    public Pointer Cbc_getRowSlack(@In Pointer model);

    /**
     * int Cbc_getColNz(Cbc_Model *model, int col);
     */
    public int Cbc_getColNz(@In Pointer model, int col);

    /**
     * int *Cbc_getColIndices(Cbc_Model *model, int col);
     */
    public Pointer Cbc_getColIndices(@In Pointer model, int col);

    /**
     * double *Cbc_getColCoeffs(Cbc_Model *model, int col);
     */
    public Pointer Cbc_getColCoeffs(@In Pointer model, int col);

    /**
     * void Cbc_addCol(Cbc_Model *model, const char *name, double lb, double ub, double obj, char
     * isInteger, int nz, int *rows, double *coefs);
     */
    public void Cbc_addCol(@In Pointer model, @In @Transient String name, double lb, double ub, double obj, byte isInteger, int nz, @In @Transient int[] rows, @In @Transient double[] coefs);

    /**
     * void Cbc_addRow(Cbc_Model *model, const char *name, int nz, const int *cols, const double
     * *coefs, char sense, double rhs);
     */
    public void Cbc_addRow(@In Pointer model, @In @Transient String name, int nz, @In @Transient int[] cols, @In @Transient double[] coefs, byte sense, double rhs);

    /**
     * void Cbc_addLazyConstraint(Cbc_Model *model, int nz, int *cols, double *coefs, char sense,
     * double rhs);
     */
    public void Cbc_addLazyConstraint(@In Pointer model, int nz, @In @Transient int[] cols, @In @Transient double[] coefs, byte sense, double rhs);

    /**
     * void Cbc_addSOS(Cbc_Model *model, int numRows, const int *rowStarts, const int *colIndices,
     * const double *weights, const int type);
     */
    public void Cbc_addSOS(@In Pointer model, int numRows, @In @Transient int[] rowStarts, @In @Transient int[] colIndices, @In @Transient double[] weights, @In @Transient int type);

    /**
     * int Cbc_numberSOS(Cbc_Model *model);
     */
    public int Cbc_numberSOS(@In Pointer model);

    /**
     * void Cbc_setObjCoeff(Cbc_Model *model, int index, double value);
     */
    public void Cbc_setObjCoeff(@In Pointer model, int index, double value);

    /**
     * double Cbc_getObjSense(Cbc_Model *model);
     */
    public double Cbc_getObjSense(@In Pointer model);

    /**
     * const double *Cbc_getObjCoefficients(Cbc_Model *model);
     */
    public Pointer Cbc_getObjCoefficients(@In Pointer model);

    /**
     * const double *Cbc_getColSolution(Cbc_Model *model);
     */
    public Pointer Cbc_getColSolution(@In Pointer model);

    /**
     * const double *Cbc_getReducedCost(Cbc_Model *model);
     */
    public Pointer Cbc_getReducedCost(@In Pointer model);

    /**
     * double *Cbc_bestSolution(Cbc_Model *model);
     */
    public Pointer Cbc_bestSolution(@In Pointer model);

    /**
     * int Cbc_numberSavedSolutions(Cbc_Model *model);
     */
    public int Cbc_numberSavedSolutions(@In Pointer model);

    /**
     * const double *Cbc_savedSolution(Cbc_Model *model, int whichSol);
     */
    public Pointer Cbc_savedSolution(@In Pointer model, int whichSol);

    /**
     * double Cbc_savedSolutionObj(Cbc_Model *model, int whichSol);
     */
    public double Cbc_savedSolutionObj(@In Pointer model, int whichSol);

    /**
     * double Cbc_getObjValue(Cbc_Model *model);
     */
    public double Cbc_getObjValue(@In Pointer model);

    /**
     * void Cbc_setObjSense(Cbc_Model *model, double sense);
     */
    public void Cbc_setObjSense(@In Pointer model, double sense);

    /**
     * int Cbc_isProvenOptimal(Cbc_Model *model);
     */
    public int Cbc_isProvenOptimal(@In Pointer model);

    /**
     * int Cbc_isProvenInfeasible(Cbc_Model *model);
     */
    public int Cbc_isProvenInfeasible(@In Pointer model);

    /**
     * int Cbc_isContinuousUnbounded(Cbc_Model *model);
     */
    public int Cbc_isContinuousUnbounded(@In Pointer model);

    /**
     * int Cbc_isAbandoned(Cbc_Model *model);
     */
    public int Cbc_isAbandoned(@In Pointer model);

    /**
     * const double *Cbc_getColLower(Cbc_Model *model);
     */
    public Pointer Cbc_getColLower(@In Pointer model);

    /**
     * const double *Cbc_getColUpper(Cbc_Model *model);
     */
    public Pointer Cbc_getColUpper(@In Pointer model);

    /**
     * double Cbc_getColObj(Cbc_Model *model, int colIdx);
     */
    public double Cbc_getColObj(@In Pointer model, int colIdx);

    /**
     * double Cbc_getColLB(Cbc_Model *model, int colIdx);
     */
    public double Cbc_getColLB(@In Pointer model, int colIdx);

    /**
     * double Cbc_getColUB(Cbc_Model *model, int colIdx);
     */
    public double Cbc_getColUB(@In Pointer model, int colIdx);

    /**
     * void Cbc_setColLower(Cbc_Model *model, int index, double value);
     */
    public void Cbc_setColLower(@In Pointer model, int index, double value);

    /**
     * void Cbc_setColUpper(Cbc_Model *model, int index, double value);
     */
    public void Cbc_setColUpper(@In Pointer model, int index, double value);

    /**
     * int Cbc_isInteger(Cbc_Model *model, int i);
     */
    public int Cbc_isInteger(@In Pointer model, int i);

    /**
     * void Cbc_getColName(Cbc_Model *model, int iColumn, char *name, size_t maxLength);
     */
    public void Cbc_getColName(@In Pointer model, int iColumn, String name, @size_t long maxLength);

    /**
     * void Cbc_getRowName(Cbc_Model *model, int iRow, char *name, size_t maxLength);
     */
    public void Cbc_getRowName(@In Pointer model, int iRow, @Out String name, @size_t long maxLength);

    /**
     * void Cbc_setContinuous(Cbc_Model *model, int iColumn);
     */
    public void Cbc_setContinuous(@In Pointer model, int iColumn);

    /**
     * void Cbc_setInteger(Cbc_Model *model, int iColumn);
     */
    public void Cbc_setInteger(@In Pointer model, int iColumn);

    /**
     * void Cbc_setIntParam(Cbc_Model *model, enum IntParam which, const int val);
     */
    public void Cbc_setIntParam(@In Pointer model, int which, @In @Transient int val);

    /**
     * void Cbc_setDblParam(Cbc_Model *model, enum DblParam which, const double val);
     */
    public void Cbc_setDblParam(@In Pointer model, int which, @In @Transient double val);

    /**
     * void Cbc_setParameter(Cbc_Model *model, const char *name, const char *value);
     */
    public void Cbc_setParameter(@In Pointer model, @In @Transient String name, @In @Transient String value);

    /**
     * double Cbc_getCutoff(Cbc_Model *model);
     */
    public double Cbc_getCutoff(@In Pointer model);

    /**
     * void Cbc_setCutoff(Cbc_Model *model, double cutoff);
     */
    public void Cbc_setCutoff(@In Pointer model, double cutoff);

    /**
     * double Cbc_getAllowableGap(Cbc_Model *model);
     */
    public double Cbc_getAllowableGap(@In Pointer model);

    /**
     * void Cbc_setAllowableGap(Cbc_Model *model, double allowedGap);
     */
    public void Cbc_setAllowableGap(@In Pointer model, double allowedGap);

    /**
     * double Cbc_getAllowableFractionGap(Cbc_Model *model);
     */
    public double Cbc_getAllowableFractionGap(@In Pointer model);

    /**
     * void Cbc_setAllowableFractionGap(Cbc_Model *model, double allowedFracionGap);
     */
    public void Cbc_setAllowableFractionGap(@In Pointer model, double allowedFracionGap);

    /**
     * double Cbc_getAllowablePercentageGap(Cbc_Model *model);
     */
    public double Cbc_getAllowablePercentageGap(@In Pointer model);

    /**
     * void Cbc_setAllowablePercentageGap(Cbc_Model *model, double allowedPercentageGap);
     */
    public void Cbc_setAllowablePercentageGap(@In Pointer model, double allowedPercentageGap);

    /**
     * double Cbc_getMaximumSeconds(Cbc_Model *model);
     */
    public double Cbc_getMaximumSeconds(@In Pointer model);

    /**
     * void Cbc_setMaximumSeconds(Cbc_Model *model, double maxSeconds);
     */
    public void Cbc_setMaximumSeconds(@In Pointer model, double maxSeconds);

    /**
     * int Cbc_getMaximumNodes(Cbc_Model *model);
     */
    public int Cbc_getMaximumNodes(@In Pointer model);

    /**
     * void Cbc_setMaximumNodes(Cbc_Model *model, int maxNodes);
     */
    public void Cbc_setMaximumNodes(@In Pointer model, int maxNodes);

    /**
     * int Cbc_getMaximumSolutions(Cbc_Model *model);
     */
    public int Cbc_getMaximumSolutions(@In Pointer model);

    /**
     * void Cbc_setMaximumSolutions(Cbc_Model *model, int maxSolutions);
     */
    public void Cbc_setMaximumSolutions(@In Pointer model, int maxSolutions);

    /**
     * int Cbc_getLogLevel(Cbc_Model *model);
     */
    public int Cbc_getLogLevel(@In Pointer model);

    /**
     * void Cbc_setLogLevel(Cbc_Model *model, int logLevel);
     */
    public void Cbc_setLogLevel(@In Pointer model, int logLevel);

    /**
     * double Cbc_getBestPossibleObjValue(Cbc_Model *model);
     */
    public double Cbc_getBestPossibleObjValue(@In Pointer model);

    /**
     * void Cbc_setMIPStart(Cbc_Model *model, int count, const char **colNames, const double
     * colValues[]);
     */
    public void Cbc_setMIPStartI(@In Pointer model, int count, @In @Transient int colIdxs[], @In @Transient double colValues[]);

    /**
     * void Cbc_setLPmethod(Cbc_Model *model, enum LPMethod lpm );
     */
    public void Cbc_setLPmethod(@In Pointer model, int lpm);

    /**
     * void Cbc_updateConflictGraph( Cbc_Model *model );
     */
    public void Cbc_updateConflictGraph(@In Pointer model);

    /**
     * const void *Cbc_conflictGraph( Cbc_Model *model );
     */
    public Pointer Cbc_conflictGraph(@In Pointer model);

    /**
     * int Cbc_solve(Cbc_Model *model);
     */
    public int Cbc_solve(@In Pointer model);

    /**
     * int Cbc_solveLinearProgram(Cbc_Model *model);
     */
    public int Cbc_solveLinearProgram(@In Pointer model);

    /**
     * void Cgl_generateCuts( void *osiClpSolver, enum CutType ct, void *osiCuts, int strength );
     */
    public void Cgl_generateCuts(@In @Transient Pointer osiClpSolver, int ct, @In @Transient Pointer osiCuts, int strength);

    /**
     * void Cbc_strengthenPacking(Cbc_Model *model);
     */
    public void Cbc_strengthenPacking(@In Pointer model);

    /**
     * void Cbc_strengthenPackingRows(Cbc_Model *model, size_t n, const size_t rows[]);
     */
    public void Cbc_strengthenPackingRows(@In Pointer model, @size_t long n, @In @Transient @size_t long rows[]);

    /**
     * void *Cbc_getSolverPtr(Cbc_Model *model);
     */
    public Pointer Cbc_getSolverPtr(@In Pointer model);

    /**
     * void *Cbc_deleteModel(Cbc_Model *model);
     */
    public Pointer Cbc_deleteModel(@In Pointer model);

    /**
     * int Osi_getNumIntegers( void *osi );
     */
    public int Osi_getNumIntegers(@In @Transient Pointer osi);

    /**
     * const double *Osi_getReducedCost( void *osi );
     */
    public Pointer Osi_getReducedCost(@In @Transient Pointer osi);

    /**
     * const double *Osi_getObjCoefficients();
     */
    public Pointer Osi_getObjCoefficients();

    /**
     * double Osi_getObjSense();
     */
    public double Osi_getObjSense();

    /**
     * void *Osi_newSolver();
     */
    public Pointer Osi_newSolver();

    /**
     * void Osi_deleteSolver( void *osi );
     */
    public void Osi_deleteSolver(@In @Transient Pointer osi);

    /**
     * void Osi_initialSolve(void *osi);
     */
    public void Osi_initialSolve(@In @Transient Pointer osi);

    /**
     * void Osi_resolve(void *osi);
     */
    public void Osi_resolve(@In @Transient Pointer osi);

    /**
     * void Osi_branchAndBound(void *osi);
     */
    public void Osi_branchAndBound(@In @Transient Pointer osi);

    /**
     * char Osi_isAbandoned(void *osi);
     */
    public byte Osi_isAbandoned(@In @Transient Pointer osi);

    /**
     * char Osi_isProvenOptimal(void *osi);
     */
    public byte Osi_isProvenOptimal(@In @Transient Pointer osi);

    /**
     * char Osi_isProvenPrimalInfeasible(void *osi);
     */
    public byte Osi_isProvenPrimalInfeasible(@In @Transient Pointer osi);

    /**
     * char Osi_isProvenDualInfeasible(void *osi);
     */
    public byte Osi_isProvenDualInfeasible(@In @Transient Pointer osi);

    /**
     * char Osi_isPrimalObjectiveLimitReached(void *osi);
     */
    public byte Osi_isPrimalObjectiveLimitReached(@In @Transient Pointer osi);

    /**
     * char Osi_isDualObjectiveLimitReached(void *osi);
     */
    public byte Osi_isDualObjectiveLimitReached(@In @Transient Pointer osi);

    /**
     * char Osi_isIterationLimitReached(void *osi);
     */
    public byte Osi_isIterationLimitReached(@In @Transient Pointer osi);

    /**
     * double Osi_getObjValue( void *osi );
     */
    public double Osi_getObjValue(@In @Transient Pointer osi);

    /**
     * void Osi_setColUpper (void *osi, int elementIndex, double ub);
     */
    public void Osi_setColUpper(@In @Transient Pointer osi, int elementIndex, double ub);

    /**
     * void Osi_setColLower(void *osi, int elementIndex, double lb);
     */
    public void Osi_setColLower(@In @Transient Pointer osi, int elementIndex, double lb);

    /**
     * int Osi_getNumCols( void *osi );
     */
    public int Osi_getNumCols(@In @Transient Pointer osi);

    /**
     * void Osi_getColName( void *osi, int i, char *name, int maxLen );
     */
    public void Osi_getColName(@In @Transient Pointer osi, int i, String name, int maxLen);

    /**
     * const double *Osi_getColLower( void *osi );
     */
    public Pointer Osi_getColLower(@In @Transient Pointer osi);

    /**
     * const double *Osi_getColUpper( void *osi );
     */
    public Pointer Osi_getColUpper(@In @Transient Pointer osi);

    /**
     * int Osi_isInteger( void *osi, int col );
     */
    public int Osi_isInteger(@In @Transient Pointer osi, int col);

    /**
     * int Osi_getNumRows( void *osi );
     */
    public int Osi_getNumRows(@In @Transient Pointer osi);

    /**
     * int Osi_getRowNz(void *osi, int row);
     */
    public int Osi_getRowNz(@In @Transient Pointer osi, int row);

    /**
     * const int *Osi_getRowIndices(void *osi, int row);
     */
    public Pointer Osi_getRowIndices(@In @Transient Pointer osi, int row);

    /**
     * const double *Osi_getRowCoeffs(void *osi, int row);
     */
    public Pointer Osi_getRowCoeffs(@In @Transient Pointer osi, int row);

    /**
     * double Osi_getRowRHS(void *osi, int row);
     */
    public double Osi_getRowRHS(@In @Transient Pointer osi, int row);

    /**
     * char Osi_getRowSense(void *osi, int row);
     */
    public byte Osi_getRowSense(@In @Transient Pointer osi, int row);

    /**
     * void Osi_setObjCoef(void *osi, int index, double obj);
     */
    public void Osi_setObjCoef(@In @Transient Pointer osi, int index, double obj);

    /**
     * void Osi_setObjSense(void *osi, double sense);
     */
    public void Osi_setObjSense(@In @Transient Pointer osi, double sense);

    /**
     * const double *Osi_getColSolution(void *osi);
     */
    public Pointer Osi_getColSolution(@In @Transient Pointer osi);

    /**
     * void *OsiCuts_new();
     */
    public Pointer OsiCuts_new();

    /**
     * void OsiCuts_addRowCut( void *osiCuts, int nz, const int *idx, const double *coef, char
     * sense, double rhs );
     */
    public void OsiCuts_addRowCut(@In @Transient Pointer osiCuts, int nz, @In @Transient int[] idx, @In @Transient double[] coef, byte sense, double rhs);

    /**
     * void OsiCuts_addGlobalRowCut( void *osiCuts, int nz, const int *idx, const double *coef,
     * char sense, double rhs );
     */
    public void OsiCuts_addGlobalRowCut(@In @Transient Pointer osiCuts, int nz, @In @Transient int[] idx, @In @Transient double[] coef, byte sense, double rhs);

    /**
     * int OsiCuts_sizeRowCuts( void *osiCuts );
     */
    public int OsiCuts_sizeRowCuts(@In @Transient Pointer osiCuts);

    /**
     * int OsiCuts_nzRowCut( void *osiCuts, int iRowCut );
     */
    public int OsiCuts_nzRowCut(@In @Transient Pointer osiCuts, int iRowCut);

    /**
     * const int * OsiCuts_idxRowCut( void *osiCuts, int iRowCut );
     */
    public Pointer OsiCuts_idxRowCut(@In @Transient Pointer osiCuts, int iRowCut);

    /**
     * const double *OsiCuts_coefRowCut( void *osiCuts, int iRowCut );
     */
    public Pointer OsiCuts_coefRowCut(@In @Transient Pointer osiCuts, int iRowCut);

    /**
     * double OsiCuts_rhsRowCut( void *osiCuts, int iRowCut );
     */
    public double OsiCuts_rhsRowCut(@In @Transient Pointer osiCuts, int iRowCut);

    /**
     * char OsiCuts_senseRowCut( void *osiCuts, int iRowCut );
     */
    public byte OsiCuts_senseRowCut(@In @Transient Pointer osiCuts, int iRowCut);

    /**
     * void OsiCuts_delete( void *osiCuts );
     */
    public void OsiCuts_delete(@In @Transient Pointer osiCuts);

    /**
     * void Osi_addCol(void *osi, const char *name, double lb, double ub, double obj, char
     * isInteger, int nz, int *rows, double *coefs);
     */
    public void Osi_addCol(@In @Transient Pointer osi, @In @Transient String name, double lb, double ub, double obj, byte isInteger, int nz, @Pinned int[] rows, @Pinned double[] coefs);

    /**
     * void Osi_addRow(void *osi, const char *name, int nz, const int *cols, const double *coefs,
     * char sense, double rhs);
     */
    public void Osi_addRow(@In @Transient Pointer osi, @In @Transient String name, int nz, @Pinned int[] cols, @Pinned double[] coefs, byte sense, double rhs);

    /**
     * void Cbc_deleteRows(Cbc_Model *model, int numRows, const int rows[]);
     */
    public void Cbc_deleteRows(@In Pointer model, int numRows, @In @Transient int rows[]);

    /**
     * void Cbc_deleteRows(@In Pointer model, int numRows, const int rows[]);
     */
    public void Cbc_deleteCols(@In Pointer model, int numCols, @In @Transient int cols[]);

    /**
     * void Cbc_storeNameIndexes(Cbc_Model *model, char _store);
     */
    public void Cbc_storeNameIndexes(@In Pointer model, byte _store);

    /**
     * int Cbc_getColNameIndex(Cbc_Model *model, const char *name);
     */
    public int Cbc_getColNameIndex(@In Pointer model, @In @Transient String name);

    /**
     * int Cbc_getRowNameIndex(Cbc_Model *model, const char *name);
     */
    public int Cbc_getRowNameIndex(@In Pointer model, @In @Transient String name);

    /**
     * void Cbc_problemName(Cbc_Model *model, int maxNumberCharacters, char *array);
     */
    public void Cbc_problemName(@In Pointer model, int maxNumberCharacters, String array);

    /**
     * int Cbc_setProblemName(Cbc_Model *model, const char *array);
     */
    public int Cbc_setProblemName(@In Pointer model, @In @Transient String array);

    /**
     * double Cbc_getPrimalTolerance(Cbc_Model *model);
     */
    public double Cbc_getPrimalTolerance(@In Pointer model);

    /**
     * void Cbc_setPrimalTolerance(Cbc_Model *model, double tol);
     */
    public void Cbc_setPrimalTolerance(@In Pointer model, double tol);

    /**
     * double Cbc_getDualTolerance(Cbc_Model *model);
     */
    public double Cbc_getDualTolerance(@In Pointer model);

    /**
     * void Cbc_setDualTolerance(Cbc_Model *model, double tol);
     */
    public void Cbc_setDualTolerance(@In Pointer model, double tol);

    /**
     * void Cbc_addCutCallback(Cbc_Model *model, cbc_cut_callback cutcb, const char *name, void
     * *appData, int howOften, char atSolution );
     */
    // TODO public void Cbc_addCutCallback(@In Pointer model, cbc_cut_callback cutcb, @In String name, Pointer appData, int howOften, char atSolution);

    /**
     * void Cbc_addIncCallback(void *model, cbc_incumbent_callback inccb, void *appData );
     */
    // TODO public void Cbc_addIncCallback(@In Pointer model, cbc_incumbent_callback inccb, Pointer appData);

    /**
     * void Cbc_registerCallBack(Cbc_Model *model, cbc_callback userCallBack);
     */
    // TODO public void Cbc_registerCallBack(@In Pointer model, cbc_callback userCallBack);

    /**
     * void Cbc_addProgrCallback(void *model, cbc_progress_callback prgcbc, void *appData);
     */
    // TODO public void Cbc_addProgrCallback(@In Pointer model, cbc_progress_callback prgcbc, Pointer appData);

    /**
     * void Cbc_clearCallBack(Cbc_Model *model);
     */
    public void Cbc_clearCallBack(@In Pointer model);

    /**
     * const double *Cbc_getRowPrice(Cbc_Model *model);
     */
    public Pointer Cbc_getRowPrice(@In Pointer model);

    /**
     * const double *Osi_getRowPrice(void *osi);
     */
    public Pointer Osi_getRowPrice(@In @Transient Pointer osi);

    /**
     * double Osi_getIntegerTolerance(void *osi);
     */
    public double Osi_getIntegerTolerance(@In @Transient Pointer osi);

    /**
     * void Osi_checkCGraph( void *osi );
     */
    public void Osi_checkCGraph(@In @Transient Pointer osi);

    /**
     * const void * Osi_CGraph( void *osi );
     */
    public Pointer Osi_CGraph(@In @Transient Pointer osi);

    /**
     * size_t CG_nodes( void *cgraph );
     */
    public @size_t long CG_nodes(Pointer cgraph);

    /**
     * char CG_conflicting( void *cgraph, int n1, int n2 );
     */
    public byte CG_conflicting(Pointer cgraph, int n1, int n2);

    /**
     * double CG_density( void *cgraph );
     */
    public double CG_density(Pointer cgraph);

    /**
     * CGNeighbors CG_conflictingNodes(Cbc_Model *model, void *cgraph, size_t node);
     */
    public CGNeighbors CG_conflictingNodes(@In Pointer model, @In @Transient Pointer cgraph, @size_t long node);


    /**
     * typedef struct { size_t n; const size_t *neigh; } CGNeighbors;
     */
    public static final class CGNeighbors extends Struct {

        public final size_t n = new size_t();
        public final Pointer neigh = new Pointer();

        public CGNeighbors(Runtime runtime) {
            super(runtime);
        }
    }

    /*
     * TODO: implement callbacks
     *
     * typedef int(*cbc_progress_callback)(void *model,
     *                                     int phase,
     *                                     int step,
     *                                     const char *phaseName,
     *                                     double seconds,
     *                                     double lb,
     *                                     double ub,
     *                                     int nint,
     *                                     int *vecint,
     *                                     void *cbData
     *                                     );
     *
     *
     *     typedef void(*cbc_callback)(void *model, int msgno, int ndouble,
     *         const double *dvec, int nint, const int *ivec,
     *         int nchar, char **cvec);
     *
     *     typedef void(*cbc_cut_callback)(void *osiSolver,
     *         void *osiCuts, void *appdata);
     *
     *     typedef int (*cbc_incumbent_callback)(void *cbcModel,
     *         double obj, int nz,
     *         char **vnames, double *x, void *appData);
     */
}


// Quick conversion regex:
//
// ([a-z A-Z*(),_0-9]+);\n\n   ->   /\*\*\n \* $1;\n */\npublic $1;\n\n
// (const )*void[ ]*\*  ->  Pointer
// etc...
