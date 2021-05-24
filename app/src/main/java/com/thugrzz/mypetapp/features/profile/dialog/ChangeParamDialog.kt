package com.thugrzz.mypetapp.features.profile.dialog

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.BaseRoundedDialogFragment
import com.thugrzz.mypetapp.data.model.local.UserParam
import com.thugrzz.mypetapp.databinding.DlgChangeParamBinding
import com.thugrzz.mypetapp.ext.enumArgument
import com.thugrzz.mypetapp.ext.stringArgument

class ChangeParamDialog : BaseRoundedDialogFragment(R.layout.dlg_change_param) {

    interface Callbacks {
        fun onNameChanged(name: String)
        fun onEmailChanged(email: String)
        fun onPhoneChanged(phone: String)
        fun onAddressChanged(address: String)
        fun onPasswordChanged(password: String)
    }

    private val binding by viewBinding(DlgChangeParamBinding::bind)

    private var title by stringArgument()
    private var description by stringArgument()
    private var userParam by enumArgument<UserParam>()

    private val callback
        get() = targetFragment as Callbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        setInputType()
        setInputMode(title)
        titleView.text = title
        descriptionView.text = description
        inputLayout.hint = title

        dismissButton.setOnClickListener { dismiss() }
        confirmButton.setOnClickListener {
            val text = inputEditText.text.toString()
            when (userParam) {
                UserParam.NAME -> callback.onNameChanged(text)
                UserParam.EMAIL -> callback.onEmailChanged(text)
                UserParam.PHONE -> callback.onPhoneChanged(text)
                UserParam.ADDRESS -> callback.onAddressChanged(text)
                UserParam.PASSWORD -> callback.onPasswordChanged(text)
            }
            dismiss()
        }
    }

    private fun setInputType() = with(binding) {
        inputEditText.inputType = when (userParam) {
            UserParam.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            UserParam.PHONE -> InputType.TYPE_CLASS_PHONE
            UserParam.PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD
            else -> InputType.TYPE_CLASS_TEXT
        }
    }

    private fun setInputMode(title: String) = with(binding) {
        inputLayout.endIconMode = when (title) {
            getText(R.string.param_password) -> END_ICON_PASSWORD_TOGGLE
            else -> END_ICON_CLEAR_TEXT
        }
    }

    companion object {

        val TAG = ChangeParamDialog::class.simpleName!!

        fun show(
            fragmentManager: FragmentManager,
            title: String,
            description: String,
            target: Fragment,
            userParam: UserParam
        ) = ChangeParamDialog().apply {
            this.title = title
            this.description = description
            this.userParam = userParam
            setTargetFragment(target, 0)
            show(fragmentManager, TAG)
        }
    }
}