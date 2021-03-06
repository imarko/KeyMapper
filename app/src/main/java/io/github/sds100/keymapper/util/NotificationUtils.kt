package io.github.sds100.keymapper.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.sds100.keymapper.broadcastreceiver.OpenIMEPickerBroadcastReceiver
import io.github.sds100.keymapper.R

/**
 * Created by sds100 on 30/09/2018.
 */

object NotificationUtils {
    private const val NOTIFICATION_ID_PERSISTENT = 123
    private const val CHANNEL_ID_PERSISTENT = "channel_persistent"

    fun showIMEPickerNotification(ctx: Context) {
        ctx.apply {
            //create the channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                        CHANNEL_ID_PERSISTENT,
                        str(R.string.notification_channel_persistent),
                        NotificationManager.IMPORTANCE_MIN
                )

                val manager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }

            val openImePickerIntent = Intent(ctx, OpenIMEPickerBroadcastReceiver::class.java).apply {
                action = OpenIMEPickerBroadcastReceiver.ACTION_SHOW_IME_PICKER
            }

            val openImePickerPendingIntent = PendingIntent.getBroadcast(ctx, 0, openImePickerIntent, 0)

            val builder = NotificationCompat.Builder(ctx, CHANNEL_ID_PERSISTENT)
                    //TODO change notification icon
                    .setSmallIcon(R.drawable.ic_keyboard_notification)
                    .setColor(color(R.color.colorAccent))
                    .setContentTitle(str(R.string.notification_persistent_title))
                    .setContentText(str(R.string.notification_persistent_text))
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setOngoing(true)
                    .setVisibility(NotificationCompat.VISIBILITY_SECRET) //hide on lockscreen
                    .setContentIntent(openImePickerPendingIntent) //show IME picker on click

            val notification = builder.build()

            //show the notification
            with(NotificationManagerCompat.from(ctx)) {
                notify(NotificationUtils.NOTIFICATION_ID_PERSISTENT, notification)
            }
        }
    }

    fun hideImePickerNotification(ctx: Context) {
        val manager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(NOTIFICATION_ID_PERSISTENT)
    }
}