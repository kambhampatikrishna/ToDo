package com.example.todo.ui

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.todo.R

const val notificationId = 1
const val channelId = "channel1"
const val notificationTitle = "titleExtra"
const val notificationMessage = "messageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = context?.let {
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent?.getStringExtra(notificationTitle))
                .setContentText(intent?.getStringExtra(notificationMessage))
                .build()
        }

        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)


    }
}