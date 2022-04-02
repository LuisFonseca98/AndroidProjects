package a45125.dam.eventsapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

public class BluetoothActionReceiver( private val evtLayout: LinearLayout,
                               private val ctx: Context,
                               private val eventArray: ArrayList<String>,
                               private val typeEventArray: ArrayList<MainActivity.eventType>,
                               private val resources: Resources): BroadcastReceiver() {


    //private val listDeviceNames: ArrayList<String> = ArrayList()
    //var scanIntentFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
    //private var discoverableIntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    //private var bluetoothAdapter: BluetoothAdapter? = null

    // BluetoothAdapter.ACTION SCAN MODE CHANGED, fired when discoverable state change
    // BluetoothAdapter.ACTION DISCOVERY STARTED, fired when start discovery;
    // BluetoothAdapter.ACTION DISCOVERY FINISHED, fired when finish discovery
    // BluetoothDevice.ACTION FOUND, fired when a device is discovered

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        if (BluetoothDevice.ACTION_FOUND == action) {
            val dvc = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            val deviceName = dvc!!.name
            log("BLT DISCOVERY_STARTED", MainActivity.eventType.BLT_FUNCTIONS)
            log("BLT FOUND: $deviceName", MainActivity.eventType.BLT_FUNCTIONS)
            log("BLT DISCOVERY_FINISHED", MainActivity.eventType.BLT_FUNCTIONS)
        }

        if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED == action) {
            val smode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR)
            when (smode) {
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {
                    Toast.makeText(context, "The device is in discoverable mode but can still receive connections", Toast.LENGTH_SHORT).show()
                    log("BLT SCAN_MODE_CHANGED \n SCAN_MODE_CONNECTABLE_DISCOVERABLE",MainActivity.eventType.BLT_FUNCTIONS)
                }
                BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {
                    Toast.makeText(context, "The device is not in discoverable mode but can still receive connections", Toast.LENGTH_SHORT).show()
                    log("BLT SCAN_MODE_CHANGED \n SCAN_MODE_CONNECTABLE",MainActivity.eventType.BLT_FUNCTIONS)
                }
                BluetoothAdapter.SCAN_MODE_NONE -> {
                    Toast.makeText(context, "The device is not in discoverable mode and can not receive connections", Toast.LENGTH_SHORT).show()
                    log("BLT SCAN_MODE_CHANGED \n SCAN_MODE_NONE",MainActivity.eventType.BLT_FUNCTIONS)
                }
                else -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun displayBLT(message: String) {
        //Criação do Texto com a informação do evento
        val t = TextView(ctx)
        t.text = message + getTime()

        //Criação de parâmetros para aplicar ao texto do evento
        val layoutParams3 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams3.setMargins(convertToDp(10), convertToDp(5), convertToDp(10), convertToDp(5))

        t.textSize = 18f
        t.gravity = Gravity.CENTER_VERTICAL
        t.setBackgroundColor(resources.getColor(R.color.BlueBLT))
        t.layoutParams = layoutParams3
        t.setTextColor(resources.getColor(R.color.black))

        //Aplicar o texto ao linear layout
        evtLayout?.addView(t)

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

    fun log(message: String, typeEvent: MainActivity.eventType){
        val fullMessage = message + " - " + getTime()
        //Log.d("EVENTO", fullMessage)
        eventArray!!.add(fullMessage)
        typeEventArray!!.add(typeEvent)
        displayBLT(fullMessage)
    }


    val intentFilterBLT: IntentFilter
        get() {
            val filter = IntentFilter()
            filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
            filter.addAction(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE.toString())
            filter.addAction(BluetoothAdapter.SCAN_MODE_CONNECTABLE.toString())
            filter.addAction(BluetoothAdapter.SCAN_MODE_NONE.toString())
            //filter.addAction(BluetoothDevice.ACTION_FOUND)
            return filter
        }
}