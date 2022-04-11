package br.egsys.pokedex.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.updateMargins
import br.egsys.pokedex.R

class DefaultBottomSheetLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
        setBackgroundResource(R.drawable.bottom_control_background)
        addThumbIndicator()
    }

    private fun addThumbIndicator() {
        addView(
            View(context).apply {
                setBackgroundResource(R.drawable.bottom_sheet_thumb_indicator)
            },
            LayoutParams(
                resources.getDimension(R.dimen.bottom_sheet_thumb_indicator_width).toInt(),
                resources.getDimension(R.dimen.bottom_sheet_thumb_indicator_height).toInt()
            ).apply {
                gravity = Gravity.CENTER
                updateMargins(
                    top = resources.getDimension(R.dimen.bottom_sheet_thumb_indicator_top_offset)
                        .toInt()
                )
            }
        )
    }
}
