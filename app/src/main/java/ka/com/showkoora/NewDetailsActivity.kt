package ka.com.showkoora

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_new_details.*
import java.io.File
import java.io.FileOutputStream

class NewDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_details)


        var new: New = New(intent.getStringExtra("title"),
                intent.getStringExtra("date"),
                intent.getStringExtra("image"),
                intent.getStringExtra("desc"))


        newTitleTV.text = new.title
        newDateTV.text = new.date
        newDescTV.text = new.desc
        Glide.with(this)
                .load(new.image)
                .into(newIV)


        newIB.setOnClickListener {
            val bitmap = getBitmapFromView(newIV)
            try {
                val file = File(this.externalCacheDir, "logicchip.png")
                val fOut = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                file.setReadable(true, false)
                val intent = Intent(Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                intent.putExtra(Intent.EXTRA_TEXT, new.desc  + "\n"+ "تمت مشاركته من تطبيق Show Koora    ")
                intent.type = "image/png"
                this.startActivity(Intent.createChooser(intent, "Share image via"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

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
}
