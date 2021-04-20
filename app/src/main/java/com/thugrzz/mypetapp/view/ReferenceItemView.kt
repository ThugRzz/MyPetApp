package com.thugrzz.mypetapp.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.card.MaterialCardView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ViewReferenceItemBinding

class ReferenceItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewReferenceItemBinding::bind)

    init {

        val bgColor = ContextCompat.getColor(context, R.color.colorAccent)
        val height = resources.getDimensionPixelSize(R.dimen.height_150)
        val width = resources.getDimensionPixelSize(R.dimen.height_150)

        backgroundTintList = ColorStateList.valueOf(bgColor)
        strokeWidth = resources.getDimensionPixelSize(R.dimen.width_1)
        strokeColor = bgColor
        radius = resources.getDimension(R.dimen.radius_24)

        inflate(context, R.layout.view_reference_item, this)

        this.layoutParams = LayoutParams(width, height)
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ReferenceItemView,
            0,
            0
        )
        with(binding) {
            iconView.setImageResource(
                typedArray.getResourceId(
                    R.styleable.ReferenceItemView_itemDrawable,
                    0
                )
            )
            titleView.text = typedArray.getString(R.styleable.ReferenceItemView_itemTitle)
        }

        typedArray.recycle()
    }
}