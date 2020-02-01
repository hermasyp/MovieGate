package com.catnip.moviegate.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.catnip.moviegate.R
import com.catnip.moviegate.services.AlarmManagerUtils
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.TYPE_DAILY
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.TYPE_NEW_MOVIE
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var swDailyNotif: SwitchPreference
    private lateinit var swNewMovieNotif: SwitchPreference
    private lateinit var btnChangeLanguage: Preference
    private lateinit var keyDailyNotif: String
    private lateinit var keyNewMovie: String
    private lateinit var keyButtonChangeLanguage: String
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        initPreference()

    }

    private fun initPreference() {
        keyDailyNotif = resources.getString(R.string.txt_key_reminder_daily)
        keyNewMovie = resources.getString(R.string.txt_key_reminder_new_movie)
        keyButtonChangeLanguage = resources.getString(R.string.txt_key_change_language)

        swDailyNotif = findPreference(keyDailyNotif)!!
        swNewMovieNotif = findPreference(keyNewMovie)!!
        btnChangeLanguage = findPreference(keyButtonChangeLanguage)!!

        btnChangeLanguage.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            false
        }

    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == keyDailyNotif) {
            sharedPreferences?.let {
                val state: Boolean = it.getBoolean(keyDailyNotif, false)
                swDailyNotif.isChecked = state
                if (state) {
                    //todo : set daily notification
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 7)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    Toast.makeText(context,getString(R.string.txt_toast_daily_alarm_on),Toast.LENGTH_SHORT).show()
                    AlarmManagerUtils().setAlarm(context, calendar, AlarmManagerUtils.ID_REMINDER_DAILY,listOf(TYPE_DAILY))
                } else {
                    //todo : unset daily notification
                    Toast.makeText(context,getString(R.string.txt_toast_daily_alarm_off),Toast.LENGTH_SHORT).show()
                    AlarmManagerUtils().unsetAlarm(context,AlarmManagerUtils.ID_REMINDER_DAILY)
                }
            }
        }

        if (key == keyNewMovie) {
            sharedPreferences?.let {
                val state: Boolean = it.getBoolean(keyNewMovie, false)
                swNewMovieNotif.isChecked = state
                if (state) {
                    //todo : set new movie notification
                    Toast.makeText(context,getString(R.string.txt_toast_new_movie_alarm_on),Toast.LENGTH_SHORT).show()
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 8)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    AlarmManagerUtils().setAlarm(context, calendar, AlarmManagerUtils.ID_REMINDER_NEW_MOVIE,listOf(TYPE_NEW_MOVIE))
                } else {
                    //todo : unset new movie notification
                    Toast.makeText(context,getString(R.string.txt_toast_new_movie_alarm_off),Toast.LENGTH_SHORT).show()
                    AlarmManagerUtils().unsetAlarm(context,AlarmManagerUtils.ID_REMINDER_NEW_MOVIE)
                }
            }
        }
    }
}
