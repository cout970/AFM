package com.cout970.afm.jswing

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import java.awt.Color
import java.awt.Graphics

/**
 * Created by cout970 on 14/11/16.
 */

class ColumnRenderer(override var column: IColumn) : IColumnRender {

    override var g: Graphics? = null

    override val height: Int
        get() = column.elements.size * 22

    override val width: Int
        get() = Math.max(200, g!!.fontMetrics.stringWidth(column.elements.maxBy { it.displayName.length }?.displayName ?: "") + 5)

    override fun render(view: IView, index: Int) {
        if (column.elements.isNotEmpty()) {
            val offset = when (index) {
                0 -> 5
                1 -> view.columnRenderers[0].width + 15
                2 -> view.columnRenderers[0].width + view.columnRenderers[1].width + 25
                else -> 0
            }
            g!!.color = Color.WHITE
            g!!.drawRect(5 + offset - 3, 55 - 3, width + 6, height + 6)
            for ((i, e) in column.elements.withIndex()) {
                printElement(i, e, view, offset)
            }
        }
    }

    private fun printElement(i: Int, e: IElement, view: IView, offset: Int) {
        if (e.isSelected()) {
            if (column == view.selectedColumn) {
                g!!.color = Color.BLUE
                g!!.fillRect(5 + offset, 55 + i * 22, width, 20)
            } else if (view.columns[2] != column) {
                g!!.color = Color(0x4040FF)
                g!!.fillRect(5 + offset, 55 + i * 22, width, 20)
            }
        }
//
        if (e.file.isDirectory) {
            g!!.color = Color.WHITE
        } else {
            g!!.color = Color.GREEN
        }
        g!!.drawString(e.displayName, 7 + offset, 66 + i * 22)
    }
}