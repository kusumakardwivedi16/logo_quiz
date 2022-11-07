package com.app.logoquiz.data.network


import com.google.gson.annotations.SerializedName

data class QuestionsItem(
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("name")
    val name: String
)