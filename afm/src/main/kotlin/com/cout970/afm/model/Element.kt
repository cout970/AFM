package com.cout970.afm.model

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import com.cout970.afm.controller.MainController
import com.cout970.afm.util.windowEnv
import java.io.File

/**
 * Created by cout970 on 10/11/16.
 */

data class Element(override val file: File) : IElement {

    override val displayName: String
        get() = if (!file.name.isEmpty()) file.name else file.absolutePath

    override var column: IColumn? = null

    override fun getSubElements(): List<IElement> {
        val subFiles = file.listFiles()?.toList() ?: emptyList()
        return subFiles.filter { if(MainController.showFiles) true else !it.isFile }.map { create(it) }
    }

    override val parent: IElement? get() {
        if (file.parentFile == null){
            if(windowEnv()) return WindowsRootElement()
            return null
        }
        return Element(file.parentFile!!)
    }

    override fun copy(): IElement = Element(file)

    companion object {
        fun create(file: File): IElement {
            return Element(file)
        }
    }
}