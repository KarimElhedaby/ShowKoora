package ka.com.showkoora.pushnotification

import android.app.Service
import android.content.Intent
import android.os.IBinder

class LikeService : Service() {


    private val NOTIFICATION_ID_EXTRA = "notificationId"
    private val IMAGE_URL_EXTRA = "imageUrl"

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}