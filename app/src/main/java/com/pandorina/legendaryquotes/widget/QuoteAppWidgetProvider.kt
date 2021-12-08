package com.pandorina.legendaryquotes.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.data.Resources
import com.pandorina.legendaryquotes.ui.activity.SplashScreenActivity

class QuoteAppWidgetProvider: AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }
}

@RequiresApi(Build.VERSION_CODES.M)
internal fun updateAppWidget(
    context: Context?,
    appWidgetManager: AppWidgetManager?,
    appWidgetId: Int
) {
    val views = RemoteViews(context?.packageName, R.layout.widget_layout_quote)
    val quote = Resources.quoteListForWidget.random()
    val backgroundImage = Resources.backgroundListForWidget.random()
    views.setTextViewText(R.id.widget_tv_quote, quote.text)
    views.setTextViewText(R.id.widget_tv_owner, quote.owner)
    views.setImageViewResource(R.id.widget_bg, backgroundImage)

    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        Intent(context, SplashScreenActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

    appWidgetManager?.updateAppWidget(appWidgetId, views)
}









