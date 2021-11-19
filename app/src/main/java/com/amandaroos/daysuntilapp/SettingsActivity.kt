package com.amandaroos.daysuntilapp

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    lateinit var sharedPref: SharedPreferences

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        var isShowHoursChecked =
            sharedPref.getBoolean(getString(R.string.display_hours_key), false)
        display_hours_switch.isChecked = isShowHoursChecked

        var isShowDateandTimeChecked =
            sharedPref.getBoolean(getString(R.string.display_date_and_time_key), false)
        display_date_and_time_switch.isChecked = isShowDateandTimeChecked

        display_hours_switch.setOnCheckedChangeListener { it, isChecked ->
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.display_hours_key), isChecked)
                commit()
                updateWidget()
            }
        }

        display_date_and_time_switch.setOnCheckedChangeListener { it, isChecked ->
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.display_date_and_time_key), isChecked)
                commit()
                updateWidget()
            }
        }
    }

    fun updateWidget() {
        val ids = AppWidgetManager.getInstance(applicationContext).getAppWidgetIds(
            ComponentName(
                applicationContext,
                DaysUntilAppWidget::class.java
            )
        )
        for (id in ids) {
            updateAppWidget(applicationContext, AppWidgetManager.getInstance(applicationContext), id)
        }
    }
}
