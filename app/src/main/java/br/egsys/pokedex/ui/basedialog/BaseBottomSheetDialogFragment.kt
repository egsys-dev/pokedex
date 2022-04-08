package br.egsys.pokedex.ui.basedialog

import android.animation.LayoutTransition
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import br.egsys.pokedex.databinding.FragmentBaseBottomSheetBinding
import br.egsys.pokedex.extension.findViewByClass
import br.egsys.pokedex.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

open class BaseBottomSheetDialogFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentBaseBottomSheetBinding
    private var isScrollViewHandlingScroll: Boolean = false
        set(value) {
            field = value
            updateDialogDragState()
        }

    open var isDraggable: Boolean = true
        set(value) {
            field = value
            updateDialogDragState()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentBaseBottomSheetBinding.inflate(inflater, container, false)

        viewBinding.contentContainer.layoutTransition = LayoutTransition().apply {
            setInterpolator(LayoutTransition.CHANGING, AccelerateDecelerateInterpolator())
            setAnimateParentHierarchy(false)
        }

        return viewBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        setupDragListener(dialog)

        return dialog
    }

    open fun setFragmentContent(
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false,
    ) {
        handleChildFragmentScrollView(fragment)
        childFragmentManager.commit {

            if (addToBackStack) {
                add(viewBinding.contentContainer.id, fragment, tag)
                addToBackStack(tag)
            } else {
                replace(viewBinding.contentContainer.id, fragment, tag)
            }
        }
    }

    protected fun addView(view: View, layoutParams: LinearLayout.LayoutParams) {
        viewBinding.root.addView(view, layoutParams)
    }

    private fun handleChildFragmentScrollView(fragment: Fragment) {
        (view as? ViewGroup)?.findViewByClass(ScrollView::class.java)?.doOnAttach {
            setupScrollViewListeners(fragment as ScrollView)
        }
    }

    private fun setupScrollViewListeners(scrollView: ScrollView) {
        scrollView.setOnTouchListener { v, event ->
            val scrollView = (v as? ScrollView)?.takeIf { it.isAttachedToWindow }
                ?: return@setOnTouchListener false

            when {
                event.action == MotionEvent.ACTION_DOWN && scrollView.canScrollVertically(-1) -> {
                    isScrollViewHandlingScroll = true
                }
                event.action != MotionEvent.ACTION_MOVE -> {
                    isScrollViewHandlingScroll = false
                }
            }

            false
        }

        scrollView.doOnDetach {
            isScrollViewHandlingScroll = false
        }
    }

    private fun updateDialogDragState() {
        (dialog as? BottomSheetDialog)?.takeIf { it.isShowing }?.behavior?.isDraggable =
            isDraggable && !isScrollViewHandlingScroll
    }

    private fun setupDragListener(dialog: Dialog) {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                private var latestOffset = 0f

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_SETTLING) {
                        if (latestOffset >= -.25f) {
                            this@apply.state = BottomSheetBehavior.STATE_EXPANDED
                        } else {
                            dismiss()
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    latestOffset = slideOffset
                }
            })
        }
    }
}
