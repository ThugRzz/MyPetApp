package com.thugrzz.mypetapp.features.profile.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.BaseBottomTransparentDialogFragment
import com.thugrzz.mypetapp.databinding.DlgProfileMenuBinding
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.profile.profile_edit.ProfileEditFragment
import com.thugrzz.mypetapp.features.profile.profile_settings.ProfileSettingsFragment

class ProfileMenuDialog : BaseBottomTransparentDialogFragment(R.layout.dlg_profile_menu) {

    private val binding by viewBinding(DlgProfileMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        editProfileView.setOnClickListener {
            (requireParentFragment() as MainFragment)
                .pushFragment(ProfileEditFragment.newInstance())
            dismiss()
        }
        settingsView.setOnClickListener {
            (requireParentFragment() as MainFragment)
                .pushFragment(ProfileSettingsFragment.newInstance())
            dismiss()
        }
    }

    companion object {

        val TAG = ProfileMenuDialog::class.simpleName!!

        fun show(fragmentManager: FragmentManager) {
            val dialog = ProfileMenuDialog()
            dialog.show(fragmentManager, TAG)
        }
    }
}