package com.thugrzz.mypetapp.data.repository

import com.thugrzz.mypetapp.data.response.AuthResponse
import com.thugrzz.mypetapp.data.response.ImgResponse

interface NetworkRepository {

    suspend fun login(email: String, password: String): AuthResponse

    suspend fun img(): ImgResponse
}