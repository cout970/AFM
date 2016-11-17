package com.cout970.afm.view

import com.cout970.afm.api.IView
import com.cout970.afm.controller.EventProvider
import com.cout970.afm.controller.MainController
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.web.WebView
import javafx.stage.Stage

/**
 * Created by cout970 on 2016/11/15.
 */
class MainApplication : Application(), IView by MainController {

    override fun start(primaryStage: Stage) {
        val webView = WebView()
        MainRenderer.engine = webView.engine
        val box = VBox()
        box.children.add(webView)
        primaryStage.scene = Scene(box).apply {
            EventProvider.init(this)
        }
        webView.engine.loadContent(MainRenderer.template)
        primaryStage.show()
    }

    fun launchApp(vararg arg: String) {
        Application.launch(*arg)
    }
}