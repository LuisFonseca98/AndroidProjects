package a45125.dam.universeexplorer

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class ShowPlanetSurface : AppCompatActivity() {
    private val index = 0
    private val extras: Bundle? = null
    lateinit var vv: VideoView
    private var videoPath: String? = null
    private var uri: Uri? = null
    private var mc: MediaController? = null
    private var tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_planet_surface)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vv = findViewById(R.id.surfaceVideo)
        tv = findViewById(R.id.surface)
        mc = MediaController(this)
        videoPath = "android.resource://" + packageName + "/" + R.raw.flyover
        uri = Uri.parse(videoPath)
        vv.setVideoURI(uri)
        vv.setMediaController(mc)
        mc!!.setAnchorView(vv)
        title = "Show Planet Surface"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}