package com.pandorina.legendaryquotes.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val dao: QuoteDao,
    private val context: Context
) {

    suspend fun insertQuote(quote: Quote) = dao.insertQuote(quote)

    suspend fun deleteQuote(quote: Quote) = dao.deleteQuote(quote)

    fun getQuotes() = dao.getQuotes()

    private object PreferencesKeys {
        val IMAGE_ID = intPreferencesKey("image_id")
    }

    companion object {
        private const val QUOTE_APP_BACKGROUND_IMAGES = "quote_app_background_images"
        private val Context.dataStore by preferencesDataStore(QUOTE_APP_BACKGROUND_IMAGES)
        const val DATABASE_ERROR = "data_store_error"

    }

    suspend fun changeBackgroundImage(imageId: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IMAGE_ID] = imageId
        }
    }

    val getCurrentBackgroundImage: Flow<Int> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            Log.d(DATABASE_ERROR, exception.message.toString())
            emit(emptyPreferences())
        }
    }.map { preference ->
        val imageId = preference[PreferencesKeys.IMAGE_ID] ?: R.drawable.image_bacground_coast_2
        imageId
    }
}