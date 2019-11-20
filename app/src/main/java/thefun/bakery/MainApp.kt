package thefun.bakery

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.gson.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thefun.bakery.api.Api
import thefun.bakery.api.RequestInterceptor
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class MainApp: Application() {

    var api: Api? = null

    override fun onCreate() {
        super.onCreate()

        createRetrofit()
    }

    private fun createRetrofit() {
        val preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .connectTimeout(5 * 1000, TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor(preferences))
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()

        val gsonBuilder = GsonBuilder().registerTypeAdapter(DateTime::class.java, DateTimeConverter())

        GsonBuilder().registerTypeAdapter(DateTime::class.java, DateTimeConverter())
        val retrofit = Retrofit.Builder()
            .baseUrl(Const.API_SERVER_HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api  = retrofit.create(Api::class.java)
    }

    class DateTimeConverter: JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
        override fun serialize(src: DateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            Log.i(Const.LOG, "=============> ${JsonPrimitive(src?.toString())}, ${src?.toString()}")
            return JsonPrimitive(src?.toString())
        }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DateTime {
            return DateTime(json?.asString)
        }
    }
}