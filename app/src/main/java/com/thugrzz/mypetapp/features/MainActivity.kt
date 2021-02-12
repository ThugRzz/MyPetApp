package com.thugrzz.mypetapp.features

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.features.onboarding.OnboardingFragment
import com.thugrzz.mypetapp.features.start.StartFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToOnboardingFragment()
      //  navigateToStartScreen()
    }

    private fun navigateToStartScreen() = supportFragmentManager.commit {
        replace(R.id.mainActivityContainer, StartFragment.newInstance(), StartFragment.TAG)
    }

    private fun navigateToOnboardingFragment() = supportFragmentManager.commit {
        replace(
            R.id.mainActivityContainer,
            OnboardingFragment.newInstance(),
            OnboardingFragment.TAG
        )
    }
}