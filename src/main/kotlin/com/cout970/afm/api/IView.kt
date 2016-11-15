package com.cout970.afm.api

import com.cout970.afm.jswing.IColumnRender

/**
 * Created by cout970 on 10/11/16.
 */
interface IView {

    val columns: List<IColumn>
    val columnRenderers: List<IColumnRender>

    val selectedColumn: IColumn

    fun createRootElement(): IElement

    fun update()
}

