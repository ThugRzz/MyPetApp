package com.thugrzz.mypetapp.features.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtStartBinding
import com.thugrzz.mypetapp.features.auth.authorization.AuthDialogFragment
import com.thugrzz.mypetapp.features.auth.register.RegisterDialogFragment
import com.thugrzz.mypetapp.features.main.MainFragment

class StartFragment : Fragment(R.layout.fmt_start),
    RegisterDialogFragment.Callbacks,
    AuthDialogFragment.Callback {

    private val binding by viewBinding(FmtStartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            authButton.setOnClickListener { navigateToAuthorization() }
            registerButton.setOnClickListener { navigateToRegister() }
            btn.setOnClickListener {
                parentFragmentManager.commit {
                    replace(
                        R.id.mainActivityContainer,
                        MainFragment.newInstance(),
                        MainFragment.TAG
                    )
                    addToBackStack(null)
                }
            }
        }
    }

    override fun navigateToRegister() {
        RegisterDialogFragment.show(parentFragmentManager, this)
    }

    companion object {
        val TAG = StartFragment::class.simpleName!!

        fun newInstance() = StartFragment()
    }

    override fun navigateToAuthorization() {
        AuthDialogFragment.show(this, parentFragmentManager)
    }
}