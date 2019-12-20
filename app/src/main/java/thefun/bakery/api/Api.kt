package thefun.bakery.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import thefun.bakery.data.*

interface Api {

    @GET("auth/kakao/login")
    fun loginKakao(): Call<LoginResult>

    @GET("auth/is-login")
    fun isLogin(): Observable<IsLogin>

    @PATCH("v1/users/me")
    fun updateNickName(@Body nickName: NickName): Call<MyData>

    @GET("v1/homes/mine")
    fun getMainHome(): Call<MainHome>

    @GET("v1/assets/emotion-image")
    fun getEmotionImages(): Observable<Emotions>
}