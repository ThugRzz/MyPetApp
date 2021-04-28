package com.thugrzz.mypetapp.data.repository

import com.thugrzz.mypetapp.data.request.AuthRequest
import com.thugrzz.mypetapp.data.response.AuthResponse
import com.thugrzz.mypetapp.data.response.ImgResponse
import com.thugrzz.mypetapp.data.source.network.NetworkDataSource

class NetworkRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
) : NetworkRepository {

    override suspend fun login(email: String, password: String): AuthResponse {
        val request = AuthRequest(email, password)
        return networkDataSource.login(request).data
    }

    override suspend fun img(): ImgResponse {
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOjN9.g_tVFfrVjTDWM_XcBtcwVX9-tDtJumHVqKNIJ9lIF4k"
        return networkDataSource.img(token, "care.png")
    }
}