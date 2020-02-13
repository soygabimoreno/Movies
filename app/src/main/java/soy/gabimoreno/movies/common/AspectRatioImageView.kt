package soy.gabimoreno.movies.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import soy.gabimoreno.movies.R

class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_RATIO = 1F
    }

    private var ratio = DEFAULT_RATIO

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            with(typedArray) {
                ratio = getFloat(R.styleable.AspectRatioImageView_ratio, DEFAULT_RATIO)
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight

        if (width == 0 && height == 0) {
            return
        }

        if (width > 0) {
            height = (width * ratio).toInt()
        } else {
            width = (height / ratio).toInt()
        }

        setMeasuredDimension(width, height)
    }
}
