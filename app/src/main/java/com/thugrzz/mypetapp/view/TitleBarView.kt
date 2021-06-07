package com.thugrzz.mypetapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ViewTitleBarBinding

class TitleBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewTitleBarBinding::bind)

    var title: CharSequence?
        get() = binding.titleView.text
        set(value) {
            binding.titleView.text = value
        }

    var startIconDrawable: Drawable?
        get() = binding.titleStartIconView.iconDrawable
        set(value) {
            with(binding) {
                titleStartIconView.iconDrawable = value
                titleStartIconView.visibility = if (value != null) VISIBLE else INVISIBLE
            }
        }

    var endIconDrawable: Drawable?
        get() = binding.titleEndIconView.iconDrawable
        set(value) {
            with(binding) {
                titleEndIconView.iconDrawable = value
                titleEndIconView.visibility = if (value != null) VISIBLE else INVISIBLE
            }
        }

    init {
        orientation = HORIZONTAL
        inflate(context, R.layout.view_title_bar, this)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.TitleBarView, defStyleAttr, 0
        )
        title = typedArray.getText(R.styleable.TitleBarView_titleText)
        startIconDrawable = typedArray.getDrawable(R.styleable.TitleBarView_titleStartIcon)
        endIconDrawable = typedArray.getDrawable(R.styleable.TitleBarView_titleEndIcon)
        typedArray.recycle()
    }

    fun setStartIconClickListener(clickListener: () -> Unit) =
        binding.titleStartIconView.setOnClickListener { clickListener() }

    fun setEndIconClickListener(clickListener: () -> Unit) =
        binding.titleEndIconView.setOnClickListener { clickListener() }
}
