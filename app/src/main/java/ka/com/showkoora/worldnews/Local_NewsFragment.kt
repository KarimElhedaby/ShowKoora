package ka.com.showkoora.worldnews

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ka.com.showkoora.New
import ka.com.showkoora.NewDetailsActivity
import ka.com.showkoora.R
import ka.com.showkoora.adapter.newsAdapter
import kotlinx.android.synthetic.main.fragment_local__news.view.*
import kotlinx.android.synthetic.main.fragment_local__news.*


class Local_NewsFragment : Fragment(), newsAdapter.NewsClick {

    private lateinit var mInterstitialAd: InterstitialAd


    companion object {
        fun newInstance(): Local_NewsFragment {
            var fragment = Local_NewsFragment()
            return fragment
        }
    }

    override fun onNewsClickListener(new: New) {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
        var intent: Intent = Intent(activity, NewDetailsActivity::class.java)
        intent.putExtra("title", new.title)
        intent.putExtra("image", new.image)
        intent.putExtra("date", new.date)
        intent.putExtra("desc", new.desc)
        startActivity(intent)

    }

    var news: ArrayList<New> = arrayListOf()
    lateinit var new: New


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //  Inflate the layfragment
        var view: View = inflater.inflate(R.layout.fragment_local__news, container, false)

        view.localnewsPB.visibility = View.VISIBLE

        MobileAds.initialize(context,getString(R.string.APP_INTIALIZE_ID))

        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = getString(R.string.FULL_SCREEN_AD_ID)

        mInterstitialAd.loadAd(AdRequest.Builder().build())


        FirebaseDatabase.getInstance().getReference("CityNews").addValueEventListener(object : ValueEventListener {


            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                view.localnewsPB.visibility = View.GONE

                for (c in p0?.children!!) {
                    new = c.getValue(New::class.java)!!
                    news.add(new)

                }
                var reverseNews: ArrayList<List<New>> = arrayListOf(news.reversed())

                view.localnewsRV.layoutManager = GridLayoutManager(context, 1)
                view.localnewsRV.adapter = this@Local_NewsFragment.let { newsAdapter(context!!.applicationContext, reverseNews[0], this@Local_NewsFragment) }
            }

        })

        return view
    }
}