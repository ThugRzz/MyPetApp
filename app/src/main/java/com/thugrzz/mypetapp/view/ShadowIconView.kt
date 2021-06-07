package com.thugrzz.mypetapp.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.ext.resolveAttributeDrawable

class ShadowIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val imageView = AppCompatImageView(context)

    var iconDrawable: Drawable?
        get() = imageView.drawable
        set(value) {
            imageView.setImageDrawable(value)
        }

    init {
        val resources = context.resources
        radius = resources.getDimension(R.dimen.radius_10)
        elevation = resources.getDimension(R.dimen.elevation_5)
        foreground = context.resolveAttributeDrawable(R.attr.selectableItemBackground)

        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        val size = resources.getDimensionPixelSize(R.dimen.height_40)
        val layoutParams = LayoutParams(size, size)
        addView(imageView, layoutParams)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ShadowIconView, defStyleAttr, 0
        )
        iconDrawable = typedArray.getDrawable(R.styleable.ShadowIconView_shadowIcon)
        iconDrawable?.setTint(typedArray.getColor(R.styleable.ShadowIconView_shadowIconTint, 0))
        typedArray.recycle()
    }
}