package com.example.intellecta

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun  getFileNameFromUri(context: Context,uri:Uri):String{
    var fileName = ""
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()){
            val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                fileName = it.getString(displayNameIndex)
            }
        }

    }

    return fileName.ifEmpty { "attachment_${System.currentTimeMillis()}" }
}