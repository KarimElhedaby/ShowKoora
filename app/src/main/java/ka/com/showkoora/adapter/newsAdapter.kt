package ka.com.showkoora.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ka.com.showkoora.New
import ka.com.showkoora.R
import kotlinx.android.synthetic.main.new_item_row.view.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class newsAdapter(var context: Context, var news: ArrayList<New>, var newsListener: NewsClick) : RecyclerView.Adapter<newsVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsVH {
        return newsVH(LayoutInflater.from(parent.context)
                .inflate(R.layout.new_item_row, null))
    }

    override fun onBindViewHolder(holder: newsVH, position: Int) {
        with(holder.itemView) {
            Glide.with(context)
                    .load(news[position].image)
                    .into(newIV)

            newTV.text = news[position].title
            newDateTV.text = news[position].date

            holder.itemView.setOnClickListener {
                newsListener.onNewsClickListener(news[position])

            }

            holder.itemView.newShareIB.setOnClickListener {
                val bitmap = getBitmapFromView(newIV)
                try {
                    val file = File(context.externalCacheDir, "logicchip.png")
                    val fOut = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                    fOut.flush()
                    fOut.close()
                    file.setReadable(true, false)
                    val intent = Intent(android.content.Intent.ACTION_SEND)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                    intent.putExtra(Intent.EXTRA_TEXT, news[position].desc + "\n"+ "تمت مشاركته من تطبيق Show Koora    ")
                    intent.type = "image/png"
                    context.startActivity(Intent.createChooser(intent, "Share image via"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    open interface NewsClick {
        fun onNewsClickListener(new:New)
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.getBackground()
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable!!.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    override fun getItemCount(): Int {
        return news.size
    }
}


