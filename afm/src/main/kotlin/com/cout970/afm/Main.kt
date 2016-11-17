package com.cout970.afm

import com.cout970.afm.controller.MainController
import com.cout970.afm.view.MainApplication

/**
 * Created by cout970 on 9/11/16.
 */

fun main(args: Array<String>) {

    println("Starting")

    MainController.init()
    MainApplication().launchApp(*args)
}