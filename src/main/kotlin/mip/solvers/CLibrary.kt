package mip.solvers

import jnr.ffi.LibraryLoader
import jnr.ffi.Platform
import jnr.ffi.Pointer
import java.io.File

interface CLibrary {

    companion object {
        @JvmStatic
        val lib: CLibrary = LibraryLoader.create(CLibrary::class.java).load("c")
    }

    /**
     * int chdir(const char *path);
     */
    // fun chdir(path: String): Int

    /**
     * int fflush(FILE * stream);
     */
    fun fflush(stream: Pointer?): Int

    /**
     * int setenv(const char *envname, const char *envval, int overwrite);
     */
    // fun setenv(envname: String, envval: String, overwrite: Int): Int

    /**
     * int system(const char *command)
     */
    // fun system(command: String): Int
}
