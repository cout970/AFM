package com.cout970.afm.api

import java.io.File

/**
 * Created by cout970 on 10/11/16.
 */
interface IElement {

    val displayName: String

    val file: File

    var column: IColumn?

    val parent: IElement?

    fun copy(): IElement

    fun isSelected(): Boolean

    fun getSubElements(): List<IElement>
}