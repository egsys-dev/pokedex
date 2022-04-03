package br.egsys.pokedex.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import br.egsys.pokedex.R
import br.egsys.pokedex.extension.getEndCompoundDrawable
import br.egsys.pokedex.extension.getStartCompoundDrawable
import br.egsys.pokedex.extension.setEndCompoundDrawable
import br.egsys.pokedex.extension.setStartCompoundDrawable
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style

@Styleable("SearchBarView")
class SearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val clearSearchDrawable: Drawable? =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_search_clear, null)

    private var searchButtonClickListener: (() -> Unit)? = null
    private var clearSearchClickListener: (() -> Unit)? = null

    init {
        style(attrs)
        style(R.style.SearchEditTextStyle)

        setupSearchImeOption()
        setupTextChangeListener()
        setupTouchListener()
    }

    private fun setupTextChangeListener() {
        addTextChangedListener { text ->
            setupCancelButtonToText(text?.toString())
        }
    }

    private fun setupSearchImeOption() {
        imeOptions = imeOptions or EditorInfo.IME_ACTION_SEARCH
    }

    private fun setupCancelButtonToText(text: String?) {
        val endDrawable = if (text?.isNotBlank() == true) {
            clearSearchDrawable
        } else {
            null
        }

        if (getEndCompoundDrawable() != endDrawable) {
            setEndDrawable(endDrawable)
        }
    }

    private fun setupTouchListener() {
        setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                getStartCompoundDrawable()?.let { startDrawable ->
                    if (event.rawX <= (left + startDrawable.bounds.width())) {
                        searchButtonClickListener?.invoke()
                        return@setOnTouchListener true
                    }
                }

                getEndCompoundDrawable()?.let { endDrawable ->
                    if (event.rawX >= (right - endDrawable.bounds.width())) {
                        clearSearchClickListener?.invoke()
                        return@setOnTouchListener true
                    }
                }
            }

            false
        }
    }

    @Attr(R.styleable.SearchBarView_drawableStartCompat)
    fun setStartDrawable(drawable: Drawable?) {
        setStartCompoundDrawable(drawable)
    }

    @Attr(R.styleable.SearchBarView_drawableEndCompat)
    fun setEndDrawable(drawable: Drawable?) {
        setEndCompoundDrawable(drawable)
    }

    fun setSearchButtonClickListener(block: (() -> Unit)?) {
        searchButtonClickListener = block
    }

    fun setClearSearchButtonClickListener(block: (() -> Unit)?) {
        clearSearchClickListener = block
    }
}
