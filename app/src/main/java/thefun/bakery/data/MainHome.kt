package thefun.bakery.data

import com.google.gson.annotations.SerializedName

data class MainHome(@SerializedName("name") val name: String,
                    @SerializedName("desc") val desc: String,
                    @SerializedName("bgcolor") val bgColor: Int)