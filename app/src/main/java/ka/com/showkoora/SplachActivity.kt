package ka.com.showkoora

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
    Handler().postDelayed({
        startActivity(Intent(this, MainActivity::class.java))

        finish()
    }, 3000)

    }
}
