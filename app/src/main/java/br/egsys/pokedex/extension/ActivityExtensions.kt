package br.egsys.pokedex.extension

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.setupFullScreenSystemUiFlags() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}
