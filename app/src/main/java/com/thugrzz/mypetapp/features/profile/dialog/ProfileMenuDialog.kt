package com.thugrzz.mypetapp.features.profile.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.BaseBottomTransparentDialogFragment
import com.thugrzz.mypetapp.databinding.DlgProfileMenuBinding
import com.thugrzz.mypetapp.features.profile.ProfileSettingsFragment

class ProfileMenuDialog : BaseBottomTransparentDialogFragment(R.layout.dlg_profile_menu) {

    private val binding by viewBinding(DlgProfileMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            settingsCommentView.setOnClickListener {
                parentFragmentManager.commit {
                    replace(
                        R.id.mainFragmentContainer,
                        ProfileSettingsFragment.newInstance(),
                        ProfileSettingsFragment.TAG
                    )
                    addToBackStack(null)
                }
                dismiss()
            }
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