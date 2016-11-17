package com.cout970.afm.controller

import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyEvent

/**
 * Created by cout970 on 2016/11/15.
 */

const val KEY_RIGHT = 0x27
const val KEY_LEFT = 0x25
const val KEY_UP = 0x26
const val KEY_DOWN = 0x28

object EventProvider {

    private val listeners = mutableListOf<(code: Int) -> Unit>()

    fun init(s: Scene){
        s.onKeyPressed = EventHandler<KeyEvent> {
            listeners.forEach { l -> l(it.code.impl_getCode()) }
        }
    }

    fun registerKeyListener(listener: (code: Int) -> Unit) {
        listeners += listener
    }
}