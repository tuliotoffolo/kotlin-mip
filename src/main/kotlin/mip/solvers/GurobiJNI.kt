package mip.solvers

import jnr.ffi.Platform

internal object GurobiJNI {

    external fun addconstrs(model: Long, numconstrs: Int, numnz: Int, cbeg: IntArray, cind: IntArray, cval: DoubleArray, sense: CharArray, lhs: DoubleArray, rhs: DoubleArray, constrnames: Array<String>): Int

    external fun addgenconstrabs(model: Long, name: String, resind: Int, argind: Int): Int

    external fun addgenconstrand(model: Long, name: String, resind: Int, len: Int, ind: IntArray): Int

    external fun addgenconstrcos(model: Long, name: String, xind: Int, yind: Int, options: String): Int

    external fun addgenconstrexp(model: Long, name: String, xind: Int, yind: Int, options: String): Int

    external fun addgenconstrexpa(model: Long, name: String, xind: Int, yind: Int, a: Double, options: String): Int

    external fun addgenconstrindicator(model: Long, name: String, binind: Int, binval: Int, len: Int, ind: IntArray, vals: DoubleArray, sense: Char, rhs: Double): Int

    external fun addgenconstrlog(model: Long, name: String, xind: Int, yind: Int, options: String): Int

    external fun addgenconstrloga(model: Long, name: String, xind: Int, yind: Int, a: Double, options: String): Int

    external fun addgenconstrmax(model: Long, name: String, resind: Int, len: Int, ind: IntArray, constant: Double): Int

    external fun addgenconstrmin(model: Long, name: String, resind: Int, len: Int, ind: IntArray, constant: Double): Int

    external fun addgenconstror(model: Long, name: String, resind: Int, len: Int, ind: IntArray): Int

    external fun addgenconstrpoly(model: Long, name: String, xind: Int, yind: Int, plen: Int, pcoe: DoubleArray, options: String): Int

    external fun addgenconstrpow(model: Long, name: String, xind: Int, yind: Int, a: Double, options: String): Int

    external fun addgenconstrpwl(model: Long, name: String, xind: Int, yind: Int, npts: Int, xpts: DoubleArray, ypts: DoubleArray): Int

    external fun addgenconstrsin(model: Long, name: String, xind: Int, yind: Int, options: String): Int

    external fun addgenconstrtan(model: Long, name: String, xind: Int, yind: Int, options: String): Int

    external fun addqconstr(model: Long, lnz: Int, lind: IntArray, lval: DoubleArray, qnz: Int, qrow: IntArray, qcol: IntArray, qval: DoubleArray, sense: Char, rhs: Double, name: String): Int

    external fun addqpterms(model: Long, qnz: Int, qrow: IntArray, qcol: IntArray, qval: DoubleArray): Int

    external fun addsos(model: Long, numsos: Int, nummembers: Int, type: IntArray, beg: IntArray, ind: IntArray, weight: DoubleArray): Int

    external fun addvars(model: Long, numvars: Int, numnz: Int, vbeg: IntArray, vind: IntArray, vval: DoubleArray, obj: DoubleArray, lb: DoubleArray, ub: DoubleArray, vtype: CharArray, varnames: Array<String>): Int

    external fun basishead(model: Long, bhead: IntArray): Int

    external fun binvcolj(model: Long, col: Int, xlen: IntArray, xind: IntArray, xval: DoubleArray): Int

    external fun binvrowi(model: Long, i: Int, xlen: IntArray, xind: IntArray, xval: DoubleArray): Int

    external fun bsolve(model: Long, blen: Int, bind: IntArray, bval: DoubleArray, xlen: IntArray, xind: IntArray, xval: DoubleArray): Int

    external fun cbcutorlazy(cbdata: Long, len: Int, rhs: Double, cutind: IntArray, cutval: DoubleArray, sense: Char, iscut: Int): Int

    external fun cbgetdblinfo(cbdata: Long, where: Int, what: Int, `val`: DoubleArray): Int

    external fun cbgetintinfo(cbdata: Long, where: Int, what: Int, `val`: IntArray): Int

    external fun cbgetstrinfo(cbdata: Long, where: Int, what: Int): String

    external fun cbsolution(cbdata: Long, sol: DoubleArray, obj: DoubleArray): Int

    external fun checkmodel(model: Long): Int

    external fun chgcoeffs(model: Long, cnt: Int, cind: IntArray, vind: IntArray, `val`: DoubleArray): Int

    external fun clean2(len: IntArray, ind: IntArray, `val`: DoubleArray): Int

    external fun clean3(len: IntArray, ind0: IntArray, ind1: IntArray, `val`: DoubleArray): Int

    external fun computeIIS(model: Long): Int

    external fun copymodel(model: Long): Long

    external fun createenv(env: LongArray, major: Int, minor: Int, tech: Int): Int

    external fun delconstrs(model: Long, len: Int, ind: IntArray): Int

    external fun delgenconstrs(model: Long, len: Int, ind: IntArray): Int

    external fun delq(model: Long): Int

    external fun delqconstrs(model: Long, len: Int, ind: IntArray): Int

    external fun delsos(model: Long, len: Int, ind: IntArray): Int

    external fun delvars(model: Long, len: Int, ind: IntArray): Int

    external fun discardconcurrentenvs(model: Long)

    external fun discardmultiobjenvs(model: Long)

    external fun feasibility(model: Long, feasibility: LongArray): Int

    external fun feasrelax(model: Long, type: Int, minrelax: Int, lbpen: DoubleArray, ubpen: DoubleArray, rhspen: DoubleArray, feasobjP: DoubleArray): Int

    external fun fixedmodel(model: Long, fixed: LongArray): Int

    external fun freebatch(batch: Long)

    external fun freeenv(env: Long)

    external fun freemodel(model: Long)

    external fun fsolve(model: Long, blen: Int, bind: IntArray, bval: DoubleArray, xlen: IntArray, xind: IntArray, xval: DoubleArray): Int

    external fun getattrinfo(model: Long, attrname: String, attrinfo: IntArray): Int

    external fun getcharattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, values: CharArray): Int

    external fun getcoeff(model: Long, constr: Int, `var`: Int, value: DoubleArray): Int

    external fun getconcurrentenv(model: Long, num: Int): Long

    external fun getconstrbyname(model: Long, name: String, index: IntArray): Int

    external fun getconstrs(model: Long, numnz: IntArray, cbeg: IntArray, cind: IntArray, cval: DoubleArray, start: Int, len: Int): Int

    external fun getdblattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, values: DoubleArray): Int

    external fun getdblparam(env: Long, param: String, value: DoubleArray): Int

    external fun getdblparaminfo(env: Long, param: String, info: DoubleArray): Int

    external fun getenv(model: Long): Long

    external fun geterrormsg(env: Long): String

    external fun getgenconstrabs(model: Long, genconstr: Int, resind: IntArray, argind: IntArray): Int

    external fun getgenconstrand(model: Long, genconstr: Int, resind: IntArray, nvars: IntArray, ind: IntArray): Int

    external fun getgenconstrcos(model: Long, genconstr: Int, xind: IntArray, yind: IntArray): Int

    external fun getgenconstrexp(model: Long, genconstr: Int, xind: IntArray, yind: IntArray): Int

    external fun getgenconstrexpa(model: Long, genconstr: Int, xind: IntArray, yind: IntArray, a: DoubleArray): Int

    external fun getgenconstrindicator(model: Long, genconstr: Int, binind: IntArray, binval: IntArray, nvars: IntArray, ind: IntArray, vals: DoubleArray, sense: CharArray, rhs: DoubleArray): Int

    external fun getgenconstrlog(model: Long, genconstr: Int, xind: IntArray, yind: IntArray): Int

    external fun getgenconstrloga(model: Long, genconstr: Int, xind: IntArray, yind: IntArray, a: DoubleArray): Int

    external fun getgenconstrmax(model: Long, genconstr: Int, resind: IntArray, nvars: IntArray, ind: IntArray, constant: DoubleArray): Int

    external fun getgenconstrmin(model: Long, genconstr: Int, resind: IntArray, nvars: IntArray, ind: IntArray, constant: DoubleArray): Int

    external fun getgenconstror(model: Long, genconstr: Int, resind: IntArray, nvars: IntArray, ind: IntArray): Int

    external fun getgenconstrpoly(model: Long, genconstr: Int, xind: IntArray, yind: IntArray, plen: IntArray, pcoe: DoubleArray): Int

    external fun getgenconstrpow(model: Long, genconstr: Int, xind: IntArray, yind: IntArray, a: DoubleArray): Int

    external fun getgenconstrpwl(model: Long, genconstr: Int, xind: IntArray, yind: IntArray, npts: IntArray, xpts: DoubleArray, ypts: DoubleArray): Int

    external fun getgenconstrsin(model: Long, genconstr: Int, xind: IntArray, yind: IntArray): Int

    external fun getgenconstrtan(model: Long, genconstr: Int, xind: IntArray, yind: IntArray): Int

    external fun getintattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, values: IntArray): Int

    external fun getintparam(env: Long, param: String, value: IntArray): Int

    external fun getintparaminfo(env: Long, param: String, info: IntArray): Int

    external fun getJSONSolution(model: Long, value: Array<String>): Int

    external fun getmultiobjenv(model: Long, num: Int): Long

    external fun getpwlobj(model: Long, `var`: Int, points: IntArray, ptval: DoubleArray, ptobj: DoubleArray): Int

    external fun getq(model: Long, qnz: IntArray, qrow: IntArray, qcol: IntArray, qval: DoubleArray): Int

    external fun getqconstr(model: Long, qconstr: Int, lnz: IntArray, lind: IntArray, lval: DoubleArray, qnz: IntArray, qrow: IntArray, qcol: IntArray, qval: DoubleArray): Int

    external fun getsos(model: Long, nummembers: IntArray, sostype: IntArray, beg: IntArray, ind: IntArray, weight: DoubleArray, start: Int, len: Int): Int

    external fun getstrattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, values: Array<String>): Int

    external fun getstrparam(env: Long, param: String, value: Array<String>): Int

    external fun getstrparaminfo(env: Long, param: String, info: Array<String>): Int

    external fun gettuneresult(model: Long, i: Int): Int

    external fun getvarbyname(model: Long, name: String, index: IntArray): Int

    external fun getvars(model: Long, numnz: IntArray, vbeg: IntArray, vind: IntArray, vval: DoubleArray, start: Int, len: Int): Int

    external fun isattrfile(filename: String): Int

    external fun ismodelfile(filename: String): Int

    external fun isqp(env: LongArray, logfilename: String, isvname: String, appname: String, expiration: Int, key: String): Int

    external fun linearize(model: Long, linearized: LongArray): Int

    external fun loadclientenv(env: LongArray, logfilename: String, computeserver: String, router: String, password: String, group: String, CStlsInsecure: Int, priority: Int, timeout: Double, major: Int, minor: Int, tech: Int): Int

    external fun loadcloudenv(env: LongArray, logfilename: String, accessID: String, secretKey: String, pool: String, priority: Int, major: Int, minor: Int, tech: Int): Int

    external fun loadenv(env: LongArray, logfilename: String, major: Int, minor: Int, tech: Int, idletimeout: Int): Int

    external fun loadmodel(env: Long, Pname: String, numvars: Int, numconstrs: Int, objsense: Int, objcon: Double, obj: DoubleArray, sense: CharArray, rhs: DoubleArray, vbeg: IntArray, vlen: IntArray, vind: Array<IntArray>, vval: DoubleArray, lb: DoubleArray, ub: DoubleArray, vtype: CharArray, varnames: Array<String>, constrnames: Array<String>): Long

    external fun message(env: Long, message: String): Int

    external fun newbatch(batch: LongArray, env: Long, ID: String): Int

    external fun newmodel(jerror: IntArray, env: Long, Pname: String, numvars: Int, obj: DoubleArray, lb: DoubleArray, ub: DoubleArray, vtype: CharArray, varnames: Array<String>): Long

    external fun presolvemodel(model: Long, presolved: LongArray): Int

    external fun read(model: Long, filename: String): Int

    external fun readmodel(env: Long, filename: String): Long

    external fun readparams(env: Long, filename: String): Int

    external fun relaxmodel(model: Long, relaxed: LongArray): Int

    external fun releaselicense(env: Long)

    external fun reset(model: Long, clearall: Int): Int

    external fun resetparams(env: Long): Int

    external fun setcharattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, newvalues: CharArray): Int

    external fun setdblattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, newvalues: DoubleArray): Int

    external fun setdblparam(env: Long, param: String, newvalue: Double): Int

    external fun setintattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, newvalues: IntArray): Int

    external fun setintparam(env: Long, param: String, newvalue: Int): Int

    external fun setobjective(model: Long, sense: Int, objcon: Double, lnz: Int, lind: IntArray, lval: DoubleArray, qnz: Int, qrow: IntArray, qcol: IntArray, qval: DoubleArray): Int

    external fun setobjectiven(model: Long, index: Int, priority: Int, weight: Double, abstol: Double, reltol: Double, name: String, objcon: Double, lnz: Int, lind: IntArray, lval: DoubleArray): Int

    external fun setparam(env: Long, param: String, newvalue: String): Int

    external fun setpwlobj(model: Long, `var`: Int, points: Int, ptval: DoubleArray, ptobj: DoubleArray): Int

    external fun setstrattrlist(model: Long, attrname: String, first: Int, len: Int, ind: IntArray, newvalues: Array<String>): Int

    external fun setstrparam(env: Long, param: String, newvalue: String): Int

    external fun singlescenariomodel(model: Long, singlescenario: LongArray): Int

    external fun startenv(env: Long): Int

    external fun stoponemultiobj(model: Long, cbdata: Long, objnum: Int): Int

    external fun sync(model: Long, jcbdata: Long): Int

    external fun terminate(model: Long)

    external fun tunemodel(model: Long): Int

    external fun updatemodel(model: Long): Int

    external fun version(info: IntArray)

    external fun write(model: Long, filename: String): Int

    external fun writeparams(env: Long, filename: String): Int

    init {
        val platform = Platform.getNativePlatform();

        val versions = (10 downTo 6).flatMap { i -> (11 downTo 0).map { j -> "$i$j" } }
        val libNames: List<String> = when (platform.os) {
            Platform.OS.DARWIN, Platform.OS.LINUX -> versions.map { "gurobi$it" }.toList()
            Platform.OS.WINDOWS -> versions.map { "gurobi$it.dll" }.toList()
            else -> emptyList()
        }

        for (library in libNames) {
            try {
                System.loadLibrary(library)
                break
            }
            catch (e: UnsatisfiedLinkError) {
            }
        }
    }
}