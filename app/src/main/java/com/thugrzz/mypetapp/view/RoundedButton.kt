package com.thugrzz.mypetapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.card.MaterialCardView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.ext.resolveAttributeDrawable

class RoundedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val textView = AppCompatTextView(context)

    var text: CharSequence?
        get() = textView.text
        set(value) {
            textView.text = value
        }

    init {
        addView(textView)

        elevation = resources.getDimension(R.dimen.elevation_0)
        foreground = context.resolveAttributeDrawable(R.attr.selectableItemBackground)

        textView.apply {
            minHeight = resources.getDimensionPixelSize(R.dimen.height_48)
            gravity = Gravity.CENTER
            maxLines = 1
            val padding = resources.getDimensionPixelSize(R.dimen.padding_8)
            setPaddingRelative(padding, 0, padding, 0)
        }

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.RoundedButton, defStyleAttr, 0
        )
        radius = typedArray.getDimension(
            R.styleable.RoundedButton_roundedButtonRadius,
            context.resources.getDimension(R.dimen.radius_27)
        )

        foreground = typedArray.getDrawable(R.styleable.RoundedButton_roundedButtonForeground)

        backgroundTintList =
            typedArray.getColorStateList(R.styleable.RoundedButton_roundedButtonBgColor)

        text = typedArray.getText(R.styleable.RoundedButton_roundedButtonText)
        setTextAppearance(
            typedArray.getResourceId(
                R.styleable.RoundedButton_roundedButtonTextAppearance,
                R.style.TextRoundedButton
            )
        )
        typedArray.recycle()
    }

    fun setTextAppearance(@StyleRes resId: Int) = textView.setTextAppearance(resId)

}