package ka.com.showkoora.localnews

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
import kotlinx.android.synthetic.main.fragment_world_news.*

class WorldNewsFragment : AppCompatActivity(), newsAdapter.NewsClick {

    override fun onNewsClickListener(new: New) {
        var intent = Intent(this,NewDetailsActivity::class.java)
        intent.putExtra("title",new.title)
        intent.putExtra("image",new.image)
        intent.putExtra("date",new.date)
        intent.putExtra("desc",new.desc)
        startActivity(intent)

    }


    var news: ArrayList<New> = arrayListOf()
    lateinit var new: New

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_world_news)

            worldnewsPB.visibility=View.VISIBLE

        FirebaseDatabase.getInstance().getReference("WorldNews").addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                worldnewsPB.visibility=View.GONE

                for (c in p0?.children!!) {
                    new = c.getValue(New::class.java)!!
                    news.add(new)
                    worldnewsRV.layoutManager = GridLayoutManager(this@WorldNewsFragment, 1)

//                    worldnewsRV.layoutManager = LinearLayoutManager(this@WorldNewsFragment, LinearLayoutManager.VERTICAL,true)
                    worldnewsRV.adapter = this@WorldNewsFragment.let { newsAdapter(it, news, this@WorldNewsFragment) }
                }
            }

        })

    }


}

