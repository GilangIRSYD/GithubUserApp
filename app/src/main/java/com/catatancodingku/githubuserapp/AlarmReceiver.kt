package com.catatancodingku.githubuserapp

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        showNotification(context)
        Log.d("cek_alarm", "onReceive: notif alarm ")
    }

    fun showNotification(context: Context) {

        val channelID = "Channel_1"
        val channelName = "AlarmManager Channel"

        val pendingIntent = TaskStackBuilder.create(context)
            .addParentStack(MainActivity::class.java)
            .addNextIntent(Intent(context,MainActivity::class.java))
            .getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.alarm)
            .setContentIntent(pendingIntent)
            .setContentTitle("GithubUserAPP")
            .setContentText("Yuk liat bagaimana produktifnya mereka dalam programming")
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        //Untuk device diatas oreo, menggunakan channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelID)

            notificationManagerCompat.createNotificationChannel(channel)

        }

        val notification = builder.build()
        notificationManagerCompat.notify(100, notification)

    }

    fun setAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, 0)

        Log.d("cek_alarm", "setAlarm calendar : ${calendar.time} ")

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Alarm turn ON", Toast.LENGTH_SHORT).show()
    }

    fun cencelAlarm(context: Context) {

        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Alarm turn OFF", Toast.LENGTH_SHORT).show()
    }


    companion object {
        fun isAlarmSet(context: Context): Boolean {
            val intent = Intent(context, AlarmReceiver::class.java)
            val requestCode = 100

            return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE
            ) != null
        }
    }
}
