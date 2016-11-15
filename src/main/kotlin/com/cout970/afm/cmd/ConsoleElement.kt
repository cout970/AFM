package com.cout970.afm.cmd

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import java.io.File
import java.util.*

/**
 * Created by cout970 on 10/11/16.
 */

data class ConsoleElement(override val file: File) : IElement {

    override val displayName: String
        get() = file.name

    override var column: IColumn? = null

    override fun isSelected(): Boolean = column?.selectedElement == this

    override fun getSubElements(): List<IElement> {
        val subFiles = file.listFiles() ?: return emptyList()
        return subFiles.map { create(it) }
    }

    override val parent: IElement? get() = if (file.parentFile == null) {
        null
    } else if (file.parentFile?.parentFile?.listFiles() == null || file.parentFile?.parentFile?.listFiles()?.size != 1) {
        ConsoleElement(file.parentFile)
    } else {
        val list = LinkedList<IElement>()
        var lastFile = file.parentFile
        var sub: Array<File>?
        while (true) {
            list.addFirst(ConsoleElement(lastFile))
            sub = lastFile.parentFile.listFiles()
            println(sub)
            if (sub == null || sub.size != 1) {
                break
            } else {
                lastFile = lastFile.parentFile
            }
        }
        ComponentConsoleElement(list)
    }

    override fun copy(): IElement = ConsoleElement(file)

    companion object {
        fun create(file: File): IElement {
            var sub = file.listFiles()
            if (sub != null && sub.size == 1) {
                val list = mutableListOf<IElement>()
                list += ConsoleElement(file)
                var lastFile = file.listFiles().first()
                while (true) {
                    list += ConsoleElement(lastFile)
                    sub = lastFile.listFiles()
                    if (sub != null && sub.size == 1) {
                        lastFile = sub.first()
                    } else {
                        break
                    }
                }
                return ComponentConsoleElement(list)
            } else {
                return ConsoleElement(file)
            }
        }
    }

    data class ComponentConsoleElement(val elements: List<IElement>) : IElement {

        override val displayName: String
            get() = elements.joinToString(".") { it.displayName }

        override val file: File
            get() = elements.last().file

        override var column: IColumn? = null
        override val parent: IElement? get() = elements.first().parent

        override fun copy(): IElement = ComponentConsoleElement(elements)

        override fun isSelected(): Boolean = column?.selectedElement == this

        override fun getSubElements(): List<IElement> {
            val subFiles = file.listFiles() ?: return emptyList()
            return subFiles.map { create(it) }
        }
    }
}