package com.thugrzz.mypetapp.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ViewLoaderBinding

class Loader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewLoaderBinding::bind)

    init {
        inflate(context, R.layout.view_loader, this)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.Loader, defStyleAttr, 0
        )

        val zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom)
        binding.loaderIconView.imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.white)
        )
        binding.loaderIconView.startAnimation(zoomAnimation)

        val isDialog = typedArray.getBoolean(R.styleable.Loader_isDialog, false)
        if (isDialog) binding.fontView.background =
            ContextCompat.getDrawable(context, R.drawable.bg_dialog_loader)

        typedArray.recycle()
    }
}