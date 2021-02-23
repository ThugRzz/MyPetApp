package com.thugrzz.mypetapp.features.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.BaseBackStackBottomSheet
import com.thugrzz.mypetapp.ext.showIfNotShowing

class RegisterDialogFragment :
    BaseBackStackBottomSheet(R.layout.dlg_register, SCREEN_SIZE),
    RegisterPersonalInfoFragment.Callbacks {

    interface Callbacks {
        fun navigateToAuthorization()
    }

    private val callbacks: Callbacks
        get() = targetFragment as Callbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.commit {
                add(
                    R.id.registerFragmentContainer,
                    RegisterPersonalInfoFragment.newInstance(),
                    RegisterPersonalInfoFragment.TAG
                )
            }
        }
    }

    override fun navigateToAuthorization() {
        callbacks.navigateToAuthorization()
        dismiss()
    }

/*    override fun onRegisterCompleted() {

    }

    override fun navigateBack() {
        dialog!!.onBackPressed()
    }

    override fun navigateToAuthorization() {
        (targetFragment as Callbacks).navigateToAuthorization()
        dismiss()
    }*/

    companion object {

        private const val SCREEN_SIZE = 0.8f
        val TAG = RegisterDialogFragment::class.simpleName!!

        fun show(
            fragmentManager: FragmentManager,
            target: Fragment
        ) {
            RegisterDialogFragment().apply {
                setTargetFragment(target, 0)
                showIfNotShowing(fragmentManager, TAG)
            }
        }
    }

}