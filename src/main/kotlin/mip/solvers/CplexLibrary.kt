package mip.solvers

import jnr.ffi.*
import jnr.ffi.byref.ByteByReference
import jnr.ffi.byref.PointerByReference

internal interface CplexLibrary {

    fun fflush(stream: Pointer?) = CLibrary.lib.fflush(stream)

    companion object {

        @JvmStatic
        val lib: CplexLibrary

        init {
            val platform = Platform.getNativePlatform();

            val libNames: List<String> = when (platform.os) {
                Platform.OS.DARWIN, Platform.OS.LINUX -> listOf(
                    "/opt/cplex1210/cplex/bin/x86-64_osx/libcplex12100.dylib"
                )
                Platform.OS.WINDOWS -> listOf("cplex12100")
                else -> emptyList()
            }

            var lib: CplexLibrary? = null
            for (library in libNames) {
                try {
                    lib = LibraryLoader
                        .create(CplexLibrary::class.java)
                        .failImmediately()
                        .load(library)
                }
                catch (e: UnsatisfiedLinkError) {
                }

                if (lib != null) break
            }

            this.lib = lib!!
        }

        // region Generic constants

        const val CPX_AUTO = -1
        const val CPX_ON = 1
        const val CPX_OFF = 0
        const val CPX_MAX = -1
        const val CPX_MIN = 1

        // endregion Generic constants

        // region CPXPARAM constants

        const val CPXPARAM_Advance = 1001
        const val CPXPARAM_Barrier_Algorithm = 3007
        const val CPXPARAM_Barrier_ColNonzeros = 3009
        const val CPXPARAM_Barrier_ConvergeTol = 3002
        const val CPXPARAM_Barrier_Crossover = 3018
        const val CPXPARAM_Barrier_Display = 3010
        const val CPXPARAM_Barrier_Limits_Corrections = 3013
        const val CPXPARAM_Barrier_Limits_Growth = 3003
        const val CPXPARAM_Barrier_Limits_Iteration = 3012
        const val CPXPARAM_Barrier_Limits_ObjRange = 3004
        const val CPXPARAM_Barrier_Ordering = 3014
        const val CPXPARAM_Barrier_QCPConvergeTol = 3020
        const val CPXPARAM_Barrier_StartAlg = 3017
        const val CPXPARAM_Benders_Strategy = 1501
        const val CPXPARAM_Benders_Tolerances_feasibilitycut = 1509
        const val CPXPARAM_Benders_Tolerances_optimalitycut = 1510
        const val CPXPARAM_Benders_WorkerAlgorithm = 1500
        const val CPXPARAM_ClockType = 1006
        const val CPXPARAM_Conflict_Algorithm = 1073
        const val CPXPARAM_Conflict_Display = 1074
        const val CPXPARAM_CPUmask = 1144
        const val CPXPARAM_DetTimeLimit = 1127
        const val CPXPARAM_DistMIP_Rampup_DetTimeLimit = 2164
        const val CPXPARAM_DistMIP_Rampup_Duration = 2163
        const val CPXPARAM_DistMIP_Rampup_TimeLimit = 2165
        const val CPXPARAM_Emphasis_Memory = 1082
        const val CPXPARAM_Emphasis_MIP = 2058
        const val CPXPARAM_Emphasis_Numerical = 1083
        const val CPXPARAM_Feasopt_Mode = 1084
        const val CPXPARAM_Feasopt_Tolerance = 2073
        const val CPXPARAM_LPMethod = 1062
        const val CPXPARAM_MIP_Cuts_BQP = 2195
        const val CPXPARAM_MIP_Cuts_Cliques = 2003
        const val CPXPARAM_MIP_Cuts_Covers = 2005
        const val CPXPARAM_MIP_Cuts_Disjunctive = 2053
        const val CPXPARAM_MIP_Cuts_FlowCovers = 2040
        const val CPXPARAM_MIP_Cuts_Gomory = 2049
        const val CPXPARAM_MIP_Cuts_GUBCovers = 2044
        const val CPXPARAM_MIP_Cuts_Implied = 2041
        const val CPXPARAM_MIP_Cuts_LiftProj = 2152
        const val CPXPARAM_MIP_Cuts_LocalImplied = 2181
        const val CPXPARAM_MIP_Cuts_MCFCut = 2134
        const val CPXPARAM_MIP_Cuts_MIRCut = 2052
        const val CPXPARAM_MIP_Cuts_PathCut = 2051
        const val CPXPARAM_MIP_Cuts_RLT = 2196
        const val CPXPARAM_MIP_Cuts_ZeroHalfCut = 2111
        const val CPXPARAM_MIP_Display = 2012
        const val CPXPARAM_MIP_Interval = 2013
        const val CPXPARAM_MIP_Limits_AggForCut = 2054
        const val CPXPARAM_MIP_Limits_AuxRootThreads = 2139
        const val CPXPARAM_MIP_Limits_CutPasses = 2056
        const val CPXPARAM_MIP_Limits_CutsFactor = 2033
        const val CPXPARAM_MIP_Limits_EachCutLimit = 2102
        const val CPXPARAM_MIP_Limits_GomoryCand = 2048
        const val CPXPARAM_MIP_Limits_GomoryPass = 2050
        const val CPXPARAM_MIP_Limits_Nodes = 2017
        const val CPXPARAM_MIP_Limits_PolishTime = 2066
        const val CPXPARAM_MIP_Limits_Populate = 2108
        const val CPXPARAM_MIP_Limits_ProbeDetTime = 2150
        const val CPXPARAM_MIP_Limits_ProbeTime = 2065
        const val CPXPARAM_MIP_Limits_RepairTries = 2067
        const val CPXPARAM_MIP_Limits_Solutions = 2015
        const val CPXPARAM_MIP_Limits_StrongCand = 2045
        const val CPXPARAM_MIP_Limits_StrongIt = 2046
        const val CPXPARAM_MIP_Limits_TreeMemory = 2027
        const val CPXPARAM_MIP_OrderType = 2032
        const val CPXPARAM_MIP_PolishAfter_AbsMIPGap = 2126
        const val CPXPARAM_MIP_PolishAfter_DetTime = 2151
        const val CPXPARAM_MIP_PolishAfter_MIPGap = 2127
        const val CPXPARAM_MIP_PolishAfter_Nodes = 2128
        const val CPXPARAM_MIP_PolishAfter_Solutions = 2129
        const val CPXPARAM_MIP_PolishAfter_Time = 2130
        const val CPXPARAM_MIP_Pool_AbsGap = 2106
        const val CPXPARAM_MIP_Pool_Capacity = 2103
        const val CPXPARAM_MIP_Pool_Intensity = 2107
        const val CPXPARAM_MIP_Pool_RelGap = 2105
        const val CPXPARAM_MIP_Pool_Replace = 2104
        const val CPXPARAM_MIP_Strategy_Backtrack = 2002
        const val CPXPARAM_MIP_Strategy_BBInterval = 2039
        const val CPXPARAM_MIP_Strategy_Branch = 2001
        const val CPXPARAM_MIP_Strategy_CallbackReducedLP = 2055
        const val CPXPARAM_MIP_Strategy_Dive = 2060
        const val CPXPARAM_MIP_Strategy_File = 2016
        const val CPXPARAM_MIP_Strategy_FPHeur = 2098
        const val CPXPARAM_MIP_Strategy_HeuristicEffort = 2120
        const val CPXPARAM_MIP_Strategy_HeuristicFreq = 2031
        const val CPXPARAM_MIP_Strategy_KappaStats = 2137
        const val CPXPARAM_MIP_Strategy_LBHeur = 2063
        const val CPXPARAM_MIP_Strategy_MIQCPStrat = 2110
        const val CPXPARAM_MIP_Strategy_NodeSelect = 2018
        const val CPXPARAM_MIP_Strategy_Order = 2020
        const val CPXPARAM_MIP_Strategy_PresolveNode = 2037
        const val CPXPARAM_MIP_Strategy_Probe = 2042
        const val CPXPARAM_MIP_Strategy_RINSHeur = 2061
        const val CPXPARAM_MIP_Strategy_Search = 2109
        const val CPXPARAM_MIP_Strategy_StartAlgorithm = 2025
        const val CPXPARAM_MIP_Strategy_SubAlgorithm = 2026
        const val CPXPARAM_MIP_Strategy_VariableSelect = 2028
        const val CPXPARAM_MIP_SubMIP_StartAlg = 2205
        const val CPXPARAM_MIP_SubMIP_SubAlg = 2206
        const val CPXPARAM_MIP_SubMIP_NodeLimit = 2212
        const val CPXPARAM_MIP_SubMIP_Scale = 2207
        const val CPXPARAM_MIP_Tolerances_AbsMIPGap = 2008
        const val CPXPARAM_MIP_Tolerances_Linearization = 2068
        const val CPXPARAM_MIP_Tolerances_Integrality = 2010
        const val CPXPARAM_MIP_Tolerances_LowerCutoff = 2006
        const val CPXPARAM_MIP_Tolerances_MIPGap = 2009
        const val CPXPARAM_MIP_Tolerances_ObjDifference = 2019
        const val CPXPARAM_MIP_Tolerances_RelObjDifference = 2022
        const val CPXPARAM_MIP_Tolerances_UpperCutoff = 2007
        const val CPXPARAM_MultiObjective_Display = 1600
        const val CPXPARAM_Network_Display = 5005
        const val CPXPARAM_Network_Iterations = 5001
        const val CPXPARAM_Network_NetFind = 1022
        const val CPXPARAM_Network_Pricing = 5004
        const val CPXPARAM_Network_Tolerances_Feasibility = 5003
        const val CPXPARAM_Network_Tolerances_Optimality = 5002
        const val CPXPARAM_OptimalityTarget = 1131
        const val CPXPARAM_Output_CloneLog = 1132
        const val CPXPARAM_Output_IntSolFilePrefix = 2143
        const val CPXPARAM_Output_MPSLong = 1081
        const val CPXPARAM_Output_WriteLevel = 1114
        const val CPXPARAM_Parallel = 1109
        const val CPXPARAM_ParamDisplay = 1163
        const val CPXPARAM_Preprocessing_Aggregator = 1003
        const val CPXPARAM_Preprocessing_BoundStrength = 2029
        const val CPXPARAM_Preprocessing_CoeffReduce = 2004
        const val CPXPARAM_Preprocessing_Dependency = 1008
        const val CPXPARAM_Preprocessing_Dual = 1044
        const val CPXPARAM_Preprocessing_Fill = 1002
        const val CPXPARAM_Preprocessing_Folding = 1164
        const val CPXPARAM_Preprocessing_Linear = 1058
        const val CPXPARAM_Preprocessing_NumPass = 1052
        const val CPXPARAM_Preprocessing_Presolve = 1030
        const val CPXPARAM_Preprocessing_QCPDuals = 4003
        const val CPXPARAM_Preprocessing_QPMakePSD = 4010
        const val CPXPARAM_Preprocessing_QToLin = 4012
        const val CPXPARAM_Preprocessing_Reduce = 1057
        const val CPXPARAM_Preprocessing_Relax = 2034
        const val CPXPARAM_Preprocessing_RepeatPresolve = 2064
        const val CPXPARAM_Preprocessing_Symmetry = 2059
        const val CPXPARAM_QPMethod = 1063
        const val CPXPARAM_RandomSeed = 1124
        const val CPXPARAM_Read_APIEncoding = 1130
        const val CPXPARAM_Read_Constraints = 1021
        const val CPXPARAM_Read_DataCheck = 1056
        const val CPXPARAM_Read_FileEncoding = 1129
        const val CPXPARAM_Read_Nonzeros = 1024
        const val CPXPARAM_Read_QPNonzeros = 4001
        const val CPXPARAM_Read_Scale = 1034
        const val CPXPARAM_Read_Variables = 1023
        const val CPXPARAM_Read_WarningLimit = 1157
        const val CPXPARAM_Record = 1162
        const val CPXPARAM_ScreenOutput = 1035
        const val CPXPARAM_Sifting_Algorithm = 1077
        const val CPXPARAM_Sifting_Simplex = 1158
        const val CPXPARAM_Sifting_Display = 1076
        const val CPXPARAM_Sifting_Iterations = 1078
        const val CPXPARAM_Simplex_Crash = 1007
        const val CPXPARAM_Simplex_DGradient = 1009
        const val CPXPARAM_Simplex_Display = 1019
        const val CPXPARAM_Simplex_DynamicRows = 1161
        const val CPXPARAM_Simplex_Limits_Iterations = 1020
        const val CPXPARAM_Simplex_Limits_LowerObj = 1025
        const val CPXPARAM_Simplex_Limits_Perturbation = 1028
        const val CPXPARAM_Simplex_Limits_Singularity = 1037
        const val CPXPARAM_Simplex_Limits_UpperObj = 1026
        const val CPXPARAM_Simplex_Perturbation_Constant = 1015
        const val CPXPARAM_Simplex_Perturbation_Indicator = 1027
        const val CPXPARAM_Simplex_PGradient = 1029
        const val CPXPARAM_Simplex_Pricing = 1010
        const val CPXPARAM_Simplex_Refactor = 1031
        const val CPXPARAM_Simplex_Tolerances_Feasibility = 1016
        const val CPXPARAM_Simplex_Tolerances_Markowitz = 1013
        const val CPXPARAM_Simplex_Tolerances_Optimality = 1014
        const val CPXPARAM_SolutionType = 1147
        const val CPXPARAM_Threads = 1067
        const val CPXPARAM_TimeLimit = 1039
        const val CPXPARAM_Tune_DetTimeLimit = 1139
        const val CPXPARAM_Tune_Display = 1113
        const val CPXPARAM_Tune_Measure = 1110
        const val CPXPARAM_Tune_Repeat = 1111
        const val CPXPARAM_Tune_TimeLimit = 1112
        const val CPXPARAM_WorkDir = 1064
        const val CPXPARAM_WorkMem = 1065

        const val CPX_PARAM_ADVIND = 1001
        const val CPX_PARAM_AGGFILL = 1002
        const val CPX_PARAM_AGGIND = 1003
        const val CPX_PARAM_CLOCKTYPE = 1006
        const val CPX_PARAM_CRAIND = 1007
        const val CPX_PARAM_DEPIND = 1008
        const val CPX_PARAM_DPRIIND = 1009
        const val CPX_PARAM_PRICELIM = 1010
        const val CPX_PARAM_EPMRK = 1013
        const val CPX_PARAM_EPOPT = 1014
        const val CPX_PARAM_EPPER = 1015
        const val CPX_PARAM_EPRHS = 1016
        const val CPX_PARAM_SIMDISPLAY = 1019
        const val CPX_PARAM_ITLIM = 1020
        const val CPX_PARAM_ROWREADLIM = 1021
        const val CPX_PARAM_NETFIND = 1022
        const val CPX_PARAM_COLREADLIM = 1023
        const val CPX_PARAM_NZREADLIM = 1024
        const val CPX_PARAM_OBJLLIM = 1025
        const val CPX_PARAM_OBJULIM = 1026
        const val CPX_PARAM_PERIND = 1027
        const val CPX_PARAM_PERLIM = 1028
        const val CPX_PARAM_PPRIIND = 1029
        const val CPX_PARAM_PREIND = 1030
        const val CPX_PARAM_REINV = 1031
        const val CPX_PARAM_SCAIND = 1034
        const val CPX_PARAM_SCRIND = 1035
        const val CPX_PARAM_SINGLIM = 1037
        const val CPX_PARAM_TILIM = 1039
        const val CPX_PARAM_PREDUAL = 1044
        const val CPX_PARAM_PREPASS = 1052
        const val CPX_PARAM_DATACHECK = 1056
        const val CPX_PARAM_REDUCE = 1057
        const val CPX_PARAM_PRELINEAR = 1058
        const val CPX_PARAM_LPMETHOD = 1062
        const val CPX_PARAM_QPMETHOD = 1063
        const val CPX_PARAM_WORKDIR = 1064
        const val CPX_PARAM_WORKMEM = 1065
        const val CPX_PARAM_THREADS = 1067
        const val CPX_PARAM_CONFLICTALG = 1073
        const val CPX_PARAM_CONFLICTDISPLAY = 1074
        const val CPX_PARAM_SIFTDISPLAY = 1076
        const val CPX_PARAM_SIFTALG = 1077
        const val CPX_PARAM_SIFTITLIM = 1078
        const val CPX_PARAM_MPSLONGNUM = 1081
        const val CPX_PARAM_MEMORYEMPHASIS = 1082
        const val CPX_PARAM_NUMERICALEMPHASIS = 1083
        const val CPX_PARAM_FEASOPTMODE = 1084
        const val CPX_PARAM_PARALLELMODE = 1109
        const val CPX_PARAM_TUNINGMEASURE = 1110
        const val CPX_PARAM_TUNINGREPEAT = 1111
        const val CPX_PARAM_TUNINGTILIM = 1112
        const val CPX_PARAM_TUNINGDISPLAY = 1113
        const val CPX_PARAM_WRITELEVEL = 1114
        const val CPX_PARAM_RANDOMSEED = 1124
        const val CPX_PARAM_DETTILIM = 1127
        const val CPX_PARAM_FILEENCODING = 1129
        const val CPX_PARAM_APIENCODING = 1130
        const val CPX_PARAM_OPTIMALITYTARGET = 1131
        const val CPX_PARAM_CLONELOG = 1132
        const val CPX_PARAM_TUNINGDETTILIM = 1139
        const val CPX_PARAM_CPUMASK = 1144
        const val CPX_PARAM_SOLUTIONTYPE = 1147
        const val CPX_PARAM_WARNLIM = 1157
        const val CPX_PARAM_SIFTSIM = 1158
        const val CPX_PARAM_DYNAMICROWS = 1161
        const val CPX_PARAM_RECORD = 1162
        const val CPX_PARAM_PARAMDISPLAY = 1163
        const val CPX_PARAM_FOLDING = 1164
        const val CPX_PARAM_WORKERALG = 1500
        const val CPX_PARAM_BENDERSSTRATEGY = 1501
        const val CPX_PARAM_BENDERSFEASCUTTOL = 1509
        const val CPX_PARAM_BENDERSOPTCUTTOL = 1510
        const val CPX_PARAM_MULTIOBJDISPLAY = 1600
        const val CPX_PARAM_BRDIR = 2001
        const val CPX_PARAM_BTTOL = 2002
        const val CPX_PARAM_CLIQUES = 2003
        const val CPX_PARAM_COEREDIND = 2004
        const val CPX_PARAM_COVERS = 2005
        const val CPX_PARAM_CUTLO = 2006
        const val CPX_PARAM_CUTUP = 2007
        const val CPX_PARAM_EPAGAP = 2008
        const val CPX_PARAM_EPGAP = 2009
        const val CPX_PARAM_EPINT = 2010
        const val CPX_PARAM_MIPDISPLAY = 2012
        const val CPX_PARAM_MIPINTERVAL = 2013
        const val CPX_PARAM_INTSOLLIM = 2015
        const val CPX_PARAM_NODEFILEIND = 2016
        const val CPX_PARAM_NODELIM = 2017
        const val CPX_PARAM_NODESEL = 2018
        const val CPX_PARAM_OBJDIF = 2019
        const val CPX_PARAM_MIPORDIND = 2020
        const val CPX_PARAM_RELOBJDIF = 2022
        const val CPX_PARAM_STARTALG = 2025
        const val CPX_PARAM_SUBALG = 2026
        const val CPX_PARAM_TRELIM = 2027
        const val CPX_PARAM_VARSEL = 2028
        const val CPX_PARAM_BNDSTRENIND = 2029
        const val CPX_PARAM_HEURFREQ = 2031
        const val CPX_PARAM_MIPORDTYPE = 2032
        const val CPX_PARAM_CUTSFACTOR = 2033
        const val CPX_PARAM_RELAXPREIND = 2034
        const val CPX_PARAM_PRESLVND = 2037
        const val CPX_PARAM_BBINTERVAL = 2039
        const val CPX_PARAM_FLOWCOVERS = 2040
        const val CPX_PARAM_IMPLBD = 2041
        const val CPX_PARAM_PROBE = 2042
        const val CPX_PARAM_GUBCOVERS = 2044
        const val CPX_PARAM_STRONGCANDLIM = 2045
        const val CPX_PARAM_STRONGITLIM = 2046
        const val CPX_PARAM_FRACCAND = 2048
        const val CPX_PARAM_FRACCUTS = 2049
        const val CPX_PARAM_FRACPASS = 2050
        const val CPX_PARAM_FLOWPATHS = 2051
        const val CPX_PARAM_MIRCUTS = 2052
        const val CPX_PARAM_DISJCUTS = 2053
        const val CPX_PARAM_AGGCUTLIM = 2054
        const val CPX_PARAM_MIPCBREDLP = 2055
        const val CPX_PARAM_CUTPASS = 2056
        const val CPX_PARAM_MIPEMPHASIS = 2058
        const val CPX_PARAM_SYMMETRY = 2059
        const val CPX_PARAM_DIVETYPE = 2060
        const val CPX_PARAM_RINSHEUR = 2061
        const val CPX_PARAM_LBHEUR = 2063
        const val CPX_PARAM_REPEATPRESOLVE = 2064
        const val CPX_PARAM_PROBETIME = 2065
        const val CPX_PARAM_POLISHTIME = 2066
        const val CPX_PARAM_REPAIRTRIES = 2067
        const val CPX_PARAM_EPLIN = 2068
        const val CPX_PARAM_EPRELAX = 2073
        const val CPX_PARAM_FPHEUR = 2098
        const val CPX_PARAM_EACHCUTLIM = 2102
        const val CPX_PARAM_SOLNPOOLCAPACITY = 2103
        const val CPX_PARAM_SOLNPOOLREPLACE = 2104
        const val CPX_PARAM_SOLNPOOLGAP = 2105
        const val CPX_PARAM_SOLNPOOLAGAP = 2106
        const val CPX_PARAM_SOLNPOOLINTENSITY = 2107
        const val CPX_PARAM_POPULATELIM = 2108
        const val CPX_PARAM_MIPSEARCH = 2109
        const val CPX_PARAM_MIQCPSTRAT = 2110
        const val CPX_PARAM_ZEROHALFCUTS = 2111
        const val CPX_PARAM_HEUREFFORT = 2120
        const val CPX_PARAM_POLISHAFTEREPAGAP = 2126
        const val CPX_PARAM_POLISHAFTEREPGAP = 2127
        const val CPX_PARAM_POLISHAFTERNODE = 2128
        const val CPX_PARAM_POLISHAFTERINTSOL = 2129
        const val CPX_PARAM_POLISHAFTERTIME = 2130
        const val CPX_PARAM_MCFCUTS = 2134
        const val CPX_PARAM_MIPKAPPASTATS = 2137
        const val CPX_PARAM_AUXROOTTHREADS = 2139
        const val CPX_PARAM_INTSOLFILEPREFIX = 2143
        const val CPX_PARAM_PROBEDETTIME = 2150
        const val CPX_PARAM_POLISHAFTERDETTIME = 2151
        const val CPX_PARAM_LANDPCUTS = 2152
        const val CPX_PARAM_RAMPUPDURATION = 2163
        const val CPX_PARAM_RAMPUPDETTILIM = 2164
        const val CPX_PARAM_RAMPUPTILIM = 2165
        const val CPX_PARAM_LOCALIMPLBD = 2181
        const val CPX_PARAM_BQPCUTS = 2195
        const val CPX_PARAM_RLTCUTS = 2196
        const val CPX_PARAM_SUBMIPSTARTALG = 2205
        const val CPX_PARAM_SUBMIPSUBALG = 2206
        const val CPX_PARAM_SUBMIPSCAIND = 2207
        const val CPX_PARAM_SUBMIPNODELIMIT = 2212
        const val CPX_PARAM_BAREPCOMP = 3002
        const val CPX_PARAM_BARGROWTH = 3003
        const val CPX_PARAM_BAROBJRNG = 3004
        const val CPX_PARAM_BARALG = 3007
        const val CPX_PARAM_BARCOLNZ = 3009
        const val CPX_PARAM_BARDISPLAY = 3010
        const val CPX_PARAM_BARITLIM = 3012
        const val CPX_PARAM_BARMAXCOR = 3013
        const val CPX_PARAM_BARORDER = 3014
        const val CPX_PARAM_BARSTARTALG = 3017
        const val CPX_PARAM_BARCROSSALG = 3018
        const val CPX_PARAM_BARQCPEPCOMP = 3020
        const val CPX_PARAM_QPNZREADLIM = 4001
        const val CPX_PARAM_CALCQCPDUALS = 4003
        const val CPX_PARAM_QPMAKEPSDIND = 4010
        const val CPX_PARAM_QTOLININD = 4012
        const val CPX_PARAM_NETITLIM = 5001
        const val CPX_PARAM_NETEPOPT = 5002
        const val CPX_PARAM_NETEPRHS = 5003
        const val CPX_PARAM_NETPPRIIND = 5004
        const val CPX_PARAM_NETDISPLAY = 5005

        // endregion CPXPARAM constants
    }

    /**
     * CPXENVptr CPXopenCPLEX (int *status_p);
     */
    fun CPXopenCPLEX(status_p: Pointer): Pointer

    /**
     * CPXLPptr CPXcreateprob (CPXCENVptr env, int *status_p, char const *probname_str);
     */
    fun CPXcreateprob(env: Pointer, status_p: Pointer, probname_str: String): Pointer

    /**
     * int CPXlpopt (CPXCENVptr env, CPXLPptr lp);
     */
    fun CPXlpopt(env: Pointer, lp: Pointer): Int

    /**
     * int CPXchgobjsen (CPXCENVptr env, CPXLPptr lp, int maxormin);
     */
    fun CPXchgobjsen(env: Pointer, lp: Pointer, maxormin: Int): Int


    /**
     * int CPXgetobjsen (CPXCENVptr env, CPXCLPptr lp);
     */
    fun CPXgetobjsen(env: Pointer, lp: Pointer): Int

    /**
     * int CPXgetnumcols (CPXCENVptr env, CPXCLPptr lp);
     */
    fun CPXgetnumcols(env: Pointer, lp: Pointer): Int

    /**
     * int CPXgetnumrows (CPXCENVptr env, CPXCLPptr lp);
     */
    fun CPXgetnumrows(env: Pointer, lp: Pointer): Int

    /**
     * int CPXgetsolnpoolnumsolns (CPXCENVptr env, CPXCLPptr lp);
     */
    fun CPXgetsolnpoolnumsolns(env: Pointer, lp: Pointer): Int

    /**
     * int CPXgetstat (CPXCENVptr env, CPXCLPptr lp);
     */
    fun CPXgetstat(env: Pointer, lp: Pointer): Int

    /**
     *int CPXgetobjoffset (CPXCENVptr env, CPXCLPptr lp, double *objoffset_p);
     */
    fun CPXgetobjoffset(env: Pointer, lp: Pointer, value: Pointer?): Int

    /**
     * int CPXgetobjval (CPXCENVptr env, CPXCLPptr lp, double *objval_p);
     */
    fun CPXgetobjval(env: Pointer, lp: Pointer, value: Pointer?): Int

    /**
     * int CPXchgobjoffset (CPXCENVptr env, CPXLPptr lp, double offset);
     */
    fun CPXchgobjoffset(env: Pointer, lp: Pointer, offset: Double): Int

    /**
     * int CPXgetbestobjval (CPXCENVptr env, CPXCLPptr lp, double *objval_p);
     */
    fun CPXgetbestobjval(env: Pointer, lp: Pointer, value: Pointer?): Int

    /**
     * int CPXgetdj (CPXCENVptr env, CPXCLPptr lp, double *dj, int begin, int end);
     */
    fun CPXgetdj(env: Pointer, lp: Pointer, values: Pointer?, begin: Int, end: Int): Int

    /**
     * int CPXgetpi (CPXCENVptr env, CPXCLPptr lp, double *pi, int begin, int end);
     */
    fun CPXgetpi(env: Pointer, lp: Pointer, values: Pointer?, begin: Int, end: Int): Int

    /**
     * int CPXgetx (CPXCENVptr env, CPXCLPptr lp, double *x, int begin, int end);
     */
    fun CPXgetx(env: Pointer, lp: Pointer, values: Pointer?, begin: Int, end: Int): Int


    /**
     * int CPXnewcols (CPXCENVptr env, CPXLPptr lp, int ccnt, double const *obj, double const *lb,
     *                 double const *ub, char const *xctype, char **colname);
     */
    fun CPXnewcols(env: Pointer, lp: Pointer, ccnt: Int, obj: Pointer, lb: Pointer, ub: Pointer,
                   xctype: Pointer, colname: PointerByReference): Int

    /**
     * int CPXaddrows (CPXCENVptr env, CPXLPptr lp, int ccnt, int rcnt, int nzcnt,
     *                 double const *rhs, char const *sense, int const *rmatbeg,
     *                 int const *rmatind, double const *rmatval, char **colname, char **rowname);
     */
    fun CPXaddrows(env: Pointer, lp: Pointer, ccnt: Int, rcnt: Int, nzcnt: Int, rhs: Pointer,
                   sense: Pointer, rmatbeg: Pointer, rmatind: Pointer, rmatval: Pointer,
                   colname: PointerByReference?, rowname: PointerByReference?): Int

    /**
     *  int CPXgetcolname (CPXCENVptr env, CPXCLPptr lp, char  **name, char *namestore,
     *                     int storespace, int *surplus_p, int begin, int end);
     */
    fun CPXgetcolname(env: Pointer, lp: Pointer, name: PointerByReference?, namestore: String,
                      storeespace: Int, surplus_p: Pointer, begin: Int, end: Int): Int


    /**
     * int CPXsolution (CPXCENVptr env, CPXCLPptr lp, int *lpstat_p, double *objval_p, double *x,
     *                  double *pi, double *slack, double *dj);
     */
    fun CPXsolution(env: Pointer, lp: Pointer, lpstat_p: Pointer, objval_p: Pointer, x: Pointer,
                    pi: Pointer, slack: Pointer, dj: Pointer): Int


    /**
     * CPXgetdblparam (CPXCENVptr env, int whichparam, double *value_p);
     */
    fun CPXgetdblparam(env: Pointer, whichparam: Int, value_p: Pointer): Int

    /**
     * int CPXsetdblparam (CPXENVptr env, int whichparam, double newvalue);
     */
    fun CPXsetdblparam(env: Pointer, whichparam: Int, newvalue: Double): Int

    /**
     * CPXgetintparam (CPXCENVptr env, int whichparam, CPXINT *value_p);
     */
    fun CPXgetintparam(env: Pointer, whichparam: Int, value_p: Pointer): Int

    /**
     * int CPXsetintparam (CPXENVptr env, int whichparam, CPXINT newvalue);
     */
    fun CPXsetintparam(env: Pointer, whichparam: Int, newvalue: Int): Int


    /**
     * int CPXwriteprob (CPXCENVptr env, CPXCLPptr lp, char const *filename_str, char const *filetype);
     */
    fun CPXwriteprob(env: Pointer, lp: Pointer, filename_str: String, filetype: String): Int
}