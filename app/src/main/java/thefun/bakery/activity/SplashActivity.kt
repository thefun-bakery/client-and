package thefun.bakery.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Single
import thefun.bakery.BuildConfig
import thefun.bakery.Const
import thefun.bakery.R
import thefun.bakery.api.ApiManager
import java.util.concurrent.TimeUnit

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        val accessToken = pref.getString(Const.APP_ACCESS_TOKEN, "")

//        Single.just(accessToken)
//            .delay(1000L, TimeUnit.MILLISECONDS)
//            .flatMap {
//                Single.just(ApiManager.api?.isLogin())
//            }

    }
}