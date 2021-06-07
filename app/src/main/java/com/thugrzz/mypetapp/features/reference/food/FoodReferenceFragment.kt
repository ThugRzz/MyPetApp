package com.thugrzz.mypetapp.features.reference.food

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails
import com.thugrzz.mypetapp.databinding.FmtReferenceListBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.reference.details.ReferenceDetailsFragment
import com.thugrzz.mypetapp.features.reference.dialog.FilterReferencesDialogFragment
import com.thugrzz.mypetapp.features.reference.dialog.ReferencesFilter
import com.thugrzz.mypetapp.features.reference.food.recyclerview.FoodReferenceAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodReferenceFragment : Fragment(R.layout.fmt_reference_list),
    FilterReferencesDialogFragment.Callback {

    private val binding by viewBinding(FmtReferenceListBinding::bind)
    private val viewModel by viewModel<FoodReferenceViewModel>()

    private val adapter = FoodReferenceAdapter { item ->
        navigateToReferenceDetails(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        toolbarView.setStartIconClickListener {
            (parentFragment as MainFragment).onBackPressed()
        }
        toolbarView.title = getString(R.string.feed)

        referencesView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        referencesView.adapter = adapter

        collect(viewModel.referencesFlow, ::bindReferences)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.filterTypeFlow, ::bindFilterType)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_reference, Toast.LENGTH_LONG).show()
        }
    }

    override fun onFilterClick(filter: ReferencesFilter) {
        viewModel.onFilterSelected(filter)
    }

    private fun bindFilterType(filter: ReferencesFilter) {
        binding.toolbarView.setEndIconClickListener {
            FilterReferencesDialogFragment.show(parentFragmentManager, this, filter)
        }
    }

    private fun bindReferences(references: List<ReferenceDetails>) = with(binding) {
        referencesEmptyLayout.isVisible = references.isEmpty()
        referencesView.isVisible = references.isNotEmpty()
        adapter.setItems(references)
    }

    private fun navigateToReferenceDetails(item: ReferenceDetails) {
        ReferenceDetailsFragment.show(parentFragmentManager, item)
    }

    companion object {

        val TAG = FoodReferenceFragment::class.simpleName!!

        fun newInstance() = FoodReferenceFragment()
    }
}