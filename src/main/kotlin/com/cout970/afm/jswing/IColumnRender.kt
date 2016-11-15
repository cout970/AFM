package com.cout970.afm.jswing

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IView
import java.awt.Graphics

/**
 * Created by cout970 on 14/11/16.
 */
interface IColumnRender {

    var column: IColumn

    val height: Int
    val width: Int
    var g: Graphics?

    fun render(view: IView, index: Int)
}