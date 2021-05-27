package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.features.auth.authorization.AuthViewModel
import com.thugrzz.mypetapp.features.auth.register.RegisterViewModel
import com.thugrzz.mypetapp.features.profile.ProfileViewModel
import com.thugrzz.mypetapp.features.profile.profile_edit.ProfileEditViewModel
import com.thugrzz.mypetapp.features.profile.profile_settings.ProfileSettingsViewModel
import com.thugrzz.mypetapp.features.reference.care.CareReferenceViewModel
import com.thugrzz.mypetapp.features.reference.disease.DiseaseReferenceViewModel
import com.thugrzz.mypetapp.features.reference.food.FoodReferenceViewModel
import com.thugrzz.mypetapp.features.reference.training.TrainingReferenceViewModel
import com.thugrzz.mypetapp.features.schedule.ScheduleViewModel
import com.thugrzz.mypetapp.features.schedule.add_schedule.AddScheduleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get(), get()) }
    viewModel { ScheduleViewModel(get()) }
    viewModel { AddScheduleViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { ProfileEditViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ProfileSettingsViewModel(get()) }
    viewModel { FoodReferenceViewModel(get(), get()) }
    viewModel { CareReferenceViewModel(get(), get()) }
    viewModel { DiseaseReferenceViewModel(get(), get()) }
    viewModel { TrainingReferenceViewModel(get(), get()) }
}