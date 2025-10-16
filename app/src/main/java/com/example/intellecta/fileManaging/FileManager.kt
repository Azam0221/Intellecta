package com.example.intellecta.fileManaging

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.intellecta.model.FileMeta
import java.io.File

class FileManager(private val context: Context){

    fun openFile(fileMeta: FileMeta) {

        try {
            val attachmentDir = File(context.filesDir, "attachments")
            val file = File(attachmentDir, fileMeta.fileData)

            if (!file.exists()) {
                Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show()
                return
            }

            val authority = "${context.packageName}.provider"
            val contentUri = FileProvider.getUriForFile(context, authority, file)


            val openIntent = Intent(Intent.ACTION_VIEW).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setDataAndType(contentUri, context.contentResolver.getType(contentUri))
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(openIntent)

        } catch (e: Exception) {
            Toast.makeText(context, "Cannot open file. No application found", Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }
    }

}