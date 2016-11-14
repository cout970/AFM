package com.cout970.afm

import com.cout970.glutilities.event.EventManager
import com.cout970.glutilities.structure.GameLoop
import com.cout970.glutilities.window.GLFWLoader
import com.cout970.glutilities.window.WindowBuilder
import com.cout970.vector.extensions.vec2Of

/**
 * Created by cout970 on 2016/11/14.
 */

fun main(args: Array<String>) {
    GLFWLoader.init()
    val window = WindowBuilder.build {
        size = vec2Of(800, 600)
    }

    GameLoop {
        EventManager.pollEvents()
        window.swapBuffers()
        if(window.shouldClose()) it.stop()
    }.start()

    GLFWLoader.terminate()
}