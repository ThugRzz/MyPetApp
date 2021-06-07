package com.thugrzz.mypetapp.util

import com.thugrzz.mypetapp.data.repository.PreferencesRepository
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(
    private val preferencesRepository: PreferencesRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        val accessToken = preferencesRepository.getToken()
        builder.addHeader("Authorization", "Bearer $accessToken")
        return chain.proceed(builder.build())
    }
}