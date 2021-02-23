package com.thugrzz.mypetapp.features.main

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.card.MaterialCardView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ViewNavigationBinding
import com.thugrzz.mypetapp.databinding.ViewNavigationTabBinding
import com.thugrzz.mypetapp.ext.resolveAttribute

class NavView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewNavigationBinding::bind)

    interface TabClickListener {
        fun onNavTabClick(tab: NavTab)
    }

    private val defaultColor = ColorStateList.valueOf(
        ContextCompat.getColor(context, R.color.gray_BDBDBD)
    )
    private val selectedColor = ColorStateList.valueOf(
        ContextCompat.getColor(context, R.color.orange_CD5334)
    )

    private val tabs = NavTab.values()

    var tabClickListener: TabClickListener? = null
    var selectedTab: NavTab? = null
        set(value) {
            field = value
            for (tab in tabs) {
                getTabView(tab).setTabSelected(tab == value)
            }
        }

    init {
        val resources = context.resources
        radius = resources.getDimension(R.dimen.radius_10)
        elevation = resources.getDimension(R.dimen.elevation_5)

        inflate(context, R.layout.view_navigation, this)

        val layoutParams = LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            weight = 1f
        }
        for (tab in tabs) {
            val tabView = TabView(context, tab)
            tabView.setOnClickListener {
                tabClickListener?.onNavTabClick(tab)
            }
            binding.navTabsLayout.addView(tabView, layoutParams)
        }
    }

    fun showBadge(tab: NavTab) {
        getTabView(tab).isBadgeVisible = true
    }

    fun hideBadge(tab: NavTab) {
        getTabView(tab).isBadgeVisible = false
    }

    private fun getTabView(tab: NavTab) = binding.navTabsLayout.getChildAt(tab.ordinal) as TabView

    private inner class TabView(
        context: Context,
        tab: NavTab
    ) : FrameLayout(context) {

        private val binding by viewBinding(ViewNavigationTabBinding::bind)

        var isBadgeVisible: Boolean
            get() = binding.navTabBadgeView.isVisible
            set(value) {
                binding.navTabBadgeView.isVisible = value
            }

        init {
            foreground = ContextCompat.getDrawable(
                context,
                context.resolveAttribute(R.attr.selectableItemBackgroundBorderless)
            )
            inflate(context, R.layout.view_navigation_tab, this)
            binding.navTabIconView.setImageResource(tab.iconId)
            binding.navTabTitleView.setText(tab.titleId)
        }

        fun setTabSelected(isSelected: Boolean) = with(binding) {
            if (isSelected) {
                navTabIconView.imageTintList = selectedColor
                navTabTitleView.setTextColor(selectedColor)
            } else {
                navTabIconView.imageTintList = defaultColor
                navTabTitleView.setTextColor(defaultColor)
            }
        }
    }
}
