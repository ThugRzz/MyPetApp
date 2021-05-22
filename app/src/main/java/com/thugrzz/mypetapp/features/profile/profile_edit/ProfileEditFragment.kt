package com.thugrzz.mypetapp.features.profile.profile_edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtProfileEditBinding
import com.thugrzz.mypetapp.features.main.MainFragment

class ProfileEditFragment : Fragment(R.layout.fmt_profile_edit) {

    private val binding by viewBinding(FmtProfileEditBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        titleBarView.setStartIconClickListener {
            (requireParentFragment() as MainFragment).onBackPressed()
        }
    }

    companion object {

        val TAG = ProfileEditFragment::class.simpleName!!

        fun newInstance() = ProfileEditFragment()
    }
}