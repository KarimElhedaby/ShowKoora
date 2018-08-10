package ka.com.showkoora.localnews

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ka.com.showkoora.New
import ka.com.showkoora.NewDetailsActivity
import ka.com.showkoora.R
import ka.com.showkoora.adapter.newsAdapter
import kotlinx.android.synthetic.main.fragment_world_news.*
import kotlinx.android.synthetic.main.fragment_world_news.view.*

class WorldNewsFragment : Fragment(), newsAdapter.NewsClick {


    companion object {
        fun newInstance(): WorldNewsFragment {
            var fragment = WorldNewsFragment()
            return fragment
        }
    }

    override fun onNewsClickListener(new: New) {
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
        // Inflate the layfragment
        var view: View = inflater.inflate(R.layout.fragment_world_news, container, false)

        view.worldnewsPB.visibility = View.VISIBLE

        FirebaseDatabase.getInstance().getReference("WorldNews").addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                worldnewsPB.visibility = View.GONE

                for (c in p0?.children!!) {
                    new = c.getValue(New::class.java)!!
                    news.add(new)

                }
                var reverseNews: ArrayList<List<New>> = arrayListOf(news.reversed())

                view.worldnewsRV.layoutManager = GridLayoutManager(context, 1)
                view.worldnewsRV.adapter = this@WorldNewsFragment.let { newsAdapter(context!!.applicationContext, reverseNews[0], this@WorldNewsFragment) }
            }

        })

        return view
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_world_news)
//
//            worldnewsPB.visibility=View.VISIBLE
//
//        FirebaseDatabase.getInstance().getReference("WorldNews").addValueEventListener(object : ValueEventListener {
//
//            override fun onCancelled(p0: DatabaseError?) {
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
//
//                worldnewsPB.visibility=View.GONE
//
//                for (c in p0?.children!!) {
//                    new = c.getValue(New::class.java)!!
//                    news.add(new)
//
//                }
//                var reverseNews : ArrayList<List<New>> = arrayListOf(news.reversed())
//
//
//
//                worldnewsRV.layoutManager = GridLayoutManager(this@WorldNewsFragment, 1)
//
//                    worldnewsRV.layoutManager = LinearLayoutManager(this@WorldNewsFragment, LinearLayoutManager.VERTICAL,false)
////                    worldnewsRV.layoutManager = LinearLayoutManager(this@WorldNewsFragment, LinearLayoutManager.VERTICAL,true)
//                worldnewsRV.adapter = this@WorldNewsFragment.let { newsAdapter(it, reverseNews[0], this@WorldNewsFragment) }
//            }
//
//        })

}




