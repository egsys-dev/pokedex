package br.egsys.pokedex.extension

import android.graphics.drawable.Drawable
import android.widget.EditText

fun EditText.setStartCompoundDrawable(drawable: Drawable?) {
    val currentDrawables = compoundDrawablesRelative

    setCompoundDrawablesRelativeWithIntrinsicBounds(
        drawable,
        currentDrawables[1],
        currentDrawables[2],
        currentDrawables[3]
    )
}

fun EditText.setEndCompoundDrawable(drawable: Drawable?) {
    val currentDrawables = compoundDrawablesRelative

    setCompoundDrawablesRelativeWithIntrinsicBounds(
        currentDrawables[0],
        currentDrawables[1],
        drawable,
        currentDrawables[3]
    )
}

fun EditText.getEndCompoundDrawable(): Drawable? = compoundDrawablesRelative[2]

fun EditText.getStartCompoundDrawable(): Drawable? = compoundDrawablesRelative[0]
