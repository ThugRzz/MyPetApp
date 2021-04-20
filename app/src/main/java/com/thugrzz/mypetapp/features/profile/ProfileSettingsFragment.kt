package com.thugrzz.mypetapp.features.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtProfileSettingsBinding
import com.thugrzz.mypetapp.features.profile.dialog.ChangeParamDialog

class ProfileSettingsFragment : Fragment(R.layout.fmt_profile_settings) {

    private val binding by viewBinding(FmtProfileSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        titleBarView.setStartIconClickListener {
            parentFragmentManager.popBackStack()
        }

        nameView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.name_param),
                description = getString(R.string.name_description),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        emailView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.email_param),
                description = getString(R.string.email_description, emailView.middleText),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        addressView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.address_param),
                description = getString(R.string.address_description),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        phoneView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.phone_param),
                description = getString(R.string.phone_description, phoneView.middleText),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        passwordView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.password_param),
                description = getString(R.string.password_description),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
    }

    companion object {

        val TAG = ProfileSettingsFragment::class.simpleName!!

        fun newInstance() = ProfileSettingsFragment()
    }
}