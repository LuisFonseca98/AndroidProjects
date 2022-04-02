package a45125.dam.eventsapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    public enum class eventType { ONCREATE,
        ONDESTROY,
        ONSTART,
        ONSTOP,
        ONRESTART,
        ONRESUME,
        ONPAUSE,
        ONSAVEINSTANCE,
        ONRESTOREINSTANCE,
        SEPARATOR,
        ONSCREENON,
        ONSCREENOFF,
        BLT_FUNCTIONS}

    private lateinit var separatorButton : Button
    private lateinit var clearButton : Button
    private lateinit var verticalLayout : LinearLayout
    private lateinit var eventArray : ArrayList<String>
    private lateinit var typeEventArray : ArrayList<eventType>
    private lateinit var brScreenOnOff : ScreenActionReceiver
    private lateinit var bltScreen: BluetoothActionReceiver

    private var TAG: String = "Main Activity"

    private lateinit var bltDisc: Button
    private lateinit var bltANN: Button

    private lateinit var blt: BluetoothActionReceiver
    private val REQUEST_ENABLE_BLUETOOTH = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalLayout = findViewById(R.id.evt_layout)
        separatorButton = findViewById(R.id.sep_btn)
        clearButton = findViewById(R.id.clear_btn)
        bltANN = findViewById(R.id.blt_ann_btn)
        bltDisc = findViewById(R.id.blt_disc)

        if (savedInstanceState != null) {
            eventArray = savedInstanceState.getStringArrayList("eventArray") as ArrayList<String>
            typeEventArray = savedInstanceState.get("typeEventArray") as ArrayList<eventType>

            for(idx in 0 until eventArray.size){
                addTextToUI(eventArray[idx], chooseColor(typeEventArray[idx]))
            }
        }else{
            eventArray = ArrayList()
            typeEventArray = ArrayList()
        }

        brScreenOnOff = ScreenActionReceiver(verticalLayout, this, eventArray, typeEventArray, resources) // create
        registerReceiver(brScreenOnOff, brScreenOnOff.intentFilter)



        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Log.d(TAG, "Device does not support BLT")
        }
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        if(bluetoothAdapter.isDiscovering){
            bluetoothAdapter.cancelDiscovery()
        }

        bltScreen = BluetoothActionReceiver(verticalLayout, this, eventArray, typeEventArray, resources) // create
        registerReceiver(bltScreen, bltScreen.intentFilterBLT)

        separatorButton.setOnClickListener{
            log("- - - - - - - - - ", eventType.SEPARATOR)
        }

        clearButton.setOnClickListener {
            verticalLayout?.removeAllViews()
            eventArray.clear()
            typeEventArray.clear()
        }

        bltANN.setOnClickListener {
            val discIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60)
            startActivity(discIntent)
        }

        bltDisc.setOnClickListener {
            bluetoothAdapter.startDiscovery()
        }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            log(object {}.javaClass.enclosingMethod!!.name + " - Landscape", eventType.ONCREATE)
        }

        else {
            log(object {}.javaClass.enclosingMethod!!.name + " - Portrait", eventType.ONCREATE)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(brScreenOnOff)
        unregisterReceiver(bltScreen)
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONDESTROY)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONSTART)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONSTOP)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONRESTART)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONRESUME)
        log("- - - - - - - - - ", eventType.SEPARATOR)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONPAUSE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("eventArray", eventArray)
        outState.putSerializable("typeEventArray", typeEventArray)
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONSAVEINSTANCE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getStringArrayList("eventArray")
        savedInstanceState.get("typeEventArray")
        log(object {}.javaClass.enclosingMethod!!.name, eventType.ONRESTOREINSTANCE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun log(message: String, typeEvent: eventType){
        val fullMessage = message + " - " + getTime()
        //Log.d("EVENTO", fullMessage)
        eventArray!!.add(fullMessage)
        typeEventArray!!.add(typeEvent)
        addTextToUI(fullMessage, chooseColor(typeEvent))
    }

    private fun addTextToUI(message: String, colorId: Int) {
        //Criação do Texto com a informação do evento
        val t = TextView(this)
        t.text = message

        //Criação de parâmetros para aplicar ao texto do evento
        val layoutParams3 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams3.setMargins(convertToDp(10), convertToDp(5), convertToDp(10), convertToDp(5))

        t.textSize = 18f
        t.gravity = Gravity.CENTER_VERTICAL
        t.setBackgroundColor(resources.getColor(colorId))
        t.layoutParams = layoutParams3
        t.setTextColor(resources.getColor(R.color.black))

        //Aplicar o texto ao linear layout
        verticalLayout?.addView(t)

    }

    private fun convertToDp(sizeInDp: Int) : Int{
        val scale = resources.displayMetrics.density
        val dpAsPixels = (sizeInDp * scale + 0.5f).toInt()
        return dpAsPixels
    }

    private fun getTime() : String{
        val date = SimpleDateFormat("HH:mm:ss.SSS")
        return date.format(Date())
    }

    private fun chooseColor(typeEvent: eventType) : Int{
        var color = 0
        when (typeEvent) {
            eventType.ONCREATE -> {
                color = R.color.greenTv
            }
            eventType.ONDESTROY -> {
                color = R.color.greenTv
            }
            eventType.ONSTART -> {
                color = R.color.yellowTv
            }
            eventType.ONSTOP -> {
                color = R.color.greenTv
            }
            eventType.ONRESTART -> {
                color = R.color.greenTv
            }
            eventType.ONRESUME -> {
                color = R.color.yellowTv
            }
            eventType.ONPAUSE -> {
                color = R.color.greenTv
            }
            eventType.ONSAVEINSTANCE -> {
                color = R.color.orangeTV
            }
            eventType.ONRESTOREINSTANCE -> {
                color = R.color.orangeTV
            }
            eventType.SEPARATOR -> {
                color = R.color.gray
            }
            eventType.ONSCREENON -> {
                color = R.color.orangeTV
            }
            eventType.ONSCREENOFF -> {
                color = R.color.orangeTV
            }
            eventType.BLT_FUNCTIONS-> {
                color = R.color.BlueBLT
            }
        }
        return color
    }
}