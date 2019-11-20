package thefun.bakery.api

import io.reactivex.Completable
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {

    @GET
    open fun loginKakao(@Url url: String): Completable
}