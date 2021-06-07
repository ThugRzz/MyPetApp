package com.thugrzz.mypetapp.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.card.MaterialCardView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ViewProfileItemBinding

class ProfileItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.cardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewProfileItemBinding::bind)

    var iconDrawable: Drawable?
        get() = binding.iconView.drawable
        set(value) {
            binding.iconView.setImageDrawable(value)
        }

    var topText: CharSequence?
        get() = binding.topTextView.text
        set(value) {
            with(binding) {
                topTextView.text = value
                topTextView.isVisible = (value != null)
            }
        }

    var middleText: CharSequence?
        get() = binding.middleTextView.text
        set(value) {
            with(binding) {
                middleTextView.text = value
                middleTextView.isVisible = (value != null)
            }
        }

    var bottomText: CharSequence?
        get() = binding.bottomTextView.text
        set(value) {
            with(binding) {
                bottomTextView.text = value
                bottomTextView.isVisible = (value != null)
            }
        }

    var actionIconDrawable: Drawable?
        get() = binding.actionIconView.drawable
        set(value) {
            with(binding) {
                actionIconView.setImageDrawable(value)
                actionIconView.isVisible = value != null
            }
        }


    init {
        val resources = context.resources
        radius = resources.getDimension(R.dimen.radius_16)
        elevation = resources.getDimension(R.dimen.elevation_5)

        inflate(context, R.layout.view_profile_item, this)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ProfileItemView, defStyleAttr, 0
        )

        iconDrawable = typedArray.getDrawable(R.styleable.ProfileItemView_profileItemIcon)
        setIconBackgroundTintColor(
            typedArray.getColor(R.styleable.ProfileItemView_profileItemIconBgColor, Color.WHITE)
        )

        topText = typedArray.getText(R.styleable.ProfileItemView_profileItemTopText)
        middleText = typedArray.getText(R.styleable.ProfileItemView_profileItemMiddleText)
        bottomText = typedArray.getText(R.styleable.ProfileItemView_profileItemBottomText)

        actionIconDrawable =
            typedArray.getDrawable(R.styleable.ProfileItemView_profileItemActionIcon)

        typedArray.recycle()
    }

    fun setIconBackgroundTintColor(@ColorInt color: Int) {
        binding.iconView.setBackgroundColor(color)
    }

    fun setActionIconClickListener(clickListener: () -> Unit) =
        binding.actionIconView.setOnClickListener { clickListener() }
}
