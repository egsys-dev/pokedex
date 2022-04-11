package br.egsys.pokedex.extension

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.os.Build
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.text.TextUtilsCompat
import androidx.core.view.*
import java.util.*

const val DEFAULT_ANIMATION_DURATION = 300L

typealias InitialPadding = Rect
typealias InitialMargin = Rect

inline fun View.fadeOutAnimator(
    duration: Long = DEFAULT_ANIMATION_DURATION,
    crossinline withStartAction: () -> Unit = {},
    crossinline withEndAction: () -> Unit = {}
): ViewPropertyAnimator =
    animate().apply {
        alpha(0f)
        this.duration = duration
        withStartAction {
            withStartAction()
        }
        withEndAction {
            isVisible = false
            alpha = 1f
            withEndAction()
        }
    }

inline fun View.fadeInAnimator(
    duration: Long = DEFAULT_ANIMATION_DURATION,
    crossinline withStartAction: () -> Unit = {},
    crossinline withEndAction: () -> Unit = {}
): ViewPropertyAnimator =
    animate().apply {
        alpha(1f)
        this.duration = duration
        withStartAction {
            isVisible = true
            withStartAction()
        }
        withEndAction {
            withEndAction()
        }
    }

inline fun View.fadeIn(
    duration: Long = DEFAULT_ANIMATION_DURATION,
    crossinline withStartAction: () -> Unit = {},
    crossinline withEndAction: () -> Unit = {}
) = fadeInAnimator(duration, withStartAction, withEndAction).start()

inline fun View.fadeOut(
    duration: Long = DEFAULT_ANIMATION_DURATION,
    crossinline withStartAction: () -> Unit = {},
    crossinline withEndAction: () -> Unit = {}
) = fadeOutAnimator(duration, withStartAction, withEndAction).start()

fun View.shrinkAnimator(to: Int = 0, duration: Long = DEFAULT_ANIMATION_DURATION): ValueAnimator {
    val layoutParams = this.layoutParams

    return ValueAnimator.ofInt(measuredHeight, to).apply {
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            layoutParams.height = it.animatedValue as Int
            this@shrinkAnimator.layoutParams = layoutParams
        }
        doOnEnd {
            visibility = View.GONE
        }
    }
}

fun View.stretchAnimator(to: Int, duration: Long = DEFAULT_ANIMATION_DURATION): ValueAnimator {
    val layoutParams = this.layoutParams

    return ValueAnimator.ofInt(measuredHeight, to).apply {
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            layoutParams.height = it.animatedValue as Int
            this@stretchAnimator.layoutParams = layoutParams
            requestLayout()
        }
        doOnStart {
            visibility = View.VISIBLE
        }
    }
}

inline fun View.onClickListener(delay: Long = 1000L, crossinline block: View.() -> Unit) {
    setOnClickListener {
        isEnabled = false
        postDelayed({ isEnabled = true }, delay)
        block()
    }
}

fun View.requestKeyboard() {
    requestFocus()

    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
        this,
        InputMethodManager.SHOW_IMPLICIT
    )
}

fun View.updateMarginLayoutParams(applier: ViewGroup.MarginLayoutParams.() -> Unit) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply(applier)
}

fun View.getGlobalVisibleRect(removeStatusBarHeight: Boolean = true): Rect {
    val rect = Rect()
    getGlobalVisibleRect(rect)

    if (removeStatusBarHeight) {
        context.resources.getIdentifier("status_bar_height", "dimen", "android").takeIf { it > 0 }
            ?.let { statusBarResId ->
                val statusBarHeight = context.resources.getDimensionPixelSize(statusBarResId)
                rect.top -= statusBarHeight
                rect.bottom -= statusBarHeight
            }
    }

    return rect
}

fun View.translateTo(referenceRect: Rect, topOffset: Int = 0) {
    x = referenceRect.left.toFloat() + referenceRect.width() / 2f - measuredHeight / 2f
    y = referenceRect.top.toFloat() + referenceRect.height() / 2f - measuredWidth / 2f + topOffset
}

fun TextView.enableUnderline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun View.updateMargin(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) {
    updateMarginLayoutParams {
        setMargins(left, top, right, bottom)
    }
}

fun View.doOnApplyWindowInsets(applier: (View, WindowInsets, InitialPadding, InitialMargin) -> Unit) {
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)

    setOnApplyWindowInsetsListener { v, insets ->
        applier(v, insets, initialPadding, initialMargin)
        insets
    }
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    InitialPadding(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun recordInitialMarginForView(view: View) =
    InitialMargin(view.marginLeft, view.marginTop, view.marginRight, view.marginBottom)

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun View.getViewRect(): RectF = RectF(x, y, x + width, y + height)

val View.isLTR: Boolean
    get() = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR

fun TextView.setTextAppearanceCompat(context: Context, @StyleRes styleId: Int) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        setTextAppearance(styleId)
    } else {
        setTextAppearance(context, styleId)
    }
}

fun View.setVisibilityWithAnimation(
    isVisible: Boolean,
    duration: Long = DEFAULT_ANIMATION_DURATION
) {
    if (isVisible) {
        fadeIn(duration)
    } else {
        fadeOut(duration)
    }
}

/*
   The setBackgroundTintList is broken on API 21, this is a work around to fix this
    @see https://stackoverflow.com/a/63602100/10762301
    @see https://stackoverflow.com/a/33640551/10762301
 */
fun View.setCompatBackgroundTintList(colorStateList: ColorStateList?) {
    ViewCompat.setBackgroundTintList(this, colorStateList)

    if (Build.VERSION.SDK_INT == 21 && colorStateList != null && background != null) {
        val color = colorStateList.getColorForState(background.state, Color.TRANSPARENT)
        background.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}
