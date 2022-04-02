package a45125.dam.universeexplorer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AttackPlanet : AppCompatActivity() {
    lateinit var bombImage: ImageButton
    lateinit var invadeImage: ImageButton
    lateinit var infectImage: ImageButton
    lateinit var laserImage: ImageButton
    lateinit var exitImage: ImageButton
    lateinit var tv: TextView
    lateinit var iv: ImageView
    private var extras: Bundle? = null
    private var index = 0
    private var infectAnimation: AnimationDrawable? = null
    private var bombAnimation: Animation? = null
    private var laserAnimation: Animation? = null
    private var bombState = false
    private var invadeState = false
    private var infectedState = false
    private var laserState = false
    private val virusState = false
    private var timer: TimerBroadcastReceiver? = null
    private var sp: SoundPool? = null
    private var sound1 = 0
    private var sound2 = 0
    private var sound3 = 0
    private var sound4 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attack_planet)
        startService(Intent(this, MusicService::class.java))
        bombImage = findViewById(R.id.bombPic)
        invadeImage = findViewById(R.id.invasionPic)
        laserImage = findViewById(R.id.laserPic)
        exitImage = findViewById(R.id.exitPic)
        tv = findViewById(R.id.planetName)
        iv = findViewById(R.id.planetPic)
        bombAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation)
        bombImage.startAnimation(bombAnimation)
        infectImage = findViewById(R.id.infectionPic)
        infectImage.setBackgroundResource(R.drawable.infect_anim)
        infectAnimation = infectImage.getBackground() as AnimationDrawable
        infectAnimation!!.start()
        laserAnimation = AnimationUtils.loadAnimation(this, R.anim.translation)
        /*timer = object : TimerBroadcastReceiver(this) {
            override fun onTick() {
                laserImage.clearAnimation()
            }
        }*/
        extras = intent.extras
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setPlanetPicture()
        sp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                .setMaxStreams(10)
                .build()
        } else {
            SoundPool(10, AudioManager.STREAM_MUSIC, 1)
        }
        sound1 = sp!!.load(this, R.raw.explosion, 1)
        sound2 = sp!!.load(this, R.raw.transport, 2)
        sound3 = sp!!.load(this, R.raw.virus, 3)
        sound4 = sp!!.load(this, R.raw.laser, 4)
        bombImage.setOnClickListener(View.OnClickListener {
            if (!bombState) {
                bombImage.setPressed(true)
                bombState = true
                bombImage.setPressed(false)
                bombImage.setActivated(true)
                bombImage.clearAnimation()
                sp!!.play(sound1, 1f, 1f, 0, 0, 1f)
            } else if (bombState) {
                bombImage.setActivated(false)
                bombImage.setPressed(true)
                bombState = false
                bombImage.setPressed(false)
                bombImage.startAnimation(bombAnimation)
            }
        })
        invadeImage.setOnClickListener(View.OnClickListener {
            if (!invadeState) {
                invadeImage.setPressed(true)
                invadeState = true
                invadeImage.setPressed(false)
                invadeImage.setActivated(true)
                sp!!.play(sound2, 1f, 1f, 0, 0, 1f)
            } else if (invadeState) {
                invadeImage.setActivated(false)
                invadeImage.setPressed(true)
                invadeState = false
                invadeImage.setPressed(false)
            }
        })
        infectImage.setOnClickListener(View.OnClickListener {
            if (!infectedState) {
                infectAnimation!!.stop()
                infectImage.setPressed(true)
                infectedState = true
                infectImage.setPressed(false)
                infectImage.setActivated(true)
                sp!!.play(sound3, 1f, 1f, 0, 0, 1f)
            } else if (infectedState) {
                infectAnimation!!.start()
                infectImage.setActivated(false)
                infectImage.setPressed(true)
                infectedState = false
                infectImage.setPressed(false)
            }
        })
        laserImage.setOnClickListener(View.OnClickListener {
            if (!laserState) {
                laserImage.startAnimation(laserAnimation)
                timer?.start(6)
                laserImage.setPressed(true)
                laserState = true
                laserImage.setPressed(false)
                laserImage.setActivated(true)
                sp!!.play(sound4, 1f, 1f, 0, 0, 1f)
            } else if (laserState) {
                laserImage.clearAnimation()
                timer?.cancel()
                laserImage.setActivated(false)
                laserImage.setPressed(true)
                laserState = false
                laserImage.setPressed(false)
            }
        })
        exitImage.setOnClickListener(View.OnClickListener {
            stopService(Intent(this@AttackPlanet, MusicService::class.java))
            finish()
        })
    }

    @SuppressLint("ResourceType")
    private fun setPlanetPicture() {
        val arrayPlanets = resources.obtainTypedArray(R.array.planets)
        index = extras!!.getInt("idx")
        val idPlanet = arrayPlanets.getResourceId(index, -1)
        val planet = resources.obtainTypedArray(idPlanet)
        tv!!.text = planet.getString(0)
        iv!!.setImageResource(planet.getResourceId(4, 0))
        title = "Attack Planet" + tv!!.text.toString()
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                stopService(Intent(this@AttackPlanet, MusicService::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}