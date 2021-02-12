package com.thugrzz.mypetapp.features.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtRegisterPersonalInfoBinding

class RegisterPersonalInfoFragment : Fragment(R.layout.fmt_register_personal_info) {

    interface Callbacks {
        fun navigateToAuthorization()
    }

    private val binding by viewBinding(FmtRegisterPersonalInfoBinding::bind)

    private val callbacks: Callbacks
        get() = parentFragment as Callbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            authNavigateButton.setOnClickListener {callbacks.navigateToAuthorization() }
        }
    }

    companion object {

        val TAG = RegisterPersonalInfoFragment::class.simpleName!!

        fun newInstance() = RegisterPersonalInfoFragment()
    }
}