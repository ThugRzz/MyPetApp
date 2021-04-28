package com.thugrzz.mypetapp.features.auth.authorization

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.DimensionalBottomSheetFragment
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Acceptance
import com.thugrzz.mypetapp.databinding.DlgAuthBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.ext.showIfNotShowing
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthDialogFragment : DimensionalBottomSheetFragment(
    R.layout.dlg_auth
) {

    interface Callback {
        fun navigateToRegister()
    }

    private val binding by viewBinding(DlgAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModel()

    private val callbacks: Callback
        get() = targetFragment as Callback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        emailInputText.doOnTextChanged { text, _, _, _ -> viewModel.onEmailInputChange(text.toString()) }
        passwordInputText.doOnTextChanged { text, _, _, _ -> viewModel.onPasswordInputChange(text.toString()) }

        registerButton.setOnClickListener {
            callbacks.navigateToRegister()
            dismiss()
        }
        authButton.setOnClickListener {
            viewModel.login()
        }

        collect(viewModel.emailFlow, ::bindEmail)
        collect(viewModel.passwordFlow, ::bindPassword)
        collect(viewModel.isActionButtonEnabledFlow, authButton::setEnabled)
    }

    private fun bindEmail(acceptableValue: AcceptableValue<String>) = with(binding) {
        val email = acceptableValue.value
        emailInputText.setTextKeepState(email)
        emailInputLayout.error = when (acceptableValue.status) {
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            Acceptance.DECLINED -> getString(R.string.validation_email_incorrect)
            else -> null
        }
    }

    private fun bindPassword(acceptableValue: AcceptableValue<String>) = with(binding) {
        val password = acceptableValue.value
        passwordInputText.setTextKeepState(password)
        passwordInputLayout.error = when (acceptableValue.status) {
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            Acceptance.LENGTH_SMALL -> getString(R.string.validation_small_password)
            else -> null
        }
    }

    companion object {

        val TAG = AuthDialogFragment::class.simpleName!!

        fun show(
            target: Fragment,
            fragmentManager: FragmentManager,
        ) = AuthDialogFragment().apply {
            setTargetFragment(target, 0)
            showIfNotShowing(fragmentManager, TAG)
        }
    }
}