package com.thugrzz.mypetapp.features.auth.register

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Acceptance
import com.thugrzz.mypetapp.databinding.FmtRegisterPetInfoBinding
import com.thugrzz.mypetapp.ext.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterPetInfoFragment : Fragment(R.layout.fmt_register_pet_info) {

    interface Callback {
        fun navigateToAuth()
    }

    private val binding by viewBinding(FmtRegisterPetInfoBinding::bind)
    private val viewModel by sharedViewModel<RegisterViewModel>()

    private val callback: Callback
        get() = parentFragment as Callback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        petNameInputView.doOnTextChanged { text, _, _, _ -> viewModel.onPetNameChange(text.toString()) }
        petAgeInputView.doOnTextChanged { text, _, _, _ -> viewModel.onPetAgeChange(text.toString()) }
        petTypeInputView.setOnItemClickListener { _, _, position, _ ->
            viewModel.onPetTypeClick(position)
        }

        petBreedInputView.setOnItemClickListener { _, _, position, _ ->
            viewModel.onPetBreedClick(position)
        }

        registerButton.setOnClickListener { viewModel.registration() }

        collect(viewModel.petTypesFlow, ::bindPetTypes)
        collect(viewModel.petBreedsFlow, ::bindPetBreeds)
        collect(viewModel.petNameFlow, ::bindPetName)
        collect(viewModel.petAgeFlow, petAgeInputView::setTextKeepState)
        collect(viewModel.isRegisterButtonEnabledFlow, registerButton::setEnabled)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_register, Toast.LENGTH_LONG).show()
        }
        collect(viewModel.successRegisterActionFlow){
            callback.navigateToAuth()
        }
    }

    private fun bindPetName(petName: AcceptableValue<String>) = with(binding) {
        petNameInputView.setTextKeepState(petName.value)
        petNameInputLayout.error = when (petName.status) {
            Acceptance.LENGTH_SMALL -> getString(R.string.validation_name_small)
            Acceptance.LENGTH_LARGE -> getString(R.string.validation_name_large)
            Acceptance.EMPTY -> getString(R.string.validation_empty)
            Acceptance.DECLINED -> getString(R.string.validation_declined)
            else -> null
        }
        petNameInputLayout.isErrorEnabled = !petName.isAccepted
    }

    private fun bindPetBreeds(breeds: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, breeds)
        binding.petBreedInputView.setAdapter(adapter)
    }

    private fun bindPetTypes(types: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, types)
        binding.petTypeInputView.setAdapter(adapter)
    }

    companion object {

        val TAG = RegisterPetInfoFragment::class.simpleName!!

        fun newInstance() = RegisterPetInfoFragment()
    }
}