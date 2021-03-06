package com.cout970.afm.view

import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IColumnRender
import com.cout970.afm.api.IElement
import com.cout970.afm.api.IView
import com.cout970.afm.controller.MainController
import javafx.application.Platform
import javafx.scene.web.WebEngine
import java.io.FileInputStream
import java.util.zip.ZipFile

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
        fileContent = ""
        repaint()
        try {
            if (MainController.selectedColumn.selectedElement?.file?.isFile ?: false) {
                Platform.runLater {
                    val file = MainController.selectedColumn.selectedElement?.file!!
                    if (file.extension == "zip" || file.extension == "jar") {
                        val zip = ZipFile(file)
                        fileContent += "Zip File:\n"
                        zip.stream().forEach { fileContent += "\t${it.name}\n" }
                        MainApplication.runOnMain(Runnable {
                            repaint()
                        })
                    } else if(file.length() < 1024 * 50) {
                        val buff = ByteArray(100)
                        val read: Int
                        try {
                            read = FileInputStream(file).read(buff)
                        } catch (e: Exception) {
                            return@runLater
                        }
                        var errors = 0
                        for (i in 0..Math.min(100, read) - 1) {
                            if (buff[i].toInt() < 32 && buff[i] !in "\n\t\r".toByteArray()) {
                                errors++
                            }
                        }
                        val isTxt = errors < read / 4
                        if (isTxt) {
                            fileContent = file.readText()
                            MainApplication.runOnMain(Runnable {
                                repaint()
                            })
                        } else {
                            fileContent = ""
                            var aux = 0
                            var count = 0


                            if (file.canRead()) {
                                val stream = file.inputStream()
                                while (true) {
                                    aux = stream.read()
                                    if (aux == -1) break
                                    count++
                                    if (count > 1024) break
                                    val str = Integer.toHexString(aux).toUpperCase()
                                    if (str.length == 2) {
                                        fileContent += str
                                    } else {
                                        fileContent += '0' + str
                                    }
                                    fileContent += ' '
                                }
                                MainApplication.runOnMain(Runnable {
                                    repaint()
                                })
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            //ignored
        }

    }

    fun repaint() {
        if (engine.document != null) {
            for ((j, i) in columnRenderers.withIndex()) {
                i.render(this, j)
            }
            val node = engine.document.getElementById("path")
            node.setAttribute("value", selectedColumn.selectedElement?.file?.absolutePath ?: "")

            if (fileContent.isNotEmpty()) {
                engine.document.getElementById("column2").setAttribute("style", "display: none;")
                engine.document.getElementById("text").setAttribute("style", "display: block;")
                engine.document.getElementById("text").textContent = fileContent
            } else {
                engine.document.getElementById("column2").setAttribute("style", "display: block;")
                engine.document.getElementById("text").setAttribute("style", "display: none;")
            }
        }

    }

    override fun createRootElement(): IElement = MainController.createRootElement()

    val template: String = """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
    * {
        margin: 0;
        padding: 0;
        font-size: 24px;
        -fx-font-smoothing-type: lcd;
        font-family: Helvetica, Monospaced, sans-serif;
    }
    .columnGen {
        word-wrap: break-word;
        margin-left: 0;
        padding: 3px;
        max-height: 93vh;
        outline: solid white 1px;
    }
    .column_fixed {
        width: 200px;
    }
    #text {
        border: solid white 1px;
        height: 81vh;
        color: white;
        font-size: 14px;
        background-color: black;
        width: 98%;
        padding-left: 1%;
        padding-right: 1%;
    }
    .cell {
        display: table-cell;
        vertical-align: top;
    }
    #path {
        border: solid white 1px;
        margin-left: 1%;
        margin-top: 5px;
        margin-bottom: 5px;
        font-size: 1em;
        width: 97%;
        color: white;
        padding-top: 2px;
        padding-bottom: 2px;
        padding-left: 0.5%;
        padding-right: 0.5%;
        background-color: black;
    }
    .wrapper {
        display: table;
        table-layout: fixed;
        margin-left: 1%;
        width: 98%;
    }
    </style>
</head>
<body style="width: 100vw; background-color: #101010; overflow-y: hidden; overflow-x: hidden;">
<input type="text" id="path" readonly>
<div style="width: 100vw;">
    <div class="wrapper">
        <div class="cell column_fixed">
            <div id="column0" class="columnGen"></div>
        </div>
        <div class="cell column_fixed">
            <div id="column1" class="columnGen"></div>
        </div>
        <div class="cell">
            <div id="column2" class="columnGen" style="background-color: red;"></div>
            <textarea id="text" readonly></textarea>
        </div>
    </div>
</div>
</body>
</html>"""
}
