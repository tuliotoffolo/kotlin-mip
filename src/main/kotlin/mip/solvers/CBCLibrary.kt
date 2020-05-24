package mip.solvers

import jnr.ffi.*
import jnr.ffi.types.*
import jnr.ffi.annotations.Out
import jnr.ffi.annotations.Transient
import jnr.ffi.Struct.SignedLong
import jnr.ffi.Struct.time_t
import jnr.ffi.annotations.Delegate
import jnr.ffi.byref.PointerByReference

interface CBCLibrary {

    fun fflush(stream: Pointer?)

    // typedef struct {
    //     size_t n;
    //     const size_t *neigh;
    // } CGNeighbors;
    class CGNeighbors(runtime: Runtime) : Struct(runtime) {
        val n: size_t = super.size_t()
        val neight: Pointer = super.Pointer()
    }

    // typedef int(*cbc_progress_callback)(void *model,
    // int phase,
    // int step,
    // const char *phaseName,
    // double seconds,
    // double lb,
    // double ub,
    // int nint,
    // int *vecint,
    // void *cbData
    // );
    //
    // typedef void(*cbc_callback)(void *model, int msgno, int ndouble,
    // const double *dvec, int nint, const int *ivec,
    // int nchar, char **cvec);
    //
    // typedef void(*cbc_cut_callback)(void *osiSolver,
    // void *osiCuts, void *appdata);
    //
    // typedef int (*cbc_incumbent_callback)(void *cbcModel, double obj, int nz, char **vnames,
    //                                       double *x, void *appData);
    interface CBCIncumbentCallback {
        @Delegate
        fun incumbentCallback(cbcModel: Pointer, obj: Double, nz: Int, vnames: PointerByReference,
                              x: Pointer, appData: Pointer): Int
    }

    // typedef void Cbc_Model;

    // void *Cbc_newModel()
    fun Cbc_newModel(): Pointer

    // void Cbc_readLp(Cbc_Model *model, const char *file)
    fun Cbc_readLp(model: Pointer, file: String)

    // void Cbc_readMps(Cbc_Model *model, const char *file);
    fun Cbc_readMps(model: Pointer, file: String)

    // void Cbc_writeLp(Cbc_Model *model, const char *file);
    fun Cbc_writeLp(model: Pointer, file: String);

    // void Cbc_writeMps(Cbc_Model *model, const char *file);
    fun Cbc_writeMps(model: Pointer, file: String)

    // int Cbc_getNumCols(Cbc_Model *model);
    fun Cbc_getNumCols(model: Pointer): Int

    // int Cbc_getNumRows(Cbc_Model *model);
    fun Cbc_getNumRows(model: Pointer): Int

    // int Cbc_getNumIntegers(Cbc_Model *model);
    fun Cbc_getNumIntegers(model: Pointer): Int

    // int Cbc_getNumElements(Cbc_Model *model);
    fun Cbc_getNumElements(model: Pointer): Int

    // int Cbc_getRowNz(Cbc_Model *model, int row);
    fun Cbc_getRowNz(model: Pointer, row: Int): Int

    // int *Cbc_getRowIndices(Cbc_Model *model, int row);
    fun Cbc_getRowIndices(model: Pointer, row: Int): Pointer // -> int*

    // double *Cbc_getRowCoeffs(Cbc_Model *model, int row);
    fun Cbc_getRowCoeffs(model: Pointer, row: Int): Pointer // -> double*

    // double Cbc_getRowRHS(Cbc_Model *model, int row);
    fun Cbc_getRowRHS(model: Pointer, row: Int): Double

    // void Cbc_setRowRHS(Cbc_Model *model, int row, double rhs);
    fun Cbc_setRowRHS(model: Pointer, row: Int, rhs: Double)

    // char Cbc_getRowSense(Cbc_Model *model, int row);
    fun Cbc_getRowSense(model: Pointer, row: Int): Byte

    // const double *Cbc_getRowActivity(Cbc_Model *model);
    fun Cbc_getRowActivity(model: Pointer): Pointer // -> const double *

    // const double *Cbc_getRowSlack(Cbc_Model *model);
    fun Cbc_getRowSlack(model: Pointer): Pointer // -> const double*

    // int Cbc_getColNz(Cbc_Model *model, int col);
    fun Cbc_getColNz(model: Pointer, col: Int): Int

    // int *Cbc_getColIndices(Cbc_Model *model, int col);
    fun Cbc_getColIndices(model: Pointer, col: Int): Pointer // -> int*

    // double *Cbc_getColCoeffs(Cbc_Model *model, int col);
    fun Cbc_getColCoeffs(model: Pointer, col: Int): Pointer // -> double*

    // void Cbc_addCol(Cbc_Model *model, const char *name, double lb, double ub, double obj,
    //                 char isInteger, int nz, int *rows, double *coefs);
    fun Cbc_addCol(model: Pointer, name: String, lb: Double, ub: Double, obj: Double,
                   isInteger: Byte, nz: Int, rows: Pointer?, coeffs: Pointer?)

    // void Cbc_addRow(Cbc_Model *model, const char *name, int nz, const int *cols,
    //                 const double *coefs, char sense, double rhs);
    fun Cbc_addRow(model: Pointer, name: String, nz: Int, cols: Pointer, coeffs: Pointer,
                   sense: Byte, rhs: Double)

    // void Cbc_addLazyConstraint(Cbc_Model *model, int nz, int *cols, double *coefs, char sense,
    //                            double rhs);
    fun Cbc_addLazyConstraint(model: Pointer, nz: Int, cols: IntArray?, coefs: DoubleArray?,
                              sense: Byte, rhs: Double)

    // void Cbc_addSOS(Cbc_Model *model, int numRows, const int *rowStarts, const int *colIndices,
    //                 const double *weights, const int type);
    fun Cbc_addSOS(model: Pointer, numRows: Int, rowStarts: IntArray?, colIndices: IntArray,
                   weights: DoubleArray?, type: Int)

    // void Cbc_setObjCoeff(Cbc_Model *model, int index, double value);
    fun Cbc_setObjCoeff(model: Pointer, index: Int, value: Double)

    // double Cbc_getObjSense(Cbc_Model *model);
    fun Cbc_getObjSense(model: Pointer): Double

    // const double *Cbc_getObjCoefficients(Cbc_Model *model);
    fun Cbc_getObjCoefficients(model: Pointer): Pointer // -> const double*

    // const double *Cbc_getColSolution(Cbc_Model *model);
    fun Cbc_getColSolution(model: Pointer): Pointer // -> const double*

    // const double *Cbc_getReducedCost(Cbc_Model *model);
    fun Cbc_getReducedCost(model: Pointer): Pointer // -> const double*

    // double *Cbc_bestSolution(Cbc_Model *model);
    fun Cbc_bestSolution(model: Pointer): Pointer // -> double*

    // int Cbc_numberSavedSolutions(Cbc_Model *model);
    fun Cbc_numberSavedSolutions(model: Pointer): Int

    // const double *Cbc_savedSolution(Cbc_Model *model, int whichSol);
    fun Cbc_savedSolution(model: Pointer, whichSol: Int): Pointer // -> const double*

    // double Cbc_savedSolutionObj(Cbc_Model *model, int whichSol);
    fun Cbc_savedSolutionObj(model: Pointer, whichSol: Int): Double

    // double Cbc_getObjValue(Cbc_Model *model);
    fun Cbc_getObjValue(model: Pointer): Double

    // void Cbc_setObjSense(Cbc_Model *model, double sense);
    fun Cbc_setObjSense(model: Pointer, sense: Double)

    // int Cbc_isProvenOptimal(Cbc_Model *model);
    fun Cbc_isProvenOptimal(model: Pointer): Int

    // int Cbc_isProvenInfeasible(Cbc_Model *model);
    fun Cbc_isProvenInfeasible(model: Pointer): Int

    // int Cbc_isContinuousUnbounded(Cbc_Model *model);
    fun Cbc_isContinuousUnbounded(model: Pointer): Int

    // int Cbc_isAbandoned(Cbc_Model *model);
    fun Cbc_isAbandoned(model: Pointer): Int

    // const double *Cbc_getColLower(Cbc_Model *model);
    fun Cbc_getColLower(model: Pointer): Pointer // -> const double*

    // const double *Cbc_getColUpper(Cbc_Model *model);
    fun Cbc_getColUpper(model: Pointer): Pointer // -> const double*

    // double Cbc_getColObj(Cbc_Model *model, int colIdx);
    fun Cbc_getColObj(model: Pointer, colIdx: Int): Double

    // double Cbc_getColLB(Cbc_Model *model, int colIdx);
    fun Cbc_getColLB(model: Pointer, colIdx: Int): Double

    // double Cbc_getColUB(Cbc_Model *model, int colIdx);
    fun Cbc_getColUB(model: Pointer, colIdx: Int): Double

    // void Cbc_setColLower(Cbc_Model *model, int index, double value);
    fun Cbc_setColLower(model: Pointer, index: Int, value: Double)

    // void Cbc_setColUpper(Cbc_Model *model, int index, double value);
    fun Cbc_setColUpper(model: Pointer, index: Int, value: Double)

    // int Cbc_isInteger(Cbc_Model *model, int i);
    fun Cbc_isInteger(model: Pointer, i: Int): Int

    // void Cbc_getColName(Cbc_Model *model, int iColumn, char *name, size_t maxLength);
    fun Cbc_getColName(model: Pointer, iColumn: Int, name: Pointer, @size_t maxLength: Int)

    // void Cbc_getRowName(Cbc_Model *model, int iRow, char *name, size_t maxLength);
    fun Cbc_getRowName(model: Pointer, iRow: Int, name: Pointer, @size_t maxLength: Int)

    // void Cbc_setContinuous(Cbc_Model *model, int iColumn);
    fun Cbc_setContinuous(model: Pointer, iColumn: Int)

    // void Cbc_setInteger(Cbc_Model *model, int iColumn);
    fun Cbc_setInteger(model: Pointer, iColumn: Int)

    // void Cbc_setIntParam(Cbc_Model *model, enum IntParam which, const int val);
    fun Cbc_setIntParam(model: Pointer, intParam: Int, value: Int)

    // void Cbc_setDblParam(Cbc_Model *model, enum DblParam which, const double val);
    fun Cbc_setDblParam(model: Pointer, doubleParam: Int, value: Double)

    // void Cbc_setParameter(Cbc_Model *model, const char *name, const char *value);
    fun Cbc_setParameter(model: Pointer, name: String, value: String);

    // double Cbc_getCutoff(Cbc_Model *model);
    fun Cbc_getCutoff(model: Pointer): Double

    // void Cbc_setCutoff(Cbc_Model *model, double cutoff);
    //
    // double Cbc_getAllowableGap(Cbc_Model *model);
    //
    // void Cbc_setAllowableGap(Cbc_Model *model, double allowedGap);
    //
    // double Cbc_getAllowableFractionGap(Cbc_Model *model);
    //
    // void Cbc_setAllowableFractionGap(Cbc_Model *model,
    // double allowedFracionGap);
    //
    // double Cbc_getAllowablePercentageGap(Cbc_Model *model);
    //
    // void Cbc_setAllowablePercentageGap(Cbc_Model *model, double allowedPercentageGap);
    //
    // double Cbc_getMaximumSeconds(Cbc_Model *model);
    //
    // void Cbc_setMaximumSeconds(Cbc_Model *model, double maxSeconds);
    //
    // int Cbc_getMaximumNodes(Cbc_Model *model);
    //
    // void Cbc_setMaximumNodes(Cbc_Model *model, int maxNodes);
    //
    // int Cbc_getMaximumSolutions(Cbc_Model *model);
    //
    // void Cbc_setMaximumSolutions(Cbc_Model *model, int maxSolutions);
    //
    // int Cbc_getLogLevel(Cbc_Model *model);
    //
    // void Cbc_setLogLevel(Cbc_Model *model, int logLevel);
    //
    // double Cbc_getBestPossibleObjValue(Cbc_Model *model);
    fun Cbc_getBestPossibleObjValue(model: Pointer): Double;

    // void Cbc_setMIPStart(Cbc_Model *model, int count, const char **colNames,
    //                      const double colValues[]);
    //
    // void Cbc_setMIPStartI(Cbc_Model *model, int count, const int colIdxs[],
    //                       const double colValues[]);
    //
    // void Cbc_setLPmethod(Cbc_Model *model, enum LPMethod lpm );
    //
    // void Cbc_updateConflictGraph( Cbc_Model *model );
    //
    // const void *Cbc_conflictGraph( Cbc_Model *model );

    // int Cbc_solve(Cbc_Model *model);
    fun Cbc_solve(model: Pointer): Int

    // int Cbc_solveLinearProgram(Cbc_Model *model);
    fun Cbc_solveLinearProgram(model: Pointer)

    // void Cgl_generateCuts( void *osiClpSolver, enum CutType ct, void *osiCuts, int strength );
    fun Cgl_generateCuts(osiClpSolver: Pointer, ct: Int, osiCuts: Pointer, strength: Int)

    // void *Cbc_getSolverPtr(Cbc_Model *model);
    fun Cbc_getSolverPtr(model: Pointer)

    // void *Cbc_deleteModel(Cbc_Model *model);
    fun Cbc_deleteModel(model: Pointer): Pointer

    // int Osi_getNumIntegers( void *osi );
    fun Osi_getNumIntegers(osi: Pointer): Int

    // const double *Osi_getReducedCost( void *osi );
    fun Osi_getReducedCost(osi: Pointer): Pointer

    // const double *Osi_getObjCoefficients();
    // fun Osi_getObjCoefficients(): Pointer

    // double Osi_getObjSense();
    // fun Osi_getObjSense(): Double

    // void *Osi_newSolver();
    fun Osi_newSolver(): Pointer

    // void Osi_deleteSolver( void *osi );
    // fun Osi_deleteSolver(osi: Pointer)

    // void Osi_initialSolve(void *osi);
    fun Osi_initialSolve(osi: Pointer)

    // void Osi_resolve(void *osi);
    fun Osi_resolve(osi: Pointer)

    // void Osi_branchAndBound(void *osi);
    fun Osi_branchAndBound(osi: Pointer)

    // char Osi_isAbandoned(void *osi);
    fun Osi_isAbandoned(osi: Pointer): Byte

    // char Osi_isProvenOptimal(void *osi);
    fun Osi_isProvenOptimal(osi: Pointer): Byte

    // char Osi_isProvenPrimalInfeasible(void *osi);
    fun Osi_isProvenPrimalInfeasible(osi: Pointer): Byte

    // char Osi_isProvenDualInfeasible(void *osi);
    fun Osi_isProvenDualInfeasible(osi: Pointer): Byte

    // char Osi_isPrimalObjectiveLimitReached(void *osi);
    fun Osi_isPrimalObjectiveLimitReached(osi: Pointer): Byte

    // char Osi_isDualObjectiveLimitReached(void *osi);
    fun Osi_isDualObjectiveLimitReached(osi: Pointer): Byte

    // char Osi_isIterationLimitReached(void *osi);
    fun Osi_isIterationLimitReached(osi: Pointer): Byte

    // double Osi_getObjValue( void *osi );
    fun Osi_getObjValue(osi: Pointer): Double

    // void Osi_setColUpper (void *osi, int elementIndex, double ub);
    fun Osi_setColUpper(osi: Pointer, elementIndex: Int, ub: Double)

    // void Osi_setColLower(void *osi, int elementIndex, double lb);
    fun Osi_setColLower(osi: Pointer, elementIndex: Int, lb: Double)

    // int Osi_getNumCols( void *osi );
    fun Osi_getNumCols(osi: Pointer): Int

    // void Osi_getColName( void *osi, int i, char *name, int maxLen );
    fun Osi_getColName(osi: Pointer, i: Int, name: String, maxLen: Int)

    // const double *Osi_getColLower( void *osi );
    fun Osi_getColLower(osi: Pointer): Pointer

    // const double *Osi_getColUpper( void *osi );
    fun Osi_getColUpper(osi: Pointer): Pointer

    // int Osi_isInteger( void *osi, int col );
    fun Osi_isInteger(osi: Pointer, col: Int): Int

    // int Osi_getNumRows( void *osi );
    fun Osi_getNumRows(osi: Pointer): Int

    // int Osi_getRowNz(void *osi, int row);
    fun Osi_getRowNz(osi: Pointer, row: Int): Int

    // const int *Osi_getRowIndices(void *osi, int row);
    fun Osi_getRowIndices(osi: Pointer, row: Int): Pointer

    // const double *Osi_getRowCoeffs(void *osi, int row);
    fun Osi_getRowCoeffs(osi: Pointer, row: Int): Pointer

    // double Osi_getRowRHS(void *osi, int row);
    fun Osi_getRowRHS(osi: Pointer, row: Int): Double

    // char Osi_getRowSense(void *osi, int row);
    fun Osi_getRowSense(osi: Pointer, row: Int): Byte

    // void Osi_setObjCoef(void *osi, int index, double obj);
    fun Osi_setObjCoef(osi: Pointer, index: Int, obj: Double)

    // void Osi_setObjSense(void *osi, double sense);
    fun Osi_setObjSense(osi: Pointer, sense: Double)

    // const double *Osi_getColSolution(void *osi);
    fun Osi_getColSolution(osi: Pointer): Pointer

    // void *OsiCuts_new();
    fun OsiCuts_new(): Pointer

    // void OsiCuts_addRowCut(void *osiCuts, int nz, const int *idx,
    //                        const double *coef, char sense, double rhs);
    fun OsiCuts_addRowCut(osiCuts: Pointer, nz: Int, idx: Pointer, coef: Pointer, sense: Byte,
                          rhs: Double)

    //
    // void OsiCuts_addGlobalRowCut(void *osiCuts, int nz, const int *idx,
    //                              const double *coef, char sense, double rhs);
    fun OsiCuts_addGlobalRowCut(osiCuts: Pointer, nz: Int, idx: Pointer, coef: Pointer,
                                sense: Byte, rhs: Double)

    // int OsiCuts_sizeRowCuts( void *osiCuts );
    fun OsiCuts_sizeRowCuts(osiCuts: Pointer): Int

    // int OsiCuts_nzRowCut( void *osiCuts, int iRowCut );
    fun OsiCuts_nzRowCut(osiCuts: Pointer, iRowCut: Int): Int

    // const int * OsiCuts_idxRowCut( void *osiCuts, int iRowCut );
    fun OsiCuts_idxRowCut(osiCuts: Pointer, iRowCut: Int): Pointer

    // const double *OsiCuts_coefRowCut( void *osiCuts, int iRowCut );
    fun OsiCuts_coefRowCut(osiCuts: Pointer, iRowCut: Int): Pointer

    // double OsiCuts_rhsRowCut( void *osiCuts, int iRowCut );
    fun OsiCuts_rhsRowCut(osiCuts: Pointer, iRowCut: Int): Double

    // char OsiCuts_senseRowCut( void *osiCuts, int iRowCut );
    fun OsiCuts_senseRowCut(osiCuts: Pointer, iRowCut: Int): Byte

    // void OsiCuts_delete( void *osiCuts );
    fun OsiCuts_delete(osiCuts: Pointer)

    // void Osi_addCol(void *osi, const char *name, double lb, double ub, double obj,
    //                 char isInteger, int nz, int *rows, double *coefs);
    fun Osi_addCol(osi: Pointer, name: String, lb: Double, ub: Double, obj: Double,
                   isInteger: Byte, nz: Int, rows: Pointer, coefs: Pointer)

    // void Osi_addRow(void *osi, const char *name, int nz, const int *cols, const double *coefs,
    //                 char sense, double rhs);
    fun Osi_addRow(osi: Pointer, name: String, nz: Int, cols: Pointer, coefs: Pointer,
                   sense: Byte, rhs: Double)

    // void Cbc_deleteRows(Cbc_Model *model, int numRows, const int rows[]);
    fun Cbc_deleteRows(model: Pointer, numRows: Int, rows: Pointer)

    // void Cbc_deleteCols(Cbc_Model *model, int numCols, const int cols[]);
    fun Cbc_deleteCols(model: Pointer, numCols: Int, cols: Pointer)

    // void Cbc_storeNameIndexes(Cbc_Model *model, char _store);
    fun Cbc_storeNameIndexes(model: Pointer, _store: Byte);

    // int Cbc_getColNameIndex(Cbc_Model *model, const char *name);
    fun Cbc_getColNameIndex(model: Pointer, name: String): Int

    // int Cbc_getRowNameIndex(Cbc_Model *model, const char *name);
    fun Cbc_getRowNameIndex(model: Pointer, name: String): Int

    // void Cbc_problemName(Cbc_Model *model, int maxNumberCharacters,  char *array);
    fun Cbc_problemName(model: Pointer, maxNumberCharacters: Int, array: String)

    // int Cbc_setProblemName(Cbc_Model *model, const char *array);
    fun Cbc_setProblemName(model: Pointer, array: String): Int

    // double Cbc_getPrimalTolerance(Cbc_Model *model);
    fun Cbc_getPrimalTolerance(model: Pointer): Double

    // void Cbc_setPrimalTolerance(Cbc_Model *model, double tol);
    fun Cbc_setPrimalTolerance(model: Pointer, tol: Double)

    // double Cbc_getDualTolerance(Cbc_Model *model);
    fun Cbc_getDualTolerance(model: Pointer): Double

    // void Cbc_setDualTolerance(Cbc_Model *model, double tol);
    fun Cbc_setDualTolerance(model: Pointer, tol: Double)

    // void Cbc_addCutCallback(Cbc_Model *model, cbc_cut_callback cutcb,
    //                         const char *name, void *appData, int howOften, char atSolution );
    // fun Cbc_addCutCallback(model: Pointer, cutcb: CBCCutCallback, name: String,
    //                        appData: Pointer, howOften: Int, atSolution: Char)

    // void Cbc_addIncCallback(void *model, cbc_incumbent_callback inccb, void *appData);
    // fun Cbc_addIncCallback(model: Pointer, inccb: CBCIncumbentCallback, appData: Pointer)

    // void Cbc_registerCallBack(Cbc_Model *model, cbc_callback userCallBack);
    // fun Cbc_registerCallBack(model: Pointer, userCallBack: CBCCallback)

    // void Cbc_addProgrCallback(void *model, cbc_progress_callback prgcbc, void *appData);
    // fun Cbc_addProgrCallback(model: Pointer, prgcbc: CBCProgressCallback, appData: Pointer)

    // void Cbc_clearCallBack(Cbc_Model *model);
    fun Cbc_clearCallBack(model: Pointer)

    // const double *Cbc_getRowPrice(Cbc_Model *model);
    fun Cbc_getRowPrice(model: Pointer): Pointer

    // const double *Osi_getRowPrice(void *osi);
    // fun Cbc_Osi_getRowPrice(osi: Pointer): DoubleArray

    // double Osi_getIntegerTolerance(void *osi);
    fun Osi_getIntegerTolerance(osi: Pointer): Double

    // void Osi_checkCGraph( void *osi );
    fun Osi_checkCGraph(osi: Pointer)

    // const void * Osi_CGraph( void *osi );
    fun Osi_CGraph(osi: Pointer): Pointer

    // size_t CG_nodes( void *cgraph );
    fun CG_nodes(cgraph: Pointer): Int; // TODO("check @size_t")

    // char CG_conflicting( void *cgraph, int n1, int n2 );
    fun CG_conflicting(cgraph: Pointer, n1: Int, n2: Int): Byte

    // double CG_density( void *cgraph );
    // fun Cbc_density(cgraph: Pointer)

    // CGNeighbors CG_conflictingNodes(Cbc_Model *model, void *cgraph, size_t node);
    fun CG_conflictingNodes(model: Pointer, cgraph: Pointer, @size_t node: Int): CGNeighbors

    companion object {

        val lib = LibraryLoader
            .create(CBCLibrary::class.java)
            .option(LibraryOption.LoadNow, null)
            .load("/Docs/Dev/python-mip/mip/libraries/cbc-c-darwin-x86-64.dylib")

        const val CHAR_ONE: Byte = 1.toByte()
        const val CHAR_ZERO: Byte = 0.toByte()

        // enum DblParam {
        //     DBL_PARAM_PRIMAL_TOL    = 0,  /*! Tollerance to consider a solution feasible in the linear programming solver. */
        //     DBL_PARAM_DUAL_TOL      = 1,  /*! Tollerance for a solution to be considered optimal in the linear programming solver. */
        //     DBL_PARAM_ZERO_TOL      = 2,  /*! Coefficients less that this value will be ignored when reading instances */
        //     DBL_PARAM_INT_TOL       = 3,  /*! Maximum allowed distance from integer value for a variable to be considered integral */
        //     DBL_PARAM_PRESOLVE_TOL  = 4,  /*! Tollerance used in the presolver, should be increased if the pre-solver is declaring infeasible a feasible problem */
        //     DBL_PARAM_TIME_LIMIT    = 5,  /*! Time limit in seconds */
        //     DBL_PARAM_PSI           = 6,  /*! Two dimensional princing factor in the Positive Edge pivot strategy. */
        //     DBL_PARAM_CUTOFF        = 7,  /*! Only search for solutions with cost less-or-equal to this value. */
        //     DBL_PARAM_ALLOWABLE_GAP = 8,  /*! Allowable gap between the lower and upper bound to conclude the search */
        //     DBL_PARAM_GAP_RATIO     = 9   /*! Stops the search when the difference between the upper and lower bound is less than this fraction of the larger value */
        // };
        // #define N_DBL_PARAMS 10
        const val DBL_PARAM_PRIMAL_TOL = 0
        const val DBL_PARAM_DUAL_TOL = 1
        const val DBL_PARAM_ZERO_TOL = 2
        const val DBL_PARAM_INT_TOL = 3
        const val DBL_PARAM_PRESOLVE_TOL = 4
        const val DBL_PARAM_TIME_LIMIT = 5
        const val DBL_PARAM_PSI = 6
        const val DBL_PARAM_CUTOFF = 7
        const val DBL_PARAM_ALLOWABLE_GAP = 8
        const val DBL_PARAM_GAP_RATIO = 9
        const val N_DBL_PARAMS = 10

        // /*! Integer parameters */
        // enum IntParam {
        //     INT_PARAM_PERT_VALUE          = 0,  /*! Method of perturbation, -5000 to 102, default 50 */
        //     INT_PARAM_IDIOT               = 1,  /*! Parameter of the "idiot" method to try to produce an initial feasible basis. -1 let the solver decide if this should be applied; 0 deactivates it and >0 sets number of passes. */
        //     INT_PARAM_STRONG_BRANCHING    = 2,  /*! Number of variables to be evaluated in strong branching. */
        //     INT_PARAM_CUT_DEPTH           = 3,  /*! Sets the application of cuts to every depth multiple of this value. -1, the default value, let the solve decide. */
        //     INT_PARAM_MAX_NODES           = 4,  /*! Maximum number of nodes to be explored in the search tree */
        //     INT_PARAM_NUMBER_BEFORE       = 5,  /*! Number of branche before trusting pseudocodes computed in strong branching. */
        //     INT_PARAM_FPUMP_ITS           = 6,  /*! Maximum number of iterations in the feasibility pump method. */
        //     INT_PARAM_MAX_SOLS            = 7,  /*! Maximum number of solutions generated during the search. Stops the search when this number of solutions is found. */
        //     INT_PARAM_CUT_PASS_IN_TREE    = 8, /*! Maxinum number of cuts passes in the search tree (with the exception of the root node). Default 1. */
        //     INT_PARAM_THREADS             = 9, /*! Number of threads that can be used in the branch-and-bound method.*/
        //     INT_PARAM_CUT_PASS            = 10, /*! Number of cut passes in the root node. Default -1, solver decides */
        //     INT_PARAM_LOG_LEVEL           = 11, /*! Verbosity level, from 0 to 2 */
        //     INT_PARAM_MAX_SAVED_SOLS      = 12, /*! Size of the pool to save the best solutions found during the search. */
        //     INT_PARAM_MULTIPLE_ROOTS      = 13, /*! Multiple root passes to get additional cuts and solutions. */
        //     INT_PARAM_ROUND_INT_VARS      = 14, /*! If integer variables should be round to remove small infeasibilities. This can increase the overall amount of infeasibilities in problems with both continuous and integer variables */
        //     INT_PARAM_RANDOM_SEED         = 15, /*! When solving LP and MIP, randomization is used to break ties in some decisions. This changes the random seed so that multiple executions can produce different results */
        //     INT_PARAM_ELAPSED_TIME        = 16  /*! When =1 use elapsed (wallclock) time, otherwise use CPU time */
        // };
        // #define N_INT_PARAMS 17
        const val INT_PARAM_PERT_VALUE = 0
        const val INT_PARAM_IDIOT = 1
        const val INT_PARAM_STRONG_BRANCHING = 2
        const val INT_PARAM_CUT_DEPTH = 3
        const val INT_PARAM_MAX_NODES = 4
        const val INT_PARAM_NUMBER_BEFORE = 5
        const val INT_PARAM_FPUMP_ITS = 6
        const val INT_PARAM_MAX_SOLS = 7
        const val INT_PARAM_CUT_PASS_IN_TREE = 8
        const val INT_PARAM_THREADS = 9
        const val INT_PARAM_CUT_PASS = 10
        const val INT_PARAM_LOG_LEVEL = 11
        const val INT_PARAM_MAX_SAVED_SOLS = 12
        const val INT_PARAM_MULTIPLE_ROOTS = 13
        const val INT_PARAM_ROUND_INT_VARS = 14
        const val INT_PARAM_RANDOM_SEED = 15
        const val INT_PARAM_ELAPSED_TIME = 16
        const val N_INT_PARAMS = 17

        // enum LPMethod {
        //     LPM_Auto    = 0,  /*! Solver will decide automatically which method to use */
        //     LPM_Dual    = 1,  /*! Dual simplex */
        //     LPM_Primal  = 2,  /*! Primal simplex */
        //     LPM_Barrier = 3   /*! The barrier algorithm. */
        // };
        const val LPM_Auto = 0
        const val LPM_Dual = 1
        const val LPM_Primal = 2
        const val LPM_Barrier = 3

        // enum CutType {
        //     CT_Gomory         = 0,  /*! Gomory cuts obtained from the tableau */
        //     CT_MIR            = 1,  /*! Mixed integer rounding cuts */
        //     CT_ZeroHalf       = 2,  /*! Zero-half cuts */
        //     CT_Clique         = 3,  /*! Clique cuts */
        //     CT_KnapsackCover  = 4,  /*! Knapsack cover cuts */
        //     CT_LiftAndProject = 5   /*! Lift and project cuts */
        // };
        const val CT_Gomory = 0
        const val CT_MIR = 1
        const val CT_ZeroHalf = 2
        const val CT_Clique = 3
        const val CT_KnapsackCover = 4
        const val CT_LiftAndProject = 5
    }
}
