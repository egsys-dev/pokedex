package br.egsys.pokedex.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

val ViewGroup.children: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

fun <T : View> ViewGroup.findViewByClass(childClass: Class<T>): T? {
    if (this.javaClass == childClass) return this as? T

    return children.mapNotNull { child ->
        when {
            child.javaClass == childClass -> return child as? T
            child is ViewGroup -> child.findViewByClass(childClass)
            else -> null
        }
    }.firstOrNull()
}