package uz.nabijonov.otabek.prayertime

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

/**
 * Implementation of App Widget functionality.
 */

class DayTimesWidget : AppWidgetProvider() {
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

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.day_times_widget)


    val url = "https://islomapi.uz/api/present/day?region=Toshkent"
    val queue: RequestQueue = Volley.newRequestQueue(context)
    val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->

        try {
            val array = response.getJSONObject("times")
            views.setTextViewText(R.id.TVbomdodw, array.getString("tong_saharlik"))
            views.setTextViewText(R.id.TVquyoshw, array.getString("quyosh"))
            views.setTextViewText(R.id.TVpeshinw, array.getString("peshin"))
            views.setTextViewText(R.id.TVasrw, array.getString("asr"))
            views.setTextViewText(R.id.TVshomw, array.getString("shom_iftor"))
            views.setTextViewText(R.id.TVhuftonw, array.getString("hufton"))

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }, { error ->
        Log.e("TAG", "RESPONSE IS $error")
    })
    queue.add(request)


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

