package com.cout970.afm.api

/**
 * Created by cout970 on 10/11/16.
 */
interface IColumn {

    var selectedIndex: Int
    val elements: List<IElement>
    val selectedElement: IElement?

    fun addListener(listener: (Int, IElement, ChangeAction) -> Unit)

    fun add(e: IElement)

    fun remove(index: Int)

    fun remove(e: IElement)

    fun clear()

    fun select(index: Int)
}

enum class ChangeAction {
    ADD,
    REMOVE,
    UPDATE
}