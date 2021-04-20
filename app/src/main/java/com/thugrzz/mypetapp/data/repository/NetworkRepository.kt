package com.thugrzz.mypetapp.data.repository

import retrofit2.Call

interface NetworkRepository {

    fun createUser(): Call<Unit>
}