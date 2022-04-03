package br.egsys.pokedex.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.closeKeyboard(focusedView: View) {
    (
        getSystemService(Activity.INPUT_METHOD_SERVICE)
            as? InputMethodManager
        )?.hideSoftInputFromWindow(focusedView.windowToken, 0)
    focusedView.clearFocus()
}
