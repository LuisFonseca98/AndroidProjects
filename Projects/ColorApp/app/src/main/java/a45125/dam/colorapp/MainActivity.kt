package a45125.dam.colorapp

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var layoutLeft: LinearLayout? = null
    var layoutRight: LinearLayout? = null
    var layoutBlue: LinearLayout? = null
    private lateinit var redtv: TextView
    private lateinit var greentv: TextView
    private lateinit var bluetv: TextView
    private lateinit var colortv: TextView
    var bcr = 100
    var bcg = 100
    var bcb = 100
    var dm = 0f
    private lateinit var repeatableTimerPlus:RepeatableTimer
    private lateinit var repeatableTimerMinus:RepeatableTimer
    private var isBlueMinusClicked : Boolean = false
    private var isBluePlusClicked : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var b = findViewById<Button>(R.id.plusR)
        layoutLeft = findViewById(R.id.UpperLayout)
        layoutRight = findViewById(R.id.LowerLayout)
        layoutBlue = findViewById(R.id.BlueLayout)

        dm = getScreenDensity(this)
        dm = getDmin(dm)
        landscapeAdjust(this)

        redtv = findViewById<TextView>(R.id.resR)
        greentv = findViewById<TextView>(R.id.resG)
        bluetv = findViewById<TextView>(R.id.resB)
        colortv = findViewById<TextView>(R.id.resultFinal)


        b.setOnClickListener { buttonPress(findViewById(R.id.plusR)) }
        b.setOnTouchListener { v, event ->
            buttonPress(findViewById(R.id.plusR))
            true
        }
        b = findViewById(R.id.minusR)
        b.setOnClickListener { buttonPress(findViewById(R.id.minusR)) }
        b.setOnTouchListener { v, event ->
            buttonPress(findViewById(R.id.minusR))
            true
        }

        //#####################################BUTOES GREEN#################################
        b = findViewById(R.id.plusG)
        b.setOnClickListener {
            buttonPress(findViewById(R.id.plusG))
            object : CountDownTimer(100, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    buttonPress(findViewById(R.id.plusG))
                }

                override fun onFinish() {}
            }.start()
        }
        b = findViewById(R.id.minusG)
        b.setOnClickListener {
            object : CountDownTimer(100, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    buttonPress(findViewById(R.id.minusG))
                }
                override fun onFinish() {}
            }.start()
        }
        //######################################################################

        //############################BUTTON BLUE####################################
        b.setOnClickListener {
            buttonPress(findViewById(R.id.minusB))
            repeatableTimerPlus = object : RepeatableTimer(300, 300) {
                override fun onTick() {
                    buttonPress(findViewById(R.id.minusB))
                }

                override fun onFinish() {
                    TODO("Not yet implemented")
                }
            }
            if (!isBlueMinusClicked && !isBluePlusClicked) {
                isBluePlusClicked = true
                repeatableTimerPlus.start()
            } else if (!isBlueMinusClicked && isBluePlusClicked) {
                isBluePlusClicked = false
                repeatableTimerPlus.cancel()
            } else if (isBlueMinusClicked && !isBluePlusClicked) {
                isBlueMinusClicked = false
                repeatableTimerMinus.cancel()
                isBluePlusClicked = true
                repeatableTimerPlus.start()
            }
        }

        b.setOnClickListener {
            buttonPress(findViewById(R.id.plusB))
            repeatableTimerMinus = object : RepeatableTimer(300, 300) {
                override fun onTick() {
                    buttonPress(findViewById(R.id.plusB))
                }

                override fun onFinish() {
                    TODO("Not yet implemented")
                }
            }

            if (!isBlueMinusClicked && !isBluePlusClicked) {
                isBlueMinusClicked = true
                repeatableTimerMinus.start()
            } else if (!isBlueMinusClicked && isBluePlusClicked) {
                isBluePlusClicked = false
                repeatableTimerPlus.cancel()
                isBlueMinusClicked = true
                repeatableTimerMinus.start()
            } else if (isBlueMinusClicked && !isBluePlusClicked) {
                isBlueMinusClicked = false
                repeatableTimerMinus.cancel()
            }
        }
        //######################################################################

        if (savedInstanceState != null) {
            bcr = savedInstanceState.getInt("red")
            bcg = savedInstanceState.getInt("green")
            bcb = savedInstanceState.getInt("blue")
        }
        else{
            bcr = 100
            bcg = 100
            bcb = 100
        }
        redtv.text = bcr.toString()
        redtv.setBackgroundColor(Color.rgb(bcr, 0, 0))
        greentv.text = bcg.toString()
        greentv.setBackgroundColor(Color.rgb(0, bcg, 0))
        bluetv.text = bcb.toString()
        bluetv.setBackgroundColor(Color.rgb(0, 0, bcb))
        colortv.text = "$bcr, $bcg, $bcb"
        colortv.setBackgroundColor(Color.rgb(bcr, bcg, bcb))
    }

    fun buttonPress(v: View) {

        when (v.id) {
            R.id.minusR -> {
                bcr = decrease(redtv)
                redtv.setBackgroundColor(Color.rgb(bcr, 0, 0))
            }
            R.id.plusR -> {
                bcr = increase(redtv)
                redtv.setBackgroundColor(Color.rgb(bcr, 0, 0))
            }
            R.id.minusG -> {
                bcg = decrease(greentv)
                greentv.setBackgroundColor(Color.rgb(0, bcg, 0))
            }
            R.id.plusG -> {
                bcg = increase(greentv)
                greentv.setBackgroundColor(Color.rgb(0, bcg, 0))
            }
            R.id.minusB -> {
                bcb = decrease(bluetv)
                bluetv.setBackgroundColor(Color.rgb(0, 0, bcb))
            }
            R.id.plusB -> {
                bcb = increase(bluetv)
                bluetv.setBackgroundColor(Color.rgb(0, 0, bcb))
            }
        }
        colortv.setBackgroundColor(Color.rgb(bcr, bcg, bcb))
        colortv.text = "$bcr, $bcg, $bcb"
    }

    fun increase(tv: TextView): Int {
        val old_text = tv.text.toString().toInt()
        val new_text = if (old_text < 255) old_text + 1 else old_text
        tv.text = new_text.toString() + ""
        return new_text
    }

    fun decrease(tv: TextView): Int {
        val old_text = tv.text.toString().toInt()
        val new_text = if (old_text > 1) old_text - 1 else old_text
        tv.text = new_text.toString() + ""
        return new_text
    }

    private fun getDmin(dm: Float): Float {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        return dm * height
    }

    private fun landscapeAdjust(a: Activity) {
        val heightNeeded = dm.toInt()
        if (getScreenOrientation(a) == Configuration.ORIENTATION_LANDSCAPE) {
            val textResult = findViewById<TextView>(R.id.resultFinal)
            if (layoutLeft!!.height < heightNeeded) {
                layoutLeft!!.removeView(layoutBlue)
                layoutRight!!.removeView(textResult)
                layoutRight!!.addView(layoutBlue)
                layoutRight!!.addView(textResult)
            }
        }
    }

    companion object {
        fun getScreenOrientation(ac: Activity): Int {
            return ac.resources.configuration.orientation
        }

        fun getScreenDensity(context: Context): Float {
            val dm = DisplayMetrics()
            val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
            Objects.requireNonNull(windowManager).defaultDisplay.getMetrics(dm)
            return dm.density
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt("red")
        savedInstanceState.getInt("green")
        savedInstanceState.getInt("blue")
    }


    override fun onSaveInstanceState(outState: Bundle) {

        outState.run {
            putInt("red", bcr)
            putInt("green", bcg)
            putInt("blue", bcb)
        }

        super.onSaveInstanceState(outState)
    }
}