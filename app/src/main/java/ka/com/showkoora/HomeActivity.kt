package ka.com.showkoora

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import ka.com.showkoora.localnews.WorldNewsFragment
import ka.com.showkoora.worldnews.Local_NewsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
//            R.id.navigation_world -> {
//                supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.cointainer,
//                                WorldNewsFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit()
//                return@OnNavigationItemSelectedListener true
//            }

//            R.id.navigation_local -> {
//                supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.cointainer,
//                                Local_NewsFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit()
//                return@OnNavigationItemSelectedListener true
//            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
