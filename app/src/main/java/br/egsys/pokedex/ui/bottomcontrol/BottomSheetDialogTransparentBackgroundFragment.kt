package br.egsys.pokedex.ui.bottomcontrol

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import br.egsys.pokedex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomSheetDialogTransparentBackgroundFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomSheetBackground()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener(::initialSetup)
        return dialog
    }

    private fun initialSetup(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog

        bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )?.let { bottomSheet ->
            setupExpanded(bottomSheet)
        }
    }

    private fun setupBottomSheetBackground() {
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    private fun setupExpanded(bottomSheet: View) {
        BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = Resources.getSystem().displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}
