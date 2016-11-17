package com.cout970.afm.util

import com.cout970.afm.api.IElement

/**
 * Created by cout970 on 2016/11/17.
 */

fun IElement.isSelected(): Boolean = column?.selectedElement == this

fun windowEnv(): Boolean = System.getProperty("os.name").toLowerCase().contains("windows")