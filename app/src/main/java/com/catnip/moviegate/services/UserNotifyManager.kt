package com.catnip.moviegate.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.catnip.moviegate.R
import com.catnip.moviegate.data.network.RetrofitApi
import com.catnip.moviegate.model.common.Results
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.CH_DAILY
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.CH_NEW_MOVIE
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.ID_REMINDER_DAILY
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.ID_REMINDER_NEW_MOVIE
import com.catnip.moviegate.services.AlarmManagerUtils.Companion.TYPE_DAILY
import com.catnip.moviegate.ui.detailmovie.DetailMovieActivity
import com.catnip.moviegate.ui.main.MainActivity
import com.catnip.moviegate.utils.date.DateUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class UserNotifyManager : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra(AlarmManagerUtils.PARAM_TYPE)
        if (type.equals(TYPE_DAILY.second)) {
            //todo : setting up notification reminder daily
            NotificationUtils().makeNotification(
                context,
                Intent(context, MainActivity::class.java),
                NotificationData(
                    CH_DAILY.first,
                    CH_DAILY.second,
                    ID_REMINDER_DAILY,
                    context!!.getString(R.string.txt_notif_title_daily),
                    context.getString(R.string.txt_notif_content_daily),
                    R.drawable.ic_daily_notif
                )
            )
        } else {
            //todo : setting up notification new movie
            fetchNewFilm(context)
        }
    }

    private fun fetchNewFilm(context: Context?) {
        val api = RetrofitApi.invoke()
        api.getLatestMovie(DateUtils.getFormattedTimeNow(), DateUtils.getFormattedTimeNow())
            .enqueue(object : Callback<Results<Content>> {
                override fun onFailure(call: Call<Results<Content>>, t: Throwable) {
                    context?.let {
                        NotificationUtils().makeNotification(
                            it,
                            Intent(it, MainActivity::class.java),
                            NotificationData(
                                CH_NEW_MOVIE.first,
                                CH_NEW_MOVIE.second,
                                ID_REMINDER_NEW_MOVIE,
                                it.getString(R.string.txt_new_movie_failed_notif),
                                it.getString(R.string.txt_content_new_movie_failed),
                                R.drawable.ic_new_movie
                            )
                        )
                    }
                }

                override fun onResponse(
                    call: Call<Results<Content>>,
                    response: Response<Results<Content>>
                ) {
                    if (response.isSuccessful) {
                        val datas = response.body()?.datas
                        val content = datas?.get(0)
                        content?.let {
                            NotificationUtils().makeNotification(
                                context,
                                DetailMovieActivity.makeIntent(context, it.id),
                                NotificationData(
                                    CH_NEW_MOVIE.first,
                                    CH_NEW_MOVIE.second,
                                    ID_REMINDER_NEW_MOVIE,
                                    "New Movie : " + it.title,
                                    it.overview,
                                    R.drawable.ic_new_movie
                                )
                            )
                        }
                    }
                }
            })
    }

}

class AlarmManagerUtils {
    companion object {
        const val ID_REMINDER_DAILY = 1996
        const val ID_REMINDER_NEW_MOVIE = 1998

        const val PARAM_TYPE = "TYPE"

        val CH_DAILY = Pair("daily_notification", "1996")
        val CH_NEW_MOVIE = Pair("new_movie_notification", "1998")
        val TYPE_DAILY = Pair(PARAM_TYPE, "daily_notification")
        val TYPE_NEW_MOVIE = Pair(PARAM_TYPE, "new_movie_notification")
    }

    fun setAlarm(
        context: Context?,
        time: Calendar,
        alarmId: Int,
        args: List<Pair<String, String>>
    ) {
        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, UserNotifyManager::class.java)
        args.forEach {
            intent.putExtra(it.first, it.second)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            time.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    fun unsetAlarm(context: Context?, alarmId: Int) {

        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, UserNotifyManager::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

}

class NotificationUtils {
    fun makeNotification(context: Context?, intent: Intent, notificationData: NotificationData) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationData.notificationID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, notificationData.channelId)
            .setContentTitle(notificationData.title)
            .setContentText(notificationData.content)
            .setSmallIcon(notificationData.smallIcon)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationData.channelId,
                notificationData.channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(notificationData.channelId)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(
            notificationData.notificationID,
            builder.build()
        )
    }
}

data class NotificationData(
    var channelName: String,
    var channelId: String,
    var notificationID: Int,
    var title: String,
    var content: String,
    var smallIcon: Int
)
