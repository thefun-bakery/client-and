package thefun.bakery.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thefun.bakery.BuildConfig
import thefun.bakery.Const
import thefun.bakery.R
import thefun.bakery.api.ApiClient
import thefun.bakery.data.IsLogin
import thefun.bakery.data.LoginResult


class LoginActivity: AppCompatActivity() {

    private var sessionCallback: SessionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

        if (pref.getString(Const.APP_ACCESS_TOKEN, "").isNullOrEmpty()) {
            showKakaoLogin()
        } else {
            ApiClient.api?.isLogin()?.enqueue(object : Callback<IsLogin> {
                override fun onResponse(call: Call<IsLogin>, response: Response<IsLogin>) {
                    response.body()?.let {
                        if (it.isLogin) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            showKakaoLogin()
                        }
                    }
                }

                override fun onFailure(call: Call<IsLogin>, t: Throwable) {
                    Log.e(Const.LOG, t.localizedMessage)
                }
            })
        }
    }

    private fun showKakaoLogin() {
        sessionCallback = SessionCallback()
        Session.getCurrentSession().addCallback(sessionCallback)

        findViewById<RelativeLayout>(R.id.login_with_kakaotalk).visibility = View.VISIBLE
        findViewById<RelativeLayout>(R.id.btn_login_with_kakaotalk).setOnClickListener {
            Session.getCurrentSession().open(AuthType.KAKAO_TALK, this)
        }
    }

    override fun onStop() {
        super.onStop()

        sessionCallback?.let {
            Session.getCurrentSession().removeCallback(it)
            Session.getCurrentSession().close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.e(Const.LOG, "onActivityResult ==> ")
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            val accessToken = Session.getCurrentSession().tokenInfo.accessToken
            Log.e(Const.LOG, "Access-Token: $accessToken")

            val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString(Const.KAKAO_ACCESS_TOKEN, accessToken)
            editor.putString(Const.APP_ACCESS_TOKEN, accessToken)
            editor.commit()

            ApiClient.api?.loginKakao()?.enqueue(object : Callback<LoginResult> {
                override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {

                    response.body()?.let {
                        val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString(Const.APP_ACCESS_TOKEN, it.accessToken)
                        editor.commit()
                        Log.e(Const.LOG, "==> " + it.accessToken)

                        startActivity(Intent(this@LoginActivity, NickNameRegistActivity::class.java))
                        finish()
                    }
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.e(Const.LOG, t.localizedMessage)
                }
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.e(Const.LOG, exception?.localizedMessage)
        }
    }
}