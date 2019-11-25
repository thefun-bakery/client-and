package thefun.bakery

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.gson.*
import com.kakao.auth.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import thefun.bakery.api.ApiClient
import java.lang.reflect.Type

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSDK.init(KakaoSdkAdapter())

        JodaTimeAndroid.init(this)

        ApiClient.init(getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE))
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

    private inner class KakaoSdkAdapter: KakaoAdapter() {

        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {

                override fun isSaveFormData(): Boolean {
                    return true
                }

                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_TALK)
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { this@MainApp }
        }
    }
}