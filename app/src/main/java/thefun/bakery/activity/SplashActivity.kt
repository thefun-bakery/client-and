package thefun.bakery.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

        if (accessToken.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            Observable.just(accessToken)
                .delay(1000L, TimeUnit.MILLISECONDS)
                .flatMap {
                    ApiManager.api?.isLogin()
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isLogin) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    finish()
                }, {
                    Log.e("###", it.localizedMessage)
                })

        }
    }
}