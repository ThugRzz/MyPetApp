package com.thugrzz.mypetapp.features.reference.disease

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
import com.thugrzz.mypetapp.features.reference.disease.recyclerview.DiseaseReferenceAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiseaseReferenceFragment : Fragment(R.layout.fmt_reference_list) {

    private val binding by viewBinding(FmtReferenceListBinding::bind)
    private val viewModel by viewModel<DiseaseReferenceViewModel>()

    private val adapter = DiseaseReferenceAdapter { item ->
        navigateToReferenceDetails(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        toolbarView.setStartIconClickListener {
            (parentFragment as MainFragment).onBackPressed()
        }
        toolbarView.title = getString(R.string.disease)

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

        val TAG = DiseaseReferenceFragment::class.simpleName!!

        fun newInstance() = DiseaseReferenceFragment()
    }
}