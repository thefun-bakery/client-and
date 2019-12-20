package thefun.bakery.data

import com.google.gson.annotations.SerializedName

data class EmotionImg(@SerializedName("key") val key: String,
                      @SerializedName("lottie") val lottie: String,
                      @SerializedName("svg") val svg: String)