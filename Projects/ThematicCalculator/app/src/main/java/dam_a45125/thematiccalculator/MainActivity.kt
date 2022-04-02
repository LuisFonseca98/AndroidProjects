package dam_a45125.thematiccalculator

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var slbIcon: ImageButton? = null
    private var scpIcon: ImageButton? = null
    private var portoIcon: ImageButton? = null
    private var bSoma: Button? = null
    private var bSub: Button? = null
    private var bMult: Button? = null
    private var bDiv: Button? = null
    private var bCosseno: Button? = null
    private var bSeno: Button? = null
    private var bMod: Button? = null
    private var bTangente: Button? = null
    private var setButton: Button? = null
    private var getButton: Button? = null
    private var calc: Calculator? = null
    private var animDraw: AnimationDrawable? = null
    private var animDraw2: AnimationDrawable? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        slbIcon = findViewById<View>(R.id.imageButtonSLB) as ImageButton
        scpIcon = findViewById<View>(R.id.imageButtonSCP) as ImageButton
        portoIcon = findViewById<View>(R.id.imageButtonPorto) as ImageButton
        bSoma = findViewById<View>(R.id.buttonSoma) as Button
        bSub = findViewById<View>(R.id.buttonSubtracao) as Button
        bMult = findViewById<View>(R.id.buttonMult) as Button
        bDiv = findViewById<View>(R.id.buttonDiv) as Button
        bCosseno = findViewById<View>(R.id.buttonCos) as Button
        bSeno = findViewById<View>(R.id.buttonSeno) as Button
        bTangente = findViewById<View>(R.id.buttonTangente) as Button
        bMod = findViewById<View>(R.id.buttonModule) as Button
        setButton = findViewById<View>(R.id.buttonGetOperador1) as Button
        getButton = findViewById<View>(R.id.buttonSetOperador1) as Button
        calc = Calculator()
        val imageView = findViewById<View>(R.id.imageAnim) as ImageView
        imageView.setBackgroundResource(R.drawable.animation)
        animDraw = imageView.background as AnimationDrawable
        val imageView2 = findViewById<View>(R.id.imageAnim2) as ImageView
        imageView2.setBackgroundResource(R.drawable.animations_network)
        animDraw2 = imageView2.background as AnimationDrawable
        slbIcon!!.setOnTouchListener { arg0, arg1 ->
            setContext(R.id.imageButtonSLB)
            false
        }
        scpIcon!!.setOnTouchListener { v, event ->
            setContext(R.id.imageButtonSCP)
            false
        }
        portoIcon!!.setOnTouchListener { v, event ->
            setContext(R.id.imageButtonPorto)
            false
        }
        bSoma!!.setOnClickListener(this)
        bSub!!.setOnClickListener(this)
        bMult!!.setOnClickListener(this)
        bDiv!!.setOnClickListener(this)
        bCosseno!!.setOnClickListener(this)
        bSeno!!.setOnClickListener(this)
        bTangente!!.setOnClickListener(this)
        bMod!!.setOnClickListener(this)
        setButton!!.setOnClickListener(this)
        getButton!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var calc = Calculator()
        var number1EditText = findViewById<TextView>(R.id.editText)
        var number2EditText = findViewById<TextView>(R.id.editText2)
        var number3EditText = findViewById<TextView>(R.id.textResult)
        var sNum1 = number1EditText.text.toString()
        var sNum2 = number2EditText.text.toString()
        var num1 = getDouble(sNum1)
        var num2 = getDouble(sNum2)
        var b = v as Button
        var value = 0.0

        if (b.id == R.id.buttonSoma) {
            value = calc.sum(num1, num2)
        } else if (b.id == R.id.buttonSubtracao) {
            value = calc.sub(num1, num2)
        } else if (b.id == R.id.buttonMult) {
            value = calc.mult(num1, num2)
        } else if (b.id == R.id.buttonDiv) {
            value = calc.div(num1, num2)
        } else if (b.id == R.id.buttonSeno) {
            value = calc.seno(num1)
        } else if (b.id == R.id.buttonCos) {
            value = calc.cosseno(num1)
        } else if (b.id == R.id.buttonCos) {
            value = calc.tangente(num1)
        } else if (b.id == R.id.buttonModule) {
            value = calc.module(num1, num2)
        } else if (b.id == R.id.buttonSetOperador1) {
            value = num1.toDouble()
            println("Yay apliquei o $num1 ao resultado final")
        } else if (b.id == R.id.buttonGetOperador1) {
            getDouble(num1.toString())
            println("Yay fui buscar o valor $num1 ao primeiro operador" )
        }
        number3EditText.text = value.toString()
    }

    private fun getDouble(num: String): Double {
        return num.toDouble()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        animDraw!!.start()
        animDraw2!!.start()
    }

    private fun setContext(id: Int) {
        var title: TextView
        var fc = 0
        var bc = 0
        var textColor = 0
        val container = findViewById<View>(R.id.container)
        if (id == R.id.imageButtonPorto) {
            fc = R.color.colorBlue
            bc = R.color.colorBlue
            textColor = R.color.colorWhite
            container.setBackgroundResource(R.drawable.estadioporto)
            title = findViewById<View>(R.id.title) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.textViewOperand1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.textViewOperand3) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.textResult) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.editText) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, textColor))
            title.setBackgroundColor(ContextCompat.getColor(baseContext, bc))
            title = findViewById<View>(R.id.editText2) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, textColor))
            title.setBackgroundColor(ContextCompat.getColor(baseContext, bc))
            title = findViewById<View>(R.id.buttonSoma) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSubtracao) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonMult) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonDiv) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonCos) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSeno) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonTangente) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonModule) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonGetOperador1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSetOperador1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
        } else if (id == R.id.imageButtonSLB) {
            fc = R.color.colorRed
            bc = R.color.colorRed
            textColor = R.color.colorWhite
            container.setBackgroundResource(R.drawable.estadioluz)
            title = findViewById(R.id.title)
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.textViewOperand1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.editText) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, textColor))
            title.setBackgroundColor(ContextCompat.getColor(baseContext, bc))
            title = findViewById<View>(R.id.textViewOperand3) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.editText2) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, textColor))
            title.setBackgroundColor(ContextCompat.getColor(baseContext, bc))
            title = findViewById<View>(R.id.textResult) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSoma) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSubtracao) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonMult) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonDiv) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonCos) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSeno) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonTangente) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonModule) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonGetOperador1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSetOperador1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
        } else if (id == R.id.imageButtonSCP) {
            fc = R.color.colorGreen
            bc = R.color.colorGreen
            textColor = R.color.colorWhite
            container.setBackgroundResource(R.drawable.estadioscp)
            title = findViewById<View>(R.id.title) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.editText) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, textColor))
            title.setBackgroundColor(ContextCompat.getColor(baseContext, bc))
            title = findViewById<View>(R.id.textViewOperand1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.editText2) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, textColor))
            title.setBackgroundColor(ContextCompat.getColor(baseContext, bc))
            title = findViewById<View>(R.id.textViewOperand3) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.textResult) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSoma) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSubtracao) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonMult) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonDiv) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonCos) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSeno) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonTangente) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonModule) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonGetOperador1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
            title = findViewById<View>(R.id.buttonSetOperador1) as TextView
            title.setTextColor(ContextCompat.getColor(baseContext, fc))
        }
    }
}