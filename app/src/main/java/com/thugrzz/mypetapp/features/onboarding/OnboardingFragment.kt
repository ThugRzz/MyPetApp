package com.thugrzz.mypetapp.features.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtOnboardingBinding
import com.thugrzz.mypetapp.features.onboarding.onboarding_recycler.OnboardingAdapter
import com.thugrzz.mypetapp.features.start.StartFragment
import com.thugrzz.mypetapp.util.NavigationUtil

class OnboardingFragment : Fragment(R.layout.fmt_onboarding), OnboardingAdapter.ClickListener {

    private val binding by viewBinding(FmtOnboardingBinding::bind)

    private val adapter = OnboardingAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingPager.adapter = adapter
    }

    private fun navigateToNextFragment(pageNumber: Int) {
        val nextPageNumber = pageNumber + 1
        if (nextPageNumber == PAGE_COUNT) {
            navigateToStartFragment()
        } else {
            binding.onboardingPager.setCurrentItem(nextPageNumber, true)
        }
    }

    private fun navigateToStartFragment() =
        NavigationUtil.showNextFragment(
            parentFragmentManager = parentFragmentManager,
            R.id.mainActivityContainer,
            StartFragment.newInstance(),
            StartFragment.TAG
        )

    override fun onNextPageClicked(page: Int) {
        navigateToNextFragment(page)
    }

    override fun onSkipOnboardingClicked() {
        navigateToStartFragment()
    }

    companion object {

        private const val PAGE_COUNT = 4
        val TAG = OnboardingFragment::class.simpleName!!

        fun newInstance() = OnboardingFragment()
    }
}