package mip.solvers

import jnr.ffi.*
import jnr.ffi.annotations.*
import jnr.ffi.byref.*

internal interface GurobiJnrLib {

    fun fflush(stream: Pointer?) = CLibrary.lib.fflush(stream)

    /** GRBenv *GRBgetenv(GRBmodel *model); */
    fun GRBgetenv(@Pinned @In model: Pointer): Pointer

    /** int GRBloadenv(GRBenv **envP, const char *logfilename); */
    fun GRBloadenv(@Out envP: PointerByReference, logfilename: String?): Int

    /**
     * int GRBnewmodel(GRBenv *env, GRBmodel **modelP, const char *Pname, int numvars, double *obj,
     *                 double *lb, double *ub, char *vtype, char **varnames);
     */
    fun GRBnewmodel(@Pinned @In env: Pointer, @Out modelP: PointerByReference, Pname: String, numvars: Int,
                    obj: Pointer?, lb: Pointer?, ub: Pointer?, vtype: Pointer?,
                    varnames: PointerByReference?): Int

    /** void GRBfreeenv(GRBenv *env); */
    fun GRBfreeenv(@Pinned @In env: Pointer)

    /** int GRBfreemodel(GRBmodel *model); */
    fun GRBfreemodel(@Pinned @In model: Pointer): Int

    /** int GRBgetintattr(GRBmodel *model, const char *attrname, int *valueP); */
    fun GRBgetintattr(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, @Out valueP: IntByReference): Int

    /** int GRBsetintattr(GRBmodel *model, const char *attrname, int newvalue); */
    fun GRBsetintattr(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, newvalue: Int): Int

    /** int GRBgetintattrelement(GRBmodel *model, const char *attrname, int element, int *valueP); */
    fun GRBgetintattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, @Out valueP: Pointer?): Int

    /** int GRBsetintattrelement(GRBmodel *model, const char *attrname, int element, int newvalue); */
    fun GRBsetintattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, newvalue: Int): Int

    /** int GRBgetdblattr(GRBmodel *model, const char *attrname, double *valueP); */
    fun GRBgetdblattr(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, @Out valueP: DoubleByReference): Int

    /** int GRBsetdblattr(GRBmodel *model, const char *attrname, double newvalue); */
    fun GRBsetdblattr(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, newvalue: Double): Int

    /**
     * int GRBgetcharattrarray(GRBmodel *model, const char *attrname, int first, int len,
     *                         char *values);
     */
    fun GRBgetdblattrarray(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, first: Int, len: Int,
                           values: Pointer?): Int

    /**
     * int GRBsetdblattrarray(GRBmodel *model, const char *attrname, int first, int len,
     *                        double *newvalues);
     */
    fun GRBsetdblattrarray(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, first: Int, len: Int,
                           newvalues: Pointer?): Int

    /**
     * int GRBsetdblattrlist(GRBmodel *model, const char *attrname, int len, int *ind,
     *                       double *newvalues);
     */
    fun GRBsetdblattrlist(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, len: Int, @Pinned @In ind: IntArray, @Pinned @In newvalues: DoubleArray): Int

    /** int GRBgetdblattrelement(GRBmodel *model, const char *attrname, int element, double *valueP); */
    fun GRBgetdblattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, valueP: DoubleByReference?): Int

    /** int GRBsetdblattrelement(GRBmodel *model, const char *attrname, int element, double newvalue); */
    fun GRBsetdblattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, newvalue: Double): Int

    /**
     * int GRBgetcharattrarray(GRBmodel *model, const char *attrname, int first, int len,
     *                         char *values);
     */
    fun GRBgetcharattrarray(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, first: Int, len: Int,
                            values: String): Int

    /**
     * int GRBsetcharattrarray(GRBmodel *model, const char *attrname, int first, int len,
     *                         char *newvalues);
     */
    fun GRBsetcharattrarray(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, first: Int, len: Int,
                            newvalues: String): Int

    /** int GRBgetcharattrelement(GRBmodel *model, const char *attrname, int element, char *valueP); */
    fun GRBgetcharattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, @Out valueP: Pointer): Int

    /** int GRBsetcharattrelement(GRBmodel *model, const char *attrname, int element, char newvalue); */
    fun GRBsetcharattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, newvalue: Byte): Int

    /** int GRBgetstrattrelement(GRBmodel *model, const char *attrname, int element, char **valueP); */
    fun GRBgetstrattrelement(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, element: Int, @Out valueP: PointerByReference): Int

    /** int GRBgetstrattr (GRBmodel *model, const char *attrname, char **valueP); */
    fun GRBgetstrattr(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, @Out valueP: PointerByReference): Int

    /** int GRBsetstrattr (GRBmodel *model, const char *attrname, const char *newvalue); */
    fun GRBsetstrattr(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, @Pinned @In @Transient newvalue: String): Int

    /** int GRBgetintparam(GRBenv *env, const char *paramname, int *valueP); */
    fun GRBgetintparam(@Pinned @In env: Pointer, @Pinned @In @Transient paramname: String, @Out valueP: IntByReference): Int

    /** int GRBsetintparam(GRBenv *env, const char *paramname, int value); */
    fun GRBsetintparam(@Pinned @In env: Pointer, @Pinned @In @Transient paramname: String, value: Int): Int

    /** int GRBgetdblparam(GRBenv *env, const char *paramname, double *valueP); */
    fun GRBgetdblparam(@Pinned @In env: Pointer, @Pinned @In @Transient paramname: String, @Out valueP: DoubleByReference): Int

    /** int GRBsetdblparam(GRBenv *env, const char *paramname, double value); */
    fun GRBsetdblparam(@Pinned @In env: Pointer, @Pinned @In @Transient attrname: String, newvalue: Double): Int

    /**
     * int GRBsetobjectiven(GRBmodel *model, int index, int priority, double weight, double abstol,
     *                      double reltol, const char *name, double constant, int lnz, int *lind,
     *                      double *lval);
     */
    fun GRBsetobjectiven(@Pinned @In model: Pointer, index: Int, priority: Int, weight: Double, abstol: Double,
                         reltol: Double, name: String, constant: Double, lnz: Int, lind: Pointer?,
                         lval: Pointer?): Int

    /**
     * int GRBaddvar(GRBmodel *model, int numnz, int *vind, double *vval, double obj, double lb,
     *               double ub, char vtype, const char *varname);
     */
    fun GRBaddvar(@Pinned @In model: Pointer, numnz: Int, @Pinned @In vind: IntArray?, @Pinned @In vval: DoubleArray?, obj: Double,
                  lb: Double, ub: Double, vtype: Byte, varname: String): Int

    /**
     * int GRBaddconstr(GRBmodel *model, int numnz, int *cind, double *cval, char sense, double rhs,
     *                  const char *constrname);
     */
    fun GRBaddconstr(@Pinned @In model: Pointer, numnz: Int, @Pinned @In cind: IntArray?, @Pinned @In cval: DoubleArray?, sense: Byte,
                     rhs: Double, constrname: String): Int

    /**
     * int GRBaddsos(GRBmodel *model, int numsos, int nummembers, int *types, int *beg, int *ind,
     *               double *weight);
     */
    fun GRBaddsos(@Pinned @In model: Pointer, numsos: Int, nummembers: Int, types: Pointer?, beg: Pointer?,
                  ind: Pointer?, weight: Pointer?): Int

    /**
     * int GRBgetconstrs(GRBmodel *model, int *numnzP, int *cbeg, int *cind, double *cval,
     *                   int start, int len);
     */
    fun GRBgetconstrs(@Pinned @In model: Pointer, numnzP: Pointer?, cbeg: Pointer?, cind: Pointer?,
                      cval: Pointer?, start: Int, len: Int): Int

    /**
     * int GRBgetvars(GRBmodel *model, int *numnzP, int *vbeg, int *vind, double *vval, int start,
     *                int len);
     */
    fun GRBgetvars(@Pinned @In model: Pointer, numnzP: Pointer?, vbeg: Pointer?, vind: Pointer?,
                   vval: Pointer?, start: Int, len: Int): Int

    /** int GRBgetvarbyname(GRBmodel *model, const char *name, int *indexP); */
    fun GRBgetvarbyname(@Pinned @In model: Pointer, name: String, indexP: Pointer?): Int

    /** int GRBgetconstrbyname(GRBmodel *model, const char *name, int *indexP); */
    fun GRBgetconstrbyname(@Pinned @In model: Pointer, name: String, indexP: Pointer?): Int

    /** int GRBoptimize(GRBmodel *model); */
    fun GRBoptimize(@Pinned @In model: Pointer): Int

    /** int GRBupdatemodel(GRBmodel *model); */
    fun GRBupdatemodel(@Pinned @In model: Pointer): Int

    /** int GRBwrite(GRBmodel *model, const char *filename); */
    fun GRBwrite(@Pinned @In model: Pointer, filename: String): Int

    /** int GRBreadmodel(GRBenv *env, const char *filename, GRBmodel **modelP); */
    fun GRBreadmodel(env: Pointer, filename: String, modelP: PointerByReference): Int

    /** int GRBdelvars(GRBmodel *model, int numdel, int *ind ); */
    fun GRBdelvars(@Pinned @In model: Pointer, numdel: Int, @Pinned @In ind: IntArray?): Int

    /**
     * int GRBsetcharattrlist(GRBmodel *model, const char *attrname, int len, int *ind,
     *                        char *newvalues);
     */
    fun GRBsetcharattrlist(@Pinned @In model: Pointer, @Pinned @In @Transient attrname: String, len: Int, ind: IntArray?,
                           newvalues: ByteArray?): Int

    /** int GRBsetcallbackfunc(GRBmodel *model, gurobi_callback grbcb, void *usrdata); */
    fun GRBsetcallbackfunc(@Pinned @In model: Pointer, grbcb: GRBcallback, usrdata: Pointer): Int

    /** int GRBcbget(void *cbdata, int where, int what, void *resultP); */
    fun GRBcbget(cbdata: Pointer, where: Int, what: Int, resultP: Pointer): Int

    /** int GRBcbsetparam(void *cbdata, const char *paramname, const char *newvalue); */
    fun GRBcbsetparam(cbdata: Pointer, paramname: String, newvalue: String): Int

    /** int GRBcbsolution(void *cbdata, const double *solution, double *objvalP); */
    fun GRBcbsolution(cbdata: Pointer, solution: Pointer?, objvalP: String): Int

    /**
     * int GRBcbcut(void *cbdata, int cutlen, const int *cutind, const double *cutval,
     *              char cutsense, double cutrhs);
     */
    fun GRBcbcut(cbdata: Pointer, cutlen: Int, cutind: Pointer?, cutval: Pointer?,
                 cutsense: Byte, cutrhs: Double): Int

    /**
     * int GRBcblazy(void *cbdata, int lazylen, const int *lazyind, const double *lazyval,
     *               char lazysense, double lazyrhs);
     */
    fun GRBcblazy(cbdata: Pointer, lazylen: Int, lazyind: Pointer?, lazyval: Pointer?,
                  lazysense: Byte, lazyrhs: Double): Int

    /** int GRBdelconstrs (GRBmodel *model, int numdel, int *ind); */
    fun GRBdelconstrs(@Pinned @In model: Pointer, numdel: Int, @Pinned @In ind: IntArray?): Int


    /**
     * typedef int(*gurobi_callback)(GRBmodel *model, void *cbdata, int where, void *usrdata);
     */
    interface GRBcallback {
        @Delegate
        fun gurobiCallback(@Pinned @In model: Pointer, cbdata: Pointer, where: Int, usrdata: Pointer)
    }


    companion object {

        @JvmStatic
        var library: GurobiJnrLib? = null

        @JvmStatic
        fun loadLibrary(): GurobiJnrLib {
            if (library != null) return library!!

            val platform = Platform.getNativePlatform();

            val versions = (10 downTo 6).flatMap { i -> (11 downTo 0).map { j -> "$i$j" } }
            val libNames: List<String> = when (platform.os) {
                Platform.OS.DARWIN, Platform.OS.LINUX -> versions.map { "gurobi$it" }.toList()
                Platform.OS.WINDOWS -> versions.map { "gurobi$it.dll" }.toList()
                else -> emptyList()
            }

            var lib: GurobiJnrLib? = null
            for (library in libNames) {
                try {
                    lib = LibraryLoader
                        .create(GurobiJnrLib::class.java)
                        .failImmediately()
                        .load(library)
                }
                catch (e: UnsatisfiedLinkError) {
                }

                if (lib != null) break
            }

            library = lib
            return library!!
        }

        const val CHAR_ONE: Byte = 1.toByte()
        const val CHAR_ZERO: Byte = 0.toByte()

        const val GRB_INFINITY = 1e100
        const val GRB_UNDEFINED = 1e101
        const val GRB_MAXINT = 2000000000

        /**
         * enum DblParam {
         *     DBL_PARAM_PRIMAL_TOL    = 0,  /*! Tollerance to consider a solution feasible in the linear programming solver. */
         *     DBL_PARAM_DUAL_TOL      = 1,  /*! Tollerance for a solution to be considered optimal in the linear programming solver. */
         *     DBL_PARAM_ZERO_TOL      = 2,  /*! Coefficients less that this value will be ignored when reading instances */
         *     DBL_PARAM_INT_TOL       = 3,  /*! Maximum allowed distance from integer value for a variable to be considered integral */
         *     DBL_PARAM_PRESOLVE_TOL  = 4,  /*! Tollerance used in the presolver, should be increased if the pre-solver is declaring infeasible a feasible problem */
         *     DBL_PARAM_TIME_LIMIT    = 5,  /*! Time limit in seconds */
         *     DBL_PARAM_PSI           = 6,  /*! Two dimensional princing factor in the Positive Edge pivot strategy. */
         *     DBL_PARAM_CUTOFF        = 7,  /*! Only search for solutions with cost less-or-equal to this value. */
         *     DBL_PARAM_ALLOWABLE_GAP = 8,  /*! Allowable gap between the lower and upper bound to conclude the search */
         *     DBL_PARAM_GAP_RATIO     = 9   /*! Stops the search when the difference between the upper and lower bound is less than this fraction of the larger value */
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

        /**
         * /*! Integer parameters */
         * enum IntParam {
         *     INT_PARAM_PERT_VALUE       = 0,  /*! Method of perturbation, -5000 to 102, default 50 */
         *     INT_PARAM_IDIOT            = 1,  /*! Parameter of the "idiot" method to try to produce an initial feasible basis. -1 let the solver decide if this should be applied; 0 deactivates it and >0 sets number of passes. */
         *     INT_PARAM_STRONG_BRANCHING = 2,  /*! Number of variables to be evaluated in strong branching. */
         *     INT_PARAM_CUT_DEPTH        = 3,  /*! Sets the application of cuts to every depth multiple of this value. -1, the default value, let the solve decide. */
         *     INT_PARAM_MAX_NODES        = 4,  /*! Maximum number of nodes to be explored in the search tree */
         *     INT_PARAM_NUMBER_BEFORE    = 5,  /*! Number of branche before trusting pseudocodes computed in strong branching. */
         *     INT_PARAM_FPUMP_ITS        = 6,  /*! Maximum number of iterations in the feasibility pump method. */
         *     INT_PARAM_MAX_SOLS         = 7,  /*! Maximum number of solutions generated during the search. Stops the search when this number of solutions is found. */
         *     INT_PARAM_CUT_PASS_IN_TREE = 8, /*! Maxinum number of cuts passes in the search tree (with the exception of the root node). Default 1. */
         *     INT_PARAM_THREADS          = 9, /*! Number of threads that can be used in the branch-and-bound method.*/
         *     INT_PARAM_CUT_PASS         = 10, /*! Number of cut passes in the root node. Default -1, solver decides */
         *     INT_PARAM_LOG_LEVEL        = 11, /*! Verbosity level, from 0 to 2 */
         *     INT_PARAM_MAX_SAVED_SOLS   = 12, /*! Size of the pool to save the best solutions found during the search. */
         *     INT_PARAM_MULTIPLE_ROOTS   = 13, /*! Multiple root passes to get additional cuts and solutions. */
         *     INT_PARAM_ROUND_INT_VARS   = 14, /*! If integer variables should be round to remove small infeasibilities. This can increase the overall amount of infeasibilities in problems with both continuous and integer variables */
         *     INT_PARAM_RANDOM_SEED      = 15, /*! When solving LP and MIP, randomization is used to break ties in some decisions. This changes the random seed so that multiple executions can produce different results */
         *     INT_PARAM_ELAPSED_TIME     = 16  /*! When =1 use elapsed (wallclock) time, otherwise use CPU time */
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

        /**
         * enum LPMethod {
         *     LPM_Auto    = 0,  /*! Solver will decide automatically which method to use */
         *     LPM_Dual    = 1,  /*! Dual simplex */
         *     LPM_Primal  = 2,  /*! Primal simplex */
         *     LPM_Barrier = 3   /*! The barrier algorithm. */
         * };
         */
        const val LPM_Auto = 0
        const val LPM_Dual = 1
        const val LPM_Primal = 2
        const val LPM_Barrier = 3

        /**
         * enum CutType {
         *     CT_Gomory         = 0,  /*! Gomory cuts obtained from the tableau */
         *     CT_MIR            = 1,  /*! Mixed integer rounding cuts */
         *     CT_ZeroHalf       = 2,  /*! Zero-half cuts */
         *     CT_Clique         = 3,  /*! Clique cuts */
         *     CT_KnapsackCover  = 4,  /*! Knapsack cover cuts */
         *     CT_LiftAndProject = 5   /*! Lift and project cuts */
         * };
         */
        const val CT_Gomory = 0
        const val CT_MIR = 1
        const val CT_ZeroHalf = 2
        const val CT_Clique = 3
        const val CT_KnapsackCover = 4
        const val CT_LiftAndProject = 5
    }
}
