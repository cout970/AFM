package com.cout970.afm.jswing

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import com.cout970.afm.cmd.ConsoleColumn
import com.cout970.afm.cmd.ConsoleElement
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import java.io.FileInputStream
import javax.swing.JFrame
import javax.swing.JTextArea

/**
 * Created by cout970 on 11/11/16.
 */
class Console : JFrame("AFM"), IView {

    override val columns: List<IColumn> = listOf(ConsoleColumn(), ConsoleColumn(), ConsoleColumn())
    override val columnRenderers: List<IColumnRender> = listOf(ColumnRenderer(columns[0]), ColumnRenderer(columns[1]), ColumnRenderer(columns[2]))
    override val selectedColumn: IColumn get() = columns[selectedColumnIndex]
    var selectedColumnIndex = 0
    var fileContent: String = ""
    val textArea = JTextArea()

    override fun createRootElement(): IElement = ConsoleElement(File("/"))

    override fun update() {
        Runnable {
            synchronized(this) {
                try {
                    fileContent = ""
                    if (selectedColumn.selectedElement?.file?.isFile ?: false) {
                        val offset = when (selectedColumnIndex) {
                            0 -> columnRenderers[0].width
                            1 -> columnRenderers[0].width + columnRenderers[1].width
                            2 -> columnRenderers[0].width + columnRenderers[1].width + columnRenderers[2].width
                            else -> 0
                        }
                        val limit = (width - offset) / 8
                        if (limit > 10) {


                            val file = selectedColumn.selectedElement?.file!!
                            val buff = ByteArray(100)
                            val read: Int
                            try {
                                read = FileInputStream(file).read(buff)
                            } catch (e: Exception) {
                                repaint()
                                return@Runnable
                            }
                            var isTxt = true
                            for (i in 0..Math.min(100, read) - 1) {
                                if (buff[i].toInt() < 32 && buff[i] !in "\n\t\r".toByteArray()) {
                                    isTxt = false
                                    break
                                }
                            }
                            if (isTxt) {
                                fileContent = file.readText().replace("\t", "    ")
                            }
                        }
                    }
                } catch (e: Exception) {
                    //ignored
                }
            }
            repaint()
        }.run()
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        g.color = Color(0x1f1f1f)
        g.fillRect(0, 0, width, height)
        g.color = Color.WHITE
        g.drawString(selectedColumn.selectedElement?.file?.absolutePath ?: "", 7, 48)
        columnRenderers.forEachIndexed { i, c ->
            c.g = g
            c.render(this, i)
        }
        if (fileContent.isNotEmpty()) {

            val offset = when (selectedColumnIndex) {
                0 -> columnRenderers[0].width
                1 -> columnRenderers[0].width + columnRenderers[1].width
                2 -> columnRenderers[0].width + columnRenderers[1].width + columnRenderers[2].width
                else -> 0
            }
            val limit = width - offset - 50
            if (limit >= 250) {
                val lines = mutableListOf<String>()
                var acum = ""
                var aux: String
                var count = 0
                for (i in fileContent) {
                    aux = acum
                    aux += i
                    if (g.fontMetrics.stringWidth(aux) > limit || i == '\n') {
                        lines += acum
                        acum = ""
                        count = 0
                    }
                    acum += i
                    count++
                }

                g.color = Color.WHITE
                val name = selectedColumn.selectedElement?.displayName ?: ""
                g.drawString(name, offset + 30 + (limit - g.fontMetrics.stringWidth(name)) / 2, 68)

                for ((i, l) in lines.withIndex()) {
                    g.drawString(l, offset + 35, 88 + 20 * i)
                }

                g.color = Color.GREEN
                g.drawRect(offset + 30, 72, width - offset - 50, height)
            }
        }
    }

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
                    update()
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

                    update()
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
                    update()
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
                    update()
                }
            }
        } else {//index 0
            //nothing
        }
    }

    fun start() {
        setSize(800, 600)
        background = Color(0x101010)
//        add(textArea)
        createRootElement().getSubElements().forEach {
            columns[0].add(it)
        }
        columns[0].selectedIndex = 0
        columns[0].run { elements[selectedIndex] }.getSubElements().forEach {
            columns[1].add(it)
        }
        addWindowStateListener {
            update()
        }
        addKeyListener(object : KeyListener {

            override fun keyTyped(e: KeyEvent?) {

            }

            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_RIGHT) {
                    moveRight()
                } else if (e.keyCode == KeyEvent.VK_LEFT) {
                    moveLeft()
                } else if (e.keyCode == KeyEvent.VK_DOWN) {
                    if (selectedColumn.selectedIndex < selectedColumn.elements.size - 1) {
                        selectedColumn.selectedIndex++
                        updateRight(selectedColumnIndex)
                        update()
                    }
                } else if (e.keyCode == KeyEvent.VK_UP) {
                    if (selectedColumn.selectedIndex > 0) {
                        selectedColumn.selectedIndex--
                        updateRight(selectedColumnIndex)
                        update()
                    }
                }
            }

            override fun keyReleased(e: KeyEvent) {

            }
        })
        isVisible = true
    }
}