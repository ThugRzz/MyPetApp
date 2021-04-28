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
                title = getString(R.string.param_name),
                description = getString(R.string.description_name),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        emailView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.param_email),
                description = getString(R.string.description_email, emailView.middleText),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        addressView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.param_address),
                description = getString(R.string.description_address),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        phoneView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.param_phone),
                description = getString(R.string.description_phone, phoneView.middleText),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
        passwordView.setActionIconClickListener {
            ChangeParamDialog.Builder(
                title = getString(R.string.param_password),
                description = getString(R.string.description_password),
                target = this@ProfileSettingsFragment
            ).create().show(parentFragmentManager)
        }
    }

    companion object {

        val TAG = ProfileSettingsFragment::class.simpleName!!

        fun newInstance() = ProfileSettingsFragment()
    }
}