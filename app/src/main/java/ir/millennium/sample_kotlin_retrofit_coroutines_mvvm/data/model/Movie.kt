package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.model

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("category")
    var category: String? = null

    @SerializedName("imageUrl")
    var imageUrl: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("desc")
    var desc: String? = null
}