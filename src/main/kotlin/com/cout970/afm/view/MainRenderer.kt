package com.cout970.afm.view

/**
 * Created by cout970 on 2016/11/15.
 */
//object MainRenderer : IView by MainController {
//
//    override val columnRenderers: List<IColumnRender> = listOf(ColumnRenderer(MainController.columns[0]), ColumnRenderer(MainController.columns[1]), ColumnRenderer(MainController.columns[2]))
//    var fileContent: String = ""
//
//    override fun update() {
//        Runnable {
//            synchronized(this) {
//                try {
//                    fileContent = ""
//                    if (MainController.selectedColumn.selectedElement?.file?.isFile ?: false) {
//                        val offset = when (MainController.selectedColumnIndex) {
//                            0 -> MainController.columnRenderers[0].width
//                            1 -> MainController.columnRenderers[0].width + MainController.columnRenderers[1].width
//                            2 -> MainController.columnRenderers[0].width + MainController.columnRenderers[1].width + MainController.columnRenderers[2].width
//                            else -> 0
//                        }
//                        val limit = (width - offset) / 8
//                        if (limit > 10) {
//
//
//                            val file = MainController.selectedColumn.selectedElement?.file!!
//                            val buff = ByteArray(100)
//                            val read: Int
//                            try {
//                                read = FileInputStream(file).read(buff)
//                            } catch (e: Exception) {
//                                repaint()
//                                return@Runnable
//                            }
//                            var isTxt = true
//                            for (i in 0..Math.min(100, read) - 1) {
//                                if (buff[i].toInt() < 32 && buff[i] !in "\n\t\r".toByteArray()) {
//                                    isTxt = false
//                                    break
//                                }
//                            }
//                            if (isTxt) {
//                                fileContent = file.readText().replace("\t", "    ")
//                            }
//                        }
//                    }
//                } catch (e: Exception) {
//                    //ignored
//                }
//            }
//            repaint()
//        }.run()
//    }
//
//    override fun paint(g: Graphics) {
//        super.paint(g)
//
//        g.color = Color(0x1f1f1f)
//        g.fillRect(0, 0, width, height)
//        g.color = Color.WHITE
//        g.drawString(selectedColumn.selectedElement?.file?.absolutePath ?: "", 7, 48)
//        columnRenderers.forEachIndexed { i, c ->
//            c.g = g
//            c.render(this, i)
//        }
//        if (fileContent.isNotEmpty()) {
//
//            val offset = when (selectedColumnIndex) {
//                0 -> columnRenderers[0].width
//                1 -> columnRenderers[0].width + columnRenderers[1].width
//                2 -> columnRenderers[0].width + columnRenderers[1].width + columnRenderers[2].width
//                else -> 0
//            }
//            val limit = width - offset - 50
//            if (limit >= 250) {
//                val lines = mutableListOf<String>()
//                var acum = ""
//                var aux: String
//                var count = 0
//                for (i in fileContent) {
//                    aux = acum
//                    aux += i
//                    if (g.fontMetrics.stringWidth(aux) > limit || i == '\n') {
//                        lines += acum
//                        acum = ""
//                        count = 0
//                    }
//                    acum += i
//                    count++
//                }
//
//                g.color = Color.WHITE
//                val name = selectedColumn.selectedElement?.displayName ?: ""
//                g.drawString(name, offset + 30 + (limit - g.fontMetrics.stringWidth(name)) / 2, 68)
//
//                for ((i, l) in lines.withIndex()) {
//                    g.drawString(l, offset + 35, 88 + 20 * i)
//                }
//
//                g.color = Color.GREEN
//                g.drawRect(offset + 30, 72, width - offset - 50, height)
//            }
//        }
//    }
//}