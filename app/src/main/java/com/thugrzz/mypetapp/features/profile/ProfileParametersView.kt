package com.thugrzz.mypetapp.features.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ViewProfileParametersBinding

class ProfileParametersView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewProfileParametersBinding::bind)

    var iconDrawable: Drawable?
        get() = binding.iconView.drawable
        set(value) {
            binding.iconView.setImageDrawable(value)
        }

    var leftValue: CharSequence? = null
        get() = binding.leftValueView.text
        set(value) {
            field = value
            binding.leftValueView.text = value
        }

    var rightValue: CharSequence? = null
        get() = binding.rightValueView.text
        set(value) {
            field = value
            binding.rightValueView.text = value
        }

    init {

        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        inflate(context, R.layout.view_profile_parameters, this)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ProfileParametersView, defStyleAttr, 0
        )

        with(binding) {
            leftTitleView.text = typedArray.getString(R.styleable.ProfileParametersView_leftTitle)
            rightTitleView.text = typedArray.getString(R.styleable.ProfileParametersView_rightTitle)
            iconDrawable = typedArray.getDrawable(R.styleable.ProfileParametersView_icon)
        }

        typedArray.recycle()
    }
}