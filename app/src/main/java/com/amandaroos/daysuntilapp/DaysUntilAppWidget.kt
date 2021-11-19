package com.amandaroos.daysuntilapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.view.View
import android.widget.RemoteViews
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class DaysUntilAppWidget : AppWidgetProvider() {

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
    val MILLISECONDS_IN_DAY = 86400000
    val MILLISECONDS_IN_HOUR = 3600000
    val MILLISECONDS_IN_MINUTE = 60000
    val MILLISECONDS_IN_SECOND = 1000

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
    )
    // Create an Intent to launch SettingsActivity
//            val pendingIntent: PendingIntent = Intent(context, SettingsActivity::class.java)
//                .let { intent ->
//                    PendingIntent.getActivity(context, 0, intent, 0)
//                }

    //Create intent for updating widget
//            val intent = Intent(context, DaysUntilAppWidget::class.java)
//            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

    var layoutId: Int = R.layout.days_until_app_widget

    // Get the layout for the App Widget and attach an on-click listener
    // to the button
    val views: RemoteViews = RemoteViews(
        context.packageName,
        layoutId
    ).apply {
        //setOnClickPendingIntent(R.id.widget_settings_button, pendingIntent)
        //setOnClickPendingIntent(R.id.widget_refresh_button, pendingIntent2)
    }

    var showHours =
        sharedPref.getBoolean(context.getString(R.string.display_hours_key), false)

    var showDateAndTime =
        sharedPref.getBoolean(context.getString(R.string.display_date_and_time_key), false)

    val c = Calendar.getInstance()
    var now = c.timeInMillis

    var savedDate =
        sharedPref.getLong(
            context.getString(R.string.date_key),
            Calendar.getInstance().timeInMillis
        )

    views.setTextViewText(
        R.id.date_text,
        getDateFormat(context).format(savedDate)
    )
    views.setTextViewText(
        R.id.time_text,
        getTimeFormat(context).format(savedDate)
    )

    var days = (savedDate - now) / MILLISECONDS_IN_DAY

    if (days < 1) {
        views.setTextViewText(R.id.days_text, context.getString(R.string.days))
        views.setTextViewText(R.id.days_until_number, "0")
    } else if (days < 2) {
        views.setTextViewText(R.id.days_text, context.getString(R.string.day))
        views.setTextViewText(R.id.days_until_number, "1")
    } else if (days > 0) {
        views.setTextViewText(R.id.days_text, context.getString(R.string.days))
        views.setTextViewText(R.id.days_until_number, days.toInt().toString())
    }


    var hours = ((savedDate - now) % MILLISECONDS_IN_DAY) / MILLISECONDS_IN_HOUR
    if (hours < 1) {
        views.setTextViewText(R.id.hours_text, context.getString(R.string.hours))
        views.setTextViewText(R.id.hours_until_number, "0")
    } else if (hours < 2) {
        views.setTextViewText(R.id.hours_text, context.getString(R.string.hour))
        views.setTextViewText(R.id.hours_until_number, "1")
    } else if (hours > 0) {
        views.setTextViewText(R.id.hours_text, context.getString(R.string.hours))
        views.setTextViewText(R.id.hours_until_number, hours.toInt().toString())
    }

//            var minutes =
//                (((savedDate - now) % MILLISECONDS_IN_DAY) % MILLISECONDS_IN_HOUR) / MILLISECONDS_IN_MINUTE
//            if (minutes < 1) {
//                minutes_text.text = context.getString(R.string.minutes)
//                minutes_until_number.text = "0"
//            } else if (minutes < 2) {
//                minutes_text.text = context.getString(R.string.minute)
//                minutes_until_number.text = "1"
//            } else if (minutes > 0) {
//                minutes_text.text = context.getString(R.string.minutes)
//                minutes_until_number.text = minutes.toInt().toString()
//            }
//            var seconds =
//                ((((savedDate - now) % MILLISECONDS_IN_DAY) % MILLISECONDS_IN_HOUR) % MILLISECONDS_IN_MINUTE) / MILLISECONDS_IN_SECOND
//            if (seconds < 1) {
//                seconds_text.text = context.getString(R.string.seconds)
//                seconds_until_number.text = "0"
//            } else if (seconds < 2) {
//                seconds_text.text = context.getString(R.string.second)
//                seconds_until_number.text = "1"
//            } else if (seconds > 0) {
//                seconds_text.text = context.getString(R.string.seconds)
//                seconds_until_number.text = seconds.toInt().toString()
//            }

    if (showHours){
        views.setViewVisibility(R.id.widget_hours_layout, View.VISIBLE)
    } else {
        views.setViewVisibility(R.id.widget_hours_layout, View.GONE)
    }

    if (showDateAndTime){
        views.setViewVisibility(R.id.widget_date_time_layout, View.VISIBLE)
    } else {
        views.setViewVisibility(R.id.widget_date_time_layout, View.GONE)
    }

    // Tell the AppWidgetManager to perform an update on the current app widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}