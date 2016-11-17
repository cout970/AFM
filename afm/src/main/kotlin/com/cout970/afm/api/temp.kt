package com.cout970.afm.api

/**
 * Created by cout970 on 14/11/16.
 */
interface IColumnRender {

    var column: IColumn

    val height: Int
    val width: Int

    fun render(view: IView, index: Int)
}