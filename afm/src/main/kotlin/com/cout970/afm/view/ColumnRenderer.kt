package com.cout970.afm.view

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IColumnRender
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import com.cout970.afm.util.isSelected
import org.w3c.dom.Element

/**
 * Created by cout970 on 14/11/16.
 */

class ColumnRenderer(override var column: IColumn) : IColumnRender {
    override val height: Int
        get() = column.elements.size * 22
    override val width: Int
        get() = 200

    override fun render(view: IView, index: Int) {
        if (MainRenderer.engine.document != null) {
            val elem = MainRenderer.engine.document.getElementById("column$index")
            if (elem != null) {
                for (i in 0 until elem.childNodes.length) {
                    elem.removeChild(elem.childNodes.item(i))
                }
                if (column.elements.isNotEmpty()) {
                    elem.setAttribute("style", "")
                    val node = MainRenderer.engine.document.createElement("div")
                    var par = true
                    for ((i, e) in column.elements.withIndex()) {
                        printElement(i, index, e, view, node, par)
                        par = !par
                    }

                    elem.appendChild(node)
                } else {
                    elem.setAttribute("style", "display: none !important;")
                }
            }
        }
    }

    private fun printElement(i: Int, colIndex: Int, e: IElement, view: IView, elem: Element, par: Boolean) {
        var background: String? = null
        if(par){
            background = "#282828"
        }else{
            background = "#303030"
        }

        if (e.isSelected()) {
            if (column == view.selectedColumn) {
                background = "blue"
            } else if (view.columns[2] != column) {
                background = "#4040FF"
            }
        }

        val div = elem.ownerDocument.createElement("div")
        div.setAttribute("onclick", "app.onClick(this)")
        div.setAttribute("index", i.toString())
        div.setAttribute("column", colIndex.toString())

        div.setAttribute("style", "background-color: $background; word-wrap: break-word; margin-left 2px;")
        val span = elem.ownerDocument.createElement("span")
        if (e.file.isDirectory) {
            span.setAttribute("style", "color: white")
        } else if (e.file.isFile) {
            span.setAttribute("style", "color: green")
        } else {
            span.setAttribute("style", "color: red")

        }
        span.textContent = e.displayName
        div.appendChild(span)
        elem.appendChild(div)
    }
}