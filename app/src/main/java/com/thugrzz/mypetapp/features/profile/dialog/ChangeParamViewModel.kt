package com.thugrzz.mypetapp.features.profile.dialog

import android.util.Log
import androidx.lifecycle.ViewModel
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeParamViewModel(
    private val repository: NetworkRepository
) : ViewModel() {

    fun kek() {
        repository.createUser().enqueue(object:Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.e("success", "success")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("failed","fail")
            }

        })
        Log.e("aw", "kek")
    }
}