package com.thugrzz.mypetapp.features.reference.care

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
import com.thugrzz.mypetapp.features.reference.care.recyclerview.CareReferenceAdapter
import com.thugrzz.mypetapp.features.reference.details.ReferenceDetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareReferenceFragment : Fragment(R.layout.fmt_reference_list) {

    private val binding by viewBinding(FmtReferenceListBinding::bind)
    private val viewModel by viewModel<CareReferenceViewModel>()

    private val adapter = CareReferenceAdapter { item ->
        navigateToReferenceDetails(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        toolbarView.setStartIconClickListener {
            (parentFragment as MainFragment).onBackPressed()
        }
        toolbarView.title = getString(R.string.pet_care)

        referencesView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        referencesView.adapter = adapter

        collect(viewModel.referencesFlow, adapter::setItems)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_reference, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToReferenceDetails(item: ReferenceDetails) {
        ReferenceDetailsFragment.show(parentFragmentManager, item)
    }

    companion object {

        val TAG = CareReferenceFragment::class.simpleName!!

        fun newInstance() = CareReferenceFragment()
    }
}