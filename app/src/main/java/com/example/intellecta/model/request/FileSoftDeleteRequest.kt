package com.example.intellecta.model.request

import kotlinx.serialization.Serializable


@Serializable
data class FileSoftDeleteRequest(val is_deleted: Boolean)