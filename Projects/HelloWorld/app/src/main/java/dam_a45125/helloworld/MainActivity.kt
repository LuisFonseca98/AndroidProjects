package dam_a45125.helloworld

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var animDraw: AnimationDrawable? = null
    var ratingBar: RatingBar? = null
    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ratingBar = findViewById<View>(R.id.ratingBar) as RatingBar
        ratingBar!!.numStars = 5
        button = findViewById<View>(R.id.checkButton) as Button

        val imageView = findViewById<View>(R.id.imageAnim) as ImageView
        imageView.setBackgroundResource(R.drawable.animation)

        animDraw = imageView.background as AnimationDrawable
        ratingBar!!.onRatingBarChangeListener = OnRatingBarChangeListener {
            ratingBar, rating, fromUser -> Toast.makeText(this, "Stars: " + rating.toInt(), Toast.LENGTH_LONG).show()
        }

        button!!.setOnClickListener {
            Toast.makeText(this,
                    "Stars: " + ratingBar!!.rating.toInt(),
                    Toast.LENGTH_SHORT).show()
        }

    }



    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        animDraw!!.start()
    }

}