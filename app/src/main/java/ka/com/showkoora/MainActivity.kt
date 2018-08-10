package ka.com.showkoora

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.onesignal.OneSignal
import ka.com.showkoora.localnews.WorldNewsFragment
import ka.com.showkoora.worldnews.Local_NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setIcon(R.drawable.showkora)
        setSupportActionBar(toolbar)


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "1"
        val channel2 = "2"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = "This is BNT"
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)

            val notificationChannel2 = NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN)

            notificationChannel.description = "This is bTV"
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel2)

        }


        MobileAds.initialize(this, "ca-app-pub-7647915106985195~9847131670")

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        WorldIV.setOnClickListener {
            startActivity(Intent(this, WorldNewsFragment::class.java))
        }

        cityIV.setOnClickListener {
            startActivity(Intent(this, Local_NewsFragment::class.java))

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning = false
    }

    companion object {
        var isAppRunning: Boolean = false

    }
}
