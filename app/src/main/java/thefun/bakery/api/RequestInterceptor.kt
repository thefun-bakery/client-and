package thefun.bakery.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import thefun.bakery.Const

class RequestInterceptor(private val preferences: SharedPreferences): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept", "application/json")
        builder.header("Authorization",
            "Bearer ${preferences.getString(Const.KEY_ACCESS_TOKEN, "")}")

        val request = builder.build()
        return chain.proceed(request)
    }
}