package hu.bme.aut.android.bitprophet.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import hu.bme.aut.android.bitprophet.R

private const val NOTIFICATION_ID = 97

fun NotificationManager.sendNotification(appContext: Context) {
    val builder = NotificationCompat.Builder(
        appContext,
        appContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(appContext.getString(R.string.notification_title))
        .setContentText(appContext.getString(R.string.notification_text))

    notify(NOTIFICATION_ID, builder.build())
}