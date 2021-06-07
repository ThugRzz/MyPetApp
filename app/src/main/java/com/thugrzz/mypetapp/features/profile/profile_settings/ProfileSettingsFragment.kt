package com.thugrzz.mypetapp.features.profile.profile_settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.UserParam
import com.thugrzz.mypetapp.databinding.FmtProfileSettingsBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.profile.dialog.ChangeParamDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileSettingsFragment : Fragment(R.layout.fmt_profile_settings),
    ChangeParamDialog.Callbacks {

    private val binding by viewBinding(FmtProfileSettingsBinding::bind)

    private val viewModel by viewModel<ProfileSettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        titleBarView.setStartIconClickListener {
            (requireParentFragment() as MainFragment).onBackPressed()
        }
        nameView.setActionIconClickListener {
            ChangeParamDialog.show(
                fragmentManager = parentFragmentManager,
                title = getString(R.string.param_name),
                description = getString(R.string.description_name),
                target = this@ProfileSettingsFragment,
                userParam = UserParam.NAME
            )
        }
        emailView.setActionIconClickListener {
            ChangeParamDialog.show(
                fragmentManager = parentFragmentManager,
                title = getString(R.string.param_email),
                description = getString(R.string.description_email, emailView.middleText),
                target = this@ProfileSettingsFragment,
                userParam = UserParam.EMAIL
            )
        }
        addressView.setActionIconClickListener {
            ChangeParamDialog.show(
                fragmentManager = parentFragmentManager,
                title = getString(R.string.param_address),
                description = getString(R.string.description_address),
                target = this@ProfileSettingsFragment,
                userParam = UserParam.ADDRESS
            )
        }
        phoneView.setActionIconClickListener {
            ChangeParamDialog.show(
                fragmentManager = parentFragmentManager,
                title = getString(R.string.param_phone),
                description = getString(R.string.description_phone, phoneView.middleText),
                target = this@ProfileSettingsFragment,
                userParam = UserParam.PHONE
            )
        }
        passwordView.setActionIconClickListener {
            ChangeParamDialog.show(
                fragmentManager = parentFragmentManager,
                title = getString(R.string.param_password),
                description = getString(R.string.description_password),
                target = this@ProfileSettingsFragment,
                userParam = UserParam.PASSWORD
            )
        }

        collect(viewModel.nameFlow, nameView::middleText::set)
        collect(viewModel.emailFlow, emailView::middleText::set)
        collect(viewModel.phoneFLow, phoneView::middleText::set)
        collect(viewModel.addressFlow, addressView::middleText::set)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_something_went_wrong, Toast.LENGTH_LONG)
                .show()
        }
        collect(viewModel.successActionFlow) {
            Toast.makeText(requireContext(), R.string.success_data_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onNameChanged(name: String) {
        viewModel.saveName(name)
    }

    override fun onEmailChanged(email: String) {
        viewModel.saveEmail(email)
    }

    override fun onPhoneChanged(phone: String) {
        viewModel.savePhone(phone)
    }

    override fun onAddressChanged(address: String) {
        viewModel.saveAddress(address)
    }

    override fun onPasswordChanged(password: String) {
        viewModel.savePassword(password)
    }

    companion object {

        val TAG = ProfileSettingsFragment::class.simpleName!!

        fun newInstance() = ProfileSettingsFragment()
    }
}