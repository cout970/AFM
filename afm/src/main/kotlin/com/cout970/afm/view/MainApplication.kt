package com.cout970.afm.view

import com.cout970.afm.controller.EventProvider
import com.cout970.afm.controller.MainController
import javafx.application.Application
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.web.WebView
import javafx.stage.Stage
import netscape.javascript.JSObject
import org.w3c.dom.html.HTMLDivElement

/**
 * Created by cout970 on 2016/11/15.
 */
class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
        fun runOnMain(e: Runnable) {
            Platform.runLater(e)
        }
    }

    lateinit var scene: Scene

    override fun start(primaryStage: Stage) {
        instance = this
        val webView = WebView()
        MainRenderer.engine = webView.engine
        webView.engine.loadContent(MainRenderer.template)
        val box = StackPane()
        box.children.add(webView)
        scene = Scene(box).apply {
            EventProvider.init(this)
        }
        primaryStage.scene = scene
        primaryStage.show()
        webView.engine.loadWorker.stateProperty().addListener { observable, oldValue, newValue ->
            if (newValue == Worker.State.SUCCEEDED) {
                val window = webView.engine.executeScript("window") as JSObject
                window.setMember("app", JavascriptToJava)
            }
        }
        runOnMain(Runnable {
            MainController.select(0)
        })
    }

    fun launchApp(vararg arg: String) {
        Application.launch(*arg)
    }
}

object JavascriptToJava {

    fun onClick(a: Any?) {
        if (a is HTMLDivElement) {
            var column = -1
            var index = -1
            for (i in 0 until a.attributes.length) {
                val attr = a.attributes.item(i)
                val key = attr.nodeName
                val value = attr.nodeValue

                if (key == "column") {
                    column = value.toInt()
                } else if (key == "index") {
                    index = value.toInt()
                }
            }
            if (column != -1 && index != -1) {
                if (column == MainController.selectedColumnIndex) {
                    MainController.select(index)
                } else if (column > MainController.selectedColumnIndex) {
                    MainController.moveRight()
                    MainController.select(index)
                } else if (column < MainController.selectedColumnIndex) {
                    MainController.moveLeft()
                    MainController.select(index)
                }
            }
        }
    }
}