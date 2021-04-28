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
import com.thugrzz.mypetapp.databinding.DlgChangeParamBinding
import com.thugrzz.mypetapp.ext.stringArgument
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeParamDialog : BaseRoundedDialogFragment(R.layout.dlg_change_param) {

    interface Callbacks {

    }

    private val binding by viewBinding(DlgChangeParamBinding::bind)

    private val viewModel: ChangeParamViewModel by viewModel()

    private var title by stringArgument()
    private var description by stringArgument()

    private val callback
        get() = targetFragment as Callbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        setInputType(title)
        setInputMode(title)
        titleView.text = title
        descriptionView.text = description
        inputLayout.hint = title

        dismissButton.setOnClickListener { dismiss() }
        confirmButton.setOnClickListener { }
    }

    private fun setInputType(title: String) = with(binding) {
        inputEditText.inputType = when (title) {
            getText(R.string.param_email) -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            getText(R.string.param_phone) -> InputType.TYPE_CLASS_PHONE
            getText(R.string.param_password) -> InputType.TYPE_TEXT_VARIATION_PASSWORD
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

        private fun newInstance() = ChangeParamDialog()
    }

    fun show(fragmentManager: FragmentManager) = show(fragmentManager, TAG)

    class Builder(
        private var title: String,
        private var description: String,
        private var target: Fragment,
    ) {

        val dialog = newInstance()

        fun create() = ChangeParamDialog().apply {
            this.title = this@Builder.title
            this.description = this@Builder.description
            this@Builder.dialog.setTargetFragment(target, 0)
        }
    }
}