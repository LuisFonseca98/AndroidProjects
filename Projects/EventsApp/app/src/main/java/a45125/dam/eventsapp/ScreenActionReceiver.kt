package a45125.dam.eventsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class ScreenActionReceiver(private val evtLayout: LinearLayout,
                           private val ctx: Context,
                           private val eventArray: ArrayList<String>,
                           private val typeEventArray: ArrayList<MainActivity.eventType>,
                           private val resources: Resources) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_SCREEN_ON == action) {
            log("SCREEN TURNED ON",MainActivity.eventType.ONSCREENON)
        } else if (Intent.ACTION_SCREEN_OFF == action) {
            log("SCREEN TURNED OFF",MainActivity.eventType.ONSCREENOFF)
        }
    }

    val intentFilter: IntentFilter
        get() {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            filter.addAction(Intent.ACTION_SCREEN_ON)
            return filter
        }

    fun displaySR(s: String) {

        val t = TextView(ctx)
        t.text = s + " - "+ getTime()

        //Criação de parâmetros para aplicar ao texto do evento
        val layoutParams3 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams3.setMargins(convertToDp(10), convertToDp(5), convertToDp(10), convertToDp(5))

        t.textSize = 18f
        t.gravity = Gravity.CENTER_VERTICAL
        t.setBackgroundColor(resources.getColor(R.color.orangeTV))
        t.layoutParams = layoutParams3
        t.setTextColor(resources.getColor(R.color.black))

        //Aplicar o texto ao linear layout
        evtLayout.addView(t)
    }

    fun log(message: String, typeEvent: MainActivity.eventType){
        val fullMessage = message + " - " + getTime()
        //Log.d("EVENTO", fullMessage)
        eventArray!!.add(fullMessage)
        typeEventArray!!.add(typeEvent)
        displaySR(fullMessage)
    }

    private fun getTime() : String{
        val date = SimpleDateFormat("HH:mm:ss.SSS")
        return date.format(Date())
    }


    private fun convertToDp(sizeInDp: Int) : Int{
        val scale = resources.displayMetrics.density
        val dpAsPixels = (sizeInDp * scale + 0.5f).toInt()
        return dpAsPixels
    }
}