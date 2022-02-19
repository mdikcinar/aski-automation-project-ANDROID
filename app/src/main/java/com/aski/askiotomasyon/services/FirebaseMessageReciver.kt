package com.aski.askiotomasyon.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.aski.askiotomasyon.SplashScreen
import com.aski.askiotomasyon.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessageReceiver : FirebaseMessagingService() {
    var notification_id: Byte = 0
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //handle when receive notification via data event
        if (remoteMessage.data.size > 0) {
            showNotification(
                remoteMessage.data["title"],
                remoteMessage.data["message"]
            )
        }
        //handle when receive notification
        if (remoteMessage.notification != null) {
            showNotification(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body
            )
        }
    }

    private fun getCustomDesign(title: String?, message: String?): RemoteViews {
        val remoteViews =
            RemoteViews(applicationContext.packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        remoteViews.setImageViewResource(R.id.icon, R.drawable.icon)
        Log.e("FCM", title + message)
        return remoteViews
    }

    fun showNotification(title: String?, message: String?) {
        val intent = Intent(this, SplashScreen::class.java)
        val CHANNEL_1_ID = "ariza-bildirim"
        val CHANNEL_2_ID = "duyuru-bildirim"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        var uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        if (uri == null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
            if (uri == null) {
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                if (uri == null) {
                    uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                }
            }
        }
        var builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon)
                .setSound(uri)
                .setAutoCancel(true)
                .setLights(0xff0000, 100, 100) //.setVibrate(new long[]{1000,1000,1000,1000,1000})
                //.setOnlyAlertOnce(true)
                //.setFullScreenIntent(fullScreenPendingIntent, true
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) // Set the intent that will fire when the user taps the notification
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContent(getCustomDesign(title, message))
        } else {
            builder.setContentTitle(title)
                .setContentText(message)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Aski Arıza Bildirimi",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "Aski arıza bildirimleri bu kanal üzerinden yapılır."
            channel1.setSound(uri, null)
            channel1.enableVibration(true)
            //channel1.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel1.enableLights(true)
            channel1.lightColor = Color.YELLOW
            channel1.shouldVibrate()
            channel1.canShowBadge()
            val channel2 =
                NotificationChannel(CHANNEL_2_ID, "Aski Duyuru", NotificationManager.IMPORTANCE_LOW)
            channel2.description = "Aski duyuruları bu kanal üzerinden yapılır."
            //notificationChannel.setSound(uri,null);
            //notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            channel1.enableLights(true)
            channel1.shouldVibrate()
            //notificationChannel.setSound(uri);
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }

        //builder.build();
        notificationManager.notify(notification_id.toInt(), builder.build())
        notification_id++

        /*int unOpenCount=AppUtill.getPreferenceInt("NOTICOUNT",this);


        AppUtill.savePreferenceLong("NOTICOUNT",unOpenCount,this);
        //notificationManager.notify(unOpenCount , notificationBuilder.build());

        // This is for bladge on home icon
        //BadgeUtils.setBadge(MyGcmListenerService.this,(int)unOpenCount);*/
    }
}