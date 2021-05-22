package com.thugrzz.mypetapp.features.auth.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Acceptance
import com.thugrzz.mypetapp.databinding.FmtRegisterPersonalInfoBinding
import com.thugrzz.mypetapp.ext.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class RegisterPersonalInfoFragment : Fragment(R.layout.fmt_register_personal_info) {

    interface Callbacks {
        fun navigateToAuthorization()
        fun navigateToPetInfoRegister()
    }

    private val binding by viewBinding(FmtRegisterPersonalInfoBinding::bind)
    private val viewModel by sharedViewModel<RegisterViewModel>()

    private val callbacks: Callbacks
        get() = parentFragment as Callbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val formatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(phoneInputText)
        formatWatcher.mask.toUnformattedString()

        emailInputText.doOnTextChanged { text, _, _, _ -> viewModel.onEmailChange(text.toString()) }
        nameInputText.doOnTextChanged { text, _, _, _ -> viewModel.onOwnerNameChange(text.toString()) }
        phoneInputText.doOnTextChanged { _, _, _, _ ->
            viewModel.onPhoneChange(formatWatcher.mask.toUnformattedString())
        }
        passwordInputText.doOnTextChanged { text, _, _, _ -> viewModel.onPasswordChange(text.toString()) }

        authNavigateButton.setOnClickListener { callbacks.navigateToAuthorization() }
        nextRegisterButton.setOnClickListener { callbacks.navigateToPetInfoRegister() }

        collect(viewModel.emailFlow, ::bindEmail)
        collect(viewModel.ownerNameFlow, ::bindOwnerName)
        collect(viewModel.phoneFlow, ::bindPhone)
        collect(viewModel.passwordFlow, ::bindPassword)
        collect(viewModel.isNextButtonEnabledFlow, nextRegisterButton::setEnabled)
    }

    private fun bindEmail(email: AcceptableValue<String>) = with(binding) {
        emailInputText.setTextKeepState(email.value)
        emailInputLayout.error = when (email.status) {
            Acceptance.DECLINED -> getString(R.string.validation_email_incorrect)
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            else -> null
        }
        emailInputLayout.isErrorEnabled = !email.isAccepted
    }

    private fun bindOwnerName(ownerName: AcceptableValue<String>) = with(binding) {
        nameInputText.setTextKeepState(ownerName.value)
        nameInputLayout.error = when (ownerName.status) {
            Acceptance.LENGTH_SMALL -> getString(R.string.validation_name_small)
            Acceptance.LENGTH_LARGE -> getString(R.string.validation_name_large)
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            Acceptance.DECLINED -> getString(R.string.validation_declined)
            else -> null
        }
        nameInputLayout.isErrorEnabled = !ownerName.isAccepted
    }

    private fun bindPhone(phone: AcceptableValue<String>) = with(binding) {
        phoneInputText.setTextKeepState(phone.value)
        phoneInputLayout.error = when (phone.status) {
            Acceptance.DECLINED -> getString(R.string.validation_phone_small)
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            else -> null
        }
        phoneInputLayout.isErrorEnabled = !phone.isAccepted
    }

    private fun bindPassword(password: AcceptableValue<String>) = with(binding) {
        passwordInputText.setTextKeepState(password.value)
        passwordInputLayout.error = when (password.status) {
            Acceptance.LENGTH_SMALL -> getString(R.string.validation_small_password)
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            else -> null
        }
        passwordInputLayout.isErrorEnabled = !password.isAccepted
    }

    companion object {

        val TAG = RegisterPersonalInfoFragment::class.simpleName!!

        fun newInstance() = RegisterPersonalInfoFragment()
    }
}