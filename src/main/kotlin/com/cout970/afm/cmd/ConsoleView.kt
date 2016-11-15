package com.cout970.afm.cmd

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import java.io.File

/**
 * Created by cout970 on 10/11/16.
 */
class ConsoleView() : IView {

    override val columns: List<IColumn> = listOf(ConsoleColumn(), ConsoleColumn(), ConsoleColumn())

    override fun createRootElement(): IElement = ConsoleElement(File("/"))

    override fun update() {

    }
}