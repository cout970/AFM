package com.cout970.afm.api

/**
 * Created by cout970 on 10/11/16.
 */
interface IEventProvider {

    fun addSelectListener(func: (index: Int, element: IElement) -> Unit)
}