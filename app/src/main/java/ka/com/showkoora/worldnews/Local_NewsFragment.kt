package ka.com.showkoora.worldnews

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ka.com.showkoora.New
import ka.com.showkoora.NewDetailsActivity
import ka.com.showkoora.R
import ka.com.showkoora.adapter.newsAdapter
import kotlinx.android.synthetic.main.fragment_local__news.*

class Local_NewsFragment : AppCompatActivity(), newsAdapter.NewsClick {


    var news: ArrayList<New> = arrayListOf()
    lateinit var new: New

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_local__news)

        localnewsPB.visibility = View.VISIBLE

        FirebaseDatabase.getInstance().getReference("CityNews").addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                localnewsPB.visibility = View.GONE

                for (c in p0?.children!!) {
                    new = c.getValue(New::class.java)!!
                    news.add(new)
                    localnewsRV.layoutManager = GridLayoutManager(this@Local_NewsFragment, 1)
//                    localnewsRV.layoutManager = LinearLayoutManager(this@Local_NewsFragment, LinearLayoutManager.VERTICAL,true)
                    localnewsRV.adapter = this@Local_NewsFragment.let { newsAdapter(it, news, this@Local_NewsFragment) }
                }
            }
        })
    }


    override fun onNewsClickListener(new: New) {
        var intent = Intent(this, NewDetailsActivity::class.java)
        intent.putExtra("title", new.title)
        intent.putExtra("image", new.image)
        intent.putExtra("date", new.date)
        intent.putExtra("desc", new.desc)
        startActivity(intent)

    }
}