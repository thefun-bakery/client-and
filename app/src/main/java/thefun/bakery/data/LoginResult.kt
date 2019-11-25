package thefun.bakery.data

import com.google.gson.annotations.SerializedName

data class LoginResult(@SerializedName("access_token") val accessToken: String)