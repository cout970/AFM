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
//        if (column.elements.isNotEmpty()) {
            val node = MainRenderer.engine.document.createElement("div")
            node.setAttribute("style", "outline: solid white 1px; padding: 3px; margin: 4px;")
            for ((i, e) in column.elements.withIndex()) {
                printElement(i, e, view, node)
            }
            val elem = MainRenderer.engine.document.getElementById("column$index")
            for(i in 0 until elem.childNodes.length){
                elem.removeChild(elem.childNodes.item(i))
            }
            elem.appendChild(node)
//        }
    }

    private fun printElement(i: Int, e: IElement, view: IView, elem: Element) {
        var background: String? = null
        if (e.isSelected()) {
            if (column == view.selectedColumn) {
                background = "blue"
            } else if (view.columns[2] != column) {
                background = "#4040FF"
            }
        }
        val div = elem.ownerDocument.createElement("div")

        if (background != null) {
            div.setAttribute("style", "background-color: $background;")
        }
        val span = elem.ownerDocument.createElement("span")
        if (e.file.isDirectory) {
            span.setAttribute("style", "color: white")
        } else if(e.file.isFile){
            span.setAttribute("style", "color: green")
        }else{
            span.setAttribute("style", "color: red")

        }
        span.textContent = e.displayName
        div.appendChild(span)
        elem.appendChild(div)
    }
}