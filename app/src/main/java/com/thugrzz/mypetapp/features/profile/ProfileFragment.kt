package com.thugrzz.mypetapp.features.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.AppBarLayout
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.databinding.FmtProfileBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.profile.dialog.ProfileMenuDialog
import com.thugrzz.mypetapp.features.profile.profile_edit.ProfileEditFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class ProfileFragment : Fragment(R.layout.fmt_profile) {

    private val binding by viewBinding(FmtProfileBinding::bind)

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()

        menuIconView.setOnClickListener {
            ProfileMenuDialog.show(parentFragmentManager)
        }
        editFabView.setOnClickListener {
            (requireParentFragment() as MainFragment)
                .pushFragment(ProfileEditFragment.newInstance())
        }

        collect(viewModel.petNameFlow, collapsingToolbarLayout::setTitle)
        collect(viewModel.petTypeFlow, ::bindType)
        collect(viewModel.petBreedFlow, ::bindBreed)
        collect(viewModel.petSexFlow, ::bindSex)
        collect(viewModel.petStatusFlow, ::bindStatus)
        collect(viewModel.petHeightFlow, ::bindHeight)
        collect(viewModel.petWeightFlow, ::bindWeight)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_data_failed, Toast.LENGTH_LONG).show()
        }
    }

    private fun bindType(petType: PetType) = with(binding.profileContentView) {
        topParamsView.leftValue = petType.type
    }

    private fun bindBreed(breed: PetBreed) = with(binding.profileContentView) {
        topParamsView.rightValue = breed.name
    }

    private fun bindSex(sex: Sex) = with(binding.profileContentView) {
        middleParamsView.leftValue = getString(sex.titleId)
    }

    private fun bindStatus(status: PetStatus) = with(binding.profileContentView) {
        middleParamsView.rightValue = getString(status.titleId)
    }

    private fun bindHeight(height: Double) = with(binding.profileContentView) {
        val stringHeight = getString(R.string.pet_height_param, height)
        bottomParamsView.leftValue = stringHeight
    }

    private fun bindWeight(weight: Double) = with(binding.profileContentView) {
        val stringWeight = getString(R.string.pet_weight_param, weight)
        bottomParamsView.rightValue = stringWeight
    }

    private fun setupAppBar() = with(binding) {
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
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