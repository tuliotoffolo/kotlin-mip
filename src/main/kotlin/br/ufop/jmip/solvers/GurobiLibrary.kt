package br.ufop.jmip.solvers

import jnr.ffi.*
import jnr.ffi.types.*
import jnr.ffi.annotations.Out
import jnr.ffi.annotations.Transient
import jnr.ffi.Struct.SignedLong
import jnr.ffi.Struct.time_t
import jnr.ffi.byref.ByReference
import jnr.ffi.byref.DoubleByReference
import jnr.ffi.byref.IntByReference
import jnr.ffi.byref.PointerByReference

interface GurobiLibrary {

    fun fflush(stream: Pointer?)

    // typedef struct _GRBmodel GRBmodel;
    // typedef struct _GRBenv GRBenv;

    // typedef int(*gurobi_callback)(GRBmodel *model, void *cbdata, int where, void *usrdata);

    // GRBenv *GRBgetenv(GRBmodel *model);
    fun GRBgetenv(model: Pointer): Pointer

    // int GRBloadenv(GRBenv **envP, const char *logfilename);
    fun GRBloadenv(envP: PointerByReference, logfilename: String?): Int

    // int GRBnewmodel(GRBenv *env, GRBmodel **modelP, const char *Pname, int numvars,  double *obj,
    //                 double *lb, double *ub, char *vtype, char **varnames);
    fun GRBnewmodel(env: Pointer, modelP: PointerByReference, Pname: String, numvars: Int,
                    obj: DoubleArray?, lb: DoubleArray?, ub: DoubleArray?, vtype: ByteArray?,
                    varnames: PointerByReference?): Int

    // void GRBfreeenv(GRBenv *env);
    fun GRBfreeenv(env: Pointer)

    // int GRBfreemodel(GRBmodel *model);
    fun GRBfreemodel(model: Pointer): Int

    // int GRBgetintattr(GRBmodel *model, const char *attrname, int *valueP);
    fun GRBgetintattr(model: Pointer, attrname: String, valueP: IntByReference): Int

    // int GRBsetintattr(GRBmodel *model, const char *attrname, int newvalue);
    fun GRBsetintattr(model: Pointer, attrname: String, newvalue: Int): Int

    // int GRBgetintattrelement(GRBmodel *model, const char *attrname, int element, int *valueP);
    fun GRBgetintattrelement(model: Pointer, attrname: String, element: Int, valueP: IntArray?): Int

    // int GRBsetintattrelement(GRBmodel *model, const char *attrname, int element, int newvalue);
    fun GRBsetintattrelement(model: Pointer, attrname: String, element: Int, newvalue: Int): Int

    // int GRBgetdblattr(GRBmodel *model, const char *attrname, double *valueP);
    fun GRBgetdblattr(model: Pointer, attrname: String, @Out valueP: DoubleByReference): Int

    // int GRBsetdblattr(GRBmodel *model, const char *attrname, double newvalue);
    fun GRBsetdblattr(model: Pointer, attrname: String, newvalue: Double): Int

    // int GRBgetcharattrarray(GRBmodel *model, const char *attrname, int first, int len,
    //                         char *values);
    fun GRBgetdblattrarray(model: Pointer, attrname: String, first: Int, len: Int,
                           values: Pointer?): Int

    // int GRBsetdblattrarray(GRBmodel *model, const char *attrname, int first, int len,
    //                        double *newvalues);
    fun GRBsetdblattrarray(model: Pointer, attrname: String, first: Int, len: Int,
                           newvalues: DoubleArray?): Int

    // int GRBsetdblattrlist(GRBmodel *model, const char *attrname, int len, int *ind,
    //                       double *newvalues);
    fun GRBsetdblattrlist(model: Pointer, attrname: String, len: Int, ind: IntArray?,
                          newvalues: DoubleArray?): Int

    // int GRBgetdblattrelement(GRBmodel *model, const char *attrname, int element, double *valueP);
    fun GRBgetdblattrelement(model: Pointer, attrname: String, element: Int, valueP: DoubleArray?): Int

    // int GRBsetdblattrelement(GRBmodel *model, const char *attrname, int element, double newvalue);
    fun GRBsetdblattrelement(model: Pointer, attrname: String, element: Int, newvalue: Double): Int

    // int GRBgetcharattrarray(GRBmodel *model, const char *attrname, int first, int len,
    //                         char *values);
    fun GRBgetcharattrarray(model: Pointer, attrname: String, first: Int, len: Int,
                            values: String): Int

    // int GRBsetcharattrarray(GRBmodel *model, const char *attrname, int first, int len,
    //                         char *newvalues);
    fun GRBsetcharattrarray(model: Pointer, attrname: String, first: Int, len: Int,
                            newvalues: String): Int

    // int GRBgetcharattrelement(GRBmodel *model, const char *attrname, int element, char *valueP);
    fun GRBgetcharattrelement(model: Pointer, attrname: String, element: Int, valueP: String): Int

    // int GRBsetcharattrelement(GRBmodel *model, const char *attrname, int element, char newvalue);
    fun GRBsetcharattrelement(model: Pointer, attrname: String, element: Int, newvalue: Byte): Int

    // int GRBgetstrattrelement(GRBmodel *model, const char *attrname, int element, char **valueP);
    // fun GRBgetstrattrelement(model: Pointer, attrname: String, element: Int, char **valueP): Int

    // int GRBgetstrattr (GRBmodel *model, const char *attrname, char **valueP);
    fun GRBgetstrattr(model: Pointer, attrname: String, valueP: PointerByReference): Int

    // int GRBsetstrattr (GRBmodel *model, const char *attrname, const char *newvalue);
    fun GRBsetstrattr(model: Pointer, attrname: String, newvalue: String): Int

    // int GRBgetintparam(GRBenv *env, const char *paramname, int *valueP);
    fun GRBgetintparam(env: Pointer, paramname: String, valueP: IntArray?): Int

    // int GRBsetintparam(GRBenv *env, const char *paramname, int value);
    fun GRBsetintparam(env: Pointer, paramname: String, value: Int): Int

    // int GRBgetdblparam(GRBenv *env, const char *paramname, double *valueP);
    fun GRBgetdblparam(env: Pointer, paramname: String, valueP: DoubleArray?): Int

    // int GRBsetdblparam(GRBenv *env, const char *paramname, double value);
    fun GRBsetdblparam(env: Pointer, paramname: String, value: Double): Int

    // int GRBsetobjectiven(GRBmodel *model, int index, int priority, double weight, double abstol,
    //                      double reltol, const char *name, double constant, int lnz, int *lind,
    //                      double *lval);
    fun GRBsetobjectiven(model: Pointer, index: Int, priority: Int, weight: Double, abstol: Double,
                         reltol: Double, name: String, constant: Double, lnz: Int, lind: IntArray?,
                         lval: DoubleArray?): Int

    // int GRBaddvar(GRBmodel *model, int numnz, int *vind, double *vval, double obj, double lb,
    //               double ub, char vtype, const char *varname);
    fun GRBaddvar(model: Pointer, numnz: Int, vind: Pointer?, vval: Pointer?, obj: Double,
                  lb: Double, ub: Double, vtype: Byte, varname: String): Int

    // int GRBaddconstr(GRBmodel *model, int numnz, int *cind, double *cval, char sense, double rhs,
    //                  const char *constrname);
    fun GRBaddconstr(model: Pointer, numnz: Int, cind: Pointer?, cval: Pointer?, sense: Byte,
                     rhs: Double, constrname: String): Int

    fun GRBaddconstr(model: Pointer, numnz: Int, cind: IntArray?, cval: DoubleArray?, sense: Byte,
                     rhs: Double, constrname: String): Int

    // int GRBaddsos(GRBmodel *model, int numsos, int nummembers, int *types, int *beg, int *ind,
    //               double *weight);
    fun GRBaddsos(model: Pointer, numsos: Int, nummembers: Int, types: IntArray?, beg: IntArray?,
                  ind: IntArray?, weight: DoubleArray?): Int

    // int GRBgetconstrs(GRBmodel *model, int *numnzP, int *cbeg, int *cind, double *cval,
    //                   int start, int len);
    fun GRBgetconstrs(model: Pointer, numnzP: IntArray?, cbeg: IntArray?, cind: IntArray?,
                      cval: DoubleArray?, start: Int, len: Int): Int

    // int GRBgetvars(GRBmodel *model, int *numnzP, int *vbeg, int *vind, double *vval, int start,
    //                int len);
    fun GRBgetvars(model: Pointer, numnzP: IntArray?, vbeg: IntArray?, vind: IntArray?,
                   vval: DoubleArray?, start: Int, len: Int): Int

    // int GRBgetvarbyname(GRBmodel *model, const char *name, int *indexP);
    fun GRBgetvarbyname(model: Pointer, name: String, indexP: IntArray?): Int

    // int GRBgetconstrbyname(GRBmodel *model, const char *name, int *indexP);
    fun GRBgetconstrbyname(model: Pointer, name: String, indexP: IntArray?): Int

    // int GRBoptimize(GRBmodel *model);
    fun GRBoptimize(model: Pointer): Int

    // int GRBupdatemodel(GRBmodel *model);
    fun GRBupdatemodel(model: Pointer): Int

    // int GRBwrite(GRBmodel *model, const char *filename);
    fun GRBwrite(model: Pointer, filename: String): Int

    // int GRBreadmodel(GRBenv *env, const char *filename, GRBmodel **modelP);
    fun GRBreadmodel(env: Pointer, filename: String, modelP: PointerByReference): Int

    // int GRBdelvars(GRBmodel *model, int numdel, int *ind );
    fun GRBdelvars(model: Pointer, numdel: Int, ind: IntArray?): Int

    // int GRBsetcharattrlist(GRBmodel *model, const char *attrname, int len, int *ind,
    //                        char *newvalues);
    fun GRBsetcharattrlist(model: Pointer, attrname: String, len: Int, ind: IntArray?,
                           newvalues: String): Int

    // int GRBsetcallbackfunc(GRBmodel *model, gurobi_callback grbcb, void *usrdata);
    // fun GRBsetcallbackfunc(model: Pointer, gurobi_callback grbcb, void *usrdata): Int
    //
    // int GRBcbget(void *cbdata, int where, int what, void *resultP);
    // fun GRBcbget(cbdata: Pointer, where: Int, what: Int, void *resultP): Int

    // int GRBcbsetparam(void *cbdata, const char *paramname, const char *newvalue);
    fun GRBcbsetparam(cbdata: Pointer, paramname: String, newvalue: String): Int

    // int GRBcbsolution(void *cbdata, const double *solution, double *objvalP);
    fun GRBcbsolution(cbdata: Pointer, solution: DoubleArray?, objvalP: String): Int

    // int GRBcbcut(void *cbdata, int cutlen, const int *cutind, const double *cutval,
    //              char cutsense, double cutrhs);
    fun GRBcbcut(cbdata: Pointer, cutlen: Int, cutind: IntArray?, cutval: DoubleArray?,
                 cutsense: Byte, cutrhs: Double): Int

    // int GRBcblazy(void *cbdata, int lazylen, const int *lazyind, const double *lazyval,
    //               char lazysense, double lazyrhs);
    fun GRBcblazy(cbdata: Pointer, lazylen: Int, lazyind: IntArray?, lazyval: DoubleArray?,
                  lazysense: Byte, lazyrhs: Double): Int

    // int GRBdelconstrs (GRBmodel *model, int numdel, int *ind);
    fun GRBdelconstrs(model: Pointer, numdel: Int, ind: IntArray?): Int
}