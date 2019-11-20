package thefun.bakery.api

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thefun.bakery.Const
import thefun.bakery.SingletonHolder
import java.util.concurrent.TimeUnit

class ApiClient constructor(preferences: SharedPreferences) {
    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .connectTimeout(5 * 1000, TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor(preferences))
            .retryOnConnectionFailure(true)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Const.API_SERVER_HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    companion object : SingletonHolder<ApiClient, SharedPreferences>(::ApiClient)

}