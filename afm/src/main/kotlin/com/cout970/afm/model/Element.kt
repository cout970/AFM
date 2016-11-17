package com.cout970.afm.model

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import java.io.File

/**
 * Created by cout970 on 10/11/16.
 */

data class Element(override val file: File) : IElement {

    override val displayName: String
        get() = if(!file.name.isEmpty()) file.name else file.absolutePath

    override var column: IColumn? = null

    override fun getSubElements(): List<IElement> {
        val subFiles = file.listFiles()?.toList() ?: emptyList()
        return subFiles.map { create(it) }
    }

    override val parent: IElement? get() = if (file.parentFile == null) {
        null
    } else
//        if (file.parentFile!!.parentFile?.listFiles() == null || file.parentFile!!.parentFile!!.listFiles().size != 1) {
        Element(file.parentFile!!)
//    } else {
//        val list = mutableListOf<IElement>()
//        var lastFile = file.parentFile
//        var sub: List<File>?
//        while (true) {
//            list.add(Element(lastFile!!))
//            sub = lastFile.parentFile!!.listFiles()?.toList() ?: emptyList()
//            if (sub.size != 1) {
//                break
//            } else {
//                lastFile = lastFile.parentFile
//            }
//        }
//        ComponentConsoleElement(list)
//    }

    override fun copy(): IElement = Element(file)

    companion object {
        fun create(file: File): IElement {
//            var sub = file.listFiles()
//            if (sub.size == 1) {
//                val list = mutableListOf<IElement>()
//                list += Element(file)
//                var lastFile = file.listFiles().first()
//                while (true) {
//                    list += Element(lastFile)
//                    sub = lastFile.listFiles()
//                    if (sub.size == 1) {
//                        lastFile = sub.first()
//                    } else {
//                        break
//                    }
//                }
//                return ComponentConsoleElement(list)
//            } else {
                return Element(file)
//            }
        }
    }


//    data class ComponentConsoleElement(val elements: List<IElement>) : IElement {
//
//        override val displayName: String
//            get() = elements.joinToString(".") { it.displayName }
//
//        override val file: File
//            get() = elements.last().file
//
//        override var column: IColumn? = null
//        override val parent: IElement? get() = elements.first().parent
//
//        override fun copy(): IElement = ComponentConsoleElement(elements)
//
//        override fun getSubElements(): List<IElement> {
//            val subFiles = file.listFiles()?.toList() ?: emptyList()
//            return subFiles.map { create(it) }
//        }
//    }
}