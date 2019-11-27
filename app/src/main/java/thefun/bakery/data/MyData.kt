package thefun.bakery.data

import com.google.gson.annotations.SerializedName

data class MyData(@SerializedName("nickname") val nickname: String,
                  @SerializedName("profile_image_url") val profileImageUrl: String)