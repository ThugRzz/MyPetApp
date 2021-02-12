package com.thugrzz.mypetapp.util.onboarding

import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.OnboardingItem

object OnboardingData {

    val items = listOf(
        OnboardingItem(
            title = "Уход",
            description = "Краткая информация по уходу за животным. Что можем предложить для облегченного ухода за домашним питомцем",
            backgroundColor = R.color.blue_43C6D0,
            iconRes = R.mipmap.onboarding_first,
            pageNumber = 0
        ),
        OnboardingItem(
            title = "Прогулка",
            description = "Краткая информация по уходу за животным. Что можем предложить для облегченного ухода за домашним питомцем",
            backgroundColor = R.color.orange_FE9900,
            iconRes = R.mipmap.onboarding_second,
            pageNumber = 1
        ),
        OnboardingItem(
            title = "Развлечения",
            description = "Краткая информация по уходу за животным. Что можем предложить для облегченного ухода за домашним питомцем",
            backgroundColor = R.color.green_ABCF15,
            iconRes = R.mipmap.onboarding_third,
            pageNumber = 2
        ),
        OnboardingItem(
            title = "Питание",
            description = "Краткая информация по уходу за животным. Что можем предложить для облегченного ухода за домашним питомцем",
            backgroundColor = R.color.purple_A349A3,
            iconRes = R.mipmap.onboarding_fourth,
            pageNumber = 3
        )
    )
}