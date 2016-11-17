package com.cout970.afm.model

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import java.io.File

/**
 * Created by cout970 on 2016/11/17.
 */
class WindowsRootElement : IElement {
    override val displayName: String = "/"
    override val file: File = File("C://")
    override var column: IColumn? = null
    override val parent: IElement? = null
    override fun copy(): IElement = WindowsRootElement()

    override fun getSubElements(): List<IElement> {
        return File.listRoots().map(::Element)
    }
}