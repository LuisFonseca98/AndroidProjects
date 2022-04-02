package a45125.dam.colorapp

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message


abstract class RepeatableTimer (initialDelay:Long,interTime:Long) {
    internal var intervalo:Long=0
    internal var delay:Long=0
    internal var cancelado:Boolean=false
    internal var timeStamp:Long=0
    val MSG :Int=1

    init {
        intervalo=interTime
        delay=initialDelay

    }

    fun start(): RepeatableTimer {
            cancelado=false
            timeStamp=System.currentTimeMillis()

            mHandler.sendMessage(mHandler.obtainMessage(MSG))
            return this

    }

    fun cancel(){
        cancelado=true
        mHandler.removeMessages(MSG)

    }
    abstract fun onTick()
    abstract fun onFinish()


    private val mHandler= @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            if(cancelado){
                return
            }else{
                if(System.currentTimeMillis()>timeStamp+delay){
                    onTick()
                }


                sendEmptyMessageDelayed(MSG, intervalo)

            }

        }
    }




}


