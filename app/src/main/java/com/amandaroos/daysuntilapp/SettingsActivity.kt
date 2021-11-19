package com.amandaroos.daysuntilapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    lateinit var sharedPref: SharedPreferences

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }
}
