package com.cout970.afm.controller

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import com.cout970.afm.model.Column
import com.cout970.afm.model.Element
import com.cout970.afm.model.WindowsRootElement
import com.cout970.afm.util.windowEnv
import com.cout970.afm.view.MainRenderer
import java.io.File

/**
 * Created by cout970 on 2016/11/15.
 */
object MainController {

    val columns: List<IColumn> = listOf(Column(), Column(), Column())
    val selectedColumn: IColumn get() = columns[selectedColumnIndex]
    var selectedColumnIndex = 0

    fun init() {
        EventProvider.registerKeyListener { e ->
            if (e == KEY_RIGHT) {
                moveRight()
            } else if (e == KEY_LEFT) {
                moveLeft()
            } else if (e == KEY_DOWN) {
                if (selectedColumn.selectedIndex < selectedColumn.elements.size - 1) {
                    selectedColumn.selectedIndex++
                    updateRight(selectedColumnIndex)
                    MainRenderer.update()
                }
            } else if (e == KEY_UP) {
                if (selectedColumn.selectedIndex > 0) {
                    selectedColumn.selectedIndex--
                    updateRight(selectedColumnIndex)
                    MainRenderer.update()
                }
            }
        }
        createRootElement().getSubElements().forEach {
            columns[0].add(it)
        }
        columns[0].selectedIndex = 0
        columns[0].run { elements[selectedIndex] }.getSubElements().forEach {
            columns[1].add(it)
        }
    }

    fun createRootElement(): IElement = if(windowEnv()) WindowsRootElement() else Element(File("/"))

    private fun updateRight(index: Int) {
        columns[index + 1].clear()
        columns[index].elements[columns[index].selectedIndex].getSubElements().forEach {
            columns[index + 1].add(it)
        }
        if (columns[index + 1].elements.isNotEmpty()) {
            columns[index + 1].selectedIndex = 0
        }
    }

    fun moveRight() {
        if (selectedColumnIndex == 0) {//index 0
            //sanity check
            if (selectedColumn.selectedElement == null) {
                if (selectedColumn.elements.isNotEmpty()) {
                    selectedColumn.selectedIndex = 0
                }
            }
            if (selectedColumn.selectedElement != null) {
                val elem = selectedColumn.selectedElement!!
                val sub = elem.getSubElements()
                if (sub.isNotEmpty()) {
                    //update column 1
                    columns[1].clear()
                    columns[1].let { col ->
                        sub.forEach { col.add(it) }
                    }
                    columns[1].selectedIndex = 0
                    selectedColumnIndex = 1

                    //update column 2
                    val subsub = columns[1].selectedElement!!.getSubElements()
                    columns[2].clear()
                    if (subsub.isNotEmpty()) {
                        columns[2].let { col ->
                            subsub.forEach { col.add(it) }
                        }
                        columns[2].selectedIndex = 0
                    }
                    MainRenderer.update()
                }
            }
        } else {//index 1
            //sanity check
            if (selectedColumn.selectedElement == null) {
                if (selectedColumn.elements.isNotEmpty()) {
                    selectedColumn.selectedIndex = 0
                }
            }
            if (selectedColumn.selectedElement != null) {
                val elem = selectedColumn.selectedElement!!
                val sub = elem.getSubElements()
                if (sub.isNotEmpty()) {
                    //update column 0
                    columns[0].clear()
                    columns[0].let { col ->
                        columns[1].elements.forEach { col.add(it.copy()) }
                    }
                    columns[0].selectedIndex = columns[1].selectedIndex

                    //update column 1
                    columns[1].clear()
                    columns[1].let { col ->
                        sub.forEach { col.add(it) }
                    }
                    columns[1].selectedIndex = 0
                    //update column 2
                    val subsub = columns[1].selectedElement!!.getSubElements()
                    columns[2].clear()
                    if (subsub.isNotEmpty()) {
                        columns[2].let { col ->
                            subsub.forEach { col.add(it) }
                        }
                        columns[2].selectedIndex = 0
                    }
                    MainRenderer.update()
                }
            }
        }
    }

    fun moveLeft() {
        if (selectedColumnIndex == 1) {//index 1
            //sanity check
            if (selectedColumn.selectedElement == null) {
                if (selectedColumn.elements.isNotEmpty()) {
                    selectedColumn.selectedIndex = 0
                }
            }
            if (selectedColumn.selectedElement != null) {
                val elem = selectedColumn.selectedElement!!
                if (elem.parent?.parent?.parent == null) {
                    //move to the column 0
                    selectedColumnIndex = 0

                    //update column 0
                    val tmp = columns[0].selectedIndex
                    columns[0].clear()
                    columns[0].let { col ->
                        createRootElement().getSubElements().forEach { col.add(it) }
                    }
                    columns[0].selectedIndex = tmp
                    //update column 2
                    columns[2].clear()
                    MainRenderer.update()
                } else {
                    //move other columns
                    //update column 2
                    columns[2].clear()
                    columns[2].let { col ->
                        columns[1].elements.forEach { col.add(it.copy()) }
                    }
                    columns[2].selectedIndex = columns[1].selectedIndex
                    //update column 1
                    columns[1].clear()
                    columns[1].let { col ->
                        columns[0].elements.forEach { col.add(it.copy()) }
                    }
                    columns[1].selectedIndex = columns[0].selectedIndex
                    //update column 0
                    val oldSelected = elem.parent!!.parent!!
                    columns[0].clear()
                    columns[0].let { col ->
                        oldSelected.parent!!.getSubElements().forEach { col.add(it) }
                    }
                    MainRenderer.update()
                }
            }
        } else {//index 0
            //nothing
        }
    }
}