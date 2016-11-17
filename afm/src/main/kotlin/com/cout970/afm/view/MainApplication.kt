package com.cout970.afm.view

import com.cout970.afm.controller.EventProvider
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.web.WebView
import javafx.stage.Stage

/**
 * Created by cout970 on 2016/11/15.
 */
class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    lateinit var scene: Scene

    override fun start(primaryStage: Stage) {
        instance = this
        val webView = WebView()
        MainRenderer.engine = webView.engine
        val box = VBox()
        box.children.add(webView)
        scene = Scene(box).apply {
            EventProvider.init(this)
        }
        primaryStage.scene = scene
        webView.engine.loadContent(MainRenderer.template)
        primaryStage.show()
    }

    fun launchApp(vararg arg: String) {
        Application.launch(*arg)
    }
}