package com.example.intellecta.ui.components

import android.net.Uri
import com.example.intellecta.FileType

data class AttachedFile(
    val uri : Uri,
    val type : FileType
)