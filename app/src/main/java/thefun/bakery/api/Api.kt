package thefun.bakery.api

import retrofit2.Call
import retrofit2.http.GET
import thefun.bakery.data.IsLogin
import thefun.bakery.data.LoginResult

interface Api {

    @GET("auth/kakao/login")
    fun loginKakao(): Call<LoginResult>

    @GET("auth/is-login")
    fun isLogin(): Call<IsLogin>
}