package ka.com.showkoora.pushnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ka.com.showkoora.MainActivity
import ka.com.showkoora.R
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val NOTIFICATION_ID_EXTRA = "notificationId"
    private val IMAGE_URL_EXTRA = "https://firebasestorage.googleapis.com/v0/b/beauty-b7791.appspot.com/o/logo%2Fshowkora.png?alt=media&token=c1608a48-9e61-45ce-b284-eb48fb3c969d"
    private val ADMIN_CHANNEL_ID = "admin_channel"
    private var notificationManager: NotificationManager? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val notificationIntent = Intent(this, MainActivity::class.java)
        if (MainActivity.isAppRunning) {
            //Some action
        } else {
            //Show notification as usual
        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)

        val pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT)

        //You should use an actual ID instead
        val notificationId = Random().nextInt(60000)


        val bitmap = getBitmapfromUrl("https://firebasestorage.googleapis.com/v0/b/beauty-b7791.appspot.com/o/logo%2Fshowkora.png?alt=media&token=c1608a48-9e61-45ce-b284-eb48fb3c969d")

        val likeIntent = Intent(this, LikeService::class.java)
        likeIntent.putExtra(NOTIFICATION_ID_EXTRA, notificationId)
        likeIntent.putExtra(IMAGE_URL_EXTRA, remoteMessage?.data!!["image-url"])
        val likePendingIntent = PendingIntent.getService(this,
                notificationId + 1, likeIntent, PendingIntent.FLAG_ONE_SHOT)


        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
        }

        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.data["title"])
                .setStyle(NotificationCompat.BigPictureStyle()
                        .setSummaryText(remoteMessage.data["message"])
                        .bigPicture(bitmap))/*Notification with Image*/
                .setContentText(remoteMessage.data["message"])
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        notificationManager!!.notify(notificationId, notificationBuilder.build())

    }

    fun getBitmapfromUrl(imageUrl: String): Bitmap? {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannelName = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin_channel_description)

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(adminChannel)
        }
    }


}