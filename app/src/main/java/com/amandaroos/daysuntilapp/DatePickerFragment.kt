package com.amandaroos.daysuntilapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        var savedDate =
            sharedPref?.getLong(
                getString(R.string.date_key),
                Calendar.getInstance().timeInMillis
            )

        val c = Calendar.getInstance()
        c.timeInMillis = savedDate!!
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        var savedDate =
            sharedPref?.getLong(
                getString(R.string.date_key),
                Calendar.getInstance().timeInMillis
            )

        val c = Calendar.getInstance()
        c.timeInMillis = savedDate!!
        c.set(year, month, day)

        sharedPref!!.edit().putLong(getString(R.string.date_key), c.timeInMillis).commit()

        val ids = AppWidgetManager.getInstance(activity).getAppWidgetIds(
            ComponentName(
                requireContext(),
                DaysUntilAppWidget::class.java
            )
        )
        for (id in ids) {
            updateAppWidget(requireContext(), AppWidgetManager.getInstance(context), id)
        }
    }
}