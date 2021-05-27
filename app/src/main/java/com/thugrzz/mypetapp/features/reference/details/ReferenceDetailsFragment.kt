package com.thugrzz.mypetapp.features.reference.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.DimensionalBottomSheetFragment
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails
import com.thugrzz.mypetapp.databinding.DlgReferenceDetailsBinding
import com.thugrzz.mypetapp.ext.parcelableArgument
import com.thugrzz.mypetapp.features.reference.details.recyclerview.ReferenceStepsAdapter

class ReferenceDetailsFragment : DimensionalBottomSheetFragment(R.layout.dlg_reference_details) {

    private val binding by viewBinding(DlgReferenceDetailsBinding::bind)

    private var details by parcelableArgument<ReferenceDetails>()

    private val adapter = ReferenceStepsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        titleView.text = details.title

        referenceStepsView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        referenceStepsView.adapter = adapter
        adapter.setItems(details.referenceSteps)
    }

    companion object {

        val TAG = ReferenceDetailsFragment::class.simpleName!!

        fun show(
            fragmentManager: FragmentManager,
            details: ReferenceDetails
        ) = ReferenceDetailsFragment().apply {
            this.details = details
            show(fragmentManager, TAG)
        }
    }
}