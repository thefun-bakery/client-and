package thefun.bakery.api

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thefun.bakery.Const
import thefun.bakery.MainApp
import java.util.concurrent.TimeUnit

object ApiClient {

    var api: Api? = null

    fun init(pref: SharedPreferences) {
        if (api == null) {
            val retrofit = createRetrofit(pref)
            retrofit?.let {
                api = it.create(Api::class.java)
            }
        }
    }

    private fun createRetrofit(pref: SharedPreferences): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .connectTimeout(5 * 1000, TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor(pref))
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()

        val gsonBuilder = GsonBuilder().registerTypeAdapter(
            DateTime::class.java,
            MainApp.DateTimeConverter()
        )

        GsonBuilder().registerTypeAdapter(DateTime::class.java, MainApp.DateTimeConverter())

        return Retrofit.Builder()
            .baseUrl(Const.API_SERVER_HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}