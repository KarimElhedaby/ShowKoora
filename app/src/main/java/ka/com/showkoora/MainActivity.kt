package ka.com.showkoora

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import ka.com.showkoora.localnews.WorldNewsFragment
import ka.com.showkoora.worldnews.Local_NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setIcon(R.drawable.showkora)
        setSupportActionBar(toolbar)


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
}
