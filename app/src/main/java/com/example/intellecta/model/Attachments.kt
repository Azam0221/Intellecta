package com.example.intellecta.model

import androidx.annotation.DrawableRes

data class Attachments (
    var  type:String,
    @DrawableRes val imageRes:Int,
    var onClick:()-> Unit
)

//val attachmentList = listOf(
//    Attachments(),
//    Attachments(),
//    Attachments()
//)