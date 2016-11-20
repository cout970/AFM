package com.cout970.afm.model

import com.cout970.afm.api.ChangeAction
import com.cout970.afm.api.IColumn
import com.cout970.afm.api.IElement

/**
 * Created by cout970 on 10/11/16.
 */

class Column() : IColumn {

    override var selectedIndex: Int = -1
    override val elements: List<IElement>
        get() = elementList

    val elementList = mutableListOf<IElement>()
    override val selectedElement: IElement?
        get() = if(selectedIndex in elements.indices) elements[selectedIndex] else null

    val listeners = mutableListOf<(Int, IElement, ChangeAction) -> Unit>()

    override fun addListener(listener: (Int, IElement, ChangeAction) -> Unit) {
        listeners += listener
    }

    override fun add(e: IElement) {
        elementList.add(e)
        e.column = this
        listeners.forEach { it(elementList.lastIndex, e, ChangeAction.ADD) }
    }

    override fun remove(index: Int) {
        if(index in elementList.indices) {
            listeners.forEach { it(index, elementList[index], ChangeAction.REMOVE) }
            elementList.removeAt(index)
        }
    }

    override fun remove(e: IElement) {
        if(e in elementList) {
            listeners.forEach { it(elementList.indexOf(e), e, ChangeAction.REMOVE) }
            elementList.remove(e)
        }
    }

    override fun clear() {
        elementList.forEachIndexed { i, iElement -> listeners.forEach { l -> l(i, iElement, ChangeAction.REMOVE) } }
        elementList.clear()
    }

    override fun select(index: Int) {
        if(index in elementList.indices){
            selectedIndex = index
        }
    }
}