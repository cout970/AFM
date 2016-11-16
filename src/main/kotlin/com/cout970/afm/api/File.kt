package com.cout970.afm.api

/**
 * Created by cout970 on 2016/11/15.
 */
data class File(val path: String) {
    val parentFile: File? = null
    val isFile: Boolean = false
    val name: String = path
    val isDirectory: Boolean = false
    fun listFiles(): List<File> = emptyList()
}