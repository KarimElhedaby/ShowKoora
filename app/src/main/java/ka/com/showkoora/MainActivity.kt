package ka.com.showkoora

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.onesignal.OneSignal
import ka.com.showkoora.localnews.WorldNewsFragment
import ka.com.showkoora.worldnews.Local_NewsFragment
import kotlinx.android.synthetic.main.activity_home.*

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        supportActionBar?.setIcon(R.drawable.showkora)
//        setSupportActionBar(toolbar)

        MobileAds.initialize(this, getString(R.string.APP_INTIALIZE_ID))

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.cointainer,
                        WorldNewsFragment.newInstance())
                .addToBackStack(null)
                .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



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


    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning = false
    }

    companion object {
        var isAppRunning: Boolean = false

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_world -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.cointainer,
                                WorldNewsFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_local -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.cointainer,
                                Local_NewsFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
}
