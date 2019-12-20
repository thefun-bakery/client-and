package thefun.bakery.data

import com.google.gson.annotations.SerializedName

data class Emotions(@SerializedName("name") val name: String,
                    @SerializedName("main_image") val mainImageUrl: String,
                    @SerializedName("images") val images: ArrayList<EmotionImg>)