package thefun.bakery.api

import android.app.Application
import thefun.bakery.MainApp
import thefun.bakery.Urls

object ApiClient {

    fun loginKakao(app: Application) {
        if (app is MainApp) {
            app.api?.loginKakao(Urls.loginKakao)
        }
    }
}