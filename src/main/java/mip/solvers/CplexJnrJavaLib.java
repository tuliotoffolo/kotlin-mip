package mip.solvers;

import jnr.ffi.*;
import jnr.ffi.annotations.*;
import jnr.ffi.byref.*;

import java.io.*;

public interface CplexJnrJavaLib {

    CplexJnrJavaLib library = null;

    public static CplexJnrJavaLib loadLibrary() {
        if (library != null) return library;

        String libname;
        String libLocation = System.getProperty("user.dir") + File.separatorChar;

        Platform platform = Platform.getNativePlatform();
        if (platform.getOS() == Platform.OS.DARWIN) {
            libname = "cbc-c-darwin-x86-64.dylib";
            libLocation += "libraries";
        }
        else if (platform.getOS() == Platform.OS.LINUX) {
            libname = "cbc-c-darwin-x86-64.dylib";
            libLocation += "libraries";
        }
        else if (platform.getOS() == Platform.OS.WINDOWS) {
            libname = "libCbcSolver-0.dll";
            libLocation += "libraries\\win64";
        }
        else {
            libname = null;
            libLocation = null;
        }

        return LibraryLoader
          .create(CplexJnrJavaLib.class)
          .failImmediately()
          .load(libLocation + File.separatorChar + libname);
    }

    /**
     * int CPXaddcols (CPXCENVptr env, CPXLPptr lp, int ccnt, int nzcnt, double const *obj, int const *cmatbeg, int const *cmatind, double const *cmatval, double const *lb, double const *ub, char **colname);
     */
    int CPXaddcols(@Pinned @In Pointer env, @Pinned @In Pointer lp, int ccnt, int nzcnt, @Pinned @In @Transient double[] obj, @Pinned @In @Transient int[] cmatbeg, @Pinned @In @Transient int[] cmatind, @Pinned @In @Transient double[] cmatval, @Pinned @In @Transient double[] lb, @Pinned @In @Transient double[] ub, Pointer colname);

    /**
     * int CPXaddfuncdest (CPXCENVptr env, CPXCHANNELptr channel, void *handle, void(CPXPUBLIC *msgfunction)(void *, const char *));
     */
    //int CPXaddfuncdest(@Pinned @In Pointer env, CPXCHANNELptr channel, Pointer handle, void(CPXPUBLIC*msgfunction)(Pointer ,const String ));

    /**
     * int CPXaddpwl (CPXCENVptr env, CPXLPptr lp, int vary, int varx, double preslope, double postslope, int nbreaks, double const *breakx, double const *breaky, char const *pwlname);
     */
    int CPXaddpwl(@Pinned @In Pointer env, @Pinned @In Pointer lp, int vary, int varx, double preslope, double postslope, int nbreaks, @Pinned @In @Transient double[] breakx, @Pinned @In @Transient double[] breaky, @Pinned @In @Transient String pwlname);

    /**
     * int CPXaddrows (CPXCENVptr env, CPXLPptr lp, int ccnt, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, char **colname, char **rowname);
     */
    int CPXaddrows(@Pinned @In Pointer env, @Pinned @In Pointer lp, int ccnt, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient byte[] sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, String[] colname, String[] rowname);

    /**
     * int CPXbasicpresolve (CPXCENVptr env, CPXLPptr lp, double *redlb, double *redub, int *rstat);
     */
    int CPXbasicpresolve(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] redlb, double[] redub, int[] rstat);

    /**
     * int CPXbinvacol (CPXCENVptr env, CPXCLPptr lp, int j, double *x);
     */
    int CPXbinvacol(@Pinned @In Pointer env, @Pinned @In Pointer lp, int j, double[] x);

    /**
     * int CPXbinvarow (CPXCENVptr env, CPXCLPptr lp, int i, double *z);
     */
    int CPXbinvarow(@Pinned @In Pointer env, @Pinned @In Pointer lp, int i, double[] z);

    /**
     * int CPXbinvcol (CPXCENVptr env, CPXCLPptr lp, int j, double *x);
     */
    int CPXbinvcol(@Pinned @In Pointer env, @Pinned @In Pointer lp, int j, double[] x);

    /**
     * int CPXbinvrow (CPXCENVptr env, CPXCLPptr lp, int i, double *y);
     */
    int CPXbinvrow(@Pinned @In Pointer env, @Pinned @In Pointer lp, int i, double[] y);

    /**
     * int CPXboundsa (CPXCENVptr env, CPXCLPptr lp, int begin, int end, double *lblower, double *lbupper, double *ublower, double *ubupper);
     */
    int CPXboundsa(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end, double[] lblower, double[] lbupper, double[] ublower, double[] ubupper);

    /**
     * int CPXbtran (CPXCENVptr env, CPXCLPptr lp, double *y);
     */
    int CPXbtran(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] y);

    /**
     * void CPXcallbackabort (CPXCALLBACKCONTEXTptr context);
     */
    //void CPXcallbackabort(CPXCALLBACKCONTEXTptr context);

    /**
     * int CPXcallbackaddusercuts (CPXCALLBACKCONTEXTptr context, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, int const *purgeable, int const *local);
     */
    //int CPXcallbackaddusercuts(CPXCALLBACKCONTEXTptr context, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, @Pinned @In @Transient int[] purgeable, @Pinned @In @Transient int[] local);

    /**
     * int CPXcallbackcandidateispoint (CPXCALLBACKCONTEXTptr context, int *ispoint_p);
     */
    //int CPXcallbackcandidateispoint(CPXCALLBACKCONTEXTptr context, int[] ispoint_p);

    /**
     * int CPXcallbackcandidateisray (CPXCALLBACKCONTEXTptr context, int *isray_p);
     */
    //int CPXcallbackcandidateisray(CPXCALLBACKCONTEXTptr context, int[] isray_p);

    /**
     * int CPXcallbackexitcutloop (CPXCALLBACKCONTEXTptr context);
     */
    //int CPXcallbackexitcutloop(CPXCALLBACKCONTEXTptr context);

    /**
     * int CPXcallbackgetcandidatepoint (CPXCALLBACKCONTEXTptr context, double *x, int begin, int end, double *obj_p);
     */
    //int CPXcallbackgetcandidatepoint(CPXCALLBACKCONTEXTptr context, double[] x, int begin, int end, double[] obj_p);

    /**
     * int CPXcallbackgetcandidateray (CPXCALLBACKCONTEXTptr context, double *x, int begin, int end);
     */
    //int CPXcallbackgetcandidateray(CPXCALLBACKCONTEXTptr context, double[] x, int begin, int end);

    /**
     * int CPXcallbackgetfunc (CPXCENVptr env, CPXCLPptr lp, CPXLONG *contextmask_p, CPXCALLBACKFUNC **callback_p, void  **cbhandle_p);
     */
    //int CPXcallbackgetfunc(@Pinned @In Pointer env, @Pinned @In Pointer lp, CPXLONG *contextmask_p, CPXCALLBACKFUNC **callback_p, void  **cbhandle_p);

    /**
     * int CPXcallbackgetincumbent (CPXCALLBACKCONTEXTptr context, double *x, int begin, int end, double *obj_p);
     */
    //int CPXcallbackgetincumbent(CPXCALLBACKCONTEXTptr context, double[] x, int begin, int end, double[] obj_p);

    /**
     * int CPXcallbackgetinfodbl (CPXCALLBACKCONTEXTptr context, CPXCALLBACKINFO what, double *data_p);
     */
    //int CPXcallbackgetinfodbl(CPXCALLBACKCONTEXTptr context, CPXCALLBACKINFO what, double[] data_p);

    /**
     * int CPXcallbackgetinfoint (CPXCALLBACKCONTEXTptr context, CPXCALLBACKINFO what, CPXINT *data_p);
     */
    //int CPXcallbackgetinfoint(CPXCALLBACKCONTEXTptr context, CPXCALLBACKINFO what, CPXint[] data_p);

    /**
     * int CPXcallbackgetinfolong (CPXCALLBACKCONTEXTptr context, CPXCALLBACKINFO what, CPXLONG *data_p);
     */
    //int CPXcallbackgetinfolong(CPXCALLBACKCONTEXTptr context, CPXCALLBACKINFO what, CPXLONG *data_p);

    /**
     * int CPXcallbackgetrelaxationpoint (CPXCALLBACKCONTEXTptr context, double *x, int begin, int end, double *obj_p);
     */
    //int CPXcallbackgetrelaxationpoint(CPXCALLBACKCONTEXTptr context, double[] x, int begin, int end, double[] obj_p);

    /**
     * int CPXcallbackgetrelaxationstatus (CPXCALLBACKCONTEXTptr context, int *nodelpstat_p, CPXLONG flags);
     */
    //int CPXcallbackgetrelaxationstatus(CPXCALLBACKCONTEXTptr context, int[] nodelpstat_p, CPXLONG flags);

    /**
     * int CPXcallbackmakebranch (CPXCALLBACKCONTEXTptr context, int varcnt, int const *varind, char const *varlu, double const *varbd, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, double nodeest, int *seqnum_p);
     */
    //int CPXcallbackmakebranch(CPXCALLBACKCONTEXTptr context, int varcnt, @Pinned @In @Transient int[] varind, @Pinned @In @Transient String varlu, @Pinned @In @Transient double[] varbd, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, double nodeest, int[] seqnum_p);

    /**
     * int CPXcallbackpostheursoln (CPXCALLBACKCONTEXTptr context, int cnt, int const *ind, double const *val, double obj, CPXCALLBACKSOLUTIONSTRATEGY strat);
     */
    //int CPXcallbackpostheursoln(CPXCALLBACKCONTEXTptr context, int cnt, @Pinned @In @Transient int[] ind, @Pinned @In @Transient double[] val, double obj, CPXCALLBACKSOLUTIONSTRATEGY strat);

    /**
     * int CPXcallbackprunenode (CPXCALLBACKCONTEXTptr context);
     */
    //int CPXcallbackprunenode(CPXCALLBACKCONTEXTptr context);

    /**
     * int CPXcallbackrejectcandidate (CPXCALLBACKCONTEXTptr context, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval);
     */
    //int CPXcallbackrejectcandidate(CPXCALLBACKCONTEXTptr context, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval);

    /**
     * int CPXcallbacksetfunc (CPXENVptr env, CPXLPptr lp, CPXLONG contextmask, CPXCALLBACKFUNC callback, void *userhandle);
     */
    //int CPXcallbacksetfunc(@Pinned @In Pointer env, @Pinned @In Pointer lp, CPXLONG contextmask, CPXCALLBACKFUNC callback, Pointer userhandle);

    /**
     * int CPXcheckdfeas (CPXCENVptr env, CPXLPptr lp, int *infeas_p);
     */
    int CPXcheckdfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] infeas_p);

    /**
     * int CPXcheckpfeas (CPXCENVptr env, CPXLPptr lp, int *infeas_p);
     */
    int CPXcheckpfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] infeas_p);

    /**
     * int CPXchecksoln (CPXCENVptr env, CPXLPptr lp, int *lpstatus_p);
     */
    int CPXchecksoln(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] lpstatus_p);

    /**
     * int CPXchgbds (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, char const *lu, double const *bd);
     */
    int CPXchgbds(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient String lu, @Pinned @In @Transient double[] bd);

    /**
     * int CPXchgcoef (CPXCENVptr env, CPXLPptr lp, int i, int j, double newvalue);
     */
    int CPXchgcoef(@Pinned @In Pointer env, @Pinned @In Pointer lp, int i, int j, double newvalue);

    /**
     * int CPXchgcoeflist (CPXCENVptr env, CPXLPptr lp, int numcoefs, int const *rowlist, int const *collist, double const *vallist);
     */
    int CPXchgcoeflist(@Pinned @In Pointer env, @Pinned @In Pointer lp, int numcoefs, @Pinned @In @Transient int[] rowlist, @Pinned @In @Transient int[] collist, @Pinned @In @Transient double[] vallist);

    /**
     * int CPXchgcolname (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, char **newname);
     */
    int CPXchgcolname(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, Pointer newname);

    /**
     * int CPXchgname (CPXCENVptr env, CPXLPptr lp, int key, int ij, char const *newname_str);
     */
    int CPXchgname(@Pinned @In Pointer env, @Pinned @In Pointer lp, int key, int ij, @Pinned @In @Transient String newname_str);

    /**
     * int CPXchgobj (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, double const *values);
     */
    int CPXchgobj(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] values);

    /**
     * int CPXchgobjoffset (CPXCENVptr env, CPXLPptr lp, double offset);
     */
    int CPXchgobjoffset(@Pinned @In Pointer env, @Pinned @In Pointer lp, double offset);

    /**
     * int CPXchgobjsen (CPXCENVptr env, CPXLPptr lp, int maxormin);
     */
    int CPXchgobjsen(@Pinned @In Pointer env, @Pinned @In Pointer lp, int maxormin);

    /**
     * int CPXchgprobname (CPXCENVptr env, CPXLPptr lp, char const *probname);
     */
    int CPXchgprobname(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String probname);

    /**
     * int CPXchgprobtype (CPXCENVptr env, CPXLPptr lp, int type);
     */
    int CPXchgprobtype(@Pinned @In Pointer env, @Pinned @In Pointer lp, int type);

    /**
     * int CPXchgprobtypesolnpool (CPXCENVptr env, CPXLPptr lp, int type, int soln);
     */
    int CPXchgprobtypesolnpool(@Pinned @In Pointer env, @Pinned @In Pointer lp, int type, int soln);

    /**
     * int CPXchgrhs (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, double const *values);
     */
    int CPXchgrhs(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] values);

    /**
     * int CPXchgrngval (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, double const *values);
     */
    int CPXchgrngval(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] values);

    /**
     * int CPXchgrowname (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, char **newname);
     */
    int CPXchgrowname(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, Pointer newname);

    /**
     * int CPXchgsense (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, char const *sense);
     */
    int CPXchgsense(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient String sense);

    /**
     * int CPXcleanup (CPXCENVptr env, CPXLPptr lp, double eps);
     */
    int CPXcleanup(@Pinned @In Pointer env, @Pinned @In Pointer lp, double eps);

    /**
     * CPXLPptr CPXcloneprob (CPXCENVptr env, CPXCLPptr lp, int *status_p);
     */
    Pointer CPXcloneprob(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out IntByReference status_p);

    /**
     * int CPXcloseCPLEX (CPXENVptr *env_p);
     */
    int CPXcloseCPLEX(PointerByReference env_p);

    /**
     * int CPXclpwrite (CPXCENVptr env, CPXCLPptr lp, char const *filename_str);
     */
    int CPXclpwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXcompletelp (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXcompletelp(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXcopybase (CPXCENVptr env, CPXLPptr lp, int const *cstat, int const *rstat);
     */
    int CPXcopybase(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] cstat, @Pinned @In @Transient int[] rstat);

    /**
     * int CPXcopybasednorms (CPXCENVptr env, CPXLPptr lp, int const *cstat, int const *rstat, double const *dnorm);
     */
    int CPXcopybasednorms(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] cstat, @Pinned @In @Transient int[] rstat, @Pinned @In @Transient double[] dnorm);

    /**
     * int CPXcopydnorms (CPXCENVptr env, CPXLPptr lp, double const *norm, int const *head, int len);
     */
    int CPXcopydnorms(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] norm, @Pinned @In @Transient int[] head, int len);

    /**
     * int CPXcopylp (CPXCENVptr env, CPXLPptr lp, int numcols, int numrows, int objsense, double const *objective, double const *rhs, char const *sense, int const *matbeg, int const *matcnt, int const *matind, double const *matval, double const *lb, double const *ub, double const *rngval);
     */
    int CPXcopylp(@Pinned @In Pointer env, @Pinned @In Pointer lp, int numcols, int numrows, int objsense, @Pinned @In @Transient double[] objective, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] matbeg, @Pinned @In @Transient int[] matcnt, @Pinned @In @Transient int[] matind, @Pinned @In @Transient double[] matval, @Pinned @In @Transient double[] lb, @Pinned @In @Transient double[] ub, @Pinned @In @Transient double[] rngval);

    /**
     * int CPXcopylpwnames (CPXCENVptr env, CPXLPptr lp, int numcols, int numrows, int objsense, double const *objective, double const *rhs, char const *sense, int const *matbeg, int const *matcnt, int const *matind, double const *matval, double const *lb, double const *ub, double const *rngval, char **colname, char **rowname);
     */
    int CPXcopylpwnames(@Pinned @In Pointer env, @Pinned @In Pointer lp, int numcols, int numrows, int objsense, @Pinned @In @Transient double[] objective, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] matbeg, @Pinned @In @Transient int[] matcnt, @Pinned @In @Transient int[] matind, @Pinned @In @Transient double[] matval, @Pinned @In @Transient double[] lb, @Pinned @In @Transient double[] ub, @Pinned @In @Transient double[] rngval, Pointer colname, Pointer rowname);

    /**
     * int CPXcopynettolp (CPXCENVptr env, CPXLPptr lp, CPXCNETptr net);
     */
    //int CPXcopynettolp(@Pinned @In Pointer env, @Pinned @In Pointer lp, CPXCNETptr net);

    /**
     * int CPXcopyobjname (CPXCENVptr env, CPXLPptr lp, char const *objname_str);
     */
    int CPXcopyobjname(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String objname_str);

    /**
     * int CPXcopypnorms (CPXCENVptr env, CPXLPptr lp, double const *cnorm, double const *rnorm, int len);
     */
    int CPXcopypnorms(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] cnorm, @Pinned @In @Transient double[] rnorm, int len);

    /**
     * int CPXcopyprotected (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices);
     */
    int CPXcopyprotected(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices);

    /**
     * int CPXcopystart (CPXCENVptr env, CPXLPptr lp, int const *cstat, int const *rstat, double const *cprim, double const *rprim, double const *cdual, double const *rdual);
     */
    int CPXcopystart(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] cstat, @Pinned @In @Transient int[] rstat, @Pinned @In @Transient double[] cprim, @Pinned @In @Transient double[] rprim, @Pinned @In @Transient double[] cdual, @Pinned @In @Transient double[] rdual);

    /**
     * CPXLPptr CPXcreateprob (CPXCENVptr env, int *status_p, char const *probname_str);
     */
    Pointer CPXcreateprob(@Pinned @In Pointer env, @Out IntByReference status_p, @Pinned @In @Transient String probname_str);

    /**
     * int CPXcrushform (CPXCENVptr env, CPXCLPptr lp, int len, int const *ind, double const *val, int *plen_p, double *poffset_p, int *pind, double *pval);
     */
    int CPXcrushform(@Pinned @In Pointer env, @Pinned @In Pointer lp, int len, @Pinned @In @Transient int[] ind, @Pinned @In @Transient double[] val, int[] plen_p, double[] poffset_p, int[] pind, double[] pval);

    /**
     * int CPXcrushpi (CPXCENVptr env, CPXCLPptr lp, double const *pi, double *prepi);
     */
    int CPXcrushpi(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] pi, double[] prepi);

    /**
     * int CPXcrushx (CPXCENVptr env, CPXCLPptr lp, double const *x, double *prex);
     */
    int CPXcrushx(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] prex);

    /**
     * int CPXdelcols (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelcols(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdeldblannotation (CPXCENVptr env, CPXLPptr lp, int idx);
     */
    int CPXdeldblannotation(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx);

    /**
     * int CPXdeldblannotations (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdeldblannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelfuncdest (CPXCENVptr env, CPXCHANNELptr channel, void *handle, void(CPXPUBLIC *msgfunction)(void *, const char *));
     */
    //int CPXdelfuncdest(@Pinned @In Pointer env, CPXCHANNELptr channel, Pointer handle, void(CPXPUBLIC*msgfunction)(Pointer ,const String ));

    /**
     * int CPXdellongannotation (CPXCENVptr env, CPXLPptr lp, int idx);
     */
    int CPXdellongannotation(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx);

    /**
     * int CPXdellongannotations (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdellongannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelnames (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXdelnames(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXdelpwl (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelpwl(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelrows (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelrows(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelsetcols (CPXCENVptr env, CPXLPptr lp, int *delstat);
     */
    int CPXdelsetcols(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delstat);

    /**
     * int CPXdelsetpwl (CPXCENVptr env, CPXLPptr lp, int *delstat);
     */
    int CPXdelsetpwl(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delstat);

    /**
     * int CPXdelsetrows (CPXCENVptr env, CPXLPptr lp, int *delstat);
     */
    int CPXdelsetrows(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delstat);

    /**
     * int CPXdeserializercreate (CPXDESERIALIZERptr *deser_p, CPXLONG size, void const *buffer);
     */
    //int CPXdeserializercreate(CPXDESERIALIZERptr *deser_p, CPXLONG size, void const*buffer);

    /**
     * void CPXdeserializerdestroy (CPXDESERIALIZERptr deser);
     */
    //void CPXdeserializerdestroy(CPXDESERIALIZERptr deser);

    /**
     * CPXLONG CPXdeserializerleft (CPXCDESERIALIZERptr deser);
     */
    //CPXLONG CPXdeserializerleft(CPXCDESERIALIZERptr deser);

    /**
     * int CPXdisconnectchannel (CPXCENVptr env, CPXCHANNELptr channel);
     */
    //int CPXdisconnectchannel(@Pinned @In Pointer env, CPXCHANNELptr channel);

    /**
     * int CPXdjfrompi (CPXCENVptr env, CPXCLPptr lp, double const *pi, double *dj);
     */
    int CPXdjfrompi(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] pi, double[] dj);

    /**
     * int CPXdperwrite (CPXCENVptr env, CPXLPptr lp, char const *filename_str, double epsilon);
     */
    int CPXdperwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, double epsilon);

    /**
     * int CPXdratio (CPXCENVptr env, CPXLPptr lp, int *indices, int cnt, double *downratio, double *upratio, int *downenter, int *upenter, int *downstatus, int *upstatus);
     */
    int CPXdratio(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] indices, int cnt, double[] downratio, double[] upratio, int[] downenter, int[] upenter, int[] downstatus, int[] upstatus);

    /**
     * int CPXdualfarkas (CPXCENVptr env, CPXCLPptr lp, double *y, double *proof_p);
     */
    int CPXdualfarkas(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] y, double[] proof_p);

    /**
     * int CPXdualopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXdualopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXdualwrite (CPXCENVptr env, CPXCLPptr lp, char const *filename_str, double *objshift_p);
     */
    int CPXdualwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, double[] objshift_p);

    /**
     * int CPXembwrite (CPXCENVptr env, CPXLPptr lp, char const *filename_str);
     */
    int CPXembwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXfeasopt (CPXCENVptr env, CPXLPptr lp, double const *rhs, double const *rng, double const *lb, double const *ub);
     */
    int CPXfeasopt(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient double[] rng, @Pinned @In @Transient double[] lb, @Pinned @In @Transient double[] ub);

    /**
     * int CPXfeasoptext (CPXCENVptr env, CPXLPptr lp, int grpcnt, int concnt, double const *grppref, int const *grpbeg, int const *grpind, char const *grptype);
     */
    int CPXfeasoptext(@Pinned @In Pointer env, @Pinned @In Pointer lp, int grpcnt, int concnt, @Pinned @In @Transient double[] grppref, @Pinned @In @Transient int[] grpbeg, @Pinned @In @Transient int[] grpind, @Pinned @In @Transient String grptype);

    /**
     * void CPXfinalize (void);
     */
    void CPXfinalize();

    /**
     * int CPXflushchannel (CPXCENVptr env, CPXCHANNELptr channel);
     */
    //int CPXflushchannel(@Pinned @In Pointer env, CPXCHANNELptr channel);

    /**
     * int CPXflushstdchannels (CPXCENVptr env);
     */
    int CPXflushstdchannels(@Pinned @In Pointer env);

    /**
     * int CPXfreepresolve (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXfreepresolve(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXfreeprob (CPXCENVptr env, CPXLPptr *lp_p);
     */
    int CPXfreeprob(@Pinned @In Pointer env, PointerByReference lp_p);

    /**
     * int CPXftran (CPXCENVptr env, CPXCLPptr lp, double *x);
     */
    int CPXftran(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] x);

    /**
     * int CPXgetax (CPXCENVptr env, CPXCLPptr lp, double *x, int begin, int end);
     */
    int CPXgetax(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] x, int begin, int end);

    /**
     * int CPXgetbaritcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetbaritcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetbase (CPXCENVptr env, CPXCLPptr lp, int *cstat, int *rstat);
     */
    int CPXgetbase(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] cstat, int[] rstat);

    /**
     * int CPXgetbasednorms (CPXCENVptr env, CPXCLPptr lp, int *cstat, int *rstat, double *dnorm);
     */
    int CPXgetbasednorms(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] cstat, int[] rstat, double[] dnorm);

    /**
     * int CPXgetbhead (CPXCENVptr env, CPXCLPptr lp, int *head, double *x);
     */
    int CPXgetbhead(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] head, double[] x);

    /**
     * int CPXgetcallbackinfo (CPXCENVptr env, void *cbdata, int wherefrom, int whichinfo, void *result_p);
     */
    int CPXgetcallbackinfo(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int whichinfo, Pointer result_p);

    /**
     * int CPXgetchannels (CPXCENVptr env, CPXCHANNELptr *cpxresults_p, CPXCHANNELptr *cpxwarning_p, CPXCHANNELptr *cpxerror_p, CPXCHANNELptr *cpxlog_p);
     */
    //int CPXgetchannels(@Pinned @In Pointer env, CPXCHANNELptr *cpxresults_p, CPXCHANNELptr *cpxwarning_p, CPXCHANNELptr *cpxerror_p, CPXCHANNELptr *cpxlog_p);

    /**
     * int CPXgetchgparam (CPXCENVptr env, int *cnt_p, int *paramnum, int pspace, int *surplus_p);
     */
    int CPXgetchgparam(@Pinned @In Pointer env, int[] cnt_p, int[] paramnum, int pspace, int[] surplus_p);

    /**
     * int CPXgetcoef (CPXCENVptr env, CPXCLPptr lp, int i, int j, double *coef_p);
     */
    int CPXgetcoef(@Pinned @In Pointer env, @Pinned @In Pointer lp, int i, int j, double[] coef_p);

    /**
     * int CPXgetcolindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetcolindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetcolinfeas (CPXCENVptr env, CPXCLPptr lp, double const *x, double *infeasout, int begin, int end);
     */
    int CPXgetcolinfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] infeasout, int begin, int end);

    /**
     * int CPXgetcolname (CPXCENVptr env, CPXCLPptr lp, char  **name, char *namestore, int storespace, int *surplus_p, int begin, int end);
     */
    int CPXgetcolname(@Pinned @In Pointer env, @Pinned @In Pointer lp, Pointer name, String namestore, int storespace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetcols (CPXCENVptr env, CPXCLPptr lp, int *nzcnt_p, int *cmatbeg, int *cmatind, double *cmatval, int cmatspace, int *surplus_p, int begin, int end);
     */
    int CPXgetcols(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] nzcnt_p, int[] cmatbeg, int[] cmatind, double[] cmatval, int cmatspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetconflict (CPXCENVptr env, CPXCLPptr lp, int *confstat_p, int *rowind, int *rowbdstat, int *confnumrows_p, int *colind, int *colbdstat, int *confnumcols_p);
     */
    int CPXgetconflict(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] confstat_p, int[] rowind, int[] rowbdstat, int[] confnumrows_p, int[] colind, int[] colbdstat, int[] confnumcols_p);

    /**
     * int CPXgetconflictext (CPXCENVptr env, CPXCLPptr lp, int *grpstat, int beg, int end);
     */
    int CPXgetconflictext(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] grpstat, int beg, int end);

    /**
     * int CPXgetcrossdexchcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetcrossdexchcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetcrossdpushcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetcrossdpushcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetcrosspexchcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetcrosspexchcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetcrossppushcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetcrossppushcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetdblannotationdefval (CPXCENVptr env, CPXCLPptr lp, int idx, double *defval_p);
     */
    int CPXgetdblannotationdefval(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, double[] defval_p);

    /**
     * int CPXgetdblannotationindex (CPXCENVptr env, CPXCLPptr lp, char const *annotationname_str, int *index_p);
     */
    int CPXgetdblannotationindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String annotationname_str, int[] index_p);

    /**
     * int CPXgetdblannotationname (CPXCENVptr env, CPXCLPptr lp, int idx, char *buf_str, int bufspace, int *surplus_p);
     */
    int CPXgetdblannotationname(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXgetdblannotations (CPXCENVptr env, CPXCLPptr lp, int idx, int objtype, double *annotation, int begin, int end);
     */
    int CPXgetdblannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, int objtype, double[] annotation, int begin, int end);

    /**
     * int CPXgetdblparam (CPXCENVptr env, int whichparam, double *value_p);
     */
    int CPXgetdblparam(@Pinned @In Pointer env, int whichparam, @Out DoubleByReference value_p);

    /**
     * int CPXgetdblquality (CPXCENVptr env, CPXCLPptr lp, double *quality_p, int what);
     */
    int CPXgetdblquality(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] quality_p, int what);

    /**
     * int CPXgetdettime (CPXCENVptr env, double *dettimestamp_p);
     */
    int CPXgetdettime(@Pinned @In Pointer env, double[] dettimestamp_p);

    /**
     * int CPXgetdj (CPXCENVptr env, CPXCLPptr lp, double *dj, int begin, int end);
     */
    int CPXgetdj(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out double[] dj, int begin, int end);

    /**
     * int CPXgetdnorms (CPXCENVptr env, CPXCLPptr lp, double *norm, int *head, int *len_p);
     */
    int CPXgetdnorms(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] norm, int[] head, int[] len_p);

    /**
     * int CPXgetdsbcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetdsbcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * CPXCCHARptr CPXgeterrorstring (CPXCENVptr env, int errcode, char *buffer_str);
     */
    //CPXCCHARptr CPXgeterrorstring(@Pinned @In Pointer env, int errcode, String buffer_str);

    /**
     * int CPXgetgrad (CPXCENVptr env, CPXCLPptr lp, int j, int *head, double *y);
     */
    int CPXgetgrad(@Pinned @In Pointer env, @Pinned @In Pointer lp, int j, int[] head, double[] y);

    /**
     * int CPXgetijdiv (CPXCENVptr env, CPXCLPptr lp, int *idiv_p, int *jdiv_p);
     */
    int CPXgetijdiv(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] idiv_p, int[] jdiv_p);

    /**
     * int CPXgetijrow (CPXCENVptr env, CPXCLPptr lp, int i, int j, int *row_p);
     */
    int CPXgetijrow(@Pinned @In Pointer env, @Pinned @In Pointer lp, int i, int j, int[] row_p);

    /**
     * int CPXgetintparam (CPXCENVptr env, int whichparam, CPXINT *value_p);
     */
    int CPXgetintparam(@Pinned @In Pointer env, int whichparam, @Out IntByReference value_p);

    /**
     * int CPXgetintquality (CPXCENVptr env, CPXCLPptr lp, int *quality_p, int what);
     */
    int CPXgetintquality(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] quality_p, int what);

    /**
     * int CPXgetitcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetitcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetlb (CPXCENVptr env, CPXCLPptr lp, double *lb, int begin, int end);
     */
    int CPXgetlb(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out DoubleByReference lb, int begin, int end);

    /**
     * int CPXgetlogfilename (CPXCENVptr env, char *buf_str, int bufspace, int *surplus_p);
     */
    int CPXgetlogfilename(@Pinned @In Pointer env, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXgetlongannotationdefval (CPXCENVptr env, CPXCLPptr lp, int idx, CPXLONG *defval_p);
     */
    int CPXgetlongannotationdefval(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, long[] defval_p);

    /**
     * int CPXgetlongannotationindex (CPXCENVptr env, CPXCLPptr lp, char const *annotationname_str, int *index_p);
     */
    int CPXgetlongannotationindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String annotationname_str, int[] index_p);

    /**
     * int CPXgetlongannotationname (CPXCENVptr env, CPXCLPptr lp, int idx, char *buf_str, int bufspace, int *surplus_p);
     */
    int CPXgetlongannotationname(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXgetlongannotations (CPXCENVptr env, CPXCLPptr lp, int idx, int objtype, CPXLONG *annotation, int begin, int end);
     */
    int CPXgetlongannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, int objtype, long[] annotation, int begin, int end);

    /**
     * int CPXgetlongparam (CPXCENVptr env, int whichparam, CPXLONG *value_p);
     */
    int CPXgetlongparam(@Pinned @In Pointer env, int whichparam, long[] value_p);

    /**
     * int CPXgetlpcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **callback_p)(CPXCENVptr, void *, int, void *), void  **cbhandle_p);
     */
    //int CPXgetlpcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**callback_p)(@Pinned @In Pointer,Pointer ,int,Pointer ),PointerByReference cbhandle_p);

    /**
     * int CPXgetmethod (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetmethod(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnetcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **callback_p)(CPXCENVptr, void *, int, void *), void  **cbhandle_p);
     */
    //int CPXgetnetcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**callback_p)(@Pinned @In Pointer,Pointer ,int,Pointer ),PointerByReference cbhandle_p);

    /**
     * int CPXgetnumcols (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumcols(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumcores (CPXCENVptr env, int *numcores_p);
     */
    int CPXgetnumcores(@Pinned @In Pointer env, int[] numcores_p);

    /**
     * int CPXgetnumdblannotations (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumdblannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumlongannotations (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumlongannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumnz (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumnz(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumobjs (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumobjs(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumpwl (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumpwl(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumrows (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumrows(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetobj (CPXCENVptr env, CPXCLPptr lp, double *obj, int begin, int end);
     */
    int CPXgetobj(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out DoubleByReference obj, int begin, int end);

    /**
     * int CPXgetobjname (CPXCENVptr env, CPXCLPptr lp, char *buf_str, int bufspace, int *surplus_p);
     */
    int CPXgetobjname(@Pinned @In Pointer env, @Pinned @In Pointer lp, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXgetobjoffset (CPXCENVptr env, CPXCLPptr lp, double *objoffset_p);
     */
    int CPXgetobjoffset(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out DoubleByReference objoffset_p);

    /**
     * int CPXgetobjsen (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetobjsen(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetobjval (CPXCENVptr env, CPXCLPptr lp, double *objval_p);
     */
    int CPXgetobjval(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out DoubleByReference objval_p);

    /**
     * int CPXgetparamhiername (CPXCENVptr env, int whichparam, char *name_str);
     */
    int CPXgetparamhiername(@Pinned @In Pointer env, int whichparam, String name_str);

    /**
     * int CPXgetparamname (CPXCENVptr env, int whichparam, char *name_str);
     */
    int CPXgetparamname(@Pinned @In Pointer env, int whichparam, String name_str);

    /**
     * int CPXgetparamnum (CPXCENVptr env, char const *name_str, int *whichparam_p);
     */
    int CPXgetparamnum(@Pinned @In Pointer env, @Pinned @In @Transient String name_str, int[] whichparam_p);

    /**
     * int CPXgetparamtype (CPXCENVptr env, int whichparam, int *paramtype);
     */
    int CPXgetparamtype(@Pinned @In Pointer env, int whichparam, int[] paramtype);

    /**
     * int CPXgetphase1cnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetphase1cnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetpi (CPXCENVptr env, CPXCLPptr lp, double *pi, int begin, int end);
     */
    int CPXgetpi(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out double[] pi, int begin, int end);

    /**
     * int CPXgetpnorms (CPXCENVptr env, CPXCLPptr lp, double *cnorm, double *rnorm, int *len_p);
     */
    int CPXgetpnorms(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] cnorm, double[] rnorm, int[] len_p);

    /**
     * int CPXgetprestat (CPXCENVptr env, CPXCLPptr lp, int *prestat_p, int *pcstat, int *prstat, int *ocstat, int *orstat);
     */
    int CPXgetprestat(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] prestat_p, int[] pcstat, int[] prstat, int[] ocstat, int[] orstat);

    /**
     * int CPXgetprobname (CPXCENVptr env, CPXCLPptr lp, char *buf_str, int bufspace, int *surplus_p);
     */
    int CPXgetprobname(@Pinned @In Pointer env, @Pinned @In Pointer lp, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXgetprobtype (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetprobtype(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetprotected (CPXCENVptr env, CPXCLPptr lp, int *cnt_p, int *indices, int pspace, int *surplus_p);
     */
    int CPXgetprotected(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] cnt_p, int[] indices, int pspace, int[] surplus_p);

    /**
     * int CPXgetpsbcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetpsbcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetpwl (CPXCENVptr env, CPXCLPptr lp, int pwlindex, int *vary_p, int *varx_p, double *preslope_p, double *postslope_p, int *nbreaks_p, double *breakx, double *breaky, int breakspace, int *surplus_p);
     */
    int CPXgetpwl(@Pinned @In Pointer env, @Pinned @In Pointer lp, int pwlindex, int[] vary_p, int[] varx_p, double[] preslope_p, double[] postslope_p, int[] nbreaks_p, double[] breakx, double[] breaky, int breakspace, int[] surplus_p);

    /**
     * int CPXgetpwlindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetpwlindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetpwlname (CPXCENVptr env, CPXCLPptr lp, char *buf_str, int bufspace, int *surplus_p, int which);
     */
    int CPXgetpwlname(@Pinned @In Pointer env, @Pinned @In Pointer lp, String buf_str, int bufspace, int[] surplus_p, int which);

    /**
     * int CPXgetray (CPXCENVptr env, CPXCLPptr lp, double *z);
     */
    int CPXgetray(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] z);

    /**
     * int CPXgetredlp (CPXCENVptr env, CPXCLPptr lp, CPXCLPptr *redlp_p);
     */
    int CPXgetredlp(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out PointerByReference redlp_p);

    /**
     * int CPXgetrhs (CPXCENVptr env, CPXCLPptr lp, double *rhs, int begin, int end);
     */
    int CPXgetrhs(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] rhs, int begin, int end);

    /**
     * int CPXgetrngval (CPXCENVptr env, CPXCLPptr lp, double *rngval, int begin, int end);
     */
    int CPXgetrngval(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] rngval, int begin, int end);

    /**
     * int CPXgetrowindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetrowindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetrowinfeas (CPXCENVptr env, CPXCLPptr lp, double const *x, double *infeasout, int begin, int end);
     */
    int CPXgetrowinfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] infeasout, int begin, int end);

    /**
     * int CPXgetrowname (CPXCENVptr env, CPXCLPptr lp, char  **name, char *namestore, int storespace, int *surplus_p, int begin, int end);
     */
    int CPXgetrowname(@Pinned @In Pointer env, @Pinned @In Pointer lp, Pointer name, String namestore, int storespace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetrows (CPXCENVptr env, CPXCLPptr lp, int *nzcnt_p, int *rmatbeg, int *rmatind, double *rmatval, int rmatspace, int *surplus_p, int begin, int end);
     */
    int CPXgetrows(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] nzcnt_p, int[] rmatbeg, int[] rmatind, double[] rmatval, int rmatspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetsense (CPXCENVptr env, CPXCLPptr lp, char *sense, int begin, int end);
     */
    int CPXgetsense(@Pinned @In Pointer env, @Pinned @In Pointer lp, String sense, int begin, int end);

    /**
     * int CPXgetsiftitcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsiftitcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetsiftphase1cnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsiftphase1cnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetslack (CPXCENVptr env, CPXCLPptr lp, double *slack, int begin, int end);
     */
    int CPXgetslack(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] slack, int begin, int end);

    /**
     * int CPXgetsolnpooldblquality (CPXCENVptr env, CPXCLPptr lp, int soln, double *quality_p, int what);
     */
    int CPXgetsolnpooldblquality(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, double[] quality_p, int what);

    /**
     * int CPXgetsolnpoolintquality (CPXCENVptr env, CPXCLPptr lp, int soln, int *quality_p, int what);
     */
    int CPXgetsolnpoolintquality(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, int[] quality_p, int what);

    /**
     * int CPXgetstat (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetstat(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * CPXCHARptr CPXgetstatstring (CPXCENVptr env, int statind, char *buffer_str);
     */
    //CPXCHARptr CPXgetstatstring(@Pinned @In Pointer env, int statind, String buffer_str);

    /**
     * int CPXgetstrparam (CPXCENVptr env, int whichparam, char *value_str);
     */
    int CPXgetstrparam(@Pinned @In Pointer env, int whichparam, String value_str);

    /**
     * int CPXgettime (CPXCENVptr env, double *timestamp_p);
     */
    int CPXgettime(@Pinned @In Pointer env, double[] timestamp_p);

    /**
     * int CPXgettuningcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **callback_p)(CPXCENVptr, void *, int, void *), void  **cbhandle_p);
     */
    //int CPXgettuningcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**callback_p)(@Pinned @In Pointer,Pointer ,int,Pointer ),PointerByReference cbhandle_p);

    /**
     * int CPXgetub (CPXCENVptr env, CPXCLPptr lp, double *ub, int begin, int end);
     */
    int CPXgetub(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out DoubleByReference ub, int begin, int end);

    /**
     * int CPXgetweight (CPXCENVptr env, CPXCLPptr lp, int rcnt, int const *rmatbeg, int const *rmatind, double const *rmatval, double *weight, int dpriind);
     */
    int CPXgetweight(@Pinned @In Pointer env, @Pinned @In Pointer lp, int rcnt, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, double[] weight, int dpriind);

    /**
     * int CPXgetx (CPXCENVptr env, CPXCLPptr lp, double *x, int begin, int end);
     */
    int CPXgetx(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out double[] x, int begin, int end);

    /**
     * int CPXhybnetopt (CPXCENVptr env, CPXLPptr lp, int method);
     */
    int CPXhybnetopt(@Pinned @In Pointer env, @Pinned @In Pointer lp, int method);

    /**
     * int CPXinfodblparam (CPXCENVptr env, int whichparam, double *defvalue_p, double *minvalue_p, double *maxvalue_p);
     */
    int CPXinfodblparam(@Pinned @In Pointer env, int whichparam, double[] defvalue_p, double[] minvalue_p, double[] maxvalue_p);

    /**
     * int CPXinfointparam (CPXCENVptr env, int whichparam, CPXINT *defvalue_p, CPXINT *minvalue_p, CPXINT *maxvalue_p);
     */
    int CPXinfointparam(@Pinned @In Pointer env, int whichparam, int[] defvalue_p, int[] minvalue_p, int[] maxvalue_p);

    /**
     * int CPXinfolongparam (CPXCENVptr env, int whichparam, CPXLONG *defvalue_p, CPXLONG *minvalue_p, CPXLONG *maxvalue_p);
     */
    int CPXinfolongparam(@Pinned @In Pointer env, int whichparam, long[] defvalue_p, long[] minvalue_p, long[] maxvalue_p);

    /**
     * int CPXinfostrparam (CPXCENVptr env, int whichparam, char *defvalue_str);
     */
    int CPXinfostrparam(@Pinned @In Pointer env, int whichparam, String defvalue_str);

    /**
     * void CPXinitialize (void);
     */
    void CPXinitialize();

    /**
     * int CPXkilldnorms (CPXLPptr lp);
     */
    int CPXkilldnorms(@Pinned @In Pointer lp);

    /**
     * int CPXkillpnorms (CPXLPptr lp);
     */
    int CPXkillpnorms(@Pinned @In Pointer lp);

    /**
     * int CPXlpopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXlpopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXmbasewrite (CPXCENVptr env, CPXCLPptr lp, char const *filename_str);
     */
    int CPXmbasewrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXmdleave (CPXCENVptr env, CPXLPptr lp, int const *indices, int cnt, double *downratio, double *upratio);
     */
    int CPXmdleave(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] indices, int cnt, double[] downratio, double[] upratio);

    /**
     * int CPXmodelasstcallbackgetfunc (CPXCENVptr env, CPXCLPptr lp, CPXMODELASSTCALLBACKFUNC **callback_p, void  **cbhandle_p);
     */
    //int CPXmodelasstcallbackgetfunc(@Pinned @In Pointer env, @Pinned @In Pointer lp, CPXMODELASSTCALLBACKFUNC **callback_p, PointerByReference cbhandle_p);

    /**
     * int CPXmodelasstcallbacksetfunc (CPXENVptr env, CPXLPptr lp, CPXMODELASSTCALLBACKFUNC callback, void *userhandle);
     */
    //int CPXmodelasstcallbacksetfunc(Pointer env, @Pinned @In Pointer lp, CPXMODELASSTCALLBACKFUNC callback, Pointer userhandle);

    /**
     * int CPXmsgstr (CPXCHANNELptr channel, char const *msg_str);
     */
    //int CPXmsgstr(CPXCHANNELptr channel, @Pinned @In @Transient String msg_str);

    /**
     * int CPXmultiobjchgattribs (CPXCENVptr env, CPXLPptr lp, int objind, double offset, double weight, int priority, double abstol, double reltol, char const *name);
     */
    int CPXmultiobjchgattribs(@Pinned @In Pointer env, @Pinned @In Pointer lp, int objind, double offset, double weight, int priority, double abstol, double reltol, @Pinned @In @Transient String name);

    /**
     * int CPXmultiobjgetdblinfo (CPXCENVptr env, CPXCLPptr lp, int subprob, double *info_p, int what);
     */
    int CPXmultiobjgetdblinfo(@Pinned @In Pointer env, @Pinned @In Pointer lp, int subprob, double[] info_p, int what);

    /**
     * int CPXmultiobjgetindex (CPXCENVptr env, CPXCLPptr lp, char const *name, int *index_p);
     */
    int CPXmultiobjgetindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String name, int[] index_p);

    /**
     * int CPXmultiobjgetintinfo (CPXCENVptr env, CPXCLPptr lp, int subprob, int *info_p, int what);
     */
    int CPXmultiobjgetintinfo(@Pinned @In Pointer env, @Pinned @In Pointer lp, int subprob, int[] info_p, int what);

    /**
     * int CPXmultiobjgetlonginfo (CPXCENVptr env, CPXCLPptr lp, int subprob, CPXLONG *info_p, int what);
     */
    int CPXmultiobjgetlonginfo(@Pinned @In Pointer env, @Pinned @In Pointer lp, int subprob, long[] info_p, int what);

    /**
     * int CPXmultiobjgetname (CPXCENVptr env, CPXCLPptr lp, int objind, char *buf_str, int bufspace, int *surplus_p);
     */
    int CPXmultiobjgetname(@Pinned @In Pointer env, @Pinned @In Pointer lp, int objind, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXmultiobjgetnumsolves (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXmultiobjgetnumsolves(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXmultiobjgetobj (CPXCENVptr env, CPXCLPptr lp, int n, double *coeffs, int begin, int end, double *offset_p, double *weight_p, int *priority_p, double *abstol_p, double *reltol_p);
     */
    int CPXmultiobjgetobj(@Pinned @In Pointer env, @Pinned @In Pointer lp, int n, double[] coeffs, int begin, int end, double[] offset_p, double[] weight_p, int[] priority_p, double[] abstol_p, double[] reltol_p);

    /**
     * int CPXmultiobjgetobjval (CPXCENVptr env, CPXCLPptr lp, int n, double *objval_p);
     */
    int CPXmultiobjgetobjval(@Pinned @In Pointer env, @Pinned @In Pointer lp, int n, double[] objval_p);

    /**
     * int CPXmultiobjgetobjvalbypriority (CPXCENVptr env, CPXCLPptr lp, int priority, double *objval_p);
     */
    int CPXmultiobjgetobjvalbypriority(@Pinned @In Pointer env, @Pinned @In Pointer lp, int priority, double[] objval_p);

    /**
     * int CPXmultiobjopt (CPXCENVptr env, CPXLPptr lp, CPXCPARAMSETptr const *paramsets);
     */
    //int CPXmultiobjopt(@Pinned @In Pointer env, @Pinned @In Pointer lp, CPXCPARAMSETptr const*paramsets);

    /**
     * int CPXmultiobjsetobj (CPXCENVptr env, CPXLPptr lp, int n, int objnz, int const *objind, double const *objval, double offset, double weight, int priority, double abstol, double reltol, char const *objname);
     */
    int CPXmultiobjsetobj(@Pinned @In Pointer env, @Pinned @In Pointer lp, int n, int objnz, @Pinned @In @Transient int[] objind, @Pinned @In @Transient double[] objval, double offset, double weight, int priority, double abstol, double reltol, @Pinned @In @Transient String objname);

    /**
     * int CPXNETextract (CPXCENVptr env, CPXNETptr net, CPXCLPptr lp, int *colmap, int *rowmap);
     */
    //int CPXNETextract(@Pinned @In Pointer env, CPXNETptr net, @Pinned @In Pointer lp, int[] colmap, int[] rowmap);

    /**
     * int CPXnewcols (CPXCENVptr env, CPXLPptr lp, int ccnt, double const *obj, double const *lb, double const *ub, char const *xctype, char **colname);
     */
    int CPXnewcols(@Pinned @In Pointer env, @Pinned @In Pointer lp, int ccnt, @Pinned @In @Transient double[] obj, @Pinned @In @Transient double[] lb, @Pinned @In @Transient double[] ub, @Pinned @In @Transient byte[] xctype, @Pinned @In @Transient String[] colname);

    /**
     * int CPXnewdblannotation (CPXCENVptr env, CPXLPptr lp, char const *annotationname_str, double defval);
     */
    int CPXnewdblannotation(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String annotationname_str, double defval);

    /**
     * int CPXnewlongannotation (CPXCENVptr env, CPXLPptr lp, char const *annotationname_str, CPXLONG defval);
     */
    int CPXnewlongannotation(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String annotationname_str, long defval);

    /**
     * int CPXnewrows (CPXCENVptr env, CPXLPptr lp, int rcnt, double const *rhs, char const *sense, double const *rngval, char **rowname);
     */
    int CPXnewrows(@Pinned @In Pointer env, @Pinned @In Pointer lp, int rcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient double[] rngval, Pointer rowname);

    /**
     * int CPXobjsa (CPXCENVptr env, CPXCLPptr lp, int begin, int end, double *lower, double *upper);
     */
    int CPXobjsa(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end, double[] lower, double[] upper);

    /**
     * CPXENVptr CPXopenCPLEX (int *status_p);
     */
    Pointer CPXopenCPLEX(@Out IntByReference status_p);

    /**
     * int CPXparamsetadddbl (CPXCENVptr env, CPXPARAMSETptr ps, int whichparam, double newvalue);
     */
    //int CPXparamsetadddbl(@Pinned @In Pointer env, CPXPARAMSETptr ps, int whichparam, double newvalue);

    /**
     * int CPXparamsetaddint (CPXCENVptr env, CPXPARAMSETptr ps, int whichparam, CPXINT newvalue);
     */
    //int CPXparamsetaddint(@Pinned @In Pointer env, CPXPARAMSETptr ps, int whichparam, int newvalue);

    /**
     * int CPXparamsetaddlong (CPXCENVptr env, CPXPARAMSETptr ps, int whichparam, CPXLONG newvalue);
     */
    //int CPXparamsetaddlong(@Pinned @In Pointer env, CPXPARAMSETptr ps, int whichparam, long newvalue);

    /**
     * int CPXparamsetaddstr (CPXCENVptr env, CPXPARAMSETptr ps, int whichparam, char const *svalue);
     */
    //int CPXparamsetaddstr(@Pinned @In Pointer env, CPXPARAMSETptr ps, int whichparam, @Pinned @In @Transient String svalue);

    /**
     * int CPXparamsetapply (CPXENVptr env, CPXCPARAMSETptr ps);
     */
    //int CPXparamsetapply(Pointer env, CPXCPARAMSETptr ps);

    /**
     * int CPXparamsetcopy (CPXCENVptr targetenv, CPXPARAMSETptr targetps, CPXCPARAMSETptr sourceps);
     */
    //int CPXparamsetcopy(@Pinned @In Pointer targetenv, CPXPARAMSETptr targetps, CPXCPARAMSETptr sourceps);

    /**
     * CPXPARAMSETptr CPXparamsetcreate (CPXCENVptr env, int *status_p);
     */
    //CPXPARAMSETptr CPXparamsetcreate(@Pinned @In Pointer env, @Out IntByReference status_p);

    /**
     * int CPXparamsetdel (CPXCENVptr env, CPXPARAMSETptr ps, int whichparam);
     */
    //int CPXparamsetdel(@Pinned @In Pointer env, CPXPARAMSETptr ps, int whichparam);

    /**
     * int CPXparamsetfree (CPXCENVptr env, CPXPARAMSETptr *ps_p);
     */
    //int CPXparamsetfree(@Pinned @In Pointer env, CPXPARAMSETptr *ps_p);

    /**
     * int CPXparamsetgetdbl (CPXCENVptr env, CPXCPARAMSETptr ps, int whichparam, double *dval_p);
     */
    //int CPXparamsetgetdbl(@Pinned @In Pointer env, CPXCPARAMSETptr ps, int whichparam, double[] dval_p);

    /**
     * int CPXparamsetgetids (CPXCENVptr env, CPXCPARAMSETptr ps, int *cnt_p, int *whichparams, int pspace, int *surplus_p);
     */
    //int CPXparamsetgetids(@Pinned @In Pointer env, CPXCPARAMSETptr ps, int[] cnt_p, int[] whichparams, int pspace, int[] surplus_p);

    /**
     * int CPXparamsetgetint (CPXCENVptr env, CPXCPARAMSETptr ps, int whichparam, CPXINT *ival_p);
     */
    //int CPXparamsetgetint(@Pinned @In Pointer env, CPXCPARAMSETptr ps, int whichparam, int[] ival_p);

    /**
     * int CPXparamsetgetlong (CPXCENVptr env, CPXCPARAMSETptr ps, int whichparam, CPXLONG *ival_p);
     */
    //int CPXparamsetgetlong(@Pinned @In Pointer env, CPXCPARAMSETptr ps, int whichparam, long[] ival_p);

    /**
     * int CPXparamsetgetstr (CPXCENVptr env, CPXCPARAMSETptr ps, int whichparam, char *sval);
     */
    //int CPXparamsetgetstr(@Pinned @In Pointer env, CPXCPARAMSETptr ps, int whichparam, String sval);

    /**
     * int CPXparamsetreadcopy (CPXENVptr env, CPXPARAMSETptr ps, char const *filename_str);
     */
    //int CPXparamsetreadcopy(Pointer env, CPXPARAMSETptr ps, @Pinned @In @Transient String filename_str);

    /**
     * int CPXparamsetwrite (CPXCENVptr env, CPXCPARAMSETptr ps, char const *filename_str);
     */
    //int CPXparamsetwrite(@Pinned @In Pointer env, CPXCPARAMSETptr ps, @Pinned @In @Transient String filename_str);

    /**
     * int CPXpivot (CPXCENVptr env, CPXLPptr lp, int jenter, int jleave, int leavestat);
     */
    int CPXpivot(@Pinned @In Pointer env, @Pinned @In Pointer lp, int jenter, int jleave, int leavestat);

    /**
     * int CPXpivotin (CPXCENVptr env, CPXLPptr lp, int const *rlist, int rlen);
     */
    int CPXpivotin(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] rlist, int rlen);

    /**
     * int CPXpivotout (CPXCENVptr env, CPXLPptr lp, int const *clist, int clen);
     */
    int CPXpivotout(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] clist, int clen);

    /**
     * int CPXpperwrite (CPXCENVptr env, CPXLPptr lp, char const *filename_str, double epsilon);
     */
    int CPXpperwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, double epsilon);

    /**
     * int CPXpratio (CPXCENVptr env, CPXLPptr lp, int *indices, int cnt, double *downratio, double *upratio, int *downleave, int *upleave, int *downleavestatus, int *upleavestatus, int *downstatus, int *upstatus);
     */
    int CPXpratio(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] indices, int cnt, double[] downratio, double[] upratio, int[] downleave, int[] upleave, int[] downleavestatus, int[] upleavestatus, int[] downstatus, int[] upstatus);

    /**
     * int CPXpreaddrows (CPXCENVptr env, CPXLPptr lp, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, char **rowname);
     */
    int CPXpreaddrows(@Pinned @In Pointer env, @Pinned @In Pointer lp, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, Pointer rowname);

    /**
     * int CPXprechgobj (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, double const *values);
     */
    int CPXprechgobj(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] values);

    /**
     * int CPXpreslvwrite (CPXCENVptr env, CPXLPptr lp, char const *filename_str, double *objoff_p);
     */
    int CPXpreslvwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, double[] objoff_p);

    /**
     * int CPXpresolve (CPXCENVptr env, CPXLPptr lp, int method);
     */
    int CPXpresolve(@Pinned @In Pointer env, @Pinned @In Pointer lp, int method);

    /**
     * int CPXprimopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXprimopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXqpdjfrompi (CPXCENVptr env, CPXCLPptr lp, double const *pi, double const *x, double *dj);
     */
    int CPXqpdjfrompi(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] pi, @Pinned @In @Transient double[] x, double[] dj);

    /**
     * int CPXqpuncrushpi (CPXCENVptr env, CPXCLPptr lp, double *pi, double const *prepi, double const *x);
     */
    int CPXqpuncrushpi(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] pi, @Pinned @In @Transient double[] prepi, @Pinned @In @Transient double[] x);

    /**
     * int CPXreadcopyannotations (CPXCENVptr env, CPXLPptr lp, char const *filename);
     */
    int CPXreadcopyannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename);

    /**
     * int CPXreadcopybase (CPXCENVptr env, CPXLPptr lp, char const *filename_str);
     */
    int CPXreadcopybase(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXreadcopyparam (CPXENVptr env, char const *filename_str);
     */
    int CPXreadcopyparam(Pointer env, @Pinned @In @Transient String filename_str);

    /**
     * int CPXreadcopyprob (CPXCENVptr env, CPXLPptr lp, char const *filename_str, char const *filetype);
     */
    int CPXreadcopyprob(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, @Pinned @In @Transient String filetype);

    /**
     * int CPXreadcopysol (CPXCENVptr env, CPXLPptr lp, char const *filename_str);
     */
    int CPXreadcopysol(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXrefineconflict (CPXCENVptr env, CPXLPptr lp, int *confnumrows_p, int *confnumcols_p);
     */
    int CPXrefineconflict(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] confnumrows_p, int[] confnumcols_p);

    /**
     * int CPXrefineconflictext (CPXCENVptr env, CPXLPptr lp, int grpcnt, int concnt, double const *grppref, int const *grpbeg, int const *grpind, char const *grptype);
     */
    int CPXrefineconflictext(@Pinned @In Pointer env, @Pinned @In Pointer lp, int grpcnt, int concnt, @Pinned @In @Transient double[] grppref, @Pinned @In @Transient int[] grpbeg, @Pinned @In @Transient int[] grpind, @Pinned @In @Transient String grptype);

    /**
     * int CPXrhssa (CPXCENVptr env, CPXCLPptr lp, int begin, int end, double *lower, double *upper);
     */
    int CPXrhssa(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end, double[] lower, double[] upper);

    /**
     * int CPXrobustopt (CPXCENVptr env, CPXLPptr lp, CPXLPptr lblp, CPXLPptr ublp, double objchg, double const *maxchg);
     */
    int CPXrobustopt(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In Pointer lblp, @Pinned @In Pointer ublp, double objchg, @Pinned @In @Transient double[] maxchg);

    /**
     * int CPXserializercreate (CPXSERIALIZERptr *ser_p);
     */
    //int CPXserializercreate(CPXSERIALIZERptr *ser_p);

    /**
     * void CPXserializerdestroy (CPXSERIALIZERptr ser);
     */
    //void CPXserializerdestroy(CPXSERIALIZERptr ser);

    /**
     * CPXLONG CPXserializerlength (CPXCSERIALIZERptr ser);
     */
    //CPXLONG CPXserializerlength(CPXCSERIALIZERptr ser);

    /**
     * void const * CPXserializerpayload (CPXCSERIALIZERptr ser);
     */
    //Pointer CPXserializerpayload(CPXCSERIALIZERptr ser);

    /**
     * int CPXsetdblannotations (CPXCENVptr env, CPXLPptr lp, int idx, int objtype, int cnt, int const *indices, double const *values);
     */
    int CPXsetdblannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, int objtype, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] values);

    /**
     * int CPXsetdblparam (CPXENVptr env, int whichparam, double newvalue);
     */
    int CPXsetdblparam(Pointer env, int whichparam, double newvalue);

    /**
     * int CPXsetdefaults (CPXENVptr env);
     */
    int CPXsetdefaults(Pointer env);

    /**
     * int CPXsetintparam (CPXENVptr env, int whichparam, CPXINT newvalue);
     */
    int CPXsetintparam(Pointer env, int whichparam, int newvalue);

    /**
     * int CPXsetlogfilename (CPXCENVptr env, char const *filename, char const *mode);
     */
    int CPXsetlogfilename(@Pinned @In Pointer env, @Pinned @In @Transient String filename, @Pinned @In @Transient String mode);

    /**
     * int CPXsetlongannotations (CPXCENVptr env, CPXLPptr lp, int idx, int objtype, int cnt, int const *indices, CPXLONG const *values);
     */
    int CPXsetlongannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, int idx, int objtype, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient long[] values);

    /**
     * int CPXsetlongparam (CPXENVptr env, int whichparam, CPXLONG newvalue);
     */
    int CPXsetlongparam(Pointer env, int whichparam, long newvalue);

    /**
     * int CPXsetlpcallbackfunc (CPXENVptr env, int(CPXPUBLIC *callback)(CPXCENVptr, void *, int, void *), void *cbhandle);
     */
    //int CPXsetlpcallbackfunc(Pointer env, int(CPXPUBLIC*callback)(@Pinned @In Pointer,Pointer ,int,Pointer ),Pointer cbhandle);

    /**
     * int CPXsetnetcallbackfunc (CPXENVptr env, int(CPXPUBLIC *callback)(CPXCENVptr, void *, int, void *), void *cbhandle);
     */
    //int CPXsetnetcallbackfunc(Pointer env, int(CPXPUBLIC*callback)(@Pinned @In Pointer,Pointer ,int,Pointer ),Pointer cbhandle);

    /**
     * int CPXsetnumobjs (CPXCENVptr env, CPXCLPptr lp, int n);
     */
    int CPXsetnumobjs(@Pinned @In Pointer env, @Pinned @In Pointer lp, int n);

    /**
     * int CPXsetphase2 (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXsetphase2(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXsetprofcallbackfunc (CPXENVptr env, int(CPXPUBLIC *callback)(CPXCENVptr, int, void *), void *cbhandle);
     */
    //int CPXsetprofcallbackfunc(Pointer env, int(CPXPUBLIC*callback)(@Pinned @In Pointer,int,Pointer ),Pointer cbhandle);

    /**
     * int CPXsetstrparam (CPXENVptr env, int whichparam, char const *newvalue_str);
     */
    int CPXsetstrparam(Pointer env, int whichparam, @Pinned @In @Transient String newvalue_str);

    /**
     * int CPXsetterminate (CPXENVptr env, volatile int *terminate_p);
     */
    //int CPXsetterminate(Pointer env, volatile int[] terminate_p);

    /**
     * int CPXsettuningcallbackfunc (CPXENVptr env, int(CPXPUBLIC *callback)(CPXCENVptr, void *, int, void *), void *cbhandle);
     */
    //int CPXsettuningcallbackfunc(Pointer env, int(CPXPUBLIC*callback)(@Pinned @In Pointer,Pointer ,int,Pointer ),Pointer cbhandle);

    /**
     * int CPXsiftopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXsiftopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXslackfromx (CPXCENVptr env, CPXCLPptr lp, double const *x, double *slack);
     */
    int CPXslackfromx(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] slack);

    /**
     * int CPXsolninfo (CPXCENVptr env, CPXCLPptr lp, int *solnmethod_p, int *solntype_p, int *pfeasind_p, int *dfeasind_p);
     */
    int CPXsolninfo(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] solnmethod_p, int[] solntype_p, int[] pfeasind_p, int[] dfeasind_p);

    /**
     * int CPXsolution (CPXCENVptr env, CPXCLPptr lp, int *lpstat_p, double *objval_p, double *x, double *pi, double *slack, double *dj);
     */
    int CPXsolution(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] lpstat_p, double[] objval_p, double[] x, double[] pi, double[] slack, double[] dj);

    /**
     * int CPXsolwrite (CPXCENVptr env, CPXCLPptr lp, char const *filename_str);
     */
    int CPXsolwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXsolwritesolnpool (CPXCENVptr env, CPXCLPptr lp, int soln, char const *filename_str);
     */
    int CPXsolwritesolnpool(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, @Pinned @In @Transient String filename_str);

    /**
     * int CPXsolwritesolnpoolall (CPXCENVptr env, CPXCLPptr lp, char const *filename_str);
     */
    int CPXsolwritesolnpoolall(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXstrongbranch (CPXCENVptr env, CPXLPptr lp, int const *indices, int cnt, double *downobj, double *upobj, int itlim);
     */
    int CPXstrongbranch(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] indices, int cnt, double[] downobj, double[] upobj, int itlim);

    /**
     * int CPXtightenbds (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, char const *lu, double const *bd);
     */
    int CPXtightenbds(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient String lu, @Pinned @In @Transient double[] bd);

    /**
     * int CPXtuneparam (CPXENVptr env, CPXLPptr lp, int intcnt, int const *intnum, int const *intval, int dblcnt, int const *dblnum, double const *dblval, int strcnt, int const *strnum, char **strval, int *tunestat_p);
     */
    int CPXtuneparam(Pointer env, @Pinned @In Pointer lp, int intcnt, @Pinned @In @Transient int[] intnum, @Pinned @In @Transient int[] intval, int dblcnt, @Pinned @In @Transient int[] dblnum, @Pinned @In @Transient double[] dblval, int strcnt, @Pinned @In @Transient int[] strnum, Pointer strval, int[] tunestat_p);

    /**
     * int CPXtuneparamprobset (CPXENVptr env, int filecnt, char **filename, char **filetype, int intcnt, int const *intnum, int const *intval, int dblcnt, int const *dblnum, double const *dblval, int strcnt, int const *strnum, char **strval, int *tunestat_p);
     */
    int CPXtuneparamprobset(Pointer env, int filecnt, Pointer filename, Pointer filetype, int intcnt, @Pinned @In @Transient int[] intnum, @Pinned @In @Transient int[] intval, int dblcnt, @Pinned @In @Transient int[] dblnum, @Pinned @In @Transient double[] dblval, int strcnt, @Pinned @In @Transient int[] strnum, Pointer strval, int[] tunestat_p);

    /**
     * int CPXuncrushform (CPXCENVptr env, CPXCLPptr lp, int plen, int const *pind, double const *pval, int *len_p, double *offset_p, int *ind, double *val);
     */
    int CPXuncrushform(@Pinned @In Pointer env, @Pinned @In Pointer lp, int plen, @Pinned @In @Transient int[] pind, @Pinned @In @Transient double[] pval, int[] len_p, double[] offset_p, int[] ind, double[] val);

    /**
     * int CPXuncrushpi (CPXCENVptr env, CPXCLPptr lp, double *pi, double const *prepi);
     */
    int CPXuncrushpi(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] pi, @Pinned @In @Transient double[] prepi);

    /**
     * int CPXuncrushx (CPXCENVptr env, CPXCLPptr lp, double *x, double const *prex);
     */
    int CPXuncrushx(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] x, @Pinned @In @Transient double[] prex);

    /**
     * int CPXunscaleprob (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXunscaleprob(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * CPXCCHARptr CPXversion (CPXCENVptr env);
     */
    //CPXCCHARptr CPXversion(@Pinned @In Pointer env);

    /**
     * int CPXversionnumber (CPXCENVptr env, int *version_p);
     */
    int CPXversionnumber(@Pinned @In Pointer env, int[] version_p);

    /**
     * int CPXwriteannotations (CPXCENVptr env, CPXCLPptr lp, char const *filename);
     */
    int CPXwriteannotations(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename);

    /**
     * int CPXwritebendersannotation (CPXCENVptr env, CPXCLPptr lp, char const *filename);
     */
    int CPXwritebendersannotation(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename);

    /**
     * int CPXwriteparam (CPXCENVptr env, char const *filename_str);
     */
    int CPXwriteparam(@Pinned @In Pointer env, @Pinned @In @Transient String filename_str);

    /**
     * int CPXwriteprob (CPXCENVptr env, CPXCLPptr lp, char const *filename_str, char const *filetype);
     */
    int CPXwriteprob(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, @Pinned @In @Transient String filetype);

    /**
     * int CPXbaropt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXbaropt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXhybbaropt (CPXCENVptr env, CPXLPptr lp, int method);
     */
    int CPXhybbaropt(@Pinned @In Pointer env, @Pinned @In Pointer lp, int method);

    /**
     * int CPXaddindconstraints (CPXCENVptr env, CPXLPptr lp, int indcnt, int const *type, int const *indvar, int const *complemented, int nzcnt, double const *rhs, char const *sense, int const *linbeg, int const *linind, double const *linval, char **indname);
     */
    int CPXaddindconstraints(@Pinned @In Pointer env, @Pinned @In Pointer lp, int indcnt, @Pinned @In @Transient int[] type, @Pinned @In @Transient int[] indvar, @Pinned @In @Transient int[] complemented, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] linbeg, @Pinned @In @Transient int[] linind, @Pinned @In @Transient double[] linval, Pointer indname);

    /**
     * int CPXaddlazyconstraints (CPXCENVptr env, CPXLPptr lp, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, char **rowname);
     */
    int CPXaddlazyconstraints(@Pinned @In Pointer env, @Pinned @In Pointer lp, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, Pointer rowname);

    /**
     * int CPXaddmipstarts (CPXCENVptr env, CPXLPptr lp, int mcnt, int nzcnt, int const *beg, int const *varindices, double const *values, int const *effortlevel, char **mipstartname);
     */
    int CPXaddmipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int mcnt, int nzcnt, @Pinned @In @Transient int[] beg, @Pinned @In @Transient int[] varindices, @Pinned @In @Transient double[] values, @Pinned @In @Transient int[] effortlevel, Pointer mipstartname);

    /**
     * int CPXaddsolnpooldivfilter (CPXCENVptr env, CPXLPptr lp, double lower_bound, double upper_bound, int nzcnt, int const *ind, double const *weight, double const *refval, char const *lname_str);
     */
    int CPXaddsolnpooldivfilter(@Pinned @In Pointer env, @Pinned @In Pointer lp, double lower_bound, double upper_bound, int nzcnt, @Pinned @In @Transient int[] ind, @Pinned @In @Transient double[] weight, @Pinned @In @Transient double[] refval, @Pinned @In @Transient String lname_str);

    /**
     * int CPXaddsolnpoolrngfilter (CPXCENVptr env, CPXLPptr lp, double lb, double ub, int nzcnt, int const *ind, double const *val, char const *lname_str);
     */
    int CPXaddsolnpoolrngfilter(@Pinned @In Pointer env, @Pinned @In Pointer lp, double lb, double ub, int nzcnt, @Pinned @In @Transient int[] ind, @Pinned @In @Transient double[] val, @Pinned @In @Transient String lname_str);

    /**
     * int CPXaddsos (CPXCENVptr env, CPXLPptr lp, int numsos, int numsosnz, char const *sostype, int const *sosbeg, int const *sosind, double const *soswt, char **sosname);
     */
    int CPXaddsos(@Pinned @In Pointer env, @Pinned @In Pointer lp, int numsos, int numsosnz, @Pinned @In @Transient String sostype, @Pinned @In @Transient int[] sosbeg, @Pinned @In @Transient int[] sosind, @Pinned @In @Transient double[] soswt, Pointer sosname);

    /**
     * int CPXaddusercuts (CPXCENVptr env, CPXLPptr lp, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, char **rowname);
     */
    int CPXaddusercuts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, Pointer rowname);

    /**
     * int CPXbendersopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXbendersopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXbranchcallbackbranchasCPLEX (CPXCENVptr env, void *cbdata, int wherefrom, int num, void *userhandle, int *seqnum_p);
     */
    int CPXbranchcallbackbranchasCPLEX(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int num, Pointer userhandle, int[] seqnum_p);

    /**
     * int CPXbranchcallbackbranchbds (CPXCENVptr env, void *cbdata, int wherefrom, int cnt, int const *indices, char const *lu, double const *bd, double nodeest, void *userhandle, int *seqnum_p);
     */
    int CPXbranchcallbackbranchbds(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient String lu, @Pinned @In @Transient double[] bd, double nodeest, Pointer userhandle, int[] seqnum_p);

    /**
     * int CPXbranchcallbackbranchconstraints (CPXCENVptr env, void *cbdata, int wherefrom, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, double nodeest, void *userhandle, int *seqnum_p);
     */
    int CPXbranchcallbackbranchconstraints(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, double nodeest, Pointer userhandle, int[] seqnum_p);

    /**
     * int CPXbranchcallbackbranchgeneral (CPXCENVptr env, void *cbdata, int wherefrom, int varcnt, int const *varind, char const *varlu, double const *varbd, int rcnt, int nzcnt, double const *rhs, char const *sense, int const *rmatbeg, int const *rmatind, double const *rmatval, double nodeest, void *userhandle, int *seqnum_p);
     */
    int CPXbranchcallbackbranchgeneral(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int varcnt, @Pinned @In @Transient int[] varind, @Pinned @In @Transient String varlu, @Pinned @In @Transient double[] varbd, int rcnt, int nzcnt, @Pinned @In @Transient double[] rhs, @Pinned @In @Transient String sense, @Pinned @In @Transient int[] rmatbeg, @Pinned @In @Transient int[] rmatind, @Pinned @In @Transient double[] rmatval, double nodeest, Pointer userhandle, int[] seqnum_p);

    /**
     * int CPXcallbackgetgloballb (CPXCALLBACKCONTEXTptr context, double *lb, int begin, int end);
     */
    //int CPXcallbackgetgloballb(CPXCALLBACKCONTEXTptr context, double[] lb, int begin, int end);

    /**
     * int CPXcallbackgetglobalub (CPXCALLBACKCONTEXTptr context, double *ub, int begin, int end);
     */
    //int CPXcallbackgetglobalub(CPXCALLBACKCONTEXTptr context, double[] ub, int begin, int end);

    /**
     * int CPXcallbackgetlocallb (CPXCALLBACKCONTEXTptr context, double *lb, int begin, int end);
     */
    //int CPXcallbackgetlocallb(CPXCALLBACKCONTEXTptr context, double[] lb, int begin, int end);

    /**
     * int CPXcallbackgetlocalub (CPXCALLBACKCONTEXTptr context, double *ub, int begin, int end);
     */
    //int CPXcallbackgetlocalub(CPXCALLBACKCONTEXTptr context, double[] ub, int begin, int end);

    /**
     * int CPXcallbacksetnodeuserhandle (CPXCENVptr env, void *cbdata, int wherefrom, int nodeindex, void *userhandle, void  **olduserhandle_p);
     */
    int CPXcallbacksetnodeuserhandle(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int nodeindex, Pointer userhandle, PointerByReference olduserhandle_p);

    /**
     * int CPXcallbacksetuserhandle (CPXCENVptr env, void *cbdata, int wherefrom, void *userhandle, void  **olduserhandle_p);
     */
    int CPXcallbacksetuserhandle(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, Pointer userhandle, PointerByReference olduserhandle_p);

    /**
     * int CPXchgctype (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, char const *xctype);
     */
    int CPXchgctype(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient String xctype);

    /**
     * int CPXchgmipstarts (CPXCENVptr env, CPXLPptr lp, int mcnt, int const *mipstartindices, int nzcnt, int const *beg, int const *varindices, double const *values, int const *effortlevel);
     */
    int CPXchgmipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int mcnt, @Pinned @In @Transient int[] mipstartindices, int nzcnt, @Pinned @In @Transient int[] beg, @Pinned @In @Transient int[] varindices, @Pinned @In @Transient double[] values, @Pinned @In @Transient int[] effortlevel);

    /**
     * int CPXcopyctype (CPXCENVptr env, CPXLPptr lp, char const *xctype);
     */
    int CPXcopyctype(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String xctype);

    /**
     * int CPXcopyorder (CPXCENVptr env, CPXLPptr lp, int cnt, int const *indices, int const *priority, int const *direction);
     */
    int CPXcopyorder(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient int[] priority, @Pinned @In @Transient int[] direction);

    /**
     * int CPXcopysos (CPXCENVptr env, CPXLPptr lp, int numsos, int numsosnz, char const *sostype, int const *sosbeg, int const *sosind, double const *soswt, char **sosname);
     */
    int CPXcopysos(@Pinned @In Pointer env, @Pinned @In Pointer lp, int numsos, int numsosnz, @Pinned @In @Transient String sostype, @Pinned @In @Transient int[] sosbeg, @Pinned @In @Transient int[] sosind, @Pinned @In @Transient double[] soswt, Pointer sosname);

    /**
     * int CPXcutcallbackadd (CPXCENVptr env, void *cbdata, int wherefrom, int nzcnt, double rhs, int sense, int const *cutind, double const *cutval, int purgeable);
     */
    int CPXcutcallbackadd(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int nzcnt, double rhs, int sense, @Pinned @In @Transient int[] cutind, @Pinned @In @Transient double[] cutval, int purgeable);

    /**
     * int CPXcutcallbackaddlocal (CPXCENVptr env, void *cbdata, int wherefrom, int nzcnt, double rhs, int sense, int const *cutind, double const *cutval);
     */
    int CPXcutcallbackaddlocal(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int nzcnt, double rhs, int sense, @Pinned @In @Transient int[] cutind, @Pinned @In @Transient double[] cutval);

    /**
     * int CPXdelindconstrs (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelindconstrs(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelmipstarts (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelmipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelsetmipstarts (CPXCENVptr env, CPXLPptr lp, int *delstat);
     */
    int CPXdelsetmipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delstat);

    /**
     * int CPXdelsetsolnpoolfilters (CPXCENVptr env, CPXLPptr lp, int *delstat);
     */
    int CPXdelsetsolnpoolfilters(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delstat);

    /**
     * int CPXdelsetsolnpoolsolns (CPXCENVptr env, CPXLPptr lp, int *delstat);
     */
    int CPXdelsetsolnpoolsolns(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delstat);

    /**
     * int CPXdelsetsos (CPXCENVptr env, CPXLPptr lp, int *delset);
     */
    int CPXdelsetsos(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] delset);

    /**
     * int CPXdelsolnpoolfilters (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelsolnpoolfilters(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelsolnpoolsolns (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelsolnpoolsolns(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXdelsos (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelsos(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXfltwrite (CPXCENVptr env, CPXCLPptr lp, char const *filename_str);
     */
    int CPXfltwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXfreelazyconstraints (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXfreelazyconstraints(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXfreeusercuts (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXfreeusercuts(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetbestobjval (CPXCENVptr env, CPXCLPptr lp, double *objval_p);
     */
    int CPXgetbestobjval(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Out DoubleByReference objval_p);

    /**
     * int CPXgetbranchcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **branchcallback_p)(CALLBACK_BRANCH_ARGS), void  **cbhandle_p);
     */
    //int CPXgetbranchcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**branchcallback_p)(CALLBACK_BRANCH_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetbranchnosolncallbackfunc (CPXCENVptr env, int(CPXPUBLIC **branchnosolncallback_p)(CALLBACK_BRANCH_ARGS), void  **cbhandle_p);
     */
    //int CPXgetbranchnosolncallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**branchnosolncallback_p)(CALLBACK_BRANCH_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetcallbackbranchconstraints (CPXCENVptr env, void *cbdata, int wherefrom, int which, int *cuts_p, int *nzcnt_p, double *rhs, char *sense, int *rmatbeg, int *rmatind, double *rmatval, int rmatsz, int *surplus_p);
     */
    int CPXgetcallbackbranchconstraints(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int which, int[] cuts_p, int[] nzcnt_p, double[] rhs, String sense, int[] rmatbeg, int[] rmatind, double[] rmatval, int rmatsz, int[] surplus_p);

    /**
     * int CPXgetcallbackctype (CPXCENVptr env, void *cbdata, int wherefrom, char *xctype, int begin, int end);
     */
    int CPXgetcallbackctype(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, String xctype, int begin, int end);

    /**
     * int CPXgetcallbackgloballb (CPXCENVptr env, void *cbdata, int wherefrom, double *lb, int begin, int end);
     */
    int CPXgetcallbackgloballb(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] lb, int begin, int end);

    /**
     * int CPXgetcallbackglobalub (CPXCENVptr env, void *cbdata, int wherefrom, double *ub, int begin, int end);
     */
    int CPXgetcallbackglobalub(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] ub, int begin, int end);

    /**
     * int CPXgetcallbackincumbent (CPXCENVptr env, void *cbdata, int wherefrom, double *x, int begin, int end);
     */
    int CPXgetcallbackincumbent(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] x, int begin, int end);

    /**
     * int CPXgetcallbackindicatorinfo (CPXCENVptr env, void *cbdata, int wherefrom, int iindex, int whichinfo, void *result_p);
     */
    int CPXgetcallbackindicatorinfo(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int iindex, int whichinfo, Pointer result_p);

    /**
     * int CPXgetcallbacklp (CPXCENVptr env, void *cbdata, int wherefrom, CPXCLPptr *lp_p);
     */
    int CPXgetcallbacklp(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, @Out PointerByReference lp_p);

    /**
     * int CPXgetcallbacknodeinfo (CPXCENVptr env, void *cbdata, int wherefrom, int nodeindex, int whichinfo, void *result_p);
     */
    int CPXgetcallbacknodeinfo(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int nodeindex, int whichinfo, Pointer result_p);

    /**
     * int CPXgetcallbacknodeintfeas (CPXCENVptr env, void *cbdata, int wherefrom, int *feas, int begin, int end);
     */
    int CPXgetcallbacknodeintfeas(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int[] feas, int begin, int end);

    /**
     * int CPXgetcallbacknodelb (CPXCENVptr env, void *cbdata, int wherefrom, double *lb, int begin, int end);
     */
    int CPXgetcallbacknodelb(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] lb, int begin, int end);

    /**
     * int CPXgetcallbacknodelp (CPXCENVptr env, void *cbdata, int wherefrom, CPXLPptr *nodelp_p);
     */
    int CPXgetcallbacknodelp(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, @Out PointerByReference nodelp_p);

    /**
     * int CPXgetcallbacknodeobjval (CPXCENVptr env, void *cbdata, int wherefrom, double *objval_p);
     */
    int CPXgetcallbacknodeobjval(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] objval_p);

    /**
     * int CPXgetcallbacknodestat (CPXCENVptr env, void *cbdata, int wherefrom, int *nodestat_p);
     */
    int CPXgetcallbacknodestat(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int[] nodestat_p);

    /**
     * int CPXgetcallbacknodeub (CPXCENVptr env, void *cbdata, int wherefrom, double *ub, int begin, int end);
     */
    int CPXgetcallbacknodeub(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] ub, int begin, int end);

    /**
     * int CPXgetcallbacknodex (CPXCENVptr env, void *cbdata, int wherefrom, double *x, int begin, int end);
     */
    int CPXgetcallbacknodex(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] x, int begin, int end);

    /**
     * int CPXgetcallbackorder (CPXCENVptr env, void *cbdata, int wherefrom, int *priority, int *direction, int begin, int end);
     */
    int CPXgetcallbackorder(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int[] priority, int[] direction, int begin, int end);

    /**
     * int CPXgetcallbackpseudocosts (CPXCENVptr env, void *cbdata, int wherefrom, double *uppc, double *downpc, int begin, int end);
     */
    int CPXgetcallbackpseudocosts(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, double[] uppc, double[] downpc, int begin, int end);

    /**
     * int CPXgetcallbackseqinfo (CPXCENVptr env, void *cbdata, int wherefrom, int seqid, int whichinfo, void *result_p);
     */
    int CPXgetcallbackseqinfo(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int seqid, int whichinfo, Pointer result_p);

    /**
     * int CPXgetcallbacksosinfo (CPXCENVptr env, void *cbdata, int wherefrom, int sosindex, int member, int whichinfo, void *result_p);
     */
    int CPXgetcallbacksosinfo(@Pinned @In Pointer env, Pointer cbdata, int wherefrom, int sosindex, int member, int whichinfo, Pointer result_p);

    /**
     * int CPXgetctype (CPXCENVptr env, CPXCLPptr lp, char *xctype, int begin, int end);
     */
    int CPXgetctype(@Pinned @In Pointer env, @Pinned @In Pointer lp, String xctype, int begin, int end);

    /**
     * int CPXgetcutoff (CPXCENVptr env, CPXCLPptr lp, double *cutoff_p);
     */
    int CPXgetcutoff(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] cutoff_p);

    /**
     * int CPXgetdeletenodecallbackfunc (CPXCENVptr env, void(CPXPUBLIC **deletecallback_p)(CALLBACK_DELETENODE_ARGS), void  **cbhandle_p);
     */
    //int CPXgetdeletenodecallbackfunc(@Pinned @In Pointer env, void(CPXPUBLIC**deletecallback_p)(CALLBACK_DELETENODE_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetheuristiccallbackfunc (CPXCENVptr env, int(CPXPUBLIC **heuristiccallback_p)(CALLBACK_HEURISTIC_ARGS), void  **cbhandle_p);
     */
    //int CPXgetheuristiccallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**heuristiccallback_p)(CALLBACK_HEURISTIC_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetincumbentcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **incumbentcallback_p)(CALLBACK_INCUMBENT_ARGS), void  **cbhandle_p);
     */
    //int CPXgetincumbentcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**incumbentcallback_p)(CALLBACK_INCUMBENT_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetindconstr (CPXCENVptr env, CPXCLPptr lp, int *indvar_p, int *complemented_p, int *nzcnt_p, double *rhs_p, char *sense_p, int *linind, double *linval, int space, int *surplus_p, int which);
     */
    int CPXgetindconstr(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] indvar_p, int[] complemented_p, int[] nzcnt_p, double[] rhs_p, String sense_p, int[] linind, double[] linval, int space, int[] surplus_p, int which);

    /**
     * int CPXgetindconstraints (CPXCENVptr env, CPXCLPptr lp, int *type, int *indvar, int *complemented, int *nzcnt_p, double *rhs, char *sense, int *linbeg, int *linind, double *linval, int linspace, int *surplus_p, int begin, int end);
     */
    int CPXgetindconstraints(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] type, int[] indvar, int[] complemented, int[] nzcnt_p, double[] rhs, String sense, int[] linbeg, int[] linind, double[] linval, int linspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetindconstrindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetindconstrindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetindconstrinfeas (CPXCENVptr env, CPXCLPptr lp, double const *x, double *infeasout, int begin, int end);
     */
    int CPXgetindconstrinfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] infeasout, int begin, int end);

    /**
     * int CPXgetindconstrname (CPXCENVptr env, CPXCLPptr lp, char *buf_str, int bufspace, int *surplus_p, int which);
     */
    int CPXgetindconstrname(@Pinned @In Pointer env, @Pinned @In Pointer lp, String buf_str, int bufspace, int[] surplus_p, int which);

    /**
     * int CPXgetindconstrslack (CPXCENVptr env, CPXCLPptr lp, double *indslack, int begin, int end);
     */
    int CPXgetindconstrslack(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] indslack, int begin, int end);

    /**
     * int CPXgetinfocallbackfunc (CPXCENVptr env, int(CPXPUBLIC **callback_p)(CPXCENVptr, void *, int, void *), void  **cbhandle_p);
     */
    //int CPXgetinfocallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**callback_p)(@Pinned @In Pointer,Pointer ,int,Pointer ),PointerByReference cbhandle_p);

    /**
     * int CPXgetlazyconstraintcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **cutcallback_p)(CALLBACK_CUT_ARGS), void  **cbhandle_p);
     */
    //int CPXgetlazyconstraintcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**cutcallback_p)(CALLBACK_CUT_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetmipcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **callback_p)(CPXCENVptr, void *, int, void *), void  **cbhandle_p);
     */
    //int CPXgetmipcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**callback_p)(@Pinned @In Pointer,Pointer ,int,Pointer ),PointerByReference cbhandle_p);

    /**
     * int CPXgetmipitcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetmipitcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetmiprelgap (CPXCENVptr env, CPXCLPptr lp, double *gap_p);
     */
    int CPXgetmiprelgap(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] gap_p);

    /**
     * int CPXgetmipstartindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetmipstartindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetmipstartname (CPXCENVptr env, CPXCLPptr lp, char  **name, char *store, int storesz, int *surplus_p, int begin, int end);
     */
    int CPXgetmipstartname(@Pinned @In Pointer env, @Pinned @In Pointer lp, Pointer name, String store, int storesz, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetmipstarts (CPXCENVptr env, CPXCLPptr lp, int *nzcnt_p, int *beg, int *varindices, double *values, int *effortlevel, int startspace, int *surplus_p, int begin, int end);
     */
    int CPXgetmipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] nzcnt_p, int[] beg, int[] varindices, double[] values, int[] effortlevel, int startspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetnodecallbackfunc (CPXCENVptr env, int(CPXPUBLIC **nodecallback_p)(CALLBACK_NODE_ARGS), void  **cbhandle_p);
     */
    //int CPXgetnodecallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**nodecallback_p)(CALLBACK_NODE_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetnodecnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnodecnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnodeint (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnodeint(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnodeleftcnt (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnodeleftcnt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumbin (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumbin(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumcuts (CPXCENVptr env, CPXCLPptr lp, int cuttype, int *num_p);
     */
    int CPXgetnumcuts(@Pinned @In Pointer env, @Pinned @In Pointer lp, int cuttype, int[] num_p);

    /**
     * int CPXgetnumindconstrs (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumindconstrs(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumint (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumint(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumlazyconstraints (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumlazyconstraints(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnummipstarts (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnummipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumsemicont (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumsemicont(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumsemiint (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumsemiint(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumsos (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumsos(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumusercuts (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumusercuts(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetorder (CPXCENVptr env, CPXCLPptr lp, int *cnt_p, int *indices, int *priority, int *direction, int ordspace, int *surplus_p);
     */
    int CPXgetorder(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] cnt_p, int[] indices, int[] priority, int[] direction, int ordspace, int[] surplus_p);

    /**
     * int CPXgetsolnpooldivfilter (CPXCENVptr env, CPXCLPptr lp, double *lower_cutoff_p, double *upper_cutoff_p, int *nzcnt_p, int *ind, double *val, double *refval, int space, int *surplus_p, int which);
     */
    int CPXgetsolnpooldivfilter(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] lower_cutoff_p, double[] upper_cutoff_p, int[] nzcnt_p, int[] ind, double[] val, double[] refval, int space, int[] surplus_p, int which);

    /**
     * int CPXgetsolnpoolfilterindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetsolnpoolfilterindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetsolnpoolfiltername (CPXCENVptr env, CPXCLPptr lp, char *buf_str, int bufspace, int *surplus_p, int which);
     */
    int CPXgetsolnpoolfiltername(@Pinned @In Pointer env, @Pinned @In Pointer lp, String buf_str, int bufspace, int[] surplus_p, int which);

    /**
     * int CPXgetsolnpoolfiltertype (CPXCENVptr env, CPXCLPptr lp, int *ftype_p, int which);
     */
    int CPXgetsolnpoolfiltertype(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] ftype_p, int which);

    /**
     * int CPXgetsolnpoolmeanobjval (CPXCENVptr env, CPXCLPptr lp, double *meanobjval_p);
     */
    int CPXgetsolnpoolmeanobjval(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] meanobjval_p);

    /**
     * int CPXgetsolnpoolnumfilters (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsolnpoolnumfilters(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetsolnpoolnumreplaced (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsolnpoolnumreplaced(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetsolnpoolnumsolns (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsolnpoolnumsolns(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetsolnpoolobjval (CPXCENVptr env, CPXCLPptr lp, int soln, double *objval_p);
     */
    int CPXgetsolnpoolobjval(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, double[] objval_p);

    /**
     * int CPXgetsolnpoolqconstrslack (CPXCENVptr env, CPXCLPptr lp, int soln, double *qcslack, int begin, int end);
     */
    int CPXgetsolnpoolqconstrslack(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, double[] qcslack, int begin, int end);

    /**
     * int CPXgetsolnpoolrngfilter (CPXCENVptr env, CPXCLPptr lp, double *lb_p, double *ub_p, int *nzcnt_p, int *ind, double *val, int space, int *surplus_p, int which);
     */
    int CPXgetsolnpoolrngfilter(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] lb_p, double[] ub_p, int[] nzcnt_p, int[] ind, double[] val, int space, int[] surplus_p, int which);

    /**
     * int CPXgetsolnpoolslack (CPXCENVptr env, CPXCLPptr lp, int soln, double *slack, int begin, int end);
     */
    int CPXgetsolnpoolslack(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, double[] slack, int begin, int end);

    /**
     * int CPXgetsolnpoolsolnindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetsolnpoolsolnindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetsolnpoolsolnname (CPXCENVptr env, CPXCLPptr lp, char *store, int storesz, int *surplus_p, int which);
     */
    int CPXgetsolnpoolsolnname(@Pinned @In Pointer env, @Pinned @In Pointer lp, String store, int storesz, int[] surplus_p, int which);

    /**
     * int CPXgetsolnpoolx (CPXCENVptr env, CPXCLPptr lp, int soln, double *x, int begin, int end);
     */
    int CPXgetsolnpoolx(@Pinned @In Pointer env, @Pinned @In Pointer lp, int soln, double[] x, int begin, int end);

    /**
     * int CPXgetsolvecallbackfunc (CPXCENVptr env, int(CPXPUBLIC **solvecallback_p)(CALLBACK_SOLVE_ARGS), void  **cbhandle_p);
     */
    //int CPXgetsolvecallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**solvecallback_p)(CALLBACK_SOLVE_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXgetsos (CPXCENVptr env, CPXCLPptr lp, int *numsosnz_p, char *sostype, int *sosbeg, int *sosind, double *soswt, int sosspace, int *surplus_p, int begin, int end);
     */
    int CPXgetsos(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] numsosnz_p, String sostype, int[] sosbeg, int[] sosind, double[] soswt, int sosspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetsosindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetsosindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetsosinfeas (CPXCENVptr env, CPXCLPptr lp, double const *x, double *infeasout, int begin, int end);
     */
    int CPXgetsosinfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] infeasout, int begin, int end);

    /**
     * int CPXgetsosname (CPXCENVptr env, CPXCLPptr lp, char  **name, char *namestore, int storespace, int *surplus_p, int begin, int end);
     */
    int CPXgetsosname(@Pinned @In Pointer env, @Pinned @In Pointer lp, Pointer name, String namestore, int storespace, int[] surplus_p, int begin, int end);

    /**
     * int CPXgetsubmethod (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsubmethod(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetsubstat (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetsubstat(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetusercutcallbackfunc (CPXCENVptr env, int(CPXPUBLIC **cutcallback_p)(CALLBACK_CUT_ARGS), void  **cbhandle_p);
     */
    //int CPXgetusercutcallbackfunc(@Pinned @In Pointer env, int(CPXPUBLIC**cutcallback_p)(CALLBACK_CUT_ARGS),PointerByReference cbhandle_p);

    /**
     * int CPXindconstrslackfromx (CPXCENVptr env, CPXCLPptr lp, double const *x, double *indslack);
     */
    int CPXindconstrslackfromx(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] indslack);

    /**
     * int CPXmipopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXmipopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXordread (CPXCENVptr env, char const *filename_str, int numcols, char **colname, int *cnt_p, int *indices, int *priority, int *direction);
     */
    int CPXordread(@Pinned @In Pointer env, @Pinned @In @Transient String filename_str, int numcols, Pointer colname, int[] cnt_p, int[] indices, int[] priority, int[] direction);

    /**
     * int CPXordwrite (CPXCENVptr env, CPXCLPptr lp, char const *filename_str);
     */
    int CPXordwrite(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXpopulate (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXpopulate(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXreadcopymipstarts (CPXCENVptr env, CPXLPptr lp, char const *filename_str);
     */
    int CPXreadcopymipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXreadcopyorder (CPXCENVptr env, CPXLPptr lp, char const *filename_str);
     */
    int CPXreadcopyorder(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXreadcopysolnpoolfilters (CPXCENVptr env, CPXLPptr lp, char const *filename_str);
     */
    int CPXreadcopysolnpoolfilters(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str);

    /**
     * int CPXrefinemipstartconflict (CPXCENVptr env, CPXLPptr lp, int mipstartindex, int *confnumrows_p, int *confnumcols_p);
     */
    int CPXrefinemipstartconflict(@Pinned @In Pointer env, @Pinned @In Pointer lp, int mipstartindex, int[] confnumrows_p, int[] confnumcols_p);

    /**
     * int CPXrefinemipstartconflictext (CPXCENVptr env, CPXLPptr lp, int mipstartindex, int grpcnt, int concnt, double const *grppref, int const *grpbeg, int const *grpind, char const *grptype);
     */
    int CPXrefinemipstartconflictext(@Pinned @In Pointer env, @Pinned @In Pointer lp, int mipstartindex, int grpcnt, int concnt, @Pinned @In @Transient double[] grppref, @Pinned @In @Transient int[] grpbeg, @Pinned @In @Transient int[] grpind, @Pinned @In @Transient String grptype);

    /**
     * int CPXsetbranchcallbackfunc (CPXENVptr env, int(CPXPUBLIC *branchcallback)(CALLBACK_BRANCH_ARGS), void *cbhandle);
     */
    //int CPXsetbranchcallbackfunc(Pointer env, int(CPXPUBLIC*branchcallback)(CALLBACK_BRANCH_ARGS),Pointer cbhandle);

    /**
     * int CPXsetbranchnosolncallbackfunc (CPXENVptr env, int(CPXPUBLIC *branchnosolncallback)(CALLBACK_BRANCH_ARGS), void *cbhandle);
     */
    //int CPXsetbranchnosolncallbackfunc(Pointer env, int(CPXPUBLIC*branchnosolncallback)(CALLBACK_BRANCH_ARGS),Pointer cbhandle);

    /**
     * int CPXsetdeletenodecallbackfunc (CPXENVptr env, void(CPXPUBLIC *deletecallback)(CALLBACK_DELETENODE_ARGS), void *cbhandle);
     */
    //int CPXsetdeletenodecallbackfunc(Pointer env, void(CPXPUBLIC*deletecallback)(CALLBACK_DELETENODE_ARGS),Pointer cbhandle);

    /**
     * int CPXsetheuristiccallbackfunc (CPXENVptr env, int(CPXPUBLIC *heuristiccallback)(CALLBACK_HEURISTIC_ARGS), void *cbhandle);
     */
    //int CPXsetheuristiccallbackfunc(Pointer env, int(CPXPUBLIC*heuristiccallback)(CALLBACK_HEURISTIC_ARGS),Pointer cbhandle);

    /**
     * int CPXsetincumbentcallbackfunc (CPXENVptr env, int(CPXPUBLIC *incumbentcallback)(CALLBACK_INCUMBENT_ARGS), void *cbhandle);
     */
    //int CPXsetincumbentcallbackfunc(Pointer env, int(CPXPUBLIC*incumbentcallback)(CALLBACK_INCUMBENT_ARGS),Pointer cbhandle);

    /**
     * int CPXsetinfocallbackfunc (CPXENVptr env, int(CPXPUBLIC *callback)(CPXCENVptr, void *, int, void *), void *cbhandle);
     */
    //int CPXsetinfocallbackfunc(Pointer env, int(CPXPUBLIC*callback)(@Pinned @In Pointer,Pointer ,int,Pointer ),Pointer cbhandle);

    /**
     * int CPXsetlazyconstraintcallbackfunc (CPXENVptr env, int(CPXPUBLIC *lazyconcallback)(CALLBACK_CUT_ARGS), void *cbhandle);
     */
    //int CPXsetlazyconstraintcallbackfunc(Pointer env, int(CPXPUBLIC*lazyconcallback)(CALLBACK_CUT_ARGS),Pointer cbhandle);

    /**
     * int CPXsetmipcallbackfunc (CPXENVptr env, int(CPXPUBLIC *callback)(CPXCENVptr, void *, int, void *), void *cbhandle);
     */
    //int CPXsetmipcallbackfunc(Pointer env, int(CPXPUBLIC*callback)(@Pinned @In Pointer,Pointer ,int,Pointer ),Pointer cbhandle);

    /**
     * int CPXsetnodecallbackfunc (CPXENVptr env, int(CPXPUBLIC *nodecallback)(CALLBACK_NODE_ARGS), void *cbhandle);
     */
    //int CPXsetnodecallbackfunc(Pointer env, int(CPXPUBLIC*nodecallback)(CALLBACK_NODE_ARGS),Pointer cbhandle);

    /**
     * int CPXsetsolvecallbackfunc (CPXENVptr env, int(CPXPUBLIC *solvecallback)(CALLBACK_SOLVE_ARGS), void *cbhandle);
     */
    //int CPXsetsolvecallbackfunc(Pointer env, int(CPXPUBLIC*solvecallback)(CALLBACK_SOLVE_ARGS),Pointer cbhandle);

    /**
     * int CPXsetusercutcallbackfunc (CPXENVptr env, int(CPXPUBLIC *cutcallback)(CALLBACK_CUT_ARGS), void *cbhandle);
     */
    //int CPXsetusercutcallbackfunc(Pointer env, int(CPXPUBLIC*cutcallback)(CALLBACK_CUT_ARGS),Pointer cbhandle);

    /**
     * int CPXwritemipstarts (CPXCENVptr env, CPXCLPptr lp, char const *filename_str, int begin, int end);
     */
    int CPXwritemipstarts(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String filename_str, int begin, int end);

    /**
     * int CPXaddindconstr (CPXCENVptr env, CPXLPptr lp, int indvar, int complemented, int nzcnt, double rhs, int sense, int const *linind, double const *linval, char const *indname_str);
     */
    int CPXaddindconstr(@Pinned @In Pointer env, @Pinned @In Pointer lp, int indvar, int complemented, int nzcnt, double rhs, int sense, @Pinned @In @Transient int[] linind, @Pinned @In @Transient double[] linval, @Pinned @In @Transient String indname_str);

    /**
     * int CPXNETaddarcs (CPXCENVptr env, CPXNETptr net, int narcs, int const *fromnode, int const *tonode, double const *low, double const *up, double const *obj, char **anames);
     */
    //int CPXNETaddarcs(@Pinned @In Pointer env, CPXNETptr net, int narcs, @Pinned @In @Transient int[] fromnode, @Pinned @In @Transient int[] tonode, @Pinned @In @Transient double[] low, @Pinned @In @Transient double[] up, @Pinned @In @Transient double[] obj, Pointer anames);

    /**
     * int CPXNETaddnodes (CPXCENVptr env, CPXNETptr net, int nnodes, double const *supply, char **name);
     */
    //int CPXNETaddnodes(@Pinned @In Pointer env, CPXNETptr net, int nnodes, @Pinned @In @Transient double[] supply, Pointer name);

    /**
     * int CPXNETbasewrite (CPXCENVptr env, CPXCNETptr net, char const *filename_str);
     */
    //int CPXNETbasewrite(@Pinned @In Pointer env, CPXCNETptr net, @Pinned @In @Transient String filename_str);

    /**
     * int CPXNETchgarcname (CPXCENVptr env, CPXNETptr net, int cnt, int const *indices, char **newname);
     */
    //int CPXNETchgarcname(@Pinned @In Pointer env, CPXNETptr net, int cnt, @Pinned @In @Transient int[] indices, Pointer newname);

    /**
     * int CPXNETchgarcnodes (CPXCENVptr env, CPXNETptr net, int cnt, int const *indices, int const *fromnode, int const *tonode);
     */
    //int CPXNETchgarcnodes(@Pinned @In Pointer env, CPXNETptr net, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient int[] fromnode, @Pinned @In @Transient int[] tonode);

    /**
     * int CPXNETchgbds (CPXCENVptr env, CPXNETptr net, int cnt, int const *indices, char const *lu, double const *bd);
     */
    //int CPXNETchgbds(@Pinned @In Pointer env, CPXNETptr net, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient String lu, @Pinned @In @Transient double[] bd);

    /**
     * int CPXNETchgname (CPXCENVptr env, CPXNETptr net, int key, int vindex, char const *name_str);
     */
    //int CPXNETchgname(@Pinned @In Pointer env, CPXNETptr net, int key, int vindex, @Pinned @In @Transient String name_str);

    /**
     * int CPXNETchgnodename (CPXCENVptr env, CPXNETptr net, int cnt, int const *indices, char **newname);
     */
    //int CPXNETchgnodename(@Pinned @In Pointer env, CPXNETptr net, int cnt, @Pinned @In @Transient int[] indices, Pointer newname);

    /**
     * int CPXNETchgobj (CPXCENVptr env, CPXNETptr net, int cnt, int const *indices, double const *obj);
     */
    //int CPXNETchgobj(@Pinned @In Pointer env, CPXNETptr net, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] obj);

    /**
     * int CPXNETchgobjsen (CPXCENVptr env, CPXNETptr net, int maxormin);
     */
    //int CPXNETchgobjsen(@Pinned @In Pointer env, CPXNETptr net, int maxormin);

    /**
     * int CPXNETchgsupply (CPXCENVptr env, CPXNETptr net, int cnt, int const *indices, double const *supply);
     */
    //int CPXNETchgsupply(@Pinned @In Pointer env, CPXNETptr net, int cnt, @Pinned @In @Transient int[] indices, @Pinned @In @Transient double[] supply);

    /**
     * int CPXNETcopybase (CPXCENVptr env, CPXNETptr net, int const *astat, int const *nstat);
     */
    //int CPXNETcopybase(@Pinned @In Pointer env, CPXNETptr net, @Pinned @In @Transient int[] astat, @Pinned @In @Transient int[] nstat);

    /**
     * int CPXNETcopynet (CPXCENVptr env, CPXNETptr net, int objsen, int nnodes, double const *supply, char **nnames, int narcs, int const *fromnode, int const *tonode, double const *low, double const *up, double const *obj, char **anames);
     */
    //int CPXNETcopynet(@Pinned @In Pointer env, CPXNETptr net, int objsen, int nnodes, @Pinned @In @Transient double[] supply, Pointer nnames, int narcs, @Pinned @In @Transient int[] fromnode, @Pinned @In @Transient int[] tonode, @Pinned @In @Transient double[] low, @Pinned @In @Transient double[] up, @Pinned @In @Transient double[] obj, Pointer anames);

    /**
     * CPXNETptr CPXNETcreateprob (CPXENVptr env, int *status_p, char const *name_str);
     */
    //CPXNETptr CPXNETcreateprob(Pointer env, @Out IntByReference status_p, @Pinned @In @Transient String name_str);

    /**
     * int CPXNETdelarcs (CPXCENVptr env, CPXNETptr net, int begin, int end);
     */
    //int CPXNETdelarcs(@Pinned @In Pointer env, CPXNETptr net, int begin, int end);

    /**
     * int CPXNETdelnodes (CPXCENVptr env, CPXNETptr net, int begin, int end);
     */
    //int CPXNETdelnodes(@Pinned @In Pointer env, CPXNETptr net, int begin, int end);

    /**
     * int CPXNETdelset (CPXCENVptr env, CPXNETptr net, int *whichnodes, int *whicharcs);
     */
    //int CPXNETdelset(@Pinned @In Pointer env, CPXNETptr net, int[] whichnodes, int[] whicharcs);

    /**
     * int CPXNETfreeprob (CPXENVptr env, CPXNETptr *net_p);
     */
    //int CPXNETfreeprob(Pointer env, CPXNETptr *net_p);

    /**
     * int CPXNETgetarcindex (CPXCENVptr env, CPXCNETptr net, char const *lname_str, int *index_p);
     */
    //int CPXNETgetarcindex(@Pinned @In Pointer env, CPXCNETptr net, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXNETgetarcname (CPXCENVptr env, CPXCNETptr net, char  **nnames, char *namestore, int namespc, int *surplus_p, int begin, int end);
     */
    //int CPXNETgetarcname(@Pinned @In Pointer env, CPXCNETptr net, Pointer nnames, String namestore, int namespc, int[] surplus_p, int begin, int end);

    /**
     * int CPXNETgetarcnodes (CPXCENVptr env, CPXCNETptr net, int *fromnode, int *tonode, int begin, int end);
     */
    //int CPXNETgetarcnodes(@Pinned @In Pointer env, CPXCNETptr net, int[] fromnode, int[] tonode, int begin, int end);

    /**
     * int CPXNETgetbase (CPXCENVptr env, CPXCNETptr net, int *astat, int *nstat);
     */
    //int CPXNETgetbase(@Pinned @In Pointer env, CPXCNETptr net, int[] astat, int[] nstat);

    /**
     * int CPXNETgetdj (CPXCENVptr env, CPXCNETptr net, double *dj, int begin, int end);
     */
    //int CPXNETgetdj(@Pinned @In Pointer env, CPXCNETptr net, double[] dj, int begin, int end);

    /**
     * int CPXNETgetitcnt (CPXCENVptr env, CPXCNETptr net);
     */
    //int CPXNETgetitcnt(@Pinned @In Pointer env, CPXCNETptr net);

    /**
     * int CPXNETgetlb (CPXCENVptr env, CPXCNETptr net, double *low, int begin, int end);
     */
    //int CPXNETgetlb(@Pinned @In Pointer env, CPXCNETptr net, double[] low, int begin, int end);

    /**
     * int CPXNETgetnodearcs (CPXCENVptr env, CPXCNETptr net, int *arccnt_p, int *arcbeg, int *arc, int arcspace, int *surplus_p, int begin, int end);
     */
    //int CPXNETgetnodearcs(@Pinned @In Pointer env, CPXCNETptr net, int[] arccnt_p, int[] arcbeg, int[] arc, int arcspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXNETgetnodeindex (CPXCENVptr env, CPXCNETptr net, char const *lname_str, int *index_p);
     */
    //int CPXNETgetnodeindex(@Pinned @In Pointer env, CPXCNETptr net, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXNETgetnodename (CPXCENVptr env, CPXCNETptr net, char  **nnames, char *namestore, int namespc, int *surplus_p, int begin, int end);
     */
    //int CPXNETgetnodename(@Pinned @In Pointer env, CPXCNETptr net, Pointer nnames, String namestore, int namespc, int[] surplus_p, int begin, int end);

    /**
     * int CPXNETgetnumarcs (CPXCENVptr env, CPXCNETptr net);
     */
    //int CPXNETgetnumarcs(@Pinned @In Pointer env, CPXCNETptr net);

    /**
     * int CPXNETgetnumnodes (CPXCENVptr env, CPXCNETptr net);
     */
    //int CPXNETgetnumnodes(@Pinned @In Pointer env, CPXCNETptr net);

    /**
     * int CPXNETgetobj (CPXCENVptr env, CPXCNETptr net, double *obj, int begin, int end);
     */
    //int CPXNETgetobj(@Pinned @In Pointer env, CPXCNETptr net, double[] obj, int begin, int end);

    /**
     * int CPXNETgetobjsen (CPXCENVptr env, CPXCNETptr net);
     */
    //int CPXNETgetobjsen(@Pinned @In Pointer env, CPXCNETptr net);

    /**
     * int CPXNETgetobjval (CPXCENVptr env, CPXCNETptr net, double *objval_p);
     */
    //int CPXNETgetobjval(@Pinned @In Pointer env, CPXCNETptr net, double[] objval_p);

    /**
     * int CPXNETgetphase1cnt (CPXCENVptr env, CPXCNETptr net);
     */
    //int CPXNETgetphase1cnt(@Pinned @In Pointer env, CPXCNETptr net);

    /**
     * int CPXNETgetpi (CPXCENVptr env, CPXCNETptr net, double *pi, int begin, int end);
     */
    //int CPXNETgetpi(@Pinned @In Pointer env, CPXCNETptr net, double[] pi, int begin, int end);

    /**
     * int CPXNETgetprobname (CPXCENVptr env, CPXCNETptr net, char *buf_str, int bufspace, int *surplus_p);
     */
    //int CPXNETgetprobname(@Pinned @In Pointer env, CPXCNETptr net, String buf_str, int bufspace, int[] surplus_p);

    /**
     * int CPXNETgetslack (CPXCENVptr env, CPXCNETptr net, double *slack, int begin, int end);
     */
    //int CPXNETgetslack(@Pinned @In Pointer env, CPXCNETptr net, double[] slack, int begin, int end);

    /**
     * int CPXNETgetstat (CPXCENVptr env, CPXCNETptr net);
     */
    //int CPXNETgetstat(@Pinned @In Pointer env, CPXCNETptr net);

    /**
     * int CPXNETgetsupply (CPXCENVptr env, CPXCNETptr net, double *supply, int begin, int end);
     */
    //int CPXNETgetsupply(@Pinned @In Pointer env, CPXCNETptr net, double[] supply, int begin, int end);

    /**
     * int CPXNETgetub (CPXCENVptr env, CPXCNETptr net, double *up, int begin, int end);
     */
    //int CPXNETgetub(@Pinned @In Pointer env, CPXCNETptr net, double[] up, int begin, int end);

    /**
     * int CPXNETgetx (CPXCENVptr env, CPXCNETptr net, double *x, int begin, int end);
     */
    //int CPXNETgetx(@Pinned @In Pointer env, CPXCNETptr net, double[] x, int begin, int end);

    /**
     * int CPXNETprimopt (CPXCENVptr env, CPXNETptr net);
     */
    //int CPXNETprimopt(@Pinned @In Pointer env, CPXNETptr net);

    /**
     * int CPXNETreadcopybase (CPXCENVptr env, CPXNETptr net, char const *filename_str);
     */
    //int CPXNETreadcopybase(@Pinned @In Pointer env, CPXNETptr net, @Pinned @In @Transient String filename_str);

    /**
     * int CPXNETreadcopyprob (CPXCENVptr env, CPXNETptr net, char const *filename_str);
     */
    //int CPXNETreadcopyprob(@Pinned @In Pointer env, CPXNETptr net, @Pinned @In @Transient String filename_str);

    /**
     * int CPXNETsolninfo (CPXCENVptr env, CPXCNETptr net, int *pfeasind_p, int *dfeasind_p);
     */
    //int CPXNETsolninfo(@Pinned @In Pointer env, CPXCNETptr net, int[] pfeasind_p, int[] dfeasind_p);

    /**
     * int CPXNETsolution (CPXCENVptr env, CPXCNETptr net, int *netstat_p, double *objval_p, double *x, double *pi, double *slack, double *dj);
     */
    //int CPXNETsolution(@Pinned @In Pointer env, CPXCNETptr net, int[] netstat_p, double[] objval_p, double[] x, double[] pi, double[] slack, double[] dj);

    /**
     * int CPXNETwriteprob (CPXCENVptr env, CPXCNETptr net, char const *filename_str, char const *format_str);
     */
    //int CPXNETwriteprob(@Pinned @In Pointer env, CPXCNETptr net, @Pinned @In @Transient String filename_str, @Pinned @In @Transient String format_str);

    /**
     * int CPXchgqpcoef (CPXCENVptr env, CPXLPptr lp, int i, int j, double newvalue);
     */
    int CPXchgqpcoef(@Pinned @In Pointer env, @Pinned @In Pointer lp, int i, int j, double newvalue);

    /**
     * int CPXcopyqpsep (CPXCENVptr env, CPXLPptr lp, double const *qsepvec);
     */
    int CPXcopyqpsep(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] qsepvec);

    /**
     * int CPXcopyquad (CPXCENVptr env, CPXLPptr lp, int const *qmatbeg, int const *qmatcnt, int const *qmatind, double const *qmatval);
     */
    int CPXcopyquad(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient int[] qmatbeg, @Pinned @In @Transient int[] qmatcnt, @Pinned @In @Transient int[] qmatind, @Pinned @In @Transient double[] qmatval);

    /**
     * int CPXgetnumqpnz (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumqpnz(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetnumquad (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumquad(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetqpcoef (CPXCENVptr env, CPXCLPptr lp, int rownum, int colnum, double *coef_p);
     */
    int CPXgetqpcoef(@Pinned @In Pointer env, @Pinned @In Pointer lp, int rownum, int colnum, double[] coef_p);

    /**
     * int CPXgetquad (CPXCENVptr env, CPXCLPptr lp, int *nzcnt_p, int *qmatbeg, int *qmatind, double *qmatval, int qmatspace, int *surplus_p, int begin, int end);
     */
    int CPXgetquad(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] nzcnt_p, int[] qmatbeg, int[] qmatind, double[] qmatval, int qmatspace, int[] surplus_p, int begin, int end);

    /**
     * int CPXqpindefcertificate (CPXCENVptr env, CPXCLPptr lp, double *x);
     */
    int CPXqpindefcertificate(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] x);

    /**
     * int CPXqpopt (CPXCENVptr env, CPXLPptr lp);
     */
    int CPXqpopt(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXaddqconstr (CPXCENVptr env, CPXLPptr lp, int linnzcnt, int quadnzcnt, double rhs, int sense, int const *linind, double const *linval, int const *quadrow, int const *quadcol, double const *quadval, char const *lname_str);
     */
    int CPXaddqconstr(@Pinned @In Pointer env, @Pinned @In Pointer lp, int linnzcnt, int quadnzcnt, double rhs, int sense, @Pinned @In @Transient int[] linind, @Pinned @In @Transient double[] linval, @Pinned @In @Transient int[] quadrow, @Pinned @In @Transient int[] quadcol, @Pinned @In @Transient double[] quadval, @Pinned @In @Transient String lname_str);

    /**
     * int CPXdelqconstrs (CPXCENVptr env, CPXLPptr lp, int begin, int end);
     */
    int CPXdelqconstrs(@Pinned @In Pointer env, @Pinned @In Pointer lp, int begin, int end);

    /**
     * int CPXgetnumqconstrs (CPXCENVptr env, CPXCLPptr lp);
     */
    int CPXgetnumqconstrs(@Pinned @In Pointer env, @Pinned @In Pointer lp);

    /**
     * int CPXgetqconstr (CPXCENVptr env, CPXCLPptr lp, int *linnzcnt_p, int *quadnzcnt_p, double *rhs_p, char *sense_p, int *linind, double *linval, int linspace, int *linsurplus_p, int *quadrow, int *quadcol, double *quadval, int quadspace, int *quadsurplus_p, int which);
     */
    int CPXgetqconstr(@Pinned @In Pointer env, @Pinned @In Pointer lp, int[] linnzcnt_p, int[] quadnzcnt_p, double[] rhs_p, String sense_p, int[] linind, double[] linval, int linspace, int[] linsurplus_p, int[] quadrow, int[] quadcol, double[] quadval, int quadspace, int[] quadsurplus_p, int which);

    /**
     * int CPXgetqconstrdslack (CPXCENVptr env, CPXCLPptr lp, int qind, int *nz_p, int *ind, double *val, int space, int *surplus_p);
     */
    int CPXgetqconstrdslack(@Pinned @In Pointer env, @Pinned @In Pointer lp, int qind, int[] nz_p, int[] ind, double[] val, int space, int[] surplus_p);

    /**
     * int CPXgetqconstrindex (CPXCENVptr env, CPXCLPptr lp, char const *lname_str, int *index_p);
     */
    int CPXgetqconstrindex(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient String lname_str, int[] index_p);

    /**
     * int CPXgetqconstrinfeas (CPXCENVptr env, CPXCLPptr lp, double const *x, double *infeasout, int begin, int end);
     */
    int CPXgetqconstrinfeas(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] infeasout, int begin, int end);

    /**
     * int CPXgetqconstrname (CPXCENVptr env, CPXCLPptr lp, char *buf_str, int bufspace, int *surplus_p, int which);
     */
    int CPXgetqconstrname(@Pinned @In Pointer env, @Pinned @In Pointer lp, String buf_str, int bufspace, int[] surplus_p, int which);

    /**
     * int CPXgetqconstrslack (CPXCENVptr env, CPXCLPptr lp, double *qcslack, int begin, int end);
     */
    int CPXgetqconstrslack(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] qcslack, int begin, int end);

    /**
     * int CPXgetxqxax (CPXCENVptr env, CPXCLPptr lp, double *xqxax, int begin, int end);
     */
    int CPXgetxqxax(@Pinned @In Pointer env, @Pinned @In Pointer lp, double[] xqxax, int begin, int end);

    /**
     * int CPXqconstrslackfromx (CPXCENVptr env, CPXCLPptr lp, double const *x, double *qcslack);
     */
    int CPXqconstrslackfromx(@Pinned @In Pointer env, @Pinned @In Pointer lp, @Pinned @In @Transient double[] x, double[] qcslack);
}
