package com.pandorina.legendaryquotes.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.pandorina.legendaryquotes.model.Quote
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.legendaryquotes.ui.adapter.QuoteListAdapter


object Util {
    object QuoteComparator : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }

    fun Activity.configureActionBar(
        fragment: Fragment,
        title: String,
        setDisplayBackButton: Boolean = false,
        setHasOptionsMenu: Boolean = false
    ) {
        fragment.setHasOptionsMenu(setHasOptionsMenu)
        (this as AppCompatActivity).supportActionBar?.apply {
            setTitle(title)
            setDisplayHomeAsUpEnabled(setDisplayBackButton)
        }

    }

    fun getScreenShotFromView(v: View): Bitmap? {
        var croppedScreenShot: Bitmap? = null
        try {
            val screenShot = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
            try {
                val matrix = Matrix()
                matrix.postScale(1.0f, 1.5f)
                croppedScreenShot = Bitmap.createBitmap(
                    screenShot,
                    0,
                    screenShot.height / 2 - screenShot.width / 2,
                    screenShot.width,
                    screenShot.width,
                    matrix, true
                )
                val canvas = Canvas(croppedScreenShot)
                v.draw(canvas)
            } catch (e: Exception) {
                Log.e("GFG", "Failed to capture screenshot because:" + e.message)
            }
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        return croppedScreenShot
    }

    fun saveMediaToStorage(bitmap: Bitmap?, context: Context): Uri? {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return imageUri
    }
}