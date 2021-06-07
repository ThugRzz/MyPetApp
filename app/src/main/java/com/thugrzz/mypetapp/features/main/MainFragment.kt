package com.thugrzz.mypetapp.features.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtMainBinding
import com.thugrzz.mypetapp.features.reference.ReferenceFragment
import com.thugrzz.mypetapp.features.profile.ProfileFragment
import com.thugrzz.mypetapp.features.qr_code.QrCodeFragment
import com.thugrzz.mypetapp.features.schedule.ScheduleFragment
import ru.dimakron.multistacks_lib.BackResultType
import ru.dimakron.multistacks_lib.MultiStacks

class MainFragment : Fragment(R.layout.fmt_main), NavView.TabClickListener {

    private val binding by viewBinding(FmtMainBinding::bind)

    private lateinit var multiStacks: MultiStacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiStacks = MultiStacks.Builder(childFragmentManager, R.id.mainFragmentContainer)
            .setState(savedInstanceState)
            .setRootFragmentInitializers(
                NavTab.values().map { navTab -> { getRootFragment(navTab) } })
            .setSelectedTabIndex(0)
            .setTabHistoryEnabled(true)
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            navView.tabClickListener = this@MainFragment

            if (savedInstanceState == null) {
                navView.selectedTab = NavTab.SCHEDULE
            }
        }
    }

    override fun onNavTabClick(tab: NavTab) = setSelectedTab(tab)

    private fun setSelectedTab(tab: NavTab) {
        binding.navView.selectedTab = tab
        val newPosition = NavTab.values().indexOfFirst { tab == it }
        if (newPosition == multiStacks.getSelectedTabIndex()) {
            multiStacks.clearStack()
        } else {
            multiStacks.setSelectedTabIndex(newPosition)
        }
    }

    //todo change root fragments
    private fun getRootFragment(tab: NavTab): Fragment = when (tab) {
        NavTab.SCHEDULE -> ScheduleFragment.newInstance()
        NavTab.REFERENCE -> ReferenceFragment.newInstance()
        NavTab.PROFILE -> ProfileFragment.newInstance()
        NavTab.QR_CODE -> QrCodeFragment.newInstance()
    }

    fun pushFragment(fragment: Fragment) {
        multiStacks.push(fragment)
    }

    fun onBackPressed() {
        val result = multiStacks.onBackPressed()
        if (result.type == BackResultType.CANCELLED) {
            requireActivity().finish()
        }
    }

    companion object {

        val TAG = MainFragment::class.simpleName!!

        fun newInstance() = MainFragment()
    }
}