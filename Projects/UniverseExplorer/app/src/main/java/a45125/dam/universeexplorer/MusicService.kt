package a45125.dam.universeexplorer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class MusicService : Service() {
    lateinit var musicPlayer: MediaPlayer
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        Toast.makeText(this, "Music Service Created", Toast.LENGTH_SHORT).show()
        musicPlayer = MediaPlayer.create(this, R.raw.music)
        musicPlayer.setLooping(true)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startid: Int): Int {
        Toast.makeText(this, "Music Service Started", Toast.LENGTH_SHORT).show()
        musicPlayer!!.start()
        return super.onStartCommand(intent, flags, startid)
    }

    override fun onDestroy() {
        Toast.makeText(this, "Music Service Stopped", Toast.LENGTH_SHORT).show()
        musicPlayer!!.stop()
    }
}