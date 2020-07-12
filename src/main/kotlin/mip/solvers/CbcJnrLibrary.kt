package mip.solvers

import jnr.ffi.Runtime
import jnr.ffi.*
import jnr.ffi.annotations.*
import jnr.ffi.types.*

import java.io.*

interface CbcJnrLibrary {

    fun fflush(stream: Pointer?) = CLibrary.lib.fflush(stream)

    /**
     * void *Cbc_newModel();
     */
    fun Cbc_newModel(): Pointer

    /**
     * void Cbc_readLp(Cbc_Model *model, const char *file);
     */
    fun Cbc_readLp(@Pinned @In model: Pointer, @Pinned @In @Transient file: String)

    /**
     * void Cbc_readMps(Cbc_Model *model, const char *file);
     */
    fun Cbc_readMps(@Pinned @In model: Pointer, @Pinned @In @Transient file: String)

    /**
     * char Cbc_supportsGzip();
     */
    fun Cbc_supportsGzip(): Byte

    /**
     * char Cbc_supportsBzip2();
     */
    fun Cbc_supportsBzip2(): Byte

    /**
     * void Cbc_writeLp(Cbc_Model *model, const char *file);
     */
    fun Cbc_writeLp(@Pinned @In model: Pointer, @Pinned @In @Transient file: String)

    /**
     * void Cbc_writeMps(Cbc_Model *model, const char *file);
     */
    fun Cbc_writeMps(@Pinned @In model: Pointer, @Pinned @In @Transient file: String)

    /**
     * int Cbc_getNumCols(Cbc_Model *model);
     */
    fun Cbc_getNumCols(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_getNumRows(Cbc_Model *model);
     */
    fun Cbc_getNumRows(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_getNumIntegers(Cbc_Model *model);
     */
    fun Cbc_getNumIntegers(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_getNumElements(Cbc_Model *model);
     */
    fun Cbc_getNumElements(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_getRowNz(Cbc_Model *model, int row);
     */
    fun Cbc_getRowNz(@Pinned @In model: Pointer, row: Int): Int

    /**
     * int *Cbc_getRowIndices(Cbc_Model *model, int row);
     */
    fun Cbc_getRowIndices(@Pinned @In model: Pointer, row: Int): Pointer

    /**
     * double *Cbc_getRowCoeffs(Cbc_Model *model, int row);
     */
    fun Cbc_getRowCoeffs(@Pinned @In model: Pointer, row: Int): Pointer

    /**
     * double Cbc_getRowRHS(Cbc_Model *model, int row);
     */
    fun Cbc_getRowRHS(@Pinned @In model: Pointer, row: Int): Double

    /**
     * void Cbc_setRowRHS(Cbc_Model *model, int row, double rhs);
     */
    fun Cbc_setRowRHS(@Pinned @In model: Pointer, row: Int, rhs: Double)

    /**
     * char Cbc_getRowSense(Cbc_Model *model, int row);
     */
    fun Cbc_getRowSense(@Pinned @In model: Pointer, row: Int): Byte

    /**
     * const double *Cbc_getRowActivity(Cbc_Model *model);
     */
    fun Cbc_getRowActivity(@Pinned @In model: Pointer): Pointer

    /**
     * const double *Cbc_getRowSlack(Cbc_Model *model);
     */
    fun Cbc_getRowSlack(@Pinned @In model: Pointer): Pointer

    /**
     * int Cbc_getColNz(Cbc_Model *model, int col);
     */
    fun Cbc_getColNz(@Pinned @In model: Pointer, col: Int): Int

    /**
     * int *Cbc_getColIndices(Cbc_Model *model, int col);
     */
    fun Cbc_getColIndices(@Pinned @In model: Pointer, col: Int): Pointer

    /**
     * double *Cbc_getColCoeffs(Cbc_Model *model, int col);
     */
    fun Cbc_getColCoeffs(@Pinned @In model: Pointer, col: Int): Pointer

    /**
     * void Cbc_addCol(Cbc_Model *model, const char *name, double lb, double ub, double obj, char
     * isInteger, int nz, int *rows, double *coefs);
     */
    fun Cbc_addCol(@Pinned @In model: Pointer, @Pinned @In @Transient name: String, lb: Double, ub: Double, obj: Double, isInteger: Byte, nz: Int, @Pinned @In @Transient rows: IntArray?, @Pinned @In @Transient coefs: DoubleArray?)

    /**
     * void Cbc_addRow(Cbc_Model *model, const char *name, int nz, const int *cols, const double
     * *coefs, char sense, double rhs);
     */
    fun Cbc_addRow(@Pinned @In model: Pointer, @Pinned @In @Transient name: String, nz: Int, @Pinned @In @Transient cols: IntArray?, @Pinned @In @Transient coefs: DoubleArray?, sense: Byte, rhs: Double)

    /**
     * void Cbc_addLazyConstraint(Cbc_Model *model, int nz, int *cols, double *coefs, char sense,
     * double rhs);
     */
    fun Cbc_addLazyConstraint(@Pinned @In model: Pointer, nz: Int, @Pinned @In @Transient cols: IntArray, @Pinned @In @Transient coefs: DoubleArray, sense: Byte, rhs: Double)

    /**
     * void Cbc_addSOS(Cbc_Model *model, int numRows, const int *rowStarts, const int *colIndices,
     * const double *weights, const int type);
     */
    fun Cbc_addSOS(@Pinned @In model: Pointer, numRows: Int, @Pinned @In @Transient rowStarts: IntArray, @Pinned @In @Transient colIndices: IntArray, @Pinned @In @Transient weights: DoubleArray, @In @Transient type: Int)

    /**
     * int Cbc_numberSOS(Cbc_Model *model);
     */
    fun Cbc_numberSOS(@Pinned @In model: Pointer): Int

    /**
     * void Cbc_setObjCoeff(Cbc_Model *model, int index, double value);
     */
    fun Cbc_setObjCoeff(@Pinned @In model: Pointer, index: Int, value: Double)

    /**
     * double Cbc_getObjSense(Cbc_Model *model);
     */
    fun Cbc_getObjSense(@Pinned @In model: Pointer): Double

    /**
     * const double *Cbc_getObjCoefficients(Cbc_Model *model);
     */
    fun Cbc_getObjCoefficients(@Pinned @In model: Pointer): Pointer

    /**
     * const double *Cbc_getColSolution(Cbc_Model *model);
     */
    fun Cbc_getColSolution(@Pinned @In model: Pointer): Pointer

    /**
     * const double *Cbc_getReducedCost(Cbc_Model *model);
     */
    fun Cbc_getReducedCost(@Pinned @In model: Pointer): Pointer

    /**
     * double *Cbc_bestSolution(Cbc_Model *model);
     */
    fun Cbc_bestSolution(@Pinned @In model: Pointer): Pointer

    /**
     * int Cbc_numberSavedSolutions(Cbc_Model *model);
     */
    fun Cbc_numberSavedSolutions(@Pinned @In model: Pointer): Int

    /**
     * const double *Cbc_savedSolution(Cbc_Model *model, int whichSol);
     */
    fun Cbc_savedSolution(@Pinned @In model: Pointer, whichSol: Int): Pointer

    /**
     * double Cbc_savedSolutionObj(Cbc_Model *model, int whichSol);
     */
    fun Cbc_savedSolutionObj(@Pinned @In model: Pointer, whichSol: Int): Double

    /**
     * double Cbc_getObjValue(Cbc_Model *model);
     */
    fun Cbc_getObjValue(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setObjSense(Cbc_Model *model, double sense);
     */
    fun Cbc_setObjSense(@Pinned @In model: Pointer, sense: Double)

    /**
     * int Cbc_isProvenOptimal(Cbc_Model *model);
     */
    fun Cbc_isProvenOptimal(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_isProvenInfeasible(Cbc_Model *model);
     */
    fun Cbc_isProvenInfeasible(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_isContinuousUnbounded(Cbc_Model *model);
     */
    fun Cbc_isContinuousUnbounded(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_isAbandoned(Cbc_Model *model);
     */
    fun Cbc_isAbandoned(@Pinned @In model: Pointer): Int

    /**
     * const double *Cbc_getColLower(Cbc_Model *model);
     */
    fun Cbc_getColLower(@Pinned @In model: Pointer): Pointer

    /**
     * const double *Cbc_getColUpper(Cbc_Model *model);
     */
    fun Cbc_getColUpper(@Pinned @In model: Pointer): Pointer

    /**
     * double Cbc_getColObj(Cbc_Model *model, int colIdx);
     */
    fun Cbc_getColObj(@Pinned @In model: Pointer, colIdx: Int): Double

    /**
     * double Cbc_getColLB(Cbc_Model *model, int colIdx);
     */
    fun Cbc_getColLB(@Pinned @In model: Pointer, colIdx: Int): Double

    /**
     * double Cbc_getColUB(Cbc_Model *model, int colIdx);
     */
    fun Cbc_getColUB(@Pinned @In model: Pointer, colIdx: Int): Double

    /**
     * void Cbc_setColLower(Cbc_Model *model, int index, double value);
     */
    fun Cbc_setColLower(@Pinned @In model: Pointer, index: Int, value: Double)

    /**
     * void Cbc_setColUpper(Cbc_Model *model, int index, double value);
     */
    fun Cbc_setColUpper(@Pinned @In model: Pointer, index: Int, value: Double)

    /**
     * int Cbc_isInteger(Cbc_Model *model, int i);
     */
    fun Cbc_isInteger(@Pinned @In model: Pointer, i: Int): Int

    /**
     * void Cbc_getColName(Cbc_Model *model, int iColumn, char *name, size_t maxLength);
     */
    fun Cbc_getColName(@Pinned @In model: Pointer, iColumn: Int, @Pinned name: String, @size_t maxLength: Long)

    /**
     * void Cbc_getRowName(Cbc_Model *model, int iRow, char *name, size_t maxLength);
     */
    fun Cbc_getRowName(@Pinned @In model: Pointer, iRow: Int, @Pinned @Out name: String, @size_t maxLength: Long)

    /**
     * void Cbc_setContinuous(Cbc_Model *model, int iColumn);
     */
    fun Cbc_setContinuous(@Pinned @In model: Pointer, iColumn: Int)

    /**
     * void Cbc_setInteger(Cbc_Model *model, int iColumn);
     */
    fun Cbc_setInteger(@Pinned @In model: Pointer, iColumn: Int)

    /**
     * void Cbc_setIntParam(Cbc_Model *model, enum IntParam which, const int val);
     */
    fun Cbc_setIntParam(model: Pointer, which: Int, @In @Transient `val`: Int)

    /**
     * void Cbc_setDblParam(Cbc_Model *model, enum DblParam which, const double val);
     */
    fun Cbc_setDblParam(@Pinned @In model: Pointer, which: Int, @In @Transient `val`: Double)

    /**
     * void Cbc_setParameter(Cbc_Model *model, const char *name, const char *value);
     */
    fun Cbc_setParameter(@Pinned @In model: Pointer, @Pinned @In @Transient name: String, @Pinned @In @Transient value: String)

    /**
     * double Cbc_getCutoff(Cbc_Model *model);
     */
    fun Cbc_getCutoff(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setCutoff(Cbc_Model *model, double cutoff);
     */
    fun Cbc_setCutoff(@Pinned @In model: Pointer, cutoff: Double)

    /**
     * double Cbc_getAllowableGap(Cbc_Model *model);
     */
    fun Cbc_getAllowableGap(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setAllowableGap(Cbc_Model *model, double allowedGap);
     */
    fun Cbc_setAllowableGap(@Pinned @In model: Pointer, allowedGap: Double)

    /**
     * double Cbc_getAllowableFractionGap(Cbc_Model *model);
     */
    fun Cbc_getAllowableFractionGap(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setAllowableFractionGap(Cbc_Model *model, double allowedFracionGap);
     */
    fun Cbc_setAllowableFractionGap(@Pinned @In model: Pointer, allowedFracionGap: Double)

    /**
     * double Cbc_getAllowablePercentageGap(Cbc_Model *model);
     */
    fun Cbc_getAllowablePercentageGap(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setAllowablePercentageGap(Cbc_Model *model, double allowedPercentageGap);
     */
    fun Cbc_setAllowablePercentageGap(@Pinned @In model: Pointer, allowedPercentageGap: Double)

    /**
     * double Cbc_getMaximumSeconds(Cbc_Model *model);
     */
    fun Cbc_getMaximumSeconds(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setMaximumSeconds(Cbc_Model *model, double maxSeconds);
     */
    fun Cbc_setMaximumSeconds(@Pinned @In model: Pointer, maxSeconds: Double)

    /**
     * int Cbc_getMaximumNodes(Cbc_Model *model);
     */
    fun Cbc_getMaximumNodes(@Pinned @In model: Pointer): Int

    /**
     * void Cbc_setMaximumNodes(Cbc_Model *model, int maxNodes);
     */
    fun Cbc_setMaximumNodes(@Pinned @In model: Pointer, maxNodes: Int)

    /**
     * int Cbc_getMaximumSolutions(Cbc_Model *model);
     */
    fun Cbc_getMaximumSolutions(@Pinned @In model: Pointer): Int

    /**
     * void Cbc_setMaximumSolutions(Cbc_Model *model, int maxSolutions);
     */
    fun Cbc_setMaximumSolutions(@Pinned @In model: Pointer, maxSolutions: Int)

    /**
     * int Cbc_getLogLevel(Cbc_Model *model);
     */
    fun Cbc_getLogLevel(@Pinned @In model: Pointer): Int

    /**
     * void Cbc_setLogLevel(Cbc_Model *model, int logLevel);
     */
    fun Cbc_setLogLevel(@Pinned @In model: Pointer, logLevel: Int)

    /**
     * double Cbc_getBestPossibleObjValue(Cbc_Model *model);
     */
    fun Cbc_getBestPossibleObjValue(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setMIPStart(Cbc_Model *model, int count, const char **colNames, const double
     * colValues[]);
     */
    fun Cbc_setMIPStartI(@Pinned @In model: Pointer, count: Int, @Pinned @In @Transient colIdxs: IntArray, @Pinned @In @Transient colValues: DoubleArray)

    /**
     * void Cbc_setLPmethod(Cbc_Model *model, enum LPMethod lpm );
     */
    fun Cbc_setLPmethod(@Pinned @In model: Pointer, lpm: Int)

    /**
     * void Cbc_updateConflictGraph( Cbc_Model *model );
     */
    fun Cbc_updateConflictGraph(@Pinned @In model: Pointer)

    /**
     * const void *Cbc_conflictGraph( Cbc_Model *model );
     */
    fun Cbc_conflictGraph(@Pinned @In model: Pointer): Pointer

    /**
     * int Cbc_solve(Cbc_Model *model);
     */
    fun Cbc_solve(@Pinned @In model: Pointer): Int

    /**
     * int Cbc_solveLinearProgram(Cbc_Model *model);
     */
    fun Cbc_solveLinearProgram(@Pinned @In model: Pointer): Int

    /**
     * void Cgl_generateCuts( void *osiClpSolver, enum CutType ct, void *osiCuts, int strength );
     */
    fun Cgl_generateCuts(@In @Transient osiClpSolver: Pointer, ct: Int, @In @Transient osiCuts: Pointer, strength: Int)

    /**
     * void Cbc_strengthenPacking(Cbc_Model *model);
     */
    fun Cbc_strengthenPacking(@Pinned @In model: Pointer)

    /**
     * void Cbc_strengthenPackingRows(Cbc_Model *model, size_t n, const size_t rows[]);
     */
    fun Cbc_strengthenPackingRows(@Pinned @In model: Pointer, @size_t n: Long, @Pinned @In @Transient @size_t rows: LongArray)

    /**
     * void *Cbc_getSolverPtr(Cbc_Model *model);
     */
    fun Cbc_getSolverPtr(@Pinned @In model: Pointer): Pointer

    /**
     * void *Cbc_deleteModel(Cbc_Model *model);
     */
    fun Cbc_deleteModel(@Pinned @In model: Pointer): Pointer

    /**
     * int Osi_getNumIntegers( void *osi );
     */
    fun Osi_getNumIntegers(@Pinned @In osi: Pointer): Int

    /**
     * const double *Osi_getReducedCost( void *osi );
     */
    fun Osi_getReducedCost(@Pinned @In osi: Pointer): Pointer

    /**
     * const double *Osi_getObjCoefficients();
     */
    fun Osi_getObjCoefficients(): Pointer

    /**
     * double Osi_getObjSense();
     */
    fun Osi_getObjSense(): Double

    /**
     * void *Osi_newSolver();
     */
    fun Osi_newSolver(): Pointer

    /**
     * void Osi_deleteSolver( void *osi );
     */
    fun Osi_deleteSolver(@Pinned @In osi: Pointer)

    /**
     * void Osi_initialSolve(void *osi);
     */
    fun Osi_initialSolve(@Pinned @In osi: Pointer)

    /**
     * void Osi_resolve(void *osi);
     */
    fun Osi_resolve(@Pinned @In osi: Pointer)

    /**
     * void Osi_branchAndBound(void *osi);
     */
    fun Osi_branchAndBound(@Pinned @In osi: Pointer)

    /**
     * char Osi_isAbandoned(void *osi);
     */
    fun Osi_isAbandoned(@Pinned @In osi: Pointer): Byte

    /**
     * char Osi_isProvenOptimal(void *osi);
     */
    fun Osi_isProvenOptimal(@Pinned @In osi: Pointer): Byte

    /**
     * char Osi_isProvenPrimalInfeasible(void *osi);
     */
    fun Osi_isProvenPrimalInfeasible(@Pinned @In osi: Pointer): Byte

    /**
     * char Osi_isProvenDualInfeasible(void *osi);
     */
    fun Osi_isProvenDualInfeasible(@Pinned @In osi: Pointer): Byte

    /**
     * char Osi_isPrimalObjectiveLimitReached(void *osi);
     */
    fun Osi_isPrimalObjectiveLimitReached(@Pinned @In osi: Pointer): Byte

    /**
     * char Osi_isDualObjectiveLimitReached(void *osi);
     */
    fun Osi_isDualObjectiveLimitReached(@Pinned @In osi: Pointer): Byte

    /**
     * char Osi_isIterationLimitReached(void *osi);
     */
    fun Osi_isIterationLimitReached(@Pinned @In osi: Pointer): Byte

    /**
     * double Osi_getObjValue( void *osi );
     */
    fun Osi_getObjValue(@Pinned @In osi: Pointer): Double

    /**
     * void Osi_setColUpper (void *osi, int elementIndex, double ub);
     */
    fun Osi_setColUpper(@Pinned @In osi: Pointer, elementIndex: Int, ub: Double)

    /**
     * void Osi_setColLower(void *osi, int elementIndex, double lb);
     */
    fun Osi_setColLower(@Pinned @In osi: Pointer, elementIndex: Int, lb: Double)

    /**
     * int Osi_getNumCols( void *osi );
     */
    fun Osi_getNumCols(@Pinned @In osi: Pointer): Int

    /**
     * void Osi_getColName( void *osi, int i, char *name, int maxLen );
     */
    fun Osi_getColName(@Pinned @In osi: Pointer, i: Int, @Pinned name: String, maxLen: Int)

    /**
     * const double *Osi_getColLower( void *osi );
     */
    fun Osi_getColLower(@Pinned @In osi: Pointer): Pointer

    /**
     * const double *Osi_getColUpper( void *osi );
     */
    fun Osi_getColUpper(@Pinned @In osi: Pointer): Pointer

    /**
     * int Osi_isInteger( void *osi, int col );
     */
    fun Osi_isInteger(@Pinned @In osi: Pointer, col: Int): Int

    /**
     * int Osi_getNumRows( void *osi );
     */
    fun Osi_getNumRows(@Pinned @In osi: Pointer): Int

    /**
     * int Osi_getRowNz(void *osi, int row);
     */
    fun Osi_getRowNz(@Pinned @In osi: Pointer, row: Int): Int

    /**
     * const int *Osi_getRowIndices(void *osi, int row);
     */
    fun Osi_getRowIndices(@Pinned @In osi: Pointer, row: Int): Pointer

    /**
     * const double *Osi_getRowCoeffs(void *osi, int row);
     */
    fun Osi_getRowCoeffs(@Pinned @In osi: Pointer, row: Int): Pointer

    /**
     * double Osi_getRowRHS(void *osi, int row);
     */
    fun Osi_getRowRHS(@Pinned @In osi: Pointer, row: Int): Double

    /**
     * char Osi_getRowSense(void *osi, int row);
     */
    fun Osi_getRowSense(@Pinned @In osi: Pointer, row: Int): Byte

    /**
     * void Osi_setObjCoef(void *osi, int index, double obj);
     */
    fun Osi_setObjCoef(@Pinned @In osi: Pointer, index: Int, obj: Double)

    /**
     * void Osi_setObjSense(void *osi, double sense);
     */
    fun Osi_setObjSense(@Pinned @In osi: Pointer, sense: Double)

    /**
     * const double *Osi_getColSolution(void *osi);
     */
    fun Osi_getColSolution(@Pinned @In osi: Pointer): Pointer

    /**
     * void *OsiCuts_new();
     */
    fun OsiCuts_new(): Pointer

    /**
     * void OsiCuts_addRowCut( void *osiCuts, int nz, const int *idx, const double *coef, char
     * sense, double rhs );
     */
    fun OsiCuts_addRowCut(@In @Transient osiCuts: Pointer, nz: Int, @Pinned @In @Transient idx: IntArray, @Pinned @In @Transient coef: DoubleArray, sense: Byte, rhs: Double)

    /**
     * void OsiCuts_addGlobalRowCut( void *osiCuts, int nz, const int *idx, const double *coef,
     * char sense, double rhs );
     */
    fun OsiCuts_addGlobalRowCut(@In @Transient osiCuts: Pointer, nz: Int, @Pinned @In @Transient idx: IntArray, @Pinned @In @Transient coef: DoubleArray, sense: Byte, rhs: Double)

    /**
     * int OsiCuts_sizeRowCuts( void *osiCuts );
     */
    fun OsiCuts_sizeRowCuts(@In @Transient osiCuts: Pointer): Int

    /**
     * int OsiCuts_nzRowCut( void *osiCuts, int iRowCut );
     */
    fun OsiCuts_nzRowCut(@In @Transient osiCuts: Pointer, iRowCut: Int): Int

    /**
     * const int * OsiCuts_idxRowCut( void *osiCuts, int iRowCut );
     */
    fun OsiCuts_idxRowCut(@In @Transient osiCuts: Pointer, iRowCut: Int): Pointer

    /**
     * const double *OsiCuts_coefRowCut( void *osiCuts, int iRowCut );
     */
    fun OsiCuts_coefRowCut(@In @Transient osiCuts: Pointer, iRowCut: Int): Pointer

    /**
     * double OsiCuts_rhsRowCut( void *osiCuts, int iRowCut );
     */
    fun OsiCuts_rhsRowCut(@In @Transient osiCuts: Pointer, iRowCut: Int): Double

    /**
     * char OsiCuts_senseRowCut( void *osiCuts, int iRowCut );
     */
    fun OsiCuts_senseRowCut(@In @Transient osiCuts: Pointer, iRowCut: Int): Byte

    /**
     * void OsiCuts_delete( void *osiCuts );
     */
    fun OsiCuts_delete(@In @Transient osiCuts: Pointer)

    /**
     * void Osi_addCol(void *osi, const char *name, double lb, double ub, double obj, char
     * isInteger, int nz, int *rows, double *coefs);
     */
    fun Osi_addCol(@Pinned @In osi: Pointer, @Pinned @In @Transient name: String, lb: Double, ub: Double, obj: Double, isInteger: Byte, nz: Int, @Pinned @In @Transient rows: IntArray, @Pinned @In @Transient coefs: DoubleArray)

    /**
     * void Osi_addRow(void *osi, const char *name, int nz, const int *cols, const double *coefs,
     * char sense, double rhs);
     */
    fun Osi_addRow(@Pinned @In osi: Pointer, @Pinned @In @Transient name: String, nz: Int, @Pinned @In @Transient cols: IntArray, @Pinned @In @Transient coefs: DoubleArray, sense: Byte, rhs: Double)

    /**
     * void Cbc_deleteRows(Cbc_Model *model, int numRows, const int rows[]);
     */
    fun Cbc_deleteRows(@Pinned @In model: Pointer, numRows: Int, @Pinned @In @Transient rows: IntArray)

    /**
     * void Cbc_deleteRows(@In Pointer model, int numRows, const int rows[]);
     */
    fun Cbc_deleteCols(@Pinned @In model: Pointer, numCols: Int, @Pinned @In @Transient cols: IntArray)

    /**
     * void Cbc_storeNameIndexes(Cbc_Model *model, char _store);
     */
    fun Cbc_storeNameIndexes(@Pinned @In model: Pointer, _store: Byte)

    /**
     * int Cbc_getColNameIndex(Cbc_Model *model, const char *name);
     */
    fun Cbc_getColNameIndex(@Pinned @In model: Pointer, @Pinned @In @Transient name: String): Int

    /**
     * int Cbc_getRowNameIndex(Cbc_Model *model, const char *name);
     */
    fun Cbc_getRowNameIndex(@Pinned @In model: Pointer, @Pinned @In @Transient name: String): Int

    /**
     * void Cbc_problemName(Cbc_Model *model, int maxNumberCharacters, char *array);
     */
    fun Cbc_problemName(@Pinned @In model: Pointer, maxNumberCharacters: Int, @Pinned array: String)

    /**
     * int Cbc_setProblemName(Cbc_Model *model, const char *array);
     */
    fun Cbc_setProblemName(@Pinned @In model: Pointer, @Pinned @In @Transient array: String): Int

    /**
     * double Cbc_getPrimalTolerance(Cbc_Model *model);
     */
    fun Cbc_getPrimalTolerance(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setPrimalTolerance(Cbc_Model *model, double tol);
     */
    fun Cbc_setPrimalTolerance(@Pinned @In model: Pointer, tol: Double)

    /**
     * double Cbc_getDualTolerance(Cbc_Model *model);
     */
    fun Cbc_getDualTolerance(@Pinned @In model: Pointer): Double

    /**
     * void Cbc_setDualTolerance(Cbc_Model *model, double tol);
     */
    fun Cbc_setDualTolerance(@Pinned @In model: Pointer, tol: Double)

    /**
     * void Cbc_addCutCallback(Cbc_Model *model, cbc_cut_callback cutcb, const char *name, void
     * *appData, int howOften, char atSolution );
     */
    fun Cbc_addCutCallback(@Pinned @In model: Pointer, cutcb: Cbc_CutCallback, @Pinned @In name: String, @Pinned @In appData: Pointer, howOften: Int, atSolution: Byte)

    /**
     * void Cbc_addIncCallback(void *model, cbc_incumbent_callback inccb, void *appData );
     */
    fun Cbc_addIncCallback(@Pinned @In model: Pointer, inccb: Cbc_IncumbentCallback, @Pinned @In appData: Pointer)

    /**
     * void Cbc_registerCallBack(Cbc_Model *model, cbc_callback userCallBack);
     */
    fun Cbc_registerCallBack(@Pinned @In model: Pointer, userCallBack: Cbc_Callback)

    /**
     * void Cbc_addProgrCallback(void *model, cbc_progress_callback prgcbc, void *appData);
     */
    fun Cbc_addProgrCallback(@Pinned @In model: Pointer, prgcbc: Cbc_ProgressCallback, @Pinned @In appData: Pointer)

    /**
     * void Cbc_clearCallBack(Cbc_Model *model);
     */
    fun Cbc_clearCallBack(@Pinned @In model: Pointer)

    /**
     * const double *Cbc_getRowPrice(Cbc_Model *model);
     */
    fun Cbc_getRowPrice(@Pinned @In model: Pointer): Pointer

    /**
     * const double *Osi_getRowPrice(void *osi);
     */
    fun Osi_getRowPrice(@Pinned @In osi: Pointer): Pointer

    /**
     * double Osi_getIntegerTolerance(void *osi);
     */
    fun Osi_getIntegerTolerance(@Pinned @In osi: Pointer): Double

    /**
     * void Osi_checkCGraph( void *osi );
     */
    fun Osi_checkCGraph(@Pinned @In osi: Pointer)

    /**
     * const void * Osi_CGraph( void *osi );
     */
    fun Osi_CGraph(@Pinned @In osi: Pointer): Pointer

    /**
     * size_t CG_nodes( void *cgraph );
     */
    @size_t
    fun CG_nodes(cgraph: Pointer): Long

    /**
     * char CG_conflicting( void *cgraph, int n1, int n2 );
     */
    fun CG_conflicting(cgraph: Pointer, n1: Int, n2: Int): Byte

    /**
     * double CG_density( void *cgraph );
     */
    fun CG_density(cgraph: Pointer): Double

    /**
     * CGNeighbors CG_conflictingNodes(Cbc_Model *model, void *cgraph, size_t node);
     */
    fun CG_conflictingNodes(@Pinned @In model: Pointer, @In @Transient cgraph: Pointer, @size_t node: Long): CGNeighbors


    /**
     * typedef struct { size_t n; const size_t *neigh; } CGNeighbors;
     */
    class CGNeighbors(runtime: Runtime) : Struct(runtime) {
        val n = size_t()
        val neigh = Pointer()
    }


    /**
     * typedef int(*cbc_progress_callback)(void *model, int phase, int step, const char *phaseName,
     * double seconds, double lb, double ub, int nint, int *vecint, void *cbData);
     */
    interface Cbc_ProgressCallback {
        @Delegate
        fun progressCallback(model: Pointer, phase: Int, step: Int, @Pinned phaseName: String, seconds: Double, lb: Double, ub: Double, nint: Int, vecint: Pointer, cbData: Pointer)
    }

    /**
     * typedef void(*cbc_callback)(void *model, int msgno, int ndouble, const double *dvec,
     * int nint, const int *ivec, int nchar, char **cvec);
     */
    interface Cbc_Callback {
        @Delegate
        fun callback(model: Pointer, msgno: Int, nDouble: Int, dvec: Pointer?, nint: Int, ivec: Pointer?, nchar: Int, cvec: Pointer?)
    }

    /**
     * typedef void(*cbc_cut_callback)(void *osiSolver, void *osiCuts, void *appdata);
     */
    interface Cbc_CutCallback {
        @Delegate
        fun cutCallback(osiSolver: Pointer, osiCuts: Pointer, appData: Pointer)
    }

    /**
     * typedef int (*cbc_incumbent_callback)(void *cbcModel, double obj, int nz, char **vnames,
     * double *x, void *appData);
     */
    interface Cbc_IncumbentCallback {
        @Delegate
        fun incumbentCallback(cbcModel: Pointer, obj: Double, nz: Int, vnames: Pointer, x: Pointer, appData: Pointer): Int
    }


    companion object {
        @JvmStatic
        fun loadLibrary(): CbcJnrLibrary {
            val library: String?
            var libLocation: String? = System.getProperty("user.dir") + File.separatorChar

            val platform = Platform.getNativePlatform()
            when (platform.os) {
                Platform.OS.DARWIN -> {
                    library = "cbc-c-darwin-x86-64.dylib"
                    libLocation += "libraries"
                }
                Platform.OS.LINUX -> {
                    library = "cbc-c-linux-x86-64.so"
                    libLocation += "libraries"
                }
                Platform.OS.WINDOWS -> {
                    library = "libCbcSolver-0.dll"
                    libLocation += "libraries\\win64"
                }
                else -> {
                    library = null
                    libLocation = null
                }
            }

            return LibraryLoader
                .create(CbcJnrJavaLibrary::class.java)
                .failImmediately()
                .load(libLocation + File.separatorChar + library)
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

        /*
         * enum LPMethod {
         *     LPM_Auto    = 0,  ! Solver will decide automatically which method to use
         *     LPM_Dual    = 1,  ! Dual simplex
         *     LPM_Primal  = 2,  ! Primal simplex
         *     LPM_Barrier = 3   ! The barrier algorithm.
         * };
         */
        const val LPM_Auto = 0
        const val LPM_Dual = 1
        const val LPM_Primal = 2
        const val LPM_Barrier = 3

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
        const val CT_Gomory = 0
        const val CT_MIR = 1
        const val CT_ZeroHalf = 2
        const val CT_Clique = 3
        const val CT_KnapsackCover = 4
        const val CT_LiftAndProject = 5

        /*
         * Additional constants
         */
        const val CHAR_ONE = 1.toByte()
        const val CHAR_ZERO = 0.toByte()

        /**
         * Map of constants' strings
         */
        val constantsMap = mapOf(
            "DBL_PARAM_PRIMAL_TOL" to DBL_PARAM_PRIMAL_TOL,
            "DBL_PARAM_DUAL_TOL" to DBL_PARAM_DUAL_TOL,
            "DBL_PARAM_ZERO_TOL" to DBL_PARAM_ZERO_TOL,
            "DBL_PARAM_INT_TOL" to DBL_PARAM_INT_TOL,
            "DBL_PARAM_PRESOLVE_TOL" to DBL_PARAM_PRESOLVE_TOL,
            "DBL_PARAM_TIME_LIMIT" to DBL_PARAM_TIME_LIMIT,
            "DBL_PARAM_PSI" to DBL_PARAM_PSI,
            "DBL_PARAM_CUTOFF" to DBL_PARAM_CUTOFF,
            "DBL_PARAM_ALLOWABLE_GAP" to DBL_PARAM_ALLOWABLE_GAP,
            "DBL_PARAM_GAP_RATIO" to DBL_PARAM_GAP_RATIO,
            "N_DBL_PARAMS" to N_DBL_PARAMS,

            "INT_PARAM_PERT_VALUE" to INT_PARAM_PERT_VALUE,
            "INT_PARAM_IDIOT" to INT_PARAM_IDIOT,
            "INT_PARAM_STRONG_BRANCHING" to INT_PARAM_STRONG_BRANCHING,
            "INT_PARAM_CUT_DEPTH" to INT_PARAM_CUT_DEPTH,
            "INT_PARAM_MAX_NODES" to INT_PARAM_MAX_NODES,
            "INT_PARAM_NUMBER_BEFORE" to INT_PARAM_NUMBER_BEFORE,
            "INT_PARAM_FPUMP_ITS" to INT_PARAM_FPUMP_ITS,
            "INT_PARAM_MAX_SOLS" to INT_PARAM_MAX_SOLS,
            "INT_PARAM_CUT_PASS_IN_TREE" to INT_PARAM_CUT_PASS_IN_TREE,
            "INT_PARAM_THREADS" to INT_PARAM_THREADS,
            "INT_PARAM_CUT_PASS" to INT_PARAM_CUT_PASS,
            "INT_PARAM_LOG_LEVEL" to INT_PARAM_LOG_LEVEL,
            "INT_PARAM_MAX_SAVED_SOLS" to INT_PARAM_MAX_SAVED_SOLS,
            "INT_PARAM_MULTIPLE_ROOTS" to INT_PARAM_MULTIPLE_ROOTS,
            "INT_PARAM_ROUND_INT_VARS" to INT_PARAM_ROUND_INT_VARS,
            "INT_PARAM_RANDOM_SEED" to INT_PARAM_RANDOM_SEED,
            "INT_PARAM_ELAPSED_TIME" to INT_PARAM_ELAPSED_TIME,
            "N_INT_PARAMS" to N_INT_PARAMS,

            "LPM_Auto" to LPM_Auto,
            "LPM_Dual" to LPM_Dual,
            "LPM_Primal" to LPM_Primal,
            "LPM_Barrier" to LPM_Barrier,

            "CT_Gomory" to CT_Gomory,
            "CT_MIR" to CT_MIR,
            "CT_ZeroHalf" to CT_ZeroHalf,
            "CT_Clique" to CT_Clique,
            "CT_KnapsackCover" to CT_KnapsackCover,
            "CT_LiftAndProject" to CT_LiftAndProject,
        )
    }
}