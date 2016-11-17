package com.cout970.afm.view

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IColumnRender
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import com.cout970.afm.controller.MainController
import javafx.scene.web.WebEngine
import java.io.FileInputStream

/**
 * Created by cout970 on 2016/11/17.
 */
object MainRenderer : IView {

    lateinit var engine: WebEngine
    override val columns: List<IColumn>
        get() = MainController.columns
    override val columnRenderers: List<IColumnRender> = listOf(ColumnRenderer(MainController.columns[0]), ColumnRenderer(MainController.columns[1]), ColumnRenderer(MainController.columns[2]))
    override val selectedColumn: IColumn
        get() = MainController.selectedColumn

    val width: Int get() = MainApplication.instance.scene.width.toInt()
    val height: Int get() = MainApplication.instance.scene.height.toInt()

    var fileContent: String = ""

    override fun update() {
        Runnable {
            synchronized(this) {
                try {
                    fileContent = ""
                    if (MainController.selectedColumn.selectedElement?.file?.isFile ?: false) {
                        val offset = when (MainController.selectedColumnIndex) {
                            0 -> columnRenderers[0].width
                            1 -> columnRenderers[0].width + columnRenderers[1].width
                            2 -> columnRenderers[0].width + columnRenderers[1].width + columnRenderers[2].width
                            else -> 0
                        }
                        val limit = (width - offset) / 8
                        if (limit > 10) {


                            val file = MainController.selectedColumn.selectedElement?.file!!
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

    fun repaint() {
        for ((j, i) in columnRenderers.withIndex()) {
            i.render(this, j)
        }
        val node = engine.document.getElementById("path")
        node.textContent = selectedColumn.selectedElement?.file?.absolutePath ?: ""

        if (fileContent.isNotEmpty()) {
            engine.document.getElementById("column2").setAttribute("style", "display: none;")
            engine.document.getElementById("text").setAttribute("style", "display: block;")
            engine.document.getElementById("text").textContent = fileContent
        } else {
            engine.document.getElementById("column2").setAttribute("style", "display: block;")
            engine.document.getElementById("text").setAttribute("style", "display: none;")
        }
    }

    override fun createRootElement(): IElement = MainController.createRootElement()

    val template: String = """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
    .column {
        width: 25%;
        height: 100%;
        float: left;
    }
    #text {
        width: 45%;
        float: left;
        color: white;
        outline: solid 1px;
    }
    #path {
        width: 100%;
        color: white;
    }
    </style>
</head>
<body style="width: 100vw; background-color: #101010; overflow-y: hidden; overflow-x: hidden;">
<span id="path"></span>
<div>
    <div id="column0" class="column"></div>
    <div id="column1" class="column"></div>
    <div id="column2" class="column"></div>
    <div id="text"></div>
</div>
</body>
</html>"""
}
