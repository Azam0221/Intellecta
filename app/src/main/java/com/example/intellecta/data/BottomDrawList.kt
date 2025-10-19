package com.example.intellecta.data



import androidx.annotation.DrawableRes
import com.example.intellecta.R

data class BottomDrawList(
    @DrawableRes val icon : Int,
    val label : String,
    val routes : String

)

val bottomDrawList = listOf(
    BottomDrawList(R.drawable.outline_home_24,"Home","home_page"),
    BottomDrawList(R.drawable.outline_search_24,"Search","search_page"),
    BottomDrawList(R.drawable.outline_chat_24,"Bot","bot_page"),
    BottomDrawList(R.drawable.outline_person_24,"Profile","profile_page")

)