package com.catnip.moviegate.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.net.toUri
import com.catnip.moviegate.R
import com.catnip.moviegate.ui.detailmovie.DetailMovieActivity
import com.catnip.moviegate.ui.widget.FavoriteStackImageWidget.Companion.INTENT_ACTION_OPEN

/**
 * Implementation of App Widget functionality.
 */
class FavoriteStackImageWidget : AppWidgetProvider() {
    companion object {
        const val PARAM_ID_CONTENT = "com.catnip.moviegate.PARAM_ID_CONTENT"
        const val INTENT_ACTION_OPEN = "com.catnip.moviegate.WIDGET_OPEN"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action.equals(INTENT_ACTION_OPEN)) {
            val id = intent?.getStringExtra(PARAM_ID_CONTENT)
            DetailMovieActivity.run(context, id)
        }

    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val i = Intent(context, FavoriteWidgetService::class.java)
    i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    i.data = i.toUri(Intent.URI_INTENT_SCHEME).toUri()
    appWidgetManager.updateAppWidget(appWidgetId, null)

    val view = RemoteViews(context.packageName, R.layout.layout_widget_favorite)
    view.setRemoteAdapter(R.id.sv_widget, i)
    view.setEmptyView(R.id.sv_widget, R.id.txt_no_data_widget)
    appWidgetManager.updateAppWidget(appWidgetId, view)


    val favIntent = Intent(context, FavoriteStackImageWidget::class.java)
    favIntent.action = INTENT_ACTION_OPEN
    favIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    i.data = i.toUri(Intent.URI_INTENT_SCHEME).toUri()
    val pendingIntent = PendingIntent
        .getBroadcast(context, 0, favIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    view.setPendingIntentTemplate(R.id.sv_widget, pendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, view)
}

object WidgetTools {
    fun changeWidget(context: Context) {
        val widManager = AppWidgetManager.getInstance(context)
        val widId = widManager.getAppWidgetIds(
            ComponentName(
                context, FavoriteStackImageWidget::class.java
            )
        )
        widManager.notifyAppWidgetViewDataChanged(widId, R.id.sv_widget)
        val update =
            Intent(context, FavoriteStackImageWidget::class.java)
        update.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widId)
        context.sendBroadcast(update)
    }
}

class FavoriteWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return RemoteViewsFavoriteWidget(this.applicationContext)
    }

}