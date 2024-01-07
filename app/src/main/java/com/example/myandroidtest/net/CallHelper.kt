package com.example.myandroidtest.net


import com.example.myandroidtest.utils.SSLSocketClient
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit


object CallHelper {
    private val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.getX509TrustManager())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())

            return builder.build()
        }

    fun call(url: String): Call {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        return okHttpClient.newCall(request)
    }
}