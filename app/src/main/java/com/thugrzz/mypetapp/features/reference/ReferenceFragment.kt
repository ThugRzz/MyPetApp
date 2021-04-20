package com.thugrzz.mypetapp.features.reference

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtReferenceBinding
import com.thugrzz.mypetapp.util.GridSpacingItemDecoration

class ReferenceFragment : Fragment(R.layout.fmt_reference) {

    private val binding by viewBinding(FmtReferenceBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            titleBarView.title = getString(R.string.reference)
            referencesView.layoutManager = GridLayoutManager(requireContext(), 2, VERTICAL, false)
            val spacing = resources.getDimensionPixelSize(R.dimen.padding_32)
        }
    }

    companion object {

        val TAG = ReferenceFragment::class.simpleName!!

        fun newInstance() = ReferenceFragment()
    }
}