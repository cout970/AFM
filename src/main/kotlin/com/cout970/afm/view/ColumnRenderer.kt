package com.cout970.afm.view

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IColumnRender
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import org.w3c.dom.Element
import kotlin.browser.document

/**
 * Created by cout970 on 14/11/16.
 */

class ColumnRenderer(override var column: IColumn) : IColumnRender {
    override val height: Int
        get() = column.elements.size * 22
    override val width: Int
        get() = 200

    override fun render(view: IView, index: Int) {
        if (column.elements.isNotEmpty()) {
            val offset = when (index) {
                0 -> 5
                1 -> view.columnRenderers[0].width + 15
                2 -> view.columnRenderers[0].width + view.columnRenderers[1].width + 25
                else -> 0
            }
            val builder = StringBuilder()
            builder.append("<div style=\"outline: solid white 1px;\">")
            for ((i, e) in column.elements.withIndex()) {
                printElement(i, e, view, builder)
            }
            builder.append("</div>")
            val code = builder.toString()
            println(code)
            val elem = document.getElementById("column$index")
            (elem as? Element)?.let {
                it.innerHTML = code
            }
        }
    }

    private fun printElement(i: Int, e: IElement, view: IView, builder: StringBuilder) {
        var background: String? = null
        if (e.isSelected()) {
            if (column == view.selectedColumn) {
                background = "blue"
            } else if (view.columns[2] != column) {
                background = "#4040FF"
            }
        }
        if (background != null) {
            builder.append("<div style=\"background-color: $background;\">")
        } else {
            builder.append("<div>")
        }
        //
        if (e.file.isDirectory) {
            builder.append("<span style=\"color: white\">")
        } else {
            builder.append("<span style=\"color: green\">")
        }
        builder.append(e.displayName)
        builder.append("</span>")
        builder.append("</div>")
    }
}
//
//    override var g: Graphics? = null
//
//    override val height: Int
//        get() = column.elements.size * 22
//
//    override val width: Int
//        get() = Math.max(200, g!!.fontMetrics.stringWidth(column.elements.maxBy { it.displayName.length }?.displayName ?: "") + 5)
//
//    override fun render(view: IView, index: Int) {
//        if (column.elements.isNotEmpty()) {
//            val offset = when (index) {
//                0 -> 5
//                1 -> view.columnRenderers[0].width + 15
//                2 -> view.columnRenderers[0].width + view.columnRenderers[1].width + 25
//                else -> 0
//            }
//            g!!.color = Color.WHITE
//            g!!.drawRect(5 + offset - 3, 55 - 3, width + 6, height + 6)
//            for ((i, e) in column.elements.withIndex()) {
//                printElement(i, e, view, offset)
//            }
//        }
//    }
//
//    private fun printElement(i: Int, e: IElement, view: IView, offset: Int) {
//        if (e.isSelected()) {
//            if (column == view.selectedColumn) {
//                g!!.color = Color.BLUE
//                g!!.fillRect(5 + offset, 55 + i * 22, width, 20)
//            } else if (view.columns[2] != column) {
//                g!!.color = Color(0x4040FF)
//                g!!.fillRect(5 + offset, 55 + i * 22, width, 20)
//            }
//        }
////
//        if (e.file.isDirectory) {
//            g!!.color = Color.WHITE
//        } else {
//            g!!.color = Color.GREEN
//        }
//        g!!.drawString(e.displayName, 7 + offset, 66 + i * 22)
//    }
//}