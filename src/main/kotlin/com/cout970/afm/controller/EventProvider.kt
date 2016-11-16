package com.cout970.afm.controller

import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.window

/**
 * Created by cout970 on 2016/11/15.
 */

const val KEY_RIGHT = 39
const val KEY_LEFT = 37
const val KEY_UP = 37
const val KEY_DOWN = 40

object EventProvider {

    fun registerKeyListener(listener: (code: Int) -> Unit) {
        window.onkeydown = { e ->
            (e as? KeyboardEvent)?.let {
                listener(it.keyCode)
            }
        }
    }
}