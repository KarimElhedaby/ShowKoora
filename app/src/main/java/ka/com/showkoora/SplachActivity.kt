package ka.com.showkoora

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds

class SplachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        MobileAds.initialize(this, getString(R.string.APP_INTIALIZE_ID))

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }, 3000)

    }
}
