package com.thugrzz.mypetapp.features.reference

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtReferenceBinding
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.reference.care.CareReferenceFragment
import com.thugrzz.mypetapp.features.reference.disease.DiseaseReferenceFragment
import com.thugrzz.mypetapp.features.reference.food.FoodReferenceFragment
import com.thugrzz.mypetapp.features.reference.training.TrainingReferenceFragment

class ReferenceFragment : Fragment(R.layout.fmt_reference) {

    private val binding by viewBinding(FmtReferenceBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        feedReferenceView.setOnClickListener {
            (parentFragment as MainFragment).pushFragment(FoodReferenceFragment.newInstance())
        }
        careReferenceView.setOnClickListener {
            (parentFragment as MainFragment).pushFragment(CareReferenceFragment.newInstance())
        }
        diseaseReferenceView.setOnClickListener {
            (parentFragment as MainFragment).pushFragment(DiseaseReferenceFragment.newInstance())
        }
        trainingReferenceView.setOnClickListener {
            (parentFragment as MainFragment).pushFragment(TrainingReferenceFragment.newInstance())
        }
    }

    companion object {

        val TAG = ReferenceFragment::class.simpleName!!

        fun newInstance() = ReferenceFragment()
    }
}