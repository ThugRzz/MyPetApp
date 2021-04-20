package com.thugrzz.mypetapp.features.onboarding.onboarding_recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.OnboardingItem
import com.thugrzz.mypetapp.databinding.ItemOnboardingPageBinding
import com.thugrzz.mypetapp.ext.getColorCompat
import com.thugrzz.mypetapp.ext.inflateHolder

class OnboardingViewHolder(
    itemView: View,
    private val listener: OnboardingAdapter.ClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(ItemOnboardingPageBinding::bind)

    fun bind(item: OnboardingItem) = with(binding) {
        val context = root.context
        val backgroundColor = context.getColorCompat(item.backgroundColor)
        titleView.text = item.title
        descriptionView.text = item.description
        onboardingImageView.setImageResource(item.iconRes)
        root.setBackgroundColor(backgroundColor)
        indicatorsView.currentPosition = item.pageNumber
        skipView.setOnClickListener { listener.onSkipOnboardingClicked() }
        actionButton.setOnClickListener { listener.onNextPageClicked(item.pageNumber) }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            listener: OnboardingAdapter.ClickListener
        ): OnboardingViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_onboarding_page)
            return OnboardingViewHolder(itemView, listener)
        }
    }
}