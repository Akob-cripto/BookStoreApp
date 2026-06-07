package com.example.bookstoreapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import androidx.core.graphics.scale

object ImageBase64Utils {

    fun uriToBase64(
        context: Context,
        uri: Uri,
        maxWidth: Int = 600,
        maxHeight: Int = 800,
        quality: Int = 65
    ): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (originalBitmap == null) return null

            val ratio = minOf(
                maxWidth.toFloat() / originalBitmap.width,
                maxHeight.toFloat() / originalBitmap.height,
                1f
            )

            val newWidth = (originalBitmap.width * ratio).toInt()
            val newHeight = (originalBitmap.height * ratio).toInt()

            val scaledBitmap = originalBitmap.scale(newWidth, newHeight)

            val outputStream = ByteArrayOutputStream()

            scaledBitmap.compress(
                Bitmap.CompressFormat.JPEG,
                quality,
                outputStream
            )

            val bytes = outputStream.toByteArray()

            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }
}