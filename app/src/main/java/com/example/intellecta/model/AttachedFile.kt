package com.example.intellecta.model

import android.net.Uri
import com.example.intellecta.fileManaging.FileType

data class AttachedFile(
    val uri : Uri,
    val type : FileType,
    val displayName: String
)