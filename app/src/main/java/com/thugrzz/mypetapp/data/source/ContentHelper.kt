package com.thugrzz.mypetapp.data.source

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import com.thugrzz.mypetapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class ContentHelper(
    private val context: Context
) {

    private val resolver = context.contentResolver

    @SuppressLint("Recycle")
    suspend fun getFileName(uri: Uri): String = withContext(Dispatchers.IO) {
        resolver.query(uri, null, null, null, null)!!.use { cursor ->
            cursor.moveToFirst()
            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))!!
        }
    }

    suspend fun getMimeType(uri: Uri): String = withContext(Dispatchers.IO) {
        resolver.getType(uri)!!
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun openInputStream(uri: Uri): InputStream = withContext(Dispatchers.IO) {
        resolver.openInputStream(uri)!!
    }

    fun createCameraTempFile(): Uri {
        val authority = context.getString(R.string.fileprovider_authorities)
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX, path)
        return FileProvider.getUriForFile(context, authority, file)
    }

    fun createAvatarCameraTempFile(): Uri {
        val authority = context.getString(R.string.fileprovider_authorities)
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(TEMP_AVATAR_PREFIX, TEMP_FILE_SUFFIX, path)
        return FileProvider.getUriForFile(context, authority, file)
    }

    private fun getQueryFileName(uri: Uri): String {
        val returnCursor: Cursor? = resolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: 0
        returnCursor?.moveToFirst()
        val name: String = returnCursor?.getString(nameIndex) ?: ""
        returnCursor?.close()
        return name
    }

    fun getFilePathUri(uri: Uri): Uri {
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val queryFIleName = getQueryFileName(uri)
        val file = File(path, queryFIleName)
        return Uri.fromFile(file)
    }

    fun clearTempAvatarImages() {
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val isPathExist = path?.exists() ?: false
        val isPathDirectory = path?.isDirectory ?: false
        if (isPathExist && isPathDirectory) {
            val files = path?.listFiles { _, name -> name.startsWith(TEMP_AVATAR_PREFIX) }
                ?: emptyArray()
            for (file in files) {
                file.delete()
            }
        }
    }

    companion object {
        private const val TEMP_FILE_PREFIX = "elife"
        private const val TEMP_FILE_SUFFIX = ".jpg"

        private const val TEMP_AVATAR_PREFIX = "avatar"
    }
}