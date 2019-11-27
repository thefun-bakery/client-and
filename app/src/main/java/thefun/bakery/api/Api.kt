package thefun.bakery.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import thefun.bakery.data.IsLogin
import thefun.bakery.data.LoginResult
import thefun.bakery.data.MyData
import thefun.bakery.data.NickName

interface Api {

    @GET("auth/kakao/login")
    fun loginKakao(): Call<LoginResult>

    @GET("auth/is-login")
    fun isLogin(): Call<IsLogin>

    @PATCH("v1/users/me")
    fun updateNickName(@Body nickName: NickName): Call<MyData>
}