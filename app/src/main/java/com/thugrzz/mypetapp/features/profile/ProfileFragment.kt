package com.thugrzz.mypetapp.features.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtProfileBinding
import com.thugrzz.mypetapp.features.profile.dialog.ProfileMenuDialog
import kotlin.math.roundToInt


class ProfileFragment : Fragment(R.layout.fmt_profile) {

    private val binding by viewBinding(FmtProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        with(binding) {
            toolbarView.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            menuIconView.setOnClickListener {
                ProfileMenuDialog.show(parentFragmentManager)
            }
        }
    }

    private fun setupAppBar() = with(binding) {
        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val currentRatio = calculateOffsetRatio(verticalOffset, totalScrollRange)
            profileIconView.alpha = currentRatio

            val mainContentPadding = resources.getDimensionPixelSize(R.dimen.padding_32)
            val currentPadding = calculateCollapsedPadding(mainContentPadding, currentRatio)
            profileContentView.root.setPadding(0, currentPadding, 0, 0)

            setupToolbarIcons(currentRatio)
        })
    }

    private fun setupToolbarIcons(ratio: Float) {
        with(binding) {
            val isRatioZero = ratio == 0f
            menuIconView.visibility = if (isRatioZero) View.VISIBLE else View.INVISIBLE
            toolbarView.navigationIcon?.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    if (isRatioZero) R.color.colorAccent else R.color.white
                )
            )
        }
    }

    private fun calculateOffsetRatio(offset: Int, totalScrollRange: Int) =
        MAX_RATIO + (offset.toFloat() / totalScrollRange)

    private fun calculateCollapsedPadding(padding: Int, ratio: Float) =
        (padding * ratio).roundToInt()

    companion object {
        private const val MAX_RATIO = 1

        val TAG = ProfileFragment::class.simpleName!!

        fun newInstance() = ProfileFragment()
    }
}