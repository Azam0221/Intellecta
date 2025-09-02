package com.example.intellecta.repository

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class FileStorageRepository(private val context:Context) {


    fun saveFile(uri: Uri, fileName : String): File?{
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputDir = File(context.filesDir,"attachments")
            if(!outputDir.exists()) outputDir.mkdirs()

            val outputFile = File(outputDir,fileName)

            inputStream.use { intput ->
                FileOutputStream(outputFile).use { output ->
                    intput?.copyTo(output)
                }
            }
            outputFile
        }
        catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun getFile(fileName: String) : File {
        return File(File(context.filesDir,"attachments"),fileName)
    }
}