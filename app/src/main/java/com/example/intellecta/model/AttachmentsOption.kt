package com.example.intellecta.model

import androidx.annotation.DrawableRes

data class AttachmentsOption(
    val name : String,
    @DrawableRes val icon : Int,
    val onClick:() -> Unit
)
