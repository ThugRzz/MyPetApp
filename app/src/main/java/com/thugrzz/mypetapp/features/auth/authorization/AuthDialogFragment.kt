package com.thugrzz.mypetapp.features.auth.authorization

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.DimensionalBottomSheetFragment
import com.thugrzz.mypetapp.databinding.DlgAuthBinding
import com.thugrzz.mypetapp.ext.showIfNotShowing
import kotlinx.coroutines.launch

class AuthDialogFragment : DimensionalBottomSheetFragment(R.layout.dlg_auth, SCREEN_SIZE) {

    interface Callback {
        fun navigateToRegister()
    }

    private val binding by viewBinding(DlgAuthBinding::bind)

    private val callbacks: Callback
        get() = targetFragment as Callback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
        }

        with(binding) {
            registerButton.setOnClickListener {
                callbacks.navigateToRegister()
                dismiss()
            }
        }
    }

    companion object {

        private const val SCREEN_SIZE = 0.8f
        val TAG = AuthDialogFragment::class.simpleName!!

        fun show(
            target: Fragment,
            fragmentManager: FragmentManager
        ) = AuthDialogFragment().apply {
            setTargetFragment(target, 0)
            showIfNotShowing(fragmentManager, TAG)
        }
    }
}