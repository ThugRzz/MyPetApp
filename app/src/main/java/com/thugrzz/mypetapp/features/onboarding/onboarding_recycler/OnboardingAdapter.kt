package com.thugrzz.mypetapp.features.onboarding.onboarding_recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.util.onboarding.OnboardingData

class OnboardingAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<OnboardingViewHolder>() {

    interface ClickListener{
        fun onNextPageClicked(page:Int)
        fun onSkipOnboardingClicked()
    }

    private val items = OnboardingData.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder =
        OnboardingViewHolder.create(parent, listener)

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}