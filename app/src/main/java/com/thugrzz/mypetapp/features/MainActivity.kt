package com.thugrzz.mypetapp.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.ext.clearBackStack
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.onboarding.OnboardingFragment
import com.thugrzz.mypetapp.features.start.StartFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToStartFragment()
    }

    private fun navigateToStartFragment() = supportFragmentManager.apply {
        clearBackStack()
        commit {
            replace(R.id.mainActivityContainer, StartFragment.newInstance(), StartFragment.TAG)
        }
    }

    private fun navigateToOnboardingFragment() = supportFragmentManager.apply {
        clearBackStack()
        commit {
            replace(
                R.id.mainActivityContainer,
                OnboardingFragment.newInstance(),
                OnboardingFragment.TAG
            )
        }
    }

    private fun navigateToMainFragment() = supportFragmentManager.apply {
        clearBackStack()
        commit {
            replace(R.id.mainActivityContainer, MainFragment.newInstance(), MainFragment.TAG)
        }
    }

    override fun onBackPressed() {
        val childFragmentManager = supportFragmentManager
            .findFragmentById(R.id.mainActivityContainer)
            ?.childFragmentManager

        val mainFragment = supportFragmentManager.findFragmentByTag(MainFragment.TAG)

        if (mainFragment != null) {
            (mainFragment as MainFragment).onBackPressed()
        } else if (childFragmentManager == null || childFragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            childFragmentManager.popBackStack()
        }
    }
}