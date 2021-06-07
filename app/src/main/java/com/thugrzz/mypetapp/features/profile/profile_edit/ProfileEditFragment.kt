package com.thugrzz.mypetapp.features.profile.profile_edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Acceptance
import com.thugrzz.mypetapp.databinding.FmtProfileEditBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.picker.BreedPickerDialogFragment
import com.thugrzz.mypetapp.features.picker.PetTypePickerDialogFragment
import com.thugrzz.mypetapp.features.picker.SexPickerDialogFragment
import com.thugrzz.mypetapp.features.picker.StatusPickerDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileEditFragment : Fragment(R.layout.fmt_profile_edit),
    SexPickerDialogFragment.Callback,
    StatusPickerDialogFragment.Callback,
    PetTypePickerDialogFragment.Callback,
    BreedPickerDialogFragment.Callback {

    private val binding by viewBinding(FmtProfileEditBinding::bind)
    private val viewModel by viewModel<ProfileEditViewModel>()

    private val petTypes = mutableListOf<PetType>()
    private val breeds = mutableListOf<PetBreed>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        titleBarView.setStartIconClickListener {
            (requireParentFragment() as MainFragment).onBackPressed()
        }
        titleBarView.setEndIconClickListener {
            viewModel.savePetProfile()
        }

        petNameInputView.doOnTextChanged { text, _, _, _ -> viewModel.onPetNameChange(text.toString()) }
        petHeightInputText.doOnTextChanged { text, _, _, _ -> viewModel.onPetHeightChange(text.toString()) }
        petWeightInputText.doOnTextChanged { text, _, _, _ -> viewModel.onPetWeightChange(text.toString()) }

        collect(viewModel.petNameFlow, ::bindPetName)
        collect(viewModel.petTypesFlow, ::bindPetTypes)
        collect(viewModel.petBreedsFlow, ::bindPetBreeds)
        collect(viewModel.selectedTypeFlow, ::bindPetType)
        collect(viewModel.selectedBreedFlow, ::bindBreed)
        collect(viewModel.statusFlow, ::bindStatus)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.petHeightFlow, petHeightInputText::setTextKeepState)
        collect(viewModel.petWeightFlow, petWeightInputText::setTextKeepState)
        collect(viewModel.sexFlow, ::bindSex)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_something_went_wrong, Toast.LENGTH_LONG)
                .show()
        }
        collect(viewModel.emptyDataActionFlow) {
            Toast.makeText(requireContext(), R.string.error_empty_data_pet, Toast.LENGTH_LONG)
                .show()
        }
        collect(viewModel.successActionFlow) {
            Toast.makeText(requireContext(), R.string.success_data_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSexPicked(sex: Sex) {
        viewModel.onSexChange(sex)
    }

    override fun onStatusPicked(status: PetStatus) {
        viewModel.onStatusChange(status)
    }

    override fun onBreedPicked(breed: PetBreed) {
        viewModel.onPetBreedClick(breed)
    }

    override fun onTypePicked(type: PetType) {
        viewModel.onPetTypeClick(type)
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

    private fun bindPetType(type: PetType?) = with(binding.petTypeView) {
        val stringType = type?.type ?: ""
        if (stringType.isNotEmpty()) text = stringType
        setOnClickListener {
            PetTypePickerDialogFragment.show(
                this@ProfileEditFragment,
                parentFragmentManager,
                petTypes,
                type
            )
        }
    }

    private fun bindBreed(breed: PetBreed?) = with(binding.petBreedView) {
        val stringBreed = breed?.name ?: ""
        if (stringBreed.isNotEmpty()) text = stringBreed
        setOnClickListener {
            BreedPickerDialogFragment.show(
                this@ProfileEditFragment,
                parentFragmentManager,
                breeds,
                breed
            )
        }
    }

    private fun bindStatus(status: PetStatus?) = with(binding.petStatusView) {
        val stringStatus = if (status != null) getString(status.titleId) else ""
        if (stringStatus.isNotEmpty()) text = stringStatus
        setOnClickListener {
            StatusPickerDialogFragment.show(
                this@ProfileEditFragment,
                parentFragmentManager,
                status
            )
        }
    }

    private fun bindSex(sex: Sex?) = with(binding.petSexView) {
        val stringSex = if (sex != null) getString(sex.titleId) else ""
        if (stringSex.isNotEmpty()) text = stringSex
        setOnClickListener {
            SexPickerDialogFragment.show(
                this@ProfileEditFragment,
                parentFragmentManager,
                sex
            )
        }
    }

    private fun bindPetBreeds(breeds: List<PetBreed>) {
        this.breeds.clear()
        this.breeds.addAll(breeds)
    }

    private fun bindPetTypes(types: List<PetType>) {
        petTypes.clear()
        petTypes.addAll(types)
    }

    companion object {

        val TAG = ProfileEditFragment::class.simpleName!!

        fun newInstance() = ProfileEditFragment()
    }
}